package br.com.renan.rinhabe2024q1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer contaId;
    private int valor;
    private String tipo;
    private String descricao;
    @JsonProperty("realizada_em")
    private LocalDateTime realizadaEm;

    public Transacao(Integer contaId, int valor, String tipo, String descricao, LocalDateTime realizadaEm) {
        this.contaId = contaId;
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = realizadaEm;
    }

    public Transacao() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getContaId() {
        return contaId;
    }

    public int getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getRealizadaEm() {
        return realizadaEm;
    }
}
