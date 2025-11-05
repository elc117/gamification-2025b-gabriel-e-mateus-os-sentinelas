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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.interfaces.RegistroCallback;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.screens.TelaMenu;
import com.ufsmSistemas.tcgParadigma.services.RegistroService;

public class TelaCriarUsuario extends TelaBase {

    private TextField campoNome;
    private TextField campoSenha;
    private TextField campoConfirmarSenha;
    private Label mensagem;
    private final RegistroService registroService;

    public TelaCriarUsuario(Main game) {
        super(game);
        this.registroService = new RegistroService(); // ✅ instância local, sem Singleton
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label labelTitulo = new Label("Criar Conta", skin);
        Label labelNome = new Label("Usuário:", skin);
        Label labelSenha = new Label("Senha:", skin);
        Label labelConfirmarSenha = new Label("Confirmar Senha:", skin);

        campoNome = new TextField("", skin);
        campoSenha = new TextField("", skin);
        campoSenha.setPasswordMode(true);
        campoSenha.setPasswordCharacter('*');

        campoConfirmarSenha = new TextField("", skin);
        campoConfirmarSenha.setPasswordMode(true);
        campoConfirmarSenha.setPasswordCharacter('*');

        mensagem = new Label("", skin);

        TextButton botaoCriar = new TextButton("Criar Conta", skin);
        botaoCriar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                criarConta();
            }
        });

        TextButton botaoVoltar = new TextButton("Voltar", skin);
        botaoVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaMenu(game));
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
        table.add(labelConfirmarSenha).pad(5);
        table.add(campoConfirmarSenha).width(200).pad(5);
        table.row();
        table.add(botaoCriar).colspan(2).pad(10);
        table.row();
        table.add(botaoVoltar).colspan(2).pad(5);
        table.row();
        table.add(mensagem).colspan(2).pad(5);
    }

    private void criarConta() {
        String nome = campoNome.getText();
        String senha = campoSenha.getText();
        String confirmarSenha = campoConfirmarSenha.getText();

        if (nome.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            mensagem.setText("Preencha todos os campos.");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            mensagem.setText("As senhas não coincidem.");
            return;
        }

        mensagem.setText("Criando conta...");

        RegistroService.registrarJogador(nome, senha, new RegistroCallback() {
            @Override
            public void onSuccess(String message) {
                Gdx.app.postRunnable(() -> {
                    mensagem.setText(message);
                    campoNome.setText("");
                    campoSenha.setText("");
                    campoConfirmarSenha.setText("");
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Gdx.app.postRunnable(() -> mensagem.setText(errorMessage));
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
