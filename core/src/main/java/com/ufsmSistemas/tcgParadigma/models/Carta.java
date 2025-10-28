package com.ufsmSistemas.tcgParadigma.models;

public class Carta {
    private int id;
    private String nome;
    private String caminhoImagem;
    private String raridade;
    private String categoria;
    private Float pullRate;
    private boolean isObtida;
    private int quantidade;

    // Getters
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCaminhoImagem() {
        return caminhoImagem;
    }
    public String getRaridade() {
        return raridade;
    }
    public Float getPullRate() {
        return pullRate;
    }
    public String getCategoria() {
        return categoria;
    }
    public boolean isObtida() {
        return isObtida;
    }
    public int getQuantidade() {
        return quantidade;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }
    public void setRaridade(String raridade) {
        this.raridade = raridade;
    }
    public void setPullRate(Float pullRate) {
        this.pullRate = pullRate;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setObtida(boolean isObtida) {
        this.isObtida = isObtida;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
