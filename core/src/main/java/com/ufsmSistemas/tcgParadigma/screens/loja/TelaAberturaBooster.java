package com.ufsmSistemas.tcgParadigma.screens.loja;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.models.*;
import com.ufsmSistemas.tcgParadigma.utils.AnimacaoCartas;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.utils.CartaVisual;

import java.util.ArrayList;
import java.util.List;

public class TelaAberturaBooster extends TelaBase {
    private List<CartaVisual> cartasVisuais;
    private AnimacaoCartas animacao;
    private Stage stageUI;
    private SpriteBatch batch;
    private TextButton btnVoltar;
    private Texture fundoCarta;
    private Label titulo;
    private Label instrucao;
    private Label contadorCartas;
    private Skin skinUI;

    private static final float ESPACAMENTO = 30f;
    private float larguraCarta;
    private float alturaCarta;
    private boolean todasCartasReveladas = false;

    public void setCartas(List<Carta> cartas) {
        this.cartasVisuais.clear();
        for (Carta carta : cartas) {
            cartasVisuais.add(new CartaVisual(carta, fundoCarta));
        }
        animacao = new AnimacaoCartas(cartasVisuais);
        atualizarContador();
    }

    public TelaAberturaBooster(Main game, Booster booster) {
        super(game);
        // Cores para tema de abertura - tons de roxo/violeta místico
        corFundoTop = new Color(0.25f, 0.15f, 0.35f, 1);
        corFundoBottom = new Color(0.1f, 0.05f, 0.2f, 1);

        this.batch = new SpriteBatch();
        this.stageUI = new Stage(new ScreenViewport());
        this.cartasVisuais = new ArrayList<>();

        fundoCarta = new Texture(Gdx.files.internal("boosterImg/imgCartaFundo.png"));

        criarInterface();

        if (booster != null) {
            carregarCartas(booster);
        }
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(stageUI);   // Primeiro: UI (botão voltar)
        multiplexer.addProcessor(stage);     // Segundo: caso o TelaBase tenha UI ou lógica
        // (opcional: multiplexer.addProcessor(...));

        Gdx.input.setInputProcessor(multiplexer);
    }

    private void carregarCartas(Booster booster) {
        for (Carta carta : booster.getCartasBooster()) {
            cartasVisuais.add(new CartaVisual(carta, fundoCarta));
        }
        animacao = new AnimacaoCartas(cartasVisuais);
        atualizarContador();
    }

