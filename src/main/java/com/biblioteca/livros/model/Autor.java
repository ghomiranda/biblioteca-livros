package com.biblioteca.livros.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Data
@NoArgsConstructor
public class Autor {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codau;

    private String nome;

    @ManyToMany(mappedBy = "autores")
    @JsonIgnore
    private List<Livro> livros;

    public Autor(Integer codau, String nome) {
        this.codau = codau;
        this.nome = nome;
    }
}
