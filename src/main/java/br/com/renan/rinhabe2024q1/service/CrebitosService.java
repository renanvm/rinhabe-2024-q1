package br.com.renan.rinhabe2024q1.service;

import br.com.renan.rinhabe2024q1.dto.ExtratoResponse;
import br.com.renan.rinhabe2024q1.dto.SaldoResponse;
import br.com.renan.rinhabe2024q1.dto.TransacaoRequest;
import br.com.renan.rinhabe2024q1.dto.TransacaoResponse;
import br.com.renan.rinhabe2024q1.entity.Cliente;
import br.com.renan.rinhabe2024q1.entity.Transacao;
import br.com.renan.rinhabe2024q1.repository.ClienteRepository;
import br.com.renan.rinhabe2024q1.repository.TransacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CrebitosService {

    private ClienteRepository clienteRepository;
    private TransacaoRepository transacaoRepository;

    public CrebitosService(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public TransacaoResponse transacao(int clienteId, TransacaoRequest transacao) {
        validarClienteId(clienteId);

        Cliente cliente = clienteRepository.findByClienteId(clienteId);

        if ("d".equals(transacao.tipo())) {
            return processarTransacaoDebito(cliente, transacao);
        }
        return processarTransacaoCredito(cliente, transacao);
    }

    @Transactional
    public ExtratoResponse getExtrato(int clienteId) {
        validarClienteId(clienteId);

        Cliente cliente = clienteRepository.findByClienteId(clienteId);

        List<Transacao> transacoes = transacaoRepository.findRecentsByClienteId(clienteId);

        return new ExtratoResponse(new SaldoResponse(cliente.getSaldo(), LocalDateTime.now(), cliente.getLimite()), transacoes);
    }

    private TransacaoResponse processarTransacaoDebito(Cliente cliente, TransacaoRequest transacao) {
        validarSaldo(cliente.getSaldo(), transacao.valor(), cliente.getLimite());
        clienteRepository.updateSaldo(-transacao.valor(), cliente.getId());
        salvarTransacao(cliente.getId(), transacao.valor(), transacao.tipo(), transacao.descricao());
        return new TransacaoResponse(cliente.getLimite(), cliente.getSaldo() - transacao.valor());
    }

    private TransacaoResponse processarTransacaoCredito(Cliente cliente, TransacaoRequest transacao) {
        clienteRepository.updateSaldo(transacao.valor(), cliente.getId());
        salvarTransacao(cliente.getId(), transacao.valor(), transacao.tipo(), transacao.descricao());
        return new TransacaoResponse(cliente.getLimite(), cliente.getSaldo() + transacao.valor());
    }

    private void salvarTransacao(int clienteId, int valor, String tipo, String descricao) {
        transacaoRepository.saveTransacao(clienteId, valor, tipo, descricao, LocalDateTime.now());
    }

    private void validarSaldo(int saldoAtual, int valorTransacao, int limite) {
        boolean isSaldoIncosistente = valorTransacao > (saldoAtual + limite);
        if (isSaldoIncosistente) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void validarClienteId(int clienteId) {
        if (clienteId < 1 || clienteId > 5) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
