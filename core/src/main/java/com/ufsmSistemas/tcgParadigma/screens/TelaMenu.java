package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.screens.login.TelaCriarUsuario;
import com.ufsmSistemas.tcgParadigma.screens.login.TelaLogin;

public class TelaMenu extends TelaBase {

    public TelaMenu(Main game) {
        super(game);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Título
        Label titulo = new Label("Bem-vindo ao TCG Paradigma!", skin);
        titulo.setFontScale(1.2f);

        // Botões principais
        TextButton botaoLogin = new TextButton("Login", skin);
        TextButton botaoCriarConta = new TextButton("Criar Conta", skin);
        TextButton botaoSair = new TextButton("Sair", skin);

        // Ações dos botões
        botaoLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaLogin(game));
            }
        });

        botaoCriarConta.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaCriarUsuario(game));
            }
        });

        botaoSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Layout da tabela
        table.add(titulo).colspan(2).pad(20);
        table.row();
        table.add(botaoLogin).width(200).pad(10);
        table.row();
        table.add(botaoCriarConta).width(200).pad(10);
        table.row();
        table.add(botaoSair).width(200).pad(10);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched() && !Main.musicaLiberada) {
            Main.musicaFundo.play();
            Main.musicaLiberada = true;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
