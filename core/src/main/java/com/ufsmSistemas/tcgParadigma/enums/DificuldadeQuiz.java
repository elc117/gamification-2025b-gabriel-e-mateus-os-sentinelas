package com.ufsmSistemas.tcgParadigma.enums;

import java.awt.*;

public enum DificuldadeQuiz {
    FACIL("Fácil", new Color(0.4f, 1f, 0.4f, 1f)),
    MEDIA("Média", new Color(1f, 0.9f, 0.3f, 1f)),
    DIFICIL("Difícil", new Color(1f, 0.3f, 0.3f, 1f));

    private final String nome;
    private final Color cor;

    DificuldadeQuiz(String nome, Color cor) {
        this.nome = nome;
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public Color getCor() {
        return cor;
    }
}
