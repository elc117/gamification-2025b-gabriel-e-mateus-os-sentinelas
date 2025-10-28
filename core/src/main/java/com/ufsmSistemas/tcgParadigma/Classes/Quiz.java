package com.ufsmSistemas.tcgParadigma.Classes;

import com.ufsmSistemas.tcgParadigma.TemaQuiz;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;


public class Quiz {
    private TemaQuiz tema;
    private String nivelDificuldade;
    private String imagemHost;
    private int pontosGanhos;
    private List<Pergunta> perguntaList;

    //Construtor
    public Quiz(String tema, String dificuldade) throws FileNotFoundException {
        this.tema = TemaQuiz.fromNome(tema); // já valida se o tema existe
        this.nivelDificuldade = dificuldade;

        // imagemHost agora vem do enum
        this.imagemHost = this.tema.getImagemHost();

        //Pontos
        if (dificuldade.equals("facil")) {
            this.pontosGanhos = 1;
        } else if (dificuldade.equals("média")) {
            this.pontosGanhos = 2;
        } else if (dificuldade.equals("difícil")) {
            this.pontosGanhos = 3;
        }

        this.perguntaList = LeJsonQuiz.carregarPerguntas(this.tema.getNome(), this.nivelDificuldade);
    }

    // Getters
    public String getTema() {
        return tema.getNome();
    }

    public String getNivelDificuldade() {
        return nivelDificuldade;
    }

    public String getImagemHost() {
        return imagemHost;
    }

    public int getPontosGanhos() {
        return pontosGanhos;
    }

    public List<Pergunta> getPerguntaList() {
        return perguntaList;
    }

}
