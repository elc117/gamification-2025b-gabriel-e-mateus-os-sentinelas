package com.ufsmSistemas.tcgParadigma.screens.loja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.models.Booster;
import com.ufsmSistemas.tcgParadigma.models.Carta;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;

import java.util.ArrayList;
import java.util.List;

public class TelaAberturaBooster extends TelaBase {
    private List<Carta> cartas;
    private List<Texture> texturasFundo;
    private List<Texture> texturasFrente;
    private List<Float> angulosCartas;
    private List<Boolean> cartasViradas;

    private Stage stage;
    private SpriteBatch batch;
    private TextButton btnVoltar;
    private Skin skin;

    private float tempoDecorrido;
    private boolean animacaoIniciada;
    private boolean todasViradas;
    private boolean cartasCarregadas;
    private int cartaAtualVirando = 0;
    private float tempoEntreCartas = 0.4f;
    private float tempoDesdeUltimaVirada = 0f;

    private Booster booster;
    private boolean verificandoCartas;

    private static final float TEMPO_VIRAR = 0.5f;
    private static final float DURACAO_ANIMACAO = 0.5f;

    private Texture fundoCarta;

    float larguraTela = Gdx.graphics.getWidth();
    float LARGURA_CARTA = larguraTela / 5f; // Aumentado: agora usa 1/5.5 da tela (antes era 1/6)
    float ALTURA_CARTA = LARGURA_CARTA * (1086f / 1086f); // Proporção correta
    private static final float ESPACAMENTO = 4f; // Reduzido o espaço entre cartas

    public TelaAberturaBooster(Main game, Booster booster) {
        super(game);
        this.booster = booster;
        this.cartas = new ArrayList<Carta>();
        this.texturasFundo = new ArrayList<Texture>();
        this.texturasFrente = new ArrayList<Texture>();
        this.angulosCartas = new ArrayList<Float>();
        this.cartasViradas = new ArrayList<Boolean>();
        this.tempoDecorrido = 0;
        this.animacaoIniciada = false;
        this.todasViradas = false;
        this.cartasCarregadas = false;
        this.verificandoCartas = (booster != null); // Só verifica se o booster foi passado

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());

        try {
            fundoCarta = new Texture(Gdx.files.internal("boosterImg/imgCartaFundo.png"));
            System.out.println("[TelaBooster] Textura de fundo carregada com sucesso!");
        } catch (Exception e) {
            System.err.println("[TelaBooster] Erro ao carregar imagem do fundo: " + e.getMessage());
            fundoCarta = null;
        }

