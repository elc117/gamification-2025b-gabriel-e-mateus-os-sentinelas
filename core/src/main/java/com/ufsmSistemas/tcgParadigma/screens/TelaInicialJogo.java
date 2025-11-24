package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    // Texturas para os botões de imagem
    private Texture texturaAlbum;
    private Texture texturaQuiz;
    private Texture texturaLoja;

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

        // Carrega as texturas dos ícones
        // Coloque as imagens na pasta assets/icons/
        texturaAlbum = new Texture(Gdx.files.internal("icons/album.png"));
        texturaQuiz = new Texture(Gdx.files.internal("icons/quiz.png"));
        texturaLoja = new Texture(Gdx.files.internal("icons/loja.png"));

        // Container principal que preenche a tela
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Título do jogo com estilo no centro
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

        // Cria ImageButtons para cada ícone
        final ImageButton botaoAlbum = criarBotaoImagem(texturaAlbum);
        final ImageButton botaoQuiz = criarBotaoImagem(texturaQuiz);
        final ImageButton botaoLoja = criarBotaoImagem(texturaLoja);

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

        // Cria legendas para os botões
        Label.LabelStyle legendaStyle = new Label.LabelStyle();
        legendaStyle.font = fonteCustomizada;
        legendaStyle.fontColor = new Color(1f, 0.9f, 0.3f, 1);

        Label legendaLoja = new Label("LOJA", legendaStyle);
        legendaLoja.setFontScale(1.5f);
        legendaLoja.setAlignment(Align.center);

        Label legendaAlbum = new Label("ALBUM", legendaStyle);
        legendaAlbum.setFontScale(1.5f);
        legendaAlbum.setAlignment(Align.center);

        Label legendaQuiz = new Label("QUIZ", legendaStyle);
        legendaQuiz.setFontScale(1.5f);
        legendaQuiz.setAlignment(Align.center);

        // Layout: Loja no topo com legenda
        mainTable.top();
        Table containerLoja = new Table();
        containerLoja.add(botaoLoja).size(400, 400).row();
        containerLoja.add(legendaLoja).padTop(10);
        mainTable.add(containerLoja).padTop(50).row();

        // Espaço para empurrar o título para o centro
        mainTable.add().expandY();

        // Título no centro
        mainTable.row();
        mainTable.add(titulo).center();

        // Espaço inferior
        mainTable.row();
        mainTable.add().expandY();

        // Container para os botões laterais (álbum e quiz)
        Table botoesLaterais = new Table();
        botoesLaterais.setFillParent(true);

        // Container Álbum na esquerda com legenda
        Table containerAlbum = new Table();
        containerAlbum.add(botaoAlbum).size(400, 400).row();
        containerAlbum.add(legendaAlbum).padTop(10);
        botoesLaterais.add(containerAlbum).left().expandX().padLeft(50);

        // Container Quiz na direita com legenda
        Table containerQuiz = new Table();
        containerQuiz.add(botaoQuiz).size(400, 400).row();
        containerQuiz.add(legendaQuiz).padTop(10);
        botoesLaterais.add(containerQuiz).right().expandX().padRight(50);

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
        stage.addActor(botoesLaterais);
        stage.addActor(footer);

        // Animação de entrada
        mainTable.setColor(1, 1, 1, 0);
        mainTable.addAction(Actions.fadeIn(0.5f));
        botoesLaterais.setColor(1, 1, 1, 0);
        botoesLaterais.addAction(Actions.fadeIn(0.5f));
    }

    private ImageButton criarBotaoImagem(Texture textura) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(textura);
        return new ImageButton(style);
    }

    private void adicionarEfeitoHover(final ImageButton botao) {
        botao.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                botao.addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                botao.addAction(Actions.scaleTo(1f, 1f, 0.1f));
            }
        });
    }

    private void adicionarEfeitoCliqueESair(final ImageButton botao, final TelaBase proximaTela) {
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
        if (texturaAlbum != null) {
            texturaAlbum.dispose();
        }
        if (texturaQuiz != null) {
            texturaQuiz.dispose();
        }
        if (texturaLoja != null) {
            texturaLoja.dispose();
        }
        desenhaMoeda.dispose();
        super.dispose();
    }
}
