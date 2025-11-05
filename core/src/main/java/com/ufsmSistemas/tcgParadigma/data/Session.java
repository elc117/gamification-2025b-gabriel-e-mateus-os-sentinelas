package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.models.Jogador;

public class Session {
    private static final Session instance = new Session();
    private Jogador jogador;
    private boolean isLogado;

    private Session() {} // impede criação fora da classe

    public static Session getInstance() {
        return instance;
    }

    // Getters
    public Jogador getJogador() {
        return jogador;
    }
    public boolean getIsLogado() {
        return isLogado;
    }

    // Setters
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
        this.isLogado = (jogador != null);
    }
    public void setLogado(boolean isLogado) {
        this.isLogado = isLogado;
    }

    public void limpar() {
        jogador = null;
        isLogado = false;
    }
}
