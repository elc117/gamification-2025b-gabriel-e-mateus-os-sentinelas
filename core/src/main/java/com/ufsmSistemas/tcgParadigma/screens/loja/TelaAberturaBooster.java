package com.ufsmSistemas.tcgParadigma.screens.loja;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private Stage stage;
    private SpriteBatch batch;
    private TextButton btnVoltar;
    private Texture fundoCarta;

    private static final float ESPACAMENTO = 30f;
    private float larguraCarta;
    private float alturaCarta;

    public void setCartas(List<Carta> cartas) {
        this.cartasVisuais.clear();
        for (Carta carta : cartas) {
            cartasVisuais.add(new CartaVisual(carta, fundoCarta));
        }
        animacao = new AnimacaoCartas(cartasVisuais);
    }


    public TelaAberturaBooster(Main game, Booster booster) {
        super(game);
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        this.cartasVisuais = new ArrayList<>();
        Gdx.input.setInputProcessor(stage);

        fundoCarta = new Texture(Gdx.files.internal("boosterImg/imgCartaFundo.png"));

        criarBotaoVoltar();
    }

    private void carregarCartas(Booster booster) {
        for (Carta carta : booster.getCartasBooster()) {
            cartasVisuais.add(new CartaVisual(carta, fundoCarta));
        }
        animacao = new AnimacaoCartas(cartasVisuais);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        animacao.atualizar(delta);

        batch.begin();
        desenharCartas();
        batch.end();

        if (animacao.isTodasViradas()) btnVoltar.setVisible(true);
        stage.act(delta);
        stage.draw();
    }

    private void desenharCartas() {
        float larguraTela = Gdx.graphics.getWidth();
        larguraCarta = larguraTela / 6f;
        alturaCarta = larguraCarta * 1.3f;

        float larguraTotal = cartasVisuais.size() * larguraCarta + (cartasVisuais.size() - 1) * ESPACAMENTO;
        float xInicial = (larguraTela - larguraTotal) / 2f;
        float yPosicao = (Gdx.graphics.getHeight() - alturaCarta) / 2f;

        for (int i = 0; i < cartasVisuais.size(); i++) {
            float x = xInicial + i * (larguraCarta + ESPACAMENTO);
            cartasVisuais.get(i).desenhar(batch, x, yPosicao, larguraCarta, alturaCarta);
        }
    }

    private void criarBotaoVoltar() {
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default", font);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        skin.add("default", style);

        btnVoltar = new TextButton("Voltar", skin);
        btnVoltar.setSize(200, 50);
        btnVoltar.setPosition(Gdx.graphics.getWidth() / 2f - 100, 50);
        btnVoltar.setVisible(false);
        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaLoja(game));
            }
        });
        stage.addActor(btnVoltar);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        fundoCarta.dispose();
        for (CartaVisual cv : cartasVisuais) cv.dispose();
    }
}
