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
import com.ufsmSistemas.tcgParadigma.interfaces.RegistroCallback;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.screens.TelaMenu;
import com.ufsmSistemas.tcgParadigma.services.RegistroService;

public class TelaCriarUsuario extends TelaBase {

    private TextField campoNome;
    private TextField campoSenha;
    private TextField campoConfirmarSenha;
    private Label mensagem;
    private TextButton botaoCriar;
    private TextButton botaoVoltar;
    private final RegistroService registroService;

    public TelaCriarUsuario(Main game) {
        super(game);
        this.registroService = new RegistroService();
        // Cores para tema de registro - tons de verde/turquesa
        corFundoTop = new Color(0.15f, 0.35f, 0.35f, 1);
        corFundoBottom = new Color(0.05f, 0.15f, 0.2f, 1);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Container principal
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Painel de registro (card estilo)
        Table painelRegistro = new Table();
        painelRegistro.setBackground(skin.getDrawable("default-round"));
        painelRegistro.pad(40);

        // Título
        Label titulo = new Label("✨ CRIAR CONTA", skin);
        titulo.setFontScale(2.2f);
        titulo.setColor(new Color(0.4f, 0.9f, 0.7f, 1)); // Verde claro
        titulo.setAlignment(Align.center);

        // Subtítulo
        Label subtitulo = new Label("Junte-se à aventura!", skin);
        subtitulo.setFontScale(1f);
        subtitulo.setColor(new Color(0.7f, 0.7f, 0.8f, 1));
        subtitulo.setAlignment(Align.center);

        // Labels dos campos com ícones
        Label labelNome = new Label("👤 Usuário:", skin);
        labelNome.setFontScale(1.1f);

        Label labelSenha = new Label("🔑 Senha:", skin);
        labelSenha.setFontScale(1.1f);

        Label labelConfirmarSenha = new Label("🔒 Confirmar Senha:", skin);
        labelConfirmarSenha.setFontScale(1.1f);

        // Campos de texto estilizados
        campoNome = new TextField("", skin);
        campoNome.setMessageText("Escolha seu nome de usuário");

        campoSenha = new TextField("", skin);
        campoSenha.setMessageText("Crie uma senha segura");
        campoSenha.setPasswordMode(true);
        campoSenha.setPasswordCharacter('•');

        campoConfirmarSenha = new TextField("", skin);
        campoConfirmarSenha.setMessageText("Digite a senha novamente");
        campoConfirmarSenha.setPasswordMode(true);
        campoConfirmarSenha.setPasswordCharacter('•');

        // Dicas de segurança
        Label dicaSenha = new Label("💡 Use pelo menos 6 caracteres", skin);
        dicaSenha.setFontScale(0.8f);
        dicaSenha.setColor(new Color(0.6f, 0.6f, 0.7f, 1));

        // Mensagem de feedback
        mensagem = new Label("", skin);
        mensagem.setFontScale(0.95f);
        mensagem.setAlignment(Align.center);
        mensagem.setWrap(true);

        // Botões
        botaoCriar = new TextButton("✓ CRIAR CONTA", skin);
        botaoCriar.getLabel().setFontScale(1.3f);
        botaoCriar.setColor(new Color(0.4f, 0.8f, 0.5f, 1)); // Verde

        botaoVoltar = new TextButton("← VOLTAR", skin);
        botaoVoltar.getLabel().setFontScale(1.1f);
        botaoVoltar.setColor(new Color(0.6f, 0.6f, 0.7f, 1)); // Cinza

        // Listeners
        botaoCriar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botão Criar Conta clicado!");
                criarConta();
            }
        });

        botaoVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botão Voltar clicado!");
                game.setScreen(new TelaMenu(game));
            }
        });

        // Efeitos de hover
        adicionarEfeitoHover(botaoCriar);
        adicionarEfeitoHover(botaoVoltar);

        // Monta o layout do painel
        painelRegistro.add(titulo).colspan(2).padBottom(10).row();
        painelRegistro.add(subtitulo).colspan(2).padBottom(30).row();

        painelRegistro.add(labelNome).left().padBottom(5).row();
        painelRegistro.add(campoNome).width(320).height(45).colspan(2).padBottom(20).row();

        painelRegistro.add(labelSenha).left().padBottom(5).row();
        painelRegistro.add(campoSenha).width(320).height(45).colspan(2).padBottom(5).row();
        painelRegistro.add(dicaSenha).left().colspan(2).padBottom(20).row();

        painelRegistro.add(labelConfirmarSenha).left().padBottom(5).row();
        painelRegistro.add(campoConfirmarSenha).width(320).height(45).colspan(2).padBottom(25).row();

        painelRegistro.add(botaoCriar).width(320).height(50).colspan(2).padBottom(15).row();
        painelRegistro.add(botaoVoltar).width(320).height(45).colspan(2).padBottom(10).row();

        painelRegistro.add(mensagem).width(320).colspan(2).padTop(10);

        mainTable.add(painelRegistro);

        // Informação na parte inferior
        Label info = new Label("🎮 Ao criar uma conta, você poderá salvar seu progresso!", skin);
        info.setFontScale(0.8f);
        info.setColor(new Color(0.6f, 0.7f, 0.6f, 0.8f));
        info.setAlignment(Align.center);
        info.setWrap(true);

        Table footer = new Table();
        footer.setFillParent(true);
        footer.bottom();
        footer.add(info).width(400).padBottom(15);

        stage.addActor(mainTable);
        stage.addActor(footer);

        // Animação de entrada
        painelRegistro.setColor(1, 1, 1, 0);
        painelRegistro.addAction(Actions.fadeIn(0.5f));

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

    private void criarConta() {
        String nome = campoNome.getText().trim();
        String senha = campoSenha.getText().trim();
        String confirmarSenha = campoConfirmarSenha.getText().trim();

        // Validações com feedback visual
        if (nome.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            exibirMensagem("⚠️ Preencha todos os campos!", new Color(1f, 0.6f, 0.2f, 1));
            animarCamposVazios();
            return;
        }

        if (senha.length() < 6) {
            exibirMensagem("⚠️ A senha deve ter pelo menos 6 caracteres!", new Color(1f, 0.6f, 0.2f, 1));
            campoSenha.addAction(criarAnimacaoErro());
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            exibirMensagem("❌ As senhas não coincidem!", new Color(1f, 0.3f, 0.3f, 1));
            campoConfirmarSenha.addAction(criarAnimacaoErro());
            return;
        }

        // Desabilita botão durante criação
        botaoCriar.setDisabled(true);
        botaoCriar.setText("CRIANDO...");
        exibirMensagem("🔄 Criando sua conta...", new Color(0.3f, 0.7f, 1f, 1));

        RegistroService.registrarJogador(nome, senha, new RegistroCallback() {
            @Override
            public void onSuccess(String message) {
                Gdx.app.postRunnable(() -> {
                    exibirMensagem("✅ " + message, new Color(0.3f, 0.9f, 0.4f, 1));

                    // Limpa os campos com animação
                    limparCamposComAnimacao();

                    // Reabilita botão
                    botaoCriar.setDisabled(false);
                    botaoCriar.setText("✓ CRIAR CONTA");

                    // Animação de sucesso no painel
                    Table painelRegistro = (Table) stage.getActors().first();
                    painelRegistro.addAction(Actions.sequence(
                        Actions.scaleTo(1.05f, 1.05f, 0.2f),
                        Actions.scaleTo(1f, 1f, 0.2f)
                    ));

                    System.out.println("Conta criada com sucesso: " + nome);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Gdx.app.postRunnable(() -> {
                    exibirMensagem("❌ " + errorMessage, new Color(1f, 0.3f, 0.3f, 1));

                    // Reabilita botão
                    botaoCriar.setDisabled(false);
                    botaoCriar.setText("✓ CRIAR CONTA");

                    // Animação de erro
                    campoNome.addAction(criarAnimacaoErro());

                    System.out.println("Erro ao criar conta: " + errorMessage);
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

    private void animarCamposVazios() {
        if (campoNome.getText().trim().isEmpty()) {
            campoNome.addAction(criarAnimacaoErro());
        }
        if (campoSenha.getText().trim().isEmpty()) {
            campoSenha.addAction(criarAnimacaoErro());
        }
        if (campoConfirmarSenha.getText().trim().isEmpty()) {
            campoConfirmarSenha.addAction(criarAnimacaoErro());
        }
    }

    private com.badlogic.gdx.scenes.scene2d.Action criarAnimacaoErro() {
        return Actions.sequence(
            Actions.moveBy(-5, 0, 0.05f),
            Actions.moveBy(10, 0, 0.05f),
            Actions.moveBy(-10, 0, 0.05f),
            Actions.moveBy(5, 0, 0.05f)
        );
    }

    private void limparCamposComAnimacao() {
        campoNome.addAction(Actions.sequence(
            Actions.fadeOut(0.3f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    campoNome.setText("");
                }
            }),
            Actions.fadeIn(0.3f)
        ));

        campoSenha.addAction(Actions.sequence(
            Actions.fadeOut(0.3f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    campoSenha.setText("");
                }
            }),
            Actions.fadeIn(0.3f)
        ));

        campoConfirmarSenha.addAction(Actions.sequence(
            Actions.fadeOut(0.3f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    campoConfirmarSenha.setText("");
                }
            }),
            Actions.fadeIn(0.3f)
        ));
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
