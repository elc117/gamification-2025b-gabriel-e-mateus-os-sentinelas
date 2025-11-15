package com.ufsmSistemas.tcgParadigma.enums;

public enum TemaQuiz {
    LIDERES_POLITICA("Lideres e Política", "cartasTrabalho/rara/AbrahamLincoln.png"),
    FILOSOFIA_HISTORIA("Filosofia e História", "cartasTrabalho/rara/Platao.png"),
    CIENCIA("Ciência", "cartasTrabalho/rara/Newton.png"),
    MATEMATICA("Matemática", "cartasTrabalho/rara/Pitagoras.png"),
    PROGRAMACAO("Programação", "cartasTrabalho/rara/AlanTuring.png"),
    ARTE_MUSICA_ENTRETENIMENTO("Arte, Música e Entretenimento", "cartasTrabalho/rara/vanGogh.png");

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
