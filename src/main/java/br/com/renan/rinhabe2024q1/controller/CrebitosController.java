package br.com.renan.rinhabe2024q1.controller;

import br.com.renan.rinhabe2024q1.dto.ExtratoResponse;
import br.com.renan.rinhabe2024q1.dto.TransacaoRequest;
import br.com.renan.rinhabe2024q1.dto.TransacaoResponse;
import br.com.renan.rinhabe2024q1.service.CrebitosService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{id}")
public class CrebitosController {

    private CrebitosService crebitosService;

    public CrebitosController(CrebitosService crebitosService) {
        this.crebitosService = crebitosService;
    }

    @PostMapping("/transacoes")
    public TransacaoResponse transacoes(@PathVariable int id, @RequestBody @Valid TransacaoRequest transacaoRequest) {
        return crebitosService.transacao(id, transacaoRequest);
    }

    @GetMapping("/extrato")
    public ExtratoResponse extrato(@PathVariable int id) {
        return crebitosService.getExtrato(id);
    }
}
