package com.ufsmSistemas.tcgParadigma.screens.album;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.data.Session;
import com.ufsmSistemas.tcgParadigma.models.Carta;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.screens.TelaInicialJogo;
import com.ufsmSistemas.tcgParadigma.services.AlbumService;

public class TelaAlbum extends TelaBase {
    private AlbumService albumService;
    private Table gridCartas;
    private ScrollPane scrollPane;
    private Label lblCarregando;
    private Label lblTotal;
    private TextButton btnVoltar;
    private TextButton btnFiltroTodas;
    private TextButton btnFiltroObtidas;
    private TextButton btnFiltroFaltando;

    private Array<Carta> todasCartas;
    private String filtroAtual = "todas";

    private static final int CARTAS_POR_LINHA = 5;
    private static final float LARGURA_CARTA = 230f;
    private static final float ALTURA_CARTA = 210f;
    private static final float ESPACAMENTO = 14f;

    public TelaAlbum(Main game) {
        super(game);
        this.albumService = new AlbumService();
        this.todasCartas = new Array<Carta>();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(30);
        stage.addActor(mainTable);

        // ======== TÍTULO ========
        Label titulo = new Label("🎴 MEU ÁLBUM DE CARTAS", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        titulo.setFontScale(2.4f);
        mainTable.add(titulo).colspan(4).padBottom(25).center().row();

        // ======== ESTATÍSTICAS ========
        lblTotal = new Label("Carregando...", new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        lblTotal.setFontScale(1.3f);
        mainTable.add(lblTotal).colspan(4).padBottom(20).center().row();

        // ======== FILTROS ========
        Table filtrosTable = new Table();
        BitmapFont font = new BitmapFont();

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font;
        btnStyle.fontColor = Color.WHITE;
        btnStyle.overFontColor = Color.YELLOW;
        btnStyle.downFontColor = Color.GRAY;

        btnFiltroTodas = new TextButton("Todas", btnStyle);
        btnFiltroObtidas = new TextButton("Obtidas", btnStyle);
        btnFiltroFaltando = new TextButton("Faltando", btnStyle);

        filtrosTable.add(btnFiltroTodas).width(130).height(45).pad(6);
        filtrosTable.add(btnFiltroObtidas).width(130).height(45).pad(6);
        filtrosTable.add(btnFiltroFaltando).width(130).height(45).pad(6);

        mainTable.add(filtrosTable).colspan(4).padBottom(25).center().row();

        // ======== GRID DE CARTAS ========
        gridCartas = new Table();
        gridCartas.defaults().pad(ESPACAMENTO / 2);
        scrollPane = new ScrollPane(gridCartas);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        mainTable.add(scrollPane).colspan(4).expand().fill().padBottom(25).row();

        // ======== LABEL DE CARREGAMENTO ========
        lblCarregando = new Label("Carregando cartas...", new Label.LabelStyle(font, Color.LIGHT_GRAY));
        lblCarregando.setFontScale(1.3f);
        gridCartas.add(lblCarregando).center();

        // ======== BOTÃO VOLTAR ========
        btnVoltar = new TextButton("⮜ Voltar", btnStyle);
        mainTable.add(btnVoltar).colspan(4).width(220).height(55).center().padTop(10);

        // ======== LISTENERS ========
        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaInicialJogo(game));
            }
        });

        btnFiltroTodas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aplicarFiltro("todas");
            }
        });

        btnFiltroObtidas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aplicarFiltro("obtidas");
            }
        });

        btnFiltroFaltando.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aplicarFiltro("faltando");
            }
        });

        carregarAlbum();
    }

    private void carregarAlbum() {
        final int idJogador = Session.getInstance().getJogador().getId();

        // Buscar todas as cartas (assíncrono)
        albumService.buscarTodasCartas(new AlbumService.CartasCallback() {
            @Override
            public void onSuccess(final Array<Carta> cartasBD) {
                // Após carregar todas as cartas, buscar cartas do jogador
                albumService.buscarCartasJogador(idJogador, new AlbumService.CartasCallback() {
                    @Override
                    public void onSuccess(Array<Carta> cartasJogador) {
                        // Marcar cartas obtidas
                        for (Carta cartaBD : cartasBD) {
                            cartaBD.setObtida(false);
                            cartaBD.setQuantidade(0);
                            for (Carta cartaJogador : cartasJogador) {
                                if (cartaBD.getId() == cartaJogador.getId()) {
                                    cartaBD.setObtida(true);
                                    cartaBD.setQuantidade(cartaJogador.getQuantidade());
                                    break;
                                }
                            }
                        }

                        todasCartas = cartasBD;

                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                atualizarEstatisticas();
                                exibirCartas(todasCartas);
                            }
                        });
                    }

                    @Override
                    public void onError(String erro) {
                        System.err.println("Erro ao carregar cartas do jogador: " + erro);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                lblCarregando.setText("Erro ao carregar álbum!");
                                lblCarregando.setColor(Color.RED);
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String erro) {
                System.err.println("Erro ao carregar todas as cartas: " + erro);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        lblCarregando.setText("Erro ao carregar álbum!");
                        lblCarregando.setColor(Color.RED);
                    }
                });
            }
        });
    }

    private void aplicarFiltro(String filtro) {
        filtroAtual = filtro;
        Array<Carta> cartasFiltradas = new Array<Carta>();

        for (Carta carta : todasCartas) {
            if (filtro.equals("todas") ||
                (filtro.equals("obtidas") && carta.isObtida()) ||
                (filtro.equals("faltando") && !carta.isObtida())) {
                cartasFiltradas.add(carta);
            }
        }

        exibirCartas(cartasFiltradas);

        btnFiltroTodas.getLabel().setColor(filtro.equals("todas") ? Color.GOLD : Color.WHITE);
        btnFiltroObtidas.getLabel().setColor(filtro.equals("obtidas") ? Color.GOLD : Color.WHITE);
        btnFiltroFaltando.getLabel().setColor(filtro.equals("faltando") ? Color.GOLD : Color.WHITE);
    }

    private void atualizarEstatisticas() {
        int total = todasCartas.size;
        int obtidas = 0;
        for (Carta c : todasCartas) {
            if (c.isObtida()) obtidas++;
        }
        float p = (float) obtidas / total * 100f;

        // Formatar porcentagem manualmente (compatível com GWT)
        int pInt = (int) p;
        int pDecimal = (int) ((p - pInt) * 10);

        lblTotal.setText("Progresso: " + obtidas + "/" + total + " cartas (" + pInt + "." + pDecimal + "%)");
    }

    private void exibirCartas(Array<Carta> cartas) {
        gridCartas.clear();

        if (cartas.size == 0) {
            Label lblVazio = new Label("Nenhuma carta encontrada", new Label.LabelStyle(new BitmapFont(), Color.GRAY));
            lblVazio.setFontScale(1.4f);
            gridCartas.add(lblVazio).center();
            return;
        }

        int coluna = 0;
        for (Carta carta : cartas) {
            Table card = criarCardCarta(carta);
            gridCartas.add(card).width(LARGURA_CARTA).height(ALTURA_CARTA + 55);
            coluna++;
            if (coluna >= CARTAS_POR_LINHA) {
                gridCartas.row();
                coluna = 0;
            }
        }
    }

    private Table criarCardCarta(final Carta carta) {
        Table card = new Table();
        card.setBackground(new Image(criarBordaSuave(carta.isObtida() ? new Color(0.2f, 0.2f, 0.2f, 1f) : new Color(0.1f, 0.1f, 0.1f, 0.9f))).getDrawable());

        Stack stack = new Stack();
        Image img;

        if (carta.isObtida()) {
            try {
                String caminho = carta.getCaminhoImagem().replace("assets/", "");
                Texture textura = new Texture(Gdx.files.internal(caminho));
                img = new Image(textura);
            } catch (Exception e) {
                img = criarImagemPlaceholder(Color.DARK_GRAY);
            }
        } else {
            img = criarImagemPlaceholder(new Color(0.15f, 0.15f, 0.15f, 0.8f));
            Label lblLock = new Label("🔒", new Label.LabelStyle(new BitmapFont(), Color.LIGHT_GRAY));
            lblLock.setFontScale(2.5f);
            stack.add(lblLock);
        }

        stack.add(img);
        card.add(stack).width(LARGURA_CARTA).height(ALTURA_CARTA).row();

        Label lblNome = new Label(carta.isObtida() ? carta.getNome() : "???",
            new Label.LabelStyle(new BitmapFont(), carta.isObtida() ? Color.WHITE : Color.DARK_GRAY));
        lblNome.setAlignment(Align.center);
        lblNome.setFontScale(0.8f);
        lblNome.setWrap(true);
        card.add(lblNome).padTop(5).width(LARGURA_CARTA).row();

        if (carta.isObtida()) {
            Label lblQtd = new Label("x" + carta.getQuantidade(),
                new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
            lblQtd.setFontScale(0.9f);
            card.add(lblQtd).padTop(3);
        }

        card.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (carta.isObtida()) mostrarDetalhesCarta(carta);
            }
        });

        return card;
    }

    private Texture criarBordaSuave(Color corFundo) {
        int w = (int) LARGURA_CARTA;
        int h = (int) ALTURA_CARTA;
        Pixmap pm = new Pixmap(w, h, Pixmap.Format.RGBA8888);

        pm.setColor(corFundo);
        pm.fill();

        pm.setColor(new Color(1, 1, 1, 0.2f));
        for (int i = 0; i < 6; i++) {
            pm.drawRectangle(i, i, w - 2 * i, h - 2 * i);
        }

        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    private Image criarImagemPlaceholder(Color cor) {
        Pixmap p = new Pixmap((int) LARGURA_CARTA, (int) ALTURA_CARTA, Pixmap.Format.RGBA8888);
        p.setColor(cor);
        p.fill();
        Texture t = new Texture(p);
        p.dispose();
        return new Image(t);
    }

    private void mostrarDetalhesCarta(Carta carta) {
        Dialog dialog = new Dialog("", new Skin(Gdx.files.internal("skin/uiskin.json")));
        Table content = new Table();
        content.pad(20);

        try {
            String caminho = carta.getCaminhoImagem().replace("assets/", "");
            Texture textura = new Texture(Gdx.files.internal(caminho));
            Image img = new Image(textura);
            content.add(img).width(280).height(380).row();
        } catch (Exception ignored) {}

        Label lblNome = new Label(carta.getNome(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblNome.setFontScale(1.5f);
        content.add(lblNome).padTop(10).row();

        Label lblRaridade = new Label("Raridade: " + carta.getRaridade(), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        content.add(lblRaridade).padTop(5).row();

        Label lblCategoria = new Label("Categoria: " + carta.getCategoria(), new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        content.add(lblCategoria).padTop(5).row();

        Label lblQtd = new Label("Você possui: " + carta.getQuantidade(), new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        content.add(lblQtd).padTop(8).row();

        dialog.getContentTable().add(content);
        dialog.button("Fechar");
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        // fundo gradiente simples
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.05f, 0.07f, 0.12f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
