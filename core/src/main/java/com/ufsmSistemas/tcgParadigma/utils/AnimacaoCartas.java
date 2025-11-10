package com.ufsmSistemas.tcgParadigma.utils;

import java.util.List;

public class AnimacaoCartas {
    private List<CartaVisual> cartas;
    private int cartaAtual = 0;
    private float tempoEntreCartas = 0.4f;
    private float tempoDesdeUltimaVirada = 0f;
    private boolean todasViradas = false;

    public AnimacaoCartas(List<CartaVisual> cartas) {
        this.cartas = cartas;
    }

    public void atualizar(float delta) {
        if (todasViradas) return;
        tempoDesdeUltimaVirada += delta;

        if (cartaAtual < cartas.size()) {
            CartaVisual carta = cartas.get(cartaAtual);
            carta.atualizar(delta);

            if (carta.isVirada() && tempoDesdeUltimaVirada >= tempoEntreCartas) {
                cartaAtual++;
                tempoDesdeUltimaVirada = 0f;
            }
        }

        if (cartaAtual >= cartas.size()) {
            todasViradas = true;
        }
    }

    public boolean isTodasViradas() {
        return todasViradas;
    }
}
