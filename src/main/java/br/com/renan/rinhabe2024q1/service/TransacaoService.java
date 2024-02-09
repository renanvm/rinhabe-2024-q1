package br.com.renan.rinhabe2024q1.service;

import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private static final int LIMITE = 1000;

    public boolean podeDebitar(int saldoAtual, int valorTransacao) {
        int novoSaldo = saldoAtual - valorTransacao;
        return novoSaldo >= (-LIMITE);
    }
}
