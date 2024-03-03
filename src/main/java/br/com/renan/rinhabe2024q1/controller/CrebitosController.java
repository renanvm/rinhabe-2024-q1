package br.com.renan.rinhabe2024q1.controller;

import br.com.renan.rinhabe2024q1.dto.ExtratoResponse;
import br.com.renan.rinhabe2024q1.dto.TransacaoRequest;
import br.com.renan.rinhabe2024q1.dto.TransacaoResponse;
import br.com.renan.rinhabe2024q1.service.TransacaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{id}")
public class CrebitosController {

    private TransacaoService transacaoService;

    public CrebitosController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/transacoes")
    public TransacaoResponse transacoes(@PathVariable int id, @RequestBody TransacaoRequest transacaoRequest) {
        return transacaoService.transacao(id, transacaoRequest);
    }

    @GetMapping("/extrato")
    public ExtratoResponse extrato(@PathVariable int id) {
        return transacaoService.getTransacoes(id);
    }
}
