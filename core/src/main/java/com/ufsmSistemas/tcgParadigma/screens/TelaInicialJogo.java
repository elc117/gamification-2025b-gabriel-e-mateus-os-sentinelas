package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.data.Session;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import com.ufsmSistemas.tcgParadigma.screens.album.TelaAlbum;
import com.ufsmSistemas.tcgParadigma.screens.loja.TelaLoja;
import com.ufsmSistemas.tcgParadigma.screens.quiz.TelaOpcoesQuiz;

public class TelaInicialJogo extends TelaBase {
    private Label titulo;
    private DesenhaMoedaTela desenhaMoeda;
    private Jogador jogador;
    private BitmapFont fonteCustomizada;

    public TelaInicialJogo(Main game) {
        super(game);
        // Cores personalizadas para um tema mais atraente
        corFundoTop = new Color(0.15f, 0.2f, 0.4f, 1);
        corFundoBottom = new Color(0.05f, 0.08f, 0.2f, 1);
        desenhaMoeda = new DesenhaMoedaTela();
        jogador = Session.getInstance().getJogador();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Carrega a fonte customizada
        fonteCustomizada = new BitmapFont(Gdx.files.internal("fonts/utf8Menor.fnt"));

        // Customiza o estilo dos botões com a fonte customizada
        customizarSkin();

        // Container principal
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Título do jogo com estilo
        Label.LabelStyle tituloStyle = new Label.LabelStyle();
        tituloStyle.font = fonteCustomizada;
        tituloStyle.fontColor = new Color(1f, 0.9f, 0.3f, 1); // Dourado

        titulo = new Label("TCG PARADIGMA", tituloStyle);
        titulo.setFontScale(2.5f);
        titulo.setAlignment(Align.center);

        // Adiciona animação ao título
        titulo.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleTo(1.05f, 1.05f, 0.8f),
                Actions.scaleTo(1f, 1f, 0.8f)
            )
        ));

        // Container para os botões principais
        Table botoesTable = new Table();
        botoesTable.defaults().width(280).height(70).pad(15);

        // Cria botões
        final TextButton botaoAlbum = criarBotaoEstilizado("ÁLBUM", skin);
        final TextButton botaoQuiz = criarBotaoEstilizado("QUIZ", skin);
        final TextButton botaoLoja = criarBotaoEstilizado("LOJA", skin);

        // Adiciona listeners
        botaoAlbum.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adicionarEfeitoCliqueESair(botaoAlbum, new TelaAlbum(game));
            }
        });

        botaoQuiz.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adicionarEfeitoCliqueESair(botaoQuiz, new TelaOpcoesQuiz(game));
            }
        });

        botaoLoja.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adicionarEfeitoCliqueESair(botaoLoja, new TelaLoja(game));
            }
        });

        // Adiciona efeitos de hover nos botões
        adicionarEfeitoHover(botaoAlbum);
        adicionarEfeitoHover(botaoQuiz);
        adicionarEfeitoHover(botaoLoja);

        // Layout
        mainTable.add(titulo).padBottom(60).row();
        mainTable.add(botaoLoja).row();

        // Container horizontal para Álbum e Quiz
        Table horizontalTable = new Table();
        horizontalTable.add(botaoAlbum).padRight(30);
        horizontalTable.add(botaoQuiz);

        mainTable.add(horizontalTable).padTop(20);

        // Adiciona versão/créditos no rodapé
        Label.LabelStyle creditosStyle = new Label.LabelStyle();
        creditosStyle.font = fonteCustomizada;
        creditosStyle.fontColor = new Color(0.6f, 0.6f, 0.7f, 1);

        Label creditos = new Label("v1.0 - UFSM Sistemas", creditosStyle);
        creditos.setFontScale(0.7f);

        Table footer = new Table();
        footer.setFillParent(true);
        footer.bottom();
        footer.add(creditos).padBottom(10);

        stage.addActor(mainTable);
        stage.addActor(footer);

        // Animação de entrada
        mainTable.setColor(1, 1, 1, 0);
        mainTable.addAction(Actions.fadeIn(0.5f));
    }

    private void customizarSkin() {
        // Cria um novo estilo de botão com a fonte customizada
        TextButton.TextButtonStyle customButtonStyle = new TextButton.TextButtonStyle(
            skin.get(TextButton.TextButtonStyle.class)
        );
        customButtonStyle.font = fonteCustomizada;

        // Adiciona o novo estilo ao skin
        skin.add("default", customButtonStyle, TextButton.TextButtonStyle.class);
    }

    private TextButton criarBotaoEstilizado(String texto, Skin skin) {
        TextButton botao = new TextButton(texto, skin);
        botao.getLabel().setFontScale(1.3f);
        return botao;
    }

    private void adicionarEfeitoHover(final TextButton botao) {
        botao.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                botao.addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                botao.addAction(Actions.scaleTo(1f, 1f, 0.1f));
            }
        });
    }

    private void adicionarEfeitoCliqueESair(final TextButton botao, final TelaBase proximaTela) {
        botao.addAction(Actions.sequence(
            Actions.scaleTo(0.9f, 0.9f, 0.1f),
            Actions.scaleTo(1f, 1f, 0.1f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(proximaTela);
                }
            })
        ));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        desenhaMoeda.desenhar(batch, jogador.getPontos());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        if (fonteCustomizada != null) {
            fonteCustomizada.dispose();
        }
        desenhaMoeda.dispose();
        super.dispose();
    }
}
