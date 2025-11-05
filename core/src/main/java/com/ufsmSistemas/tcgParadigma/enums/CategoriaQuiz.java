package com.ufsmSistemas.tcgParadigma.enums;

import java.awt.*;

public enum CategoriaQuiz {
    CIENCIA("Ciência", new Color(0.2f, 0.7f, 1f, 1f)),
    MATEMATICA("Matemática", new Color(1f, 0.4f, 0.4f, 1f)),
    PROGRAMACAO("Programação", new Color(0.4f, 1f, 0.5f, 1f)),
    POLITICA("Líderes e Política", new Color(1f, 0.8f, 0.2f, 1f)),
    FILOSOFIA("Filosofia e História", new Color(0.8f, 0.5f, 1f, 1f)),
    ARTE("Arte, Música e Entretenimento", new Color(1f, 0.5f, 0.8f, 1f));

    private final String titulo;
    private final Color cor;

    CategoriaQuiz(String titulo, Color cor) {
        this.titulo = titulo;
        this.cor = cor;
    }

    public String getTitulo() {
        return titulo;
    }

    public Color getCor() {
        return cor;
    }
}
