package com.ufsmSistemas.tcgParadigma.Classes;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SistemaQuiz {
    private List<Quiz> quizzes = new ArrayList<>();

    public void criarQuiz(String tema, String dificuldade) throws FileNotFoundException {
        Quiz novo = new Quiz(tema, dificuldade);
        quizzes.add(novo);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }
}
