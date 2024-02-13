package br.com.renan.rinhabe2024q1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record SaldoResponse(int total, @JsonProperty("data_extrato") LocalDateTime dataExtrato, int limite) {
}
