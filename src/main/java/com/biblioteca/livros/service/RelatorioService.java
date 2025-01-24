package com.biblioteca.livros.service;

import com.biblioteca.livros.model.Assunto;
import com.biblioteca.livros.model.Autor;
import com.biblioteca.livros.model.Livro;
import com.biblioteca.livros.repository.LivroRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.OutputStream;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

	 private final LivroRepository livroRepository;

	    public RelatorioService(LivroRepository livroRepository) {
	        this.livroRepository = livroRepository;
	    }

	    public void gerarRelatorioLivros(OutputStream outputStream) throws Exception {
	    	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	    	PdfWriter writer = new PdfWriter(outputStream);
	        PdfDocument pdf = new PdfDocument(writer);
	        Document document = new Document(pdf);
	        document.add(new Paragraph("Relatório Completo de Livros").setBold().setFontSize(16).setMarginBottom(20));

	        Table table = new Table(new float[]{3, 6, 6});
	        table.addHeaderCell("Livro");
	        table.addHeaderCell("Autores");
	        table.addHeaderCell("Assuntos");

	        List<Livro> livros = livroRepository.findAll();
	        for (Livro livro : livros) {
	            StringBuilder livroInfo = new StringBuilder();
	            livroInfo.append("ID: ").append(livro.getCodli()).append("\n");
	            livroInfo.append("Título: ").append(livro.getTitulo()).append("\n");
	            livroInfo.append("Editora: ").append(livro.getEditora()).append("\n");
	            livroInfo.append("Edição: ").append(livro.getEdicao()).append("\n");
	            livroInfo.append("Ano: ").append(livro.getAnoPublicacao()).append("\n");
	            livroInfo.append("Valor: ").append(currencyFormat.format(livro.getValor()));

	            table.addCell(livroInfo.toString());

	            StringBuilder autoresInfo = new StringBuilder();
	            if (livro.getAutores() != null && !livro.getAutores().isEmpty()) {
	                for (Autor autor : livro.getAutores()) {
	                    autoresInfo.append("ID: ").append(autor.getCodau()).append("\n");
	                    autoresInfo.append("Nome: ").append(autor.getNome()).append("\n");
	                    autoresInfo.append("--------------------\n");
	                }
	            } else {
	                autoresInfo.append("Nenhum autor associado.");
	            }
	            table.addCell(autoresInfo.toString());

	            StringBuilder assuntosInfo = new StringBuilder();
	            if (livro.getAssuntos() != null && !livro.getAssuntos().isEmpty()) {
	                for (Assunto assunto : livro.getAssuntos()) {
	                    assuntosInfo.append("ID: ").append(assunto.getCodas()).append("\n");
	                    assuntosInfo.append("Descrição: ").append(assunto.getDescricao()).append("\n");
	                    assuntosInfo.append("--------------------\n");
	                }
	            } else {
	                assuntosInfo.append("Nenhum assunto associado.");
	            }
	            table.addCell(assuntosInfo.toString());
	        }

	        document.add(table);
	        document.close();
	    }
	
}
