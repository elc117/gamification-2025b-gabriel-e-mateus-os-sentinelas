package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.screens.login.TelaCriarUsuario;
import com.ufsmSistemas.tcgParadigma.screens.login.TelaLogin;

public class TelaMenu extends TelaBase {
    private Label titulo;
    private Label subtitulo;

    public TelaMenu(Main game) {
        super(game);
        // Cores para o tema de menu principal - tons de roxo/azul
        corFundoTop = new Color(0.2f, 0.1f, 0.35f, 1);
        corFundoBottom = new Color(0.08f, 0.05f, 0.15f, 1);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Container principal
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Título principal com estilo
        titulo = new Label("TCG PARADIGMA", skin);
        titulo.setFontScale(3f);
        titulo.setColor(new Color(0.9f, 0.7f, 1f, 1)); // Roxo claro
        titulo.setAlignment(Align.center);

        // Subtítulo
        subtitulo = new Label("Bem-vindo ao jogo de cartas!", skin);
        subtitulo.setFontScale(1.2f);
        subtitulo.setColor(new Color(0.7f, 0.7f, 0.8f, 1));
        subtitulo.setAlignment(Align.center);

        // Container para os botões
        Table botoesTable = new Table();
        botoesTable.defaults().width(300).height(65).pad(12);

        // Criação dos botões estilizados
        TextButton botaoLogin = criarBotaoEstilizado("🎮 ENTRAR", skin, new Color(0.3f, 0.6f, 0.9f, 1));
        TextButton botaoCriarConta = criarBotaoEstilizado("✨ CRIAR CONTA", skin, new Color(0.5f, 0.8f, 0.4f, 1));
        TextButton botaoSair = criarBotaoEstilizado("🚪 SAIR", skin, new Color(0.8f, 0.4f, 0.4f, 1));

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

        // Adiciona efeitos de hover
        adicionarEfeitoHover(botaoLogin);
        adicionarEfeitoHover(botaoCriarConta);
        adicionarEfeitoHover(botaoSair);

        // Monta o layout
        mainTable.add(titulo).padBottom(15).row();
        mainTable.add(subtitulo).padBottom(50).row();

        botoesTable.add(botaoLogin).row();
        botoesTable.add(botaoCriarConta).row();
        botoesTable.add(botaoSair).row();

        mainTable.add(botoesTable);

        BitmapFont fontAcento = new BitmapFont(Gdx.files.internal("fonts/utf8Menor.fnt"));

        // Rodapé com instrução de música
        Label.LabelStyle estilo = skin.get(Label.LabelStyle.class);
        estilo = new Label.LabelStyle(estilo);
        estilo.font = fontAcento;
        Label avisoMusica = new Label("Clique na tela para iniciar a música!", estilo);

        avisoMusica.setFontScale(1.4f);
        avisoMusica.setColor(new Color(0.5f, 0.5f, 0.6f, 0.8f));

        // Animação piscante para o aviso
        if (!Main.musicaLiberada) {
            avisoMusica.addAction(Actions.forever(
                Actions.sequence(
                    Actions.fadeOut(0.8f),
                    Actions.fadeIn(0.8f)
                )
            ));
        }

        Table footer = new Table();
        footer.setFillParent(true);
        footer.bottom();
        footer.add(avisoMusica).padBottom(20);

        // Adiciona tudo ao stage
        stage.addActor(mainTable);
        stage.addActor(footer);

        // Animação de entrada suave
        mainTable.setColor(1, 1, 1, 0);
        mainTable.addAction(Actions.fadeIn(0.6f));

        footer.setColor(1, 1, 1, 0);
        footer.addAction(Actions.sequence(
            Actions.delay(0.3f),
            Actions.fadeIn(0.5f)
        ));
    }

    private TextButton criarBotaoEstilizado(String texto, Skin skin, Color cor) {
        TextButton botao = new TextButton(texto, skin);
        botao.getLabel().setFontScale(1.4f);
        botao.setColor(cor);
        return botao;
    }

    private void adicionarEfeitoHover(final TextButton botao) {
        final Color corOriginal = botao.getColor().cpy();

        botao.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                if (pointer == -1) { // Apenas para mouse hover, não toque
                    botao.addAction(Actions.scaleTo(1.08f, 1.08f, 0.15f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                if (pointer == -1) {
                    botao.addAction(Actions.scaleTo(1f, 1f, 0.15f));
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        // Ativa música no primeiro clique
        if (Gdx.input.justTouched() && !Main.musicaLiberada) {
            Main.musicaFundo.play();
            Main.musicaLiberada = true;

            // Remove o aviso de música com fade out
            for (com.badlogic.gdx.scenes.scene2d.Actor actor : stage.getActors()) {
                if (actor instanceof Table) {
                    Table table = (Table) actor;
                    for (com.badlogic.gdx.scenes.scene2d.Actor child : table.getChildren()) {
                        if (child instanceof Label) {
                            Label label = (Label) child;
                            if (label.getText().toString().contains("música")) {
                                label.clearActions();
                                label.addAction(Actions.fadeOut(1f));
                            }
                        }
                    }
                }
            }
        }

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
