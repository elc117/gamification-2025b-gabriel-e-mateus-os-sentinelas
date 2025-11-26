package com.ufsmSistemas.tcgParadigma.screens.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.screens.TelaInicialJogo;
import com.ufsmSistemas.tcgParadigma.screens.TelaMenu;
import com.ufsmSistemas.tcgParadigma.services.LoginService;

public class TelaLogin extends TelaBase {

    private TextField campoNome;
    private TextField campoSenha;
    private Label mensagem;
    private TextButton botaoLogin;
    private TextButton botaoVoltar;

    private final LoginService loginService;

    public TelaLogin(Main game) {
        super(game);
        this.loginService = new LoginService();

        corFundoTop = new Color(0.15f, 0.25f, 0.45f, 1);
        corFundoBottom = new Color(0.05f, 0.1f, 0.25f, 1);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Container principal
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Painel de login (card estilo)
        Table painelLogin = new Table();
        painelLogin.setBackground(skin.getDrawable("default-round"));
        painelLogin.pad(40);

        // Título
        Label titulo = new Label("🔐 ENTRAR", skin);
        titulo.setFontScale(2.2f);
        titulo.setColor(new Color(0.3f, 0.7f, 1f, 1));
        titulo.setAlignment(Align.center);

        // Subtítulo
        Label subtitulo = new Label("Acesse sua conta", skin);
        subtitulo.setFontScale(1f);
        subtitulo.setColor(new Color(0.7f, 0.7f, 0.8f, 1));
        subtitulo.setAlignment(Align.center);

        // Labels dos campos
        Label labelNome = new Label("👤 Username:", skin);
        labelNome.setFontScale(1.1f);

        Label labelSenha = new Label("🔑 Senha:", skin);
        labelSenha.setFontScale(1.1f);

        // Campos de texto estilizados
        campoNome = new TextField("", skin);
        campoNome.setMessageText("Digite seu username");

        campoSenha = new TextField("", skin);
        campoSenha.setMessageText("Digite sua senha");
        campoSenha.setPasswordMode(true);
        campoSenha.setPasswordCharacter('•');

        // Mensagem de feedback
        mensagem = new Label("", skin);
        mensagem.setFontScale(0.9f);
        mensagem.setAlignment(Align.center);
        mensagem.setWrap(true);

        // Botões
        botaoLogin = new TextButton("ENTRAR", skin);
        botaoLogin.getLabel().setFontScale(1.3f);
        botaoLogin.setColor(new Color(0.3f, 0.7f, 0.4f, 1)); // Verde

        botaoVoltar = new TextButton("← VOLTAR", skin);
        botaoVoltar.getLabel().setFontScale(1.1f);
        botaoVoltar.setColor(new Color(0.6f, 0.6f, 0.7f, 1)); // Cinza

        // Listeners
        botaoLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                autenticarUsuario();
            }
        });

        botaoVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaMenu(game));
            }
        });

        // Efeitos de hover
        adicionarEfeitoHover(botaoLogin);
        adicionarEfeitoHover(botaoVoltar);

        // Monta o layout do painel
        painelLogin.add(titulo).colspan(2).padBottom(10).row();
        painelLogin.add(subtitulo).colspan(2).padBottom(30).row();

        painelLogin.add(labelNome).left().padBottom(5).row();
        painelLogin.add(campoNome).width(300).height(45).colspan(2).padBottom(20).row();

        painelLogin.add(labelSenha).left().padBottom(5).row();
        painelLogin.add(campoSenha).width(300).height(45).colspan(2).padBottom(25).row();

        painelLogin.add(botaoLogin).width(300).height(50).colspan(2).padBottom(15).row();
        painelLogin.add(botaoVoltar).width(300).height(45).colspan(2).padBottom(10).row();

        painelLogin.add(mensagem).width(300).colspan(2).padTop(10);

        mainTable.add(painelLogin);

        // Dica na parte inferior
        Label dica = new Label("💡 Não tem conta? Volte e clique em 'Criar Conta'", skin);
        dica.setFontScale(0.8f);
        dica.setColor(new Color(0.6f, 0.6f, 0.7f, 0.8f));

        Table footer = new Table();
        footer.setFillParent(true);
        footer.bottom();
        footer.add(dica).padBottom(15);

        stage.addActor(mainTable);
        stage.addActor(footer);

        // Animação de entrada
        painelLogin.setColor(1, 1, 1, 0);
        painelLogin.addAction(Actions.fadeIn(0.5f));

        footer.setColor(1, 1, 1, 0);
        footer.addAction(Actions.sequence(
            Actions.delay(0.3f),
            Actions.fadeIn(0.4f)
        ));
    }

    private void adicionarEfeitoHover(final TextButton botao) {
        botao.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                if (pointer == -1) {
                    botao.addAction(Actions.scaleTo(1.05f, 1.05f, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                if (pointer == -1) {
                    botao.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                }
            }
        });
    }

    private void autenticarUsuario() {
        String nome = campoNome.getText().trim();
        String senha = campoSenha.getText().trim();

        if (nome.isEmpty() || senha.isEmpty()) {
            exibirMensagem("⚠️ Preencha todos os campos!", new Color(1f, 0.6f, 0.2f, 1));
            return;
        }

        // Desabilita botão durante autenticação
        botaoLogin.setDisabled(true);
        botaoLogin.setText("AUTENTICANDO...");
        exibirMensagem("🔄 Autenticando...", new Color(0.3f, 0.7f, 1f, 1));

        loginService.autenticar(nome, senha, new LoginService.LoginCallback() {
            @Override
            public void onSuccess(Jogador jogador) {
                Gdx.app.postRunnable(() -> {
                    exibirMensagem("✅ Login realizado com sucesso!", new Color(0.3f, 0.9f, 0.4f, 1));

                    // Pequeno delay antes de mudar de tela
                    stage.addAction(Actions.sequence(
                        Actions.delay(0.8f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new TelaInicialJogo(game));
                            }
                        })
                    ));
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Gdx.app.postRunnable(() -> {
                    exibirMensagem("❌ " + errorMessage, new Color(1f, 0.3f, 0.3f, 1));
                    System.out.println("Erro: " + errorMessage);

                    // Reabilita botão
                    botaoLogin.setDisabled(false);
                    botaoLogin.setText("ENTRAR");

                    // Animação de erro nos campos
                    campoNome.addAction(Actions.sequence(
                        Actions.moveBy(-5, 0, 0.05f),
                        Actions.moveBy(10, 0, 0.05f),
                        Actions.moveBy(-10, 0, 0.05f),
                        Actions.moveBy(5, 0, 0.05f)
                    ));
                });
            }
        });
    }

    private void exibirMensagem(String texto, Color cor) {
        mensagem.setText(texto);
        mensagem.setColor(cor);

        // Animação de fade para a mensagem
        mensagem.clearActions();
        mensagem.setColor(cor.r, cor.g, cor.b, 0);
        mensagem.addAction(Actions.fadeIn(0.3f));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
