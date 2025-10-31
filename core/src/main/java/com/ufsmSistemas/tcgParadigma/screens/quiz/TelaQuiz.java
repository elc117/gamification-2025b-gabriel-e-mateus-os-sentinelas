package com.ufsmSistemas.tcgParadigma.screens.quiz;

import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.tests.TestQuiz;

public class TelaQuiz extends TelaBase {
    public TelaQuiz(Main game, String base, String nivel) {
        super(game);
        TestQuiz test = new TestQuiz();
        test.chamaQuiz(base, nivel);
    }
}
