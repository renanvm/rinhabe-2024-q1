package br.com.renan.rinhabe2024q1.service;

import br.com.renan.rinhabe2024q1.dto.ExtratoResponse;
import br.com.renan.rinhabe2024q1.dto.SaldoResponse;
import br.com.renan.rinhabe2024q1.dto.TransacaoRequest;
import br.com.renan.rinhabe2024q1.dto.TransacaoResponse;
import br.com.renan.rinhabe2024q1.entity.Conta;
import br.com.renan.rinhabe2024q1.entity.Transacao;
import br.com.renan.rinhabe2024q1.repository.ContaRepository;
import br.com.renan.rinhabe2024q1.repository.TransacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    private ContaRepository contaRepository;
    private TransacaoRepository transacaoRepository;

    public TransacaoService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public TransacaoResponse transacao(int contaId, TransacaoRequest transacao) {
        validarContaId(contaId);

        Conta conta = contaRepository.findByContaId(contaId);

        if ("d".equals(transacao.tipo())) {
            return processarTransacaoDebito(conta, transacao);
        }
        return processarTransacaoCredito(conta, transacao);
    }

    @Transactional
    public ExtratoResponse getTransacoes(int contaId) {
        validarContaId(contaId);

        Conta conta = contaRepository.findByContaId(contaId);

        List<Transacao> transacoes = transacaoRepository.findLast10ByContaId(contaId);

        return new ExtratoResponse(new SaldoResponse(conta.getSaldo(), LocalDateTime.now(), conta.getLimite()), transacoes);
    }

    private TransacaoResponse processarTransacaoDebito(Conta conta, TransacaoRequest transacao) {
        validarLimite(conta.getSaldo(), transacao.valor(), conta.getLimite());
        contaRepository.updateSaldo(-transacao.valor(), conta.getId());
        salvarTransacao(conta.getId(), transacao.valor(), transacao.tipo(), transacao.descricao());
        return new TransacaoResponse(conta.getLimite(), conta.getSaldo() - transacao.valor());
    }

    private TransacaoResponse processarTransacaoCredito(Conta conta, TransacaoRequest transacao) {
        contaRepository.updateSaldo(transacao.valor(), conta.getId());
        salvarTransacao(conta.getId(), transacao.valor(), transacao.tipo(), transacao.descricao());
        return new TransacaoResponse(conta.getLimite(), conta.getSaldo() + transacao.valor());
    }

    private void salvarTransacao(int contaId, int valor, String tipo, String descricao) {
        transacaoRepository.saveTransacao(contaId, valor, tipo, descricao, LocalDateTime.now());
    }

    private void validarLimite(int saldoAtual, int valorTransacao, int limite) {
        int novoSaldo = saldoAtual - valorTransacao;
        boolean isLimiteExcedido = novoSaldo < limite * -1;
        if (isLimiteExcedido) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void validarContaId(int contaId) {
        if (contaId < 1 || contaId > 5) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
