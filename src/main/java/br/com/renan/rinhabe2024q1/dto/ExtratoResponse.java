package br.com.renan.rinhabe2024q1.dto;

import br.com.renan.rinhabe2024q1.entity.Transacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExtratoResponse(SaldoResponse saldo, @JsonProperty("ultimas_transacoes") List<Transacao> ultimasTransacoes) {
}
