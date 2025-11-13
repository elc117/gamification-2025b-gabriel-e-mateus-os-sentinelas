package com.ufsmSistemas.tcgParadigma.models.quiz;

import com.ufsmSistemas.tcgParadigma.enums.TemaQuiz;
import com.ufsmSistemas.tcgParadigma.utils.LeJsonQuiz;

import java.io.FileNotFoundException;
import java.util.List;

public class Quiz {
    private TemaQuiz tema;
    private String nivelDificuldade;
    private String imagemHost;
    private int pontosGanhos;
    private List<Pergunta> perguntaList;
    private int vidaHost;
    private int vidaUser;

    //Construtor
    public Quiz(String tema, String dificuldade) throws FileNotFoundException {
        this.tema = TemaQuiz.fromNome(tema);
        this.nivelDificuldade = dificuldade;
        this.imagemHost = this.tema.getImagemHost();
        this.vidaHost = 100;
        this.vidaUser = 40;

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
