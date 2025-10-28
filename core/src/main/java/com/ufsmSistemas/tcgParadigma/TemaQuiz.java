package com.ufsmSistemas.tcgParadigma;

public enum TemaQuiz {
    LIDERES_POLITICA("Líderes e Política", "Lincoln"),
    FILOSOFIA_HISTORIA("Filosofia e História", "Platão"),
    CIENCIA("Ciência", "Newton"),
    MATEMATICA("Matemática", "Pitágoras"),
    PROGRAMACAO("Programação", "Alan Turing"),
    ARTE_MUSICA_ENTRETENIMENTO("Arte, Música e Entretenimento", "Van Gogh");

    private final String nome;
    private final String imagemHost;

    TemaQuiz(String nome, String imagemHost) {
        this.nome = nome;
        this.imagemHost = imagemHost;
    }

    public String getNome() {
        return nome;
    }

    public String getImagemHost() {
        return imagemHost;
    }

    // Método para buscar enum pelo nome
    public static TemaQuiz fromNome(String nome) {
        for (TemaQuiz t : values()) {
            if (t.getNome().equals(nome)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tema inválido: " + nome);
    }
}
