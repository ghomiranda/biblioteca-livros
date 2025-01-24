package com.biblioteca.livros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.livros.model.Assunto;
import com.biblioteca.livros.repository.AssuntoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AssuntoService {

	 @Autowired
	 private AssuntoRepository assuntoRepository;

	 public List<Assunto> listarTodos() {
	     return assuntoRepository.findAll();
	 }

	 public Optional<Assunto> buscarPorId(Integer id) {
	     return assuntoRepository.findById(id);
	 }

	 public Assunto salvar(Assunto assunto) {
	     return assuntoRepository.save(assunto);
	 }

	 public void deletar(Integer id) {
	     Optional<Assunto> assunto = assuntoRepository.findById(id);

	     if (assunto.isEmpty()) {
	         throw new RuntimeException("Assunto não encontrado");
	     }

	     assuntoRepository.deleteById(id);
	 }
	
}
