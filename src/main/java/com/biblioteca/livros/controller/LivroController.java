package com.biblioteca.livros.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.biblioteca.livros.model.Livro;
import com.biblioteca.livros.service.LivroService;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
	
	private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Integer id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Livro salvar(@RequestBody Livro livro) {
        return livroService.salvar(livro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Integer id, @RequestBody Livro livro) {
        return livroService.buscarPorId(id)
            .map(livroExistente -> {
                livro.setCodli(id);
                Livro livroAtualizado = livroService.salvar(livro);
                return ResponseEntity.ok(livroAtualizado);
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
