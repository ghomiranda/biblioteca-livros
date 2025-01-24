package com.biblioteca.livros.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.biblioteca.livros.model.Livro;
import com.biblioteca.livros.model.Assunto;
import com.biblioteca.livros.model.Autor;
import com.biblioteca.livros.repository.LivroRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import java.io.ByteArrayInputStream;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

public class RelatorioServiceTest {
	
	private RelatorioService relatorioService;
    private LivroRepository livroRepository;

    @BeforeEach
    void setUp() {
        livroRepository = mock(LivroRepository.class);
        relatorioService = new RelatorioService(livroRepository);
    }

    @Test
    void deveGerarRelatorioComLivros() throws Exception {
        Autor autor = new Autor(1, "Autor Teste");
        Assunto assunto = new Assunto(1, "Assunto Teste");
        Livro livro = new Livro(1, "Livro Teste", "Editora Teste", 1, "2023", 99.99, 
                Arrays.asList(autor), Arrays.asList(assunto));

        when(livroRepository.findAll()).thenReturn(Collections.singletonList(livro));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        relatorioService.gerarRelatorioLivros(outputStream);

        byte[] pdfBytes = outputStream.toByteArray();
        PdfDocument pdfDocument = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader(new ByteArrayInputStream(pdfBytes)));

        StringBuilder pdfContent = new StringBuilder();
        for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
            pdfContent.append(com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i)));
        }
        pdfDocument.close();

        assertTrue(pdfContent.toString().contains("Livro Teste"));
        assertTrue(pdfContent.toString().contains("Autor Teste"));
        assertTrue(pdfContent.toString().contains("Assunto Teste"));
    }

}
