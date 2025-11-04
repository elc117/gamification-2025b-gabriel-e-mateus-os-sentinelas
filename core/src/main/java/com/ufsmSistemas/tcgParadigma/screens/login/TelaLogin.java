package com.ufsmSistemas.tcgParadigma.screens.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.screens.TelaInicialJogo;

public class TelaLogin extends TelaBase {

    private TextField campoNome;
    private TextField campoSenha;
    private Label mensagem;

    public TelaLogin(Main game) {
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

        Label labelTitulo = new Label("Login", skin);
        Label labelNome = new Label("Usuário:", skin);
        Label labelSenha = new Label("Senha:", skin);

        campoNome = new TextField("", skin);
        campoSenha = new TextField("", skin);
        campoSenha.setPasswordMode(true);
        campoSenha.setPasswordCharacter('*');

        mensagem = new Label("", skin);

        TextButton botaoLogin = new TextButton("Entrar", skin);
        botaoLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                validarLogin();
            }
        });

        table.add(labelTitulo).colspan(2).pad(10);
        table.row();
        table.add(labelNome).pad(5);
        table.add(campoNome).width(200).pad(5);
        table.row();
        table.add(labelSenha).pad(5);
        table.add(campoSenha).width(200).pad(5);
        table.row();
        table.add(botaoLogin).colspan(2).pad(10);
        table.row();
        table.add(mensagem).colspan(2).pad(5);
    }

    private void validarLogin() {
        String nome = campoNome.getText();
        String senha = campoSenha.getText();

        if (nome.isEmpty() || senha.isEmpty()) {
            mensagem.setText("Preencha todos os campos.");
            return;
        }

        final Jogador jogador = new Jogador(nome, senha);

        DataBaseAPI.select(jogador, new DataBaseAPI.ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                try {
                    jogador.fromJson(response);
                    mensagem.setText("Login realizado com sucesso!");
                    System.out.println("Jogador logado: " + jogador.getNome());
                    Gdx.app.postRunnable(() -> {
                        game.setScreen(new TelaInicialJogo(game));
                    });

                } catch (Exception e) {
                    mensagem.setText("Erro ao processar resposta do servidor.");
                    System.out.println("Erro JSON: " + e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                mensagem.setText("Usuário ou senha incorretos.");
                System.out.println("Erro: " + errorMessage);
            }
        });
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
