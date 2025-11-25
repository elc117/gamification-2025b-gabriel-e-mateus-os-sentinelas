package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DesenhaMoedaTela {

    private Texture moedaTexture;
    private BitmapFont font;
    private float moedaX;
    private float moedaY;
    private float moedaSize;

    public DesenhaMoedaTela() {
        // Carregar textura da moeda
        moedaTexture = new Texture(Gdx.files.internal("detalhesTela/moeda.png"));

        // Configurar fonte
        font = new BitmapFont();
        font.getData().setScale(2);
        font.setColor(Color.GOLD);

        // Definir posições e tamanho
        moedaSize = 40;
        moedaX = Gdx.graphics.getWidth() - 150;
        moedaY = Gdx.graphics.getHeight() - 80;
    }

    public void desenhar(SpriteBatch batch, int pontos) {
        // Desenha a moeda
        batch.draw(moedaTexture, moedaX, moedaY, moedaSize, moedaSize);

        // Desenha os pontos ao lado da moeda
        font.draw(batch, String.valueOf(pontos),
            moedaX + moedaSize + 10,
            moedaY + moedaSize / 2 + 10);
    }

    public void dispose() {
        moedaTexture.dispose();
        font.dispose();
    }
}
