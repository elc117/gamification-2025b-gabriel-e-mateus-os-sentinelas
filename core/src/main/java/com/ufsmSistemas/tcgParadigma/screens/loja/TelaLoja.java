package com.ufsmSistemas.tcgParadigma.screens.loja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.data.Session;
import com.ufsmSistemas.tcgParadigma.models.Booster;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import com.ufsmSistemas.tcgParadigma.screens.DesenhaMoedaTela;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.screens.TelaInicialJogo;

public class TelaLoja extends TelaBase {
    private Texture texturaBooster;
    private Label mensagemStatus;
    private final Jogador jogador;
    private DesenhaMoedaTela desenhaMoeda;

    public TelaLoja(Main game) {
        super(game);

        corFundoTop = new Color(0.35f, 0.25f, 0.15f, 1);
        corFundoBottom = new Color(0.15f, 0.1f, 0.05f, 1);
        jogador = Session.getInstance().getJogador();
        desenhaMoeda = new DesenhaMoedaTela();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        try {
            texturaBooster = new Texture(Gdx.files.internal("assets/boosterImg/imgBoosterComum.png"));
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem do booster: " + e.getMessage());
            try {
                texturaBooster = new Texture(Gdx.files.internal("boosterImg/imgBoosterComum.png"));
            } catch (Exception e2) {
                System.out.println("Erro ao carregar imagem (segunda tentativa): " + e2.getMessage());
            }
        }

        // Container principal
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Título da loja
        Label titulo = new Label("🛒 LOJA DE BOOSTERS", skin);
        titulo.setFontScale(2.5f);
        titulo.setColor(new Color(1f, 0.85f, 0.3f, 1)); // Dourado
        titulo.setAlignment(Align.center);

        // Animação de brilho no título
        titulo.addAction(Actions.forever(
            Actions.sequence(
                Actions.color(new Color(1f, 0.95f, 0.5f, 1), 1f),
                Actions.color(new Color(1f, 0.85f, 0.3f, 1), 1f)
            )
        ));

        // Subtítulo
        Label subtitulo = new Label("Abra pacotes e expanda sua coleção!", skin);
        subtitulo.setFontScale(1.2f);
        subtitulo.setColor(new Color(0.9f, 0.8f, 0.6f, 1));
        subtitulo.setAlignment(Align.center);

        // Container para os boosters
        Table boostersContainer = new Table();
        boostersContainer.defaults().pad(20);

        // Cria os cards de booster
        Table cardBooster1 = criarCardBooster("Booster Comum", "150 moedas", 1);

        boostersContainer.add(cardBooster1).width(280).height(420);

        // Mensagem de status
        mensagemStatus = new Label("", skin);
        mensagemStatus.setFontScale(1.1f);
        mensagemStatus.setAlignment(Align.center);

        // Botão Voltar
        TextButton botaoVoltar = new TextButton("← VOLTAR", skin);
        botaoVoltar.getLabel().setFontScale(1.2f);
        botaoVoltar.setColor(new Color(0.6f, 0.6f, 0.7f, 1));
        botaoVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaInicialJogo(game));
            }
        });
        adicionarEfeitoHover(botaoVoltar);

        // Layout principal
        mainTable.add(titulo).padTop(30).padBottom(10).row();
        mainTable.add(subtitulo).padBottom(40).row();
        mainTable.add(boostersContainer).expandY().row();
        mainTable.add(mensagemStatus).padBottom(20).row();
        mainTable.add(botaoVoltar).width(200).height(50).padBottom(20);

        stage.addActor(mainTable);

        // Animação de entrada
        mainTable.setColor(1, 1, 1, 0);
        mainTable.addAction(Actions.fadeIn(0.5f));
    }

    private Table criarCardBooster(String nome, String preco, int tipo) {
        Table card = new Table();
        card.setBackground(skin.getDrawable("default-round"));
        card.pad(20);

        // Imagem do booster
        Image imagemBooster;
        if (texturaBooster != null) {
            imagemBooster = new Image(texturaBooster);
            imagemBooster.setSize(200, 250);
        } else {
            // Placeholder se não carregar a imagem
            Label placeholder = new Label("📦", skin);
            placeholder.setFontScale(8f);
            imagemBooster = new Image(skin.getDrawable("default-round"));
        }

        // Nome do booster
        Label labelNome = new Label(nome, skin);
        labelNome.setFontScale(1.4f);
        labelNome.setColor(new Color(1f, 0.9f, 0.7f, 1));
        labelNome.setAlignment(Align.center);

        // Preço
        Label labelPreco = new Label(preco, skin);
        labelPreco.setFontScale(1.1f);
        labelPreco.setColor(new Color(0.9f, 0.7f, 0.3f, 1));
        labelPreco.setAlignment(Align.center);

        // Botão de compra
        TextButton botaoComprar = new TextButton("🎁 ABRIR", skin);
        botaoComprar.getLabel().setFontScale(1.2f);
        botaoComprar.setColor(new Color(0.4f, 0.8f, 0.4f, 1)); // Verde

        botaoComprar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                abrirBooster(card, botaoComprar);
            }
        });

        // Efeito hover no botão
        adicionarEfeitoHover(botaoComprar);

        // Efeito hover no card inteiro
        card.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                if (pointer == -1) {
                    card.addAction(Actions.scaleTo(1.05f, 1.05f, 0.2f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                if (pointer == -1) {
                    card.addAction(Actions.scaleTo(1f, 1f, 0.2f));
                }
            }
        });

        // Monta o card
        if (texturaBooster != null) {
            card.add(imagemBooster).size(180, 220).padBottom(15).row();
        } else {
            Label emoji = new Label("📦", skin);
            emoji.setFontScale(6f);
            card.add(emoji).padBottom(15).row();
        }
        card.add(labelNome).padBottom(5).row();
        card.add(labelPreco).padBottom(15).row();
        card.add(botaoComprar).width(180).height(45);

        return card;
    }

    private void adicionarEfeitoHover(final TextButton botao) {
        botao.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                if (pointer == -1) {
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

    private void abrirBooster(Table card, TextButton botao) {
        if (jogador.getPontos() >= 150) {
            // Desabilita o botão
            botao.setDisabled(true);
            botao.setText("ABRINDO...");

            // Mensagem de status
            mensagemStatus.setText("🎁 Abrindo booster...");
            mensagemStatus.setColor(new Color(0.3f, 0.7f, 1f, 1));
            mensagemStatus.clearActions();
            mensagemStatus.setColor(0.3f, 0.7f, 1f, 0);
            mensagemStatus.addAction(Actions.fadeIn(0.3f));

            // Animação no card
            card.addAction(Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.2f),
                Actions.scaleTo(0.95f, 0.95f, 0.2f),
                Actions.scaleTo(1f, 1f, 0.2f)
            ));

            new Booster(cartas -> {
                Gdx.app.postRunnable(() -> {
                    mensagemStatus.setText("✅ Booster aberto com sucesso!");
                    mensagemStatus.setColor(new Color(0.3f, 0.9f, 0.4f, 1));

                    // Pequeno delay antes de mudar de tela
                    stage.addAction(Actions.sequence(
                        Actions.delay(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                TelaAberturaBooster telaBooster = new TelaAberturaBooster(game, null);
                                telaBooster.setCartas(cartas);
                                game.setScreen(telaBooster);
                            }
                        })
                    ));
                });

            });
        }else{
            mensagemStatus.setText("Pontos Insuficientes para abrir o Booster!");
            mensagemStatus.setColor(new Color(0.8f, 0.1f, 1f, 1));
            mensagemStatus.clearActions();
            mensagemStatus.setColor(0.3f, 0.7f, 1f, 0);
            mensagemStatus.addAction(Actions.fadeIn(0.3f));
        }
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
        desenhaMoeda.dispose();
        super.dispose();
        if (texturaBooster != null) {
            texturaBooster.dispose();
        }
    }
}
