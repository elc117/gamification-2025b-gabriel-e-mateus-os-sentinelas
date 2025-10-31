package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.screens.quiz.TelaOpcoesQuiz;

public class TelaInicialJogo extends TelaBase {

    public TelaInicialJogo(Main game) {
        super(game);
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Carrega o skin a partir da pasta assets
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Cria o botão usando o skin
        TextButton botaoBooster = new TextButton("Abríndo Bãossteáaaar", skin);
        TextButton botaoQuiz = new TextButton("Abrir Quiz", skin);

        botaoBooster.setSize(200, 60);
        botaoQuiz.setSize(200, 60);

        float y = Gdx.graphics.getHeight() / 2f - botaoBooster.getHeight() / 2f; // vertical central
        botaoBooster.setPosition(50, y); // 50 px da esquerda
        botaoQuiz.setPosition(Gdx.graphics.getWidth() - 50 - botaoQuiz.getWidth(), y); // 50 px da direita


        botaoBooster.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Abríndo Bãossteáaaar!");
            }
        });

        botaoQuiz.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaOpcoesQuiz(game));
            }
        });

        stage.addActor(botaoBooster);
        stage.addActor(botaoQuiz);
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
