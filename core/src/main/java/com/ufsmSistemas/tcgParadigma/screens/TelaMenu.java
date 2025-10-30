package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;

public class TelaMenu extends TelaBase{

    public TelaMenu(Main game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Carrega o skin a partir da pasta assets
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Cria o botão usando o skin
        TextButton botao = new TextButton("Iniciar Jogo", skin);
        botao.setSize(200, 60);
        botao.setPosition(
            Gdx.graphics.getWidth() / 2f - 100,
            Gdx.graphics.getHeight() / 2f - 30
        );

        botao.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botão clicado!");
                game.setScreen(new TelaInicialJogo(game));
            }
        });

        stage.addActor(botao);
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
