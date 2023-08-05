package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public Page<PagamentoDto> listar (@PageableDefault(size = 10) Pageable pageable){
        return service.obterTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> listarPorId(@PathVariable @NotNull Long id){
        PagamentoDto pagamento = service.obterPorId(id);

        return ResponseEntity.ok(pagamento);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> criarPagamento(@RequestBody @Valid PagamentoDto pagamentoDto, UriComponentsBuilder uriBuilder){
        PagamentoDto pagamento = service.criarPagamento(pagamentoDto);
        URI endereco = uriBuilder.path("pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(endereco).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizarPagamento(@PathVariable @NotNull Long id, PagamentoDto pagamentoDto){
        PagamentoDto pagamento = service.atualizarPagamento(id, pagamentoDto);

        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id){
        service.deletarPagamento(id);

        return ResponseEntity.noContent().build();
    }
}
