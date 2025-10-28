package com.ufsmSistemas.tcgParadigma.Classes;

public class Session {
    private Jogador jogador;
    private boolean isLogado;

    // Getters
    public Jogador getJogador() {
        return jogador;
    }
    public boolean isLogado() {
        return isLogado;
    }

    // Setters
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    public void setLogado(boolean isLogado) {
        this.isLogado = isLogado;
    }
}
