package com.ufsmSistemas.tcgParadigma.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ufsmSistemas.tcgParadigma.models.Carta;

public class CartaVisual {
    private Carta carta;
    private Texture texturaFrente;
    private Texture texturaFundo;
    private float angulo;
    private boolean virada;

    private static final float DURACAO_VIRAR = 0.5f;

    public CartaVisual(Carta carta, Texture texturaFundo) {
        this.carta = carta;
        this.texturaFundo = texturaFundo;
        this.angulo = 0f;
        this.virada = false;

        try {
            this.texturaFrente = new Texture(Gdx.files.internal(carta.getCaminhoImagem()));
        } catch (Exception e) {
            System.err.println("[CartaVisual] Erro ao carregar imagem: " + carta.getCaminhoImagem());
            this.texturaFrente = null;
        }
    }

    public void atualizar(float delta) {
        if (!virada) {
            angulo += (180f / DURACAO_VIRAR) * delta;
            if (angulo >= 180f) {
                angulo = 180f;
                virada = true;
            }
        }
    }

    public void desenhar(SpriteBatch batch, float x, float y, float largura, float altura) {
        Texture texturaAtual = (angulo < 90f) ? texturaFundo : texturaFrente;
        float escalaX = Math.abs((float) Math.cos(Math.toRadians(angulo)));
        float larguraDesenhada = largura * Math.max(escalaX, 0.05f);
        float xCentrada = x + (largura - larguraDesenhada) / 2f;

        if (texturaAtual != null) {
            batch.draw(texturaAtual, xCentrada, y, larguraDesenhada, altura);
        }
    }

    public boolean isVirada() {
        return virada;
    }

    public void dispose() {
        if (texturaFrente != null) texturaFrente.dispose();
    }
}
