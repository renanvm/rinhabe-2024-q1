package br.com.renan.rinhabe2024q1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record TransacaoRequest(@Min(0) int valor,
                               @NotNull @Pattern(regexp = "c|d") String tipo,
                               @NotBlank @Length(min = 1, max = 10) String descricao) {
}

