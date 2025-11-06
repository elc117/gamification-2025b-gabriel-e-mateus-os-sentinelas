package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.screens.loja.TelaLoja;
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

        // Cria os botões usando o skin
        TextButton botaoAlbum = new TextButton("Álbum", skin);
        TextButton botaoQuiz = new TextButton("Quiz", skin);
        TextButton botaoLoja = new TextButton("Loja", skin); // Novo botão

        botaoAlbum.setSize(200, 60);
        botaoQuiz.setSize(200, 60);
        botaoLoja.setSize(200, 60); // Tamanho do botão da loja

        // Posicionamento
        float centerY = Gdx.graphics.getHeight() / 2f - botaoAlbum.getHeight() / 2f;
        float topY = Gdx.graphics.getHeight() - botaoLoja.getHeight() - 20; // 20 px da borda superior

        botaoAlbum.setPosition(50, centerY); // 50 px da esquerda
        botaoQuiz.setPosition(Gdx.graphics.getWidth() - 50 - botaoQuiz.getWidth(), centerY); // 50 px da direita
        botaoLoja.setPosition(Gdx.graphics.getWidth() / 2f - botaoLoja.getWidth() / 2f, topY); // centralizado no topo

        // Listeners
        botaoAlbum.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Ver Álbum");
            }
        });

        botaoQuiz.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaOpcoesQuiz(game));
            }
        });

        botaoLoja.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Abrindo Loja!");
                game.setScreen(new TelaLoja(game));
            }
        });

        // Adiciona os botões ao stage
        stage.addActor(botaoAlbum);
        stage.addActor(botaoQuiz);
        stage.addActor(botaoLoja); // adiciona o novo botão
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
