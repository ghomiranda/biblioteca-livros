package com.biblioteca.livros.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assunto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codas;

	private String descricao;

	@ManyToMany(mappedBy = "assuntos")
	@JsonIgnore
	private List<Livro> livros;

	public Assunto(Integer codas, String descricao) {
		this.codas = codas;
		this.descricao = descricao;
	}
}