    private void criarInterface() {
        skinUI = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Container superior
        Table topTable = new Table();
        topTable.setFillParent(true);
        topTable.top().pad(30);

        // Título
        titulo = new Label("✨ ABRINDO BOOSTER ✨", skinUI);
        titulo.setFontScale(2.2f);
        titulo.setColor(new Color(1f, 0.85f, 0.3f, 1));
        titulo.setAlignment(Align.center);

        // Animação de brilho no título
        titulo.addAction(Actions.forever(
            Actions.sequence(
                Actions.color(new Color(1f, 0.95f, 0.5f, 1), 0.8f),
                Actions.color(new Color(1f, 0.85f, 0.3f, 1), 0.8f)
            )
        ));

        // Instrução
        instrucao = new Label("Aguarde enquanto as cartas são reveladas...", skinUI);
        instrucao.setFontScale(1.2f);
        instrucao.setColor(new Color(0.8f, 0.8f, 0.9f, 1));
        instrucao.setAlignment(Align.center);

        // Animação piscante na instrução
        instrucao.addAction(Actions.forever(
            Actions.sequence(
                Actions.alpha(0.5f, 0.6f),
                Actions.alpha(1f, 0.6f)
            )
        ));

        // Contador de cartas reveladas
        contadorCartas = new Label("0/0 cartas reveladas", skinUI);
        contadorCartas.setFontScale(1f);
        contadorCartas.setColor(new Color(0.7f, 0.7f, 0.8f, 1));
        contadorCartas.setAlignment(Align.center);

        topTable.add(titulo).padBottom(10).row();
        topTable.add(instrucao).padBottom(5).row();
        topTable.add(contadorCartas).padBottom(10);

        // Botão Voltar
        btnVoltar = new TextButton("← VOLTAR À LOJA", skinUI);
        btnVoltar.getLabel().setFontScale(1.3f);
        btnVoltar.setColor(new Color(0.4f, 0.7f, 0.9f, 1));
        btnVoltar.setSize(250, 60);
        btnVoltar.setPosition(Gdx.graphics.getWidth() / 2f - 125, 40);
        btnVoltar.setVisible(false);

        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botão Voltar clicado!"); // Debug
                voltarParaLoja();
            }
        });

        // Garante que o botão está acima de tudo
        btnVoltar.setZIndex(1000);

        // Efeito hover no botão
        adicionarEfeitoHover(btnVoltar);

        stageUI.addActor(topTable);
        stageUI.addActor(btnVoltar);

        // Animação de entrada
        topTable.setColor(1, 1, 1, 0);
        topTable.addAction(Actions.fadeIn(0.8f));
    }

    private void adicionarEfeitoHover(final TextButton botao) {
        botao.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    botao.clearActions();
                    botao.addAction(Actions.scaleTo(1.08f, 1.08f, 0.15f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    botao.clearActions();
                    botao.addAction(Actions.scaleTo(1f, 1f, 0.15f));
                }
            }
        });
    }

    private void atualizarContador() {
        if (animacao != null && contadorCartas != null) {
            int reveladas = 0;
            for (CartaVisual cv : cartasVisuais) {
                if (cv.isVirada()) reveladas++;
            }
            contadorCartas.setText(reveladas + "/" + cartasVisuais.size() + " cartas reveladas");

            // Anima o contador quando atualiza
            contadorCartas.clearActions();
            contadorCartas.addAction(Actions.sequence(
                Actions.scaleTo(1.2f, 1.2f, 0.1f),
                Actions.scaleTo(1f, 1f, 0.1f)
            ));
        }
    }

    @Override
    public void render(float delta) {
        // Desenha o fundo gradiente
        desenharFundoGradiente();

        // Atualiza animações
        if (animacao != null) {
            animacao.atualizar(delta);
        }

        // Desenha as cartas
        batch.begin();
        desenharCartas();
        batch.end();

        // Atualiza contador
        atualizarContador();

        // Verifica se todas as cartas foram reveladas
        if (animacao != null && animacao.isTodasViradas() && !todasCartasReveladas) {
            todasCartasReveladas = true;
            mostrarBotaoVoltar();
        }

        // Desenha UI
        stageUI.act(delta);
        stageUI.draw();
    }

    private void mostrarBotaoVoltar() {
        // Atualiza mensagens
        titulo.setText("🎉 TODAS AS CARTAS REVELADAS! 🎉");
        titulo.clearActions();
        titulo.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.4f),
                Actions.scaleTo(1f, 1f, 0.4f)
            )
        ));
        btnVoltar.setVisible(true);
        instrucao.setText("Confira suas novas cartas no álbum!");
        instrucao.clearActions();
        instrucao.setColor(new Color(0.4f, 0.9f, 0.5f, 1));

        // Mostra botão com animação

        btnVoltar.setTouchable(Touchable.enabled);
        btnVoltar.toFront(); // Traz para frente
        btnVoltar.setColor(1, 1, 1, 0);
        btnVoltar.clearActions();
        btnVoltar.addAction(Actions.sequence(
            Actions.delay(0.3f),
            Actions.fadeIn(0.5f)
        ));

        System.out.println("Todas as cartas foram reveladas!");
        System.out.println("Botão visível: " + btnVoltar.isVisible());
        System.out.println("Botão touchable: " + btnVoltar.getTouchable());
        System.out.println("Posição do botão: " + btnVoltar.getX() + ", " + btnVoltar.getY());
    }

    private void voltarParaLoja() {
        System.out.println("Voltando para a loja...");
        game.setScreen(new TelaLoja(game));
        dispose();
    }

    private void desenharCartas() {
        if (cartasVisuais.isEmpty()) return;

        float larguraTela = Gdx.graphics.getWidth();
        larguraCarta = larguraTela / 6.5f;
        alturaCarta = larguraCarta * 1.4f;

        float larguraTotal = cartasVisuais.size() * larguraCarta + (cartasVisuais.size() - 1) * ESPACAMENTO;
        float xInicial = (larguraTela - larguraTotal) / 2f;
        float yPosicao = (Gdx.graphics.getHeight() - alturaCarta) / 2f;

        for (int i = 0; i < cartasVisuais.size(); i++) {
            float x = xInicial + i * (larguraCarta + ESPACAMENTO);
            cartasVisuais.get(i).desenhar(batch, x, yPosicao, larguraCarta, alturaCarta);
        }
    }

    @Override
    public void resize(int width, int height) {
        stageUI.getViewport().update(width, height, true);

        // Reposiciona o botão voltar
        if (btnVoltar != null) {
            btnVoltar.setPosition(width / 2f - 125, 40);
        }
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (stageUI != null) stageUI.dispose();
        if (fundoCarta != null) fundoCarta.dispose();
        if (skinUI != null) skinUI.dispose();

        for (CartaVisual cv : cartasVisuais) {
            if (cv != null) cv.dispose();
        }

        super.dispose();
    }
}