        criarBotaoVoltar();
    }

    public void setCartas(List<Carta> cartas) {
        System.out.println("[TelaBooster] ===== RECEBENDO CARTAS =====");
        System.out.println("[TelaBooster] Número de cartas recebidas: " + cartas.size());

        this.cartas = cartas;
        carregarTexturas();
        this.cartasCarregadas = true;

        System.out.println("[TelaBooster] Cartas configuradas com sucesso!");
        System.out.println("[TelaBooster] cartasCarregadas = " + this.cartasCarregadas);
    }

    private void carregarTexturas() {
        texturasFundo.clear();
        texturasFrente.clear();
        angulosCartas.clear();
        cartasViradas.clear();

        for (int i = 0; i < cartas.size(); i++) {
            Carta carta = cartas.get(i);
            texturasFundo.add(fundoCarta);

            try {
                Texture texFrente = new Texture(Gdx.files.internal(carta.getCaminhoImagem()));
                texturasFrente.add(texFrente);
                System.out.println("[TelaBooster] Carta " + i + " carregada: " + carta.getNome());
            } catch (Exception e) {
                System.err.println("[TelaBooster] Erro ao carregar carta " + i + ": " + carta.getCaminhoImagem());
                texturasFrente.add(null);
            }

            angulosCartas.add(0f);
            cartasViradas.add(false);
        }

        System.out.println("[TelaBooster] Total de texturas carregadas: " + texturasFrente.size());
    }

    private void criarBotaoVoltar() {
        skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.downFontColor = Color.GRAY;
        textButtonStyle.overFontColor = Color.LIGHT_GRAY;

        skin.add("default", textButtonStyle);

        btnVoltar = new TextButton("Voltar para Loja", skin);
        btnVoltar.setSize(200, 50);
        btnVoltar.setPosition(
            Gdx.graphics.getWidth() / 2f - 100,
            50
        );
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
    public void show() {
        System.out.println("[TelaBooster] ===== SHOW CHAMADO =====");
        System.out.println("[TelaBooster] Cartas carregadas: " + cartasCarregadas);
        System.out.println("[TelaBooster] Número de cartas: " + cartas.size());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Verifica se as cartas do booster foram carregadas
        if (verificandoCartas && booster != null) {
            List<Carta> cartasBooster = booster.getCartasBooster();

            if (cartasBooster != null && !cartasBooster.isEmpty()) {
                // Verifica se todas as cartas têm caminho de imagem válido
                boolean todasValidas = true;
                int cartasComImagem = 0;
                for (Carta c : cartasBooster) {
                    if (c != null && c.getCaminhoImagem() != null && !c.getCaminhoImagem().isEmpty()) {
                        cartasComImagem++;
                    } else {
                        todasValidas = false;
                    }
                }

                if (cartasBooster.size() >= 5 && cartasComImagem >= 5) {
                    System.out.println("[TelaBooster] ===== CARTAS PRONTAS! CARREGANDO TEXTURAS =====");
                    setCartas(cartasBooster);
                    verificandoCartas = false;
                }
            }
        }

        if (!cartasCarregadas) {
            // Aguardando cartas serem carregadas
            batch.begin();
            // Desenha texto de carregamento
            batch.end();

            stage.act(delta);
            stage.draw();
            return;
        }

        tempoDecorrido += delta;
        atualizarAnimacao(delta);

        batch.begin();
        desenharCartas();
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    private void atualizarAnimacao(float delta) {
        if (!cartasCarregadas || cartas.isEmpty()) return;

        if (tempoDecorrido >= TEMPO_VIRAR && !animacaoIniciada) {
            animacaoIniciada = true;
            System.out.println("[TelaBooster] Iniciando animação de virar cartas uma por uma");
        }

        if (!animacaoIniciada || todasViradas) return;

        tempoDesdeUltimaVirada += delta;

        // Só vira uma carta de cada vez, mas sem travar a sequência
        if (cartaAtualVirando < cartas.size()) {
            float anguloAtual = angulosCartas.get(cartaAtualVirando);
            anguloAtual += (180f / DURACAO_ANIMACAO) * delta;
            anguloAtual = Math.min(anguloAtual, 180f);
            angulosCartas.set(cartaAtualVirando, anguloAtual);

            if (anguloAtual >= 180f && !cartasViradas.get(cartaAtualVirando)) {
                cartasViradas.set(cartaAtualVirando, true);
                System.out.println("[TelaBooster] Carta " + cartaAtualVirando + " virada completamente");
                tempoDesdeUltimaVirada = 0f;
            }

            // Depois de um pequeno delay, avança para a próxima carta
            if (cartasViradas.get(cartaAtualVirando) && tempoDesdeUltimaVirada >= tempoEntreCartas) {
                cartaAtualVirando++;
                tempoDesdeUltimaVirada = 0f;
            }
        }

        // Quando todas virarem, mostra o botão
        if (cartaAtualVirando >= cartas.size()) {
            todasViradas = true;
            btnVoltar.setVisible(true);
            System.out.println("[TelaBooster] Todas as cartas foram viradas!");
        }
    }

    private void desenharCartas() {
        if (cartas.isEmpty()) return;

        float larguraTotal = cartas.size() * LARGURA_CARTA + (cartas.size() - 1) * ESPACAMENTO;
        float xInicial = (Gdx.graphics.getWidth() - larguraTotal) / 2f;
        float yPosicao = (Gdx.graphics.getHeight() - ALTURA_CARTA) / 2f;

        for (int i = 0; i < cartas.size(); i++) {
            float xPosicao = xInicial + i * (LARGURA_CARTA + ESPACAMENTO);
            float angulo = angulosCartas.get(i);

            // Efeito de flip 3D
            float escalaX = Math.abs((float) Math.cos(Math.toRadians(angulo)));
            if (escalaX < 0.05f) escalaX = 0.05f;

            // Escolhe textura baseado no ângulo
            Texture texturaAtual = (angulo < 90f) ? texturasFundo.get(i) : texturasFrente.get(i);

            if (texturaAtual != null) {
                float larguraDesenhada = LARGURA_CARTA * escalaX;
                float xCentrada = xPosicao + (LARGURA_CARTA - larguraDesenhada) / 2f;

                batch.draw(texturaAtual, xCentrada, yPosicao, larguraDesenhada, ALTURA_CARTA);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        btnVoltar.setPosition(width / 2f - 100, 50);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();

        if (skin != null) {
            skin.dispose();
        }

        if (fundoCarta != null) {
            fundoCarta.dispose();
        }

        for (Texture tex : texturasFrente) {
            if (tex != null) {
                tex.dispose();
            }
        }
    }
}
