package br.com.renan.rinhabe2024q1.service;

import br.com.renan.rinhabe2024q1.dto.ExtratoResponse;
import br.com.renan.rinhabe2024q1.dto.SaldoResponse;
import br.com.renan.rinhabe2024q1.dto.TransacaoRequest;
import br.com.renan.rinhabe2024q1.dto.TransacaoResponse;
import br.com.renan.rinhabe2024q1.entity.Conta;
import br.com.renan.rinhabe2024q1.entity.Transacao;
import br.com.renan.rinhabe2024q1.repository.ContaRepository;
import br.com.renan.rinhabe2024q1.repository.TransacaoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TransacaoService {

    private ContaRepository contaRepository;
    private TransacaoRepository transacaoRepository;

    public TransacaoService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public TransacaoResponse transacao(int clienteId, TransacaoRequest transacao) {
        Conta conta = contaRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if ("d".equals(transacao.tipo())) {
            return processarTransacaoDebito(conta, transacao);
        }
        return processarTransacaoCredito(conta, transacao);
    }

    @Cacheable("transacoes")
    public ExtratoResponse getTransacoes(int clienteId) {
        Conta conta = contaRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Transacao> transacoes = transacaoRepository.findAll();

        int total = transacoes.stream()
                .mapToInt(Transacao::getValor)
                .sum();

        List<Transacao> collect = transacoes.stream()
                .sorted(Comparator.comparing(Transacao::getRealizadaEm))
                .limit(10)
                .toList();

        return new ExtratoResponse(new SaldoResponse(total, LocalDateTime.now(), conta.getLimite()), collect);
    }

    private TransacaoResponse processarTransacaoDebito(Conta conta, TransacaoRequest transacao) {
        boolean transacaoValidada = transacaoValidate(conta.getSaldo(), transacao.valor(), conta.getLimite());
        if (transacaoValidada) {
            int novoSaldo = conta.getSaldo() - transacao.valor();
            conta.setSaldo(novoSaldo);
            salvarTransacao(conta.getId(), transacao.valor(), transacao.tipo(), transacao.descricao());
            contaRepository.save(conta);
            return new TransacaoResponse(conta.getLimite(), novoSaldo);
        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private TransacaoResponse processarTransacaoCredito(Conta conta, TransacaoRequest transacao) {
        int novoSaldo = conta.getSaldo() + transacao.valor();
        conta.setSaldo(novoSaldo);
        salvarTransacao(conta.getId(), transacao.valor(), transacao.tipo(), transacao.descricao());
        contaRepository.save(conta);
        return new TransacaoResponse(conta.getLimite(), novoSaldo);
    }

    private void salvarTransacao(int clienteId, int valor, String tipo, String descricao) {
        transacaoRepository.save(new Transacao(clienteId, valor, tipo, descricao, LocalDateTime.now()));
    }

    private boolean transacaoValidate(int saldoAtual, int valorTransacao, int limite) {
        int novoSaldo = saldoAtual - valorTransacao;
        return novoSaldo >= (-limite);
    }
}
