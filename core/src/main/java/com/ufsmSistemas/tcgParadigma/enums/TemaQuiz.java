package com.ufsmSistemas.tcgParadigma.enums;

public enum TemaQuiz {
    LIDERES_POLITICA("Líderes e Política", "spritesPersonagens/AbrahamLincoln.png"),
    FILOSOFIA_HISTORIA("Filosofia e História", "spritesPersonagens/Platao.png"),
    CIENCIA("Ciência", "spritesPersonagens/Newton.png"),
    MATEMATICA("Matemática", "spritesPersonagens/Pitagoras.png"),
    PROGRAMACAO("Programação", "spritesPersonagens/AlanTuring.png"),
    ARTE_MUSICA_ENTRETENIMENTO("Arte, Música e Entretenimento", "spritesPersonagens/vanGogh.png");

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
