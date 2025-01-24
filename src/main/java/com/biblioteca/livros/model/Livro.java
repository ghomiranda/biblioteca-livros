package com.biblioteca.livros.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Livro {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codli;

    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private Double valor;

    @ManyToMany
    @JoinTable(
        name = "livro_autor",
        joinColumns = @JoinColumn(name = "codli"),
        inverseJoinColumns = @JoinColumn(name = "codau")
    )
    private List<Autor> autores;

    @ManyToMany
    @JoinTable(
        name = "livro_assunto",
        joinColumns = @JoinColumn(name = "codli"),
        inverseJoinColumns = @JoinColumn(name = "codas")
    )
    private List<Assunto> assuntos;
    
    public Livro(Integer codli, String titulo, String editora, Integer edicao, String anoPublicacao, Double valor, List<Autor> autores, List<Assunto> assuntos) {
        this.codli = codli;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.valor = valor;
        this.autores = autores;
        this.assuntos = assuntos;
    }
}

