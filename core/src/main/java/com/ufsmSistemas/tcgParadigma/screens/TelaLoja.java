package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.models.Booster;
import com.ufsmSistemas.tcgParadigma.models.Carta;

public class TelaLoja extends TelaBase {

    private Booster booster1;
    private Booster booster2;
    private Stage cartasStage; // camada acima para mostrar cartas abertas

    public TelaLoja(Main game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        cartasStage = new Stage(new ScreenViewport()); // camada para cartas abertas
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        // Botões dos boosters
        TextButton botaoBooster1 = new TextButton("Booster 1", skin);
        TextButton botaoBooster2 = new TextButton("Booster 2", skin);

        botaoBooster1.setSize(200, 80);
        botaoBooster2.setSize(200, 80);

        float centerY = Gdx.graphics.getHeight() / 2f;
        botaoBooster1.setPosition(Gdx.graphics.getWidth() / 4f - botaoBooster1.getWidth() / 2f, centerY);
        botaoBooster2.setPosition(Gdx.graphics.getWidth() * 3 / 4f - botaoBooster2.getWidth() / 2f, centerY);

        botaoBooster1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                booster1 = new Booster(cartas -> mostrarCartas((Array<Carta>) cartas));
            }
        });

        botaoBooster2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                booster2 = new Booster(cartas -> mostrarCartas((Array<Carta>) cartas));
            }
        });

        stage.addActor(botaoBooster1);
        stage.addActor(botaoBooster2);

        // Botão para voltar à tela inicial
        TextButton botaoVoltar = new TextButton("Voltar", skin);
        botaoVoltar.setSize(150, 60);
        botaoVoltar.setPosition(20, Gdx.graphics.getHeight() - 80);
        botaoVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaInicialJogo(game));
            }
        });
        stage.addActor(botaoVoltar);
    }

    private void mostrarCartas(Array<Carta> cartas) {
        cartasStage.clear(); // limpa cartas anteriores
        float startX = 50;
        float startY = Gdx.graphics.getHeight() / 2f + 100;

        for (Carta c : cartas) {
            Texture textura = new Texture(Gdx.files.internal(c.getCaminhoImagem()));
            Image imgCarta = new Image(textura);
            imgCarta.setPosition(startX, startY);
            imgCarta.setSize(100, 150); // tamanho da carta
            startX += 120; // espaçamento horizontal
            cartasStage.addActor(imgCarta);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();

        // desenha camada acima com cartas abertas
        cartasStage.act(delta);
        cartasStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        cartasStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        cartasStage.dispose();
    }
}
