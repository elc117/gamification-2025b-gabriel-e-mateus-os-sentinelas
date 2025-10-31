package com.ufsmSistemas.tcgParadigma.tests;

import com.ufsmSistemas.tcgParadigma.quiz.Pergunta;
import com.ufsmSistemas.tcgParadigma.quiz.Quiz;

public class TestQuiz {
    public void chamaQuiz(String topico, String dificuldade){
        try {
            Quiz quiz = new Quiz(topico, dificuldade);

            System.out.println("Tema: " + quiz.getTema());
            System.out.println("Dificuldade: " + quiz.getNivelDificuldade());
            System.out.println("Imagem Host: " + quiz.getImagemHost());
            System.out.println("Número de perguntas: " + quiz.getPerguntaList().size());

            // Mostrar perguntas
            for (int i = 0; i < quiz.getPerguntaList().size(); i++) {
                Pergunta p = quiz.getPerguntaList().get(i);
                System.out.println((i+1) + ". " + p.getEnunciado());
                System.out.println("  Resposta certa: " + p.getRespostaCerta());
                System.out.println("  Erradas: " + p.getRespostaErrada());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
