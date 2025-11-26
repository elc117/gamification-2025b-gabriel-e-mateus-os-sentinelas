package com.ufsmSistemas.tcgParadigma.screens.album;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
    private Container<Table> progressContainer;
    private TextButton btnVoltar;
    private TextButton btnFiltroTodas;
    private TextButton btnFiltroObtidas;
    private Skin skinUI;
    private BitmapFont fonteCustomizada;

    private Array<Carta> todasCartas;
    private String filtroAtual = "todas";

    private static final int CARTAS_POR_LINHA = 5;
    private static final float LARGURA_CARTA = 200f;
    private static final float ALTURA_CARTA = 280f;
    private static final float ESPACAMENTO = 20f;

    // Cores modernas
    private static final Color COR_PRIMARIA = new Color(0.4f, 0.6f, 1f, 1); // Azul vibrante
    private static final Color COR_SECUNDARIA = new Color(0.9f, 0.5f, 1f, 1); // Roxo suave
    private static final Color COR_SUCESSO = new Color(0.3f, 0.9f, 0.5f, 1); // Verde neon
    private static final Color COR_DOURADO = new Color(1f, 0.85f, 0.3f, 1);
    private static final Color COR_TEXTO_CLARO = new Color(0.95f, 0.95f, 0.98f, 1);
    private static final Color COR_CARD_BG = new Color(0.15f, 0.18f, 0.25f, 0.95f);

    public TelaAlbum(Main game) {
        super(game);
        this.albumService = new AlbumService();
        this.todasCartas = new Array<Carta>();

        corFundoTop = new Color(0.08f, 0.12f, 0.22f, 1);
        corFundoBottom = new Color(0.12f, 0.08f, 0.18f, 1);
    }

    @Override
    public void show() {
        skinUI = new Skin(Gdx.files.internal("skin/uiskin.json"));
        fonteCustomizada = new BitmapFont(Gdx.files.internal("fonts/utf8Menor.fnt"));
        customizarSkin();

        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(30);
        stage.addActor(mainTable);

        // Cabeçalho
        Table headerTable = new Table();
        headerTable.pad(25);

        // Título
        Label.LabelStyle tituloStyle = new Label.LabelStyle();
        tituloStyle.font = fonteCustomizada;
        tituloStyle.fontColor = COR_SECUNDARIA;

        Label titulo = new Label("COLECAO", tituloStyle);
        titulo.setFontScale(3f);

        // Animação
        titulo.addAction(Actions.forever(
            Actions.sequence(
                Actions.color(COR_PRIMARIA, 2f),
                Actions.color(COR_SECUNDARIA, 2f)
            )
        ));

        // Estatísticas
        Label.LabelStyle statsStyle = new Label.LabelStyle();
        statsStyle.font = fonteCustomizada;
        statsStyle.fontColor = COR_TEXTO_CLARO;

        lblTotal = new Label("Carregando...", statsStyle);
        lblTotal.setFontScale(1.5f);

        // Container para barra de progresso
        progressContainer = new Container<Table>();
        progressContainer.setBackground(skinUI.getDrawable("default-round"));
        progressContainer.pad(10);

        headerTable.add(titulo).padBottom(20).row();
        headerTable.add(lblTotal).padBottom(15).row();
        headerTable.add(progressContainer).width(500).height(40);

        mainTable.add(headerTable).colspan(4).padBottom(30).center().fillX().row();

        // Filtros
        Table filtrosTable = new Table();
        filtrosTable.pad(15);

        btnFiltroTodas = criarBotaoModerno("TODAS");
        btnFiltroObtidas = criarBotaoModerno("OBTIDAS");

        filtrosTable.add(btnFiltroTodas).width(180).height(60).pad(10);
        filtrosTable.add(btnFiltroObtidas).width(180).height(60).pad(10);

        mainTable.add(filtrosTable).colspan(4).padBottom(25).center().row();

        // Grid de cartas
        gridCartas = new Table();
        gridCartas.defaults().pad(ESPACAMENTO / 2);
        scrollPane = new ScrollPane(gridCartas, skinUI);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setOverscroll(false, false);
        mainTable.add(scrollPane).colspan(4).expand().fill().padBottom(25).row();

        // Label de carregamento
        Label.LabelStyle loadingStyle = new Label.LabelStyle();
        loadingStyle.font = fonteCustomizada;
        loadingStyle.fontColor = COR_PRIMARIA;

        lblCarregando = new Label("Carregando cartas...", loadingStyle);
        lblCarregando.setFontScale(1.8f);
        lblCarregando.addAction(Actions.forever(
            Actions.sequence(
                Actions.alpha(0.4f, 1f),
                Actions.alpha(1f, 1f)
            )
        ));
        gridCartas.add(lblCarregando).center();

        // Voltar
        btnVoltar = criarBotaoModerno("VOLTAR");
        btnVoltar.setColor(new Color(0.5f, 0.5f, 0.6f, 1));
        mainTable.add(btnVoltar).colspan(4).width(250).height(65).center().padTop(15);


        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adicionarEfeitoClickESair(btnVoltar, new TelaInicialJogo(game));
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

        // Efeitos hover
        adicionarEfeitoHover(btnVoltar);
        adicionarEfeitoHover(btnFiltroTodas);
        adicionarEfeitoHover(btnFiltroObtidas);

        // Animação de entrada
        mainTable.setColor(1, 1, 1, 0);
        mainTable.addAction(Actions.fadeIn(0.7f));

        carregarAlbum();
    }

    private void customizarSkin() {
        TextButton.TextButtonStyle customButtonStyle = new TextButton.TextButtonStyle(
            skinUI.get(TextButton.TextButtonStyle.class)
        );
        customButtonStyle.font = fonteCustomizada;
        skinUI.add("default", customButtonStyle, TextButton.TextButtonStyle.class);
    }

    private TextButton criarBotaoModerno(String texto) {
        TextButton btn = new TextButton(texto, skinUI);
        btn.getLabel().setFontScale(1.4f);
        return btn;
    }

    private void adicionarEfeitoHover(final TextButton botao) {
        botao.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                if (pointer == -1) {
                    botao.addAction(Actions.sequence(
                        Actions.scaleTo(1.08f, 1.08f, 0.15f),
                        Actions.color(COR_PRIMARIA, 0.15f)
                    ));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                if (pointer == -1) {
                    botao.addAction(Actions.parallel(
                        Actions.scaleTo(1f, 1f, 0.15f),
                        Actions.color(Color.WHITE, 0.15f)
                    ));
                }
            }
        });
    }

    private void adicionarEfeitoClickESair(final TextButton botao, final TelaBase proximaTela) {
        botao.addAction(Actions.sequence(
            Actions.scaleTo(0.95f, 0.95f, 0.1f),
            Actions.scaleTo(1f, 1f, 0.1f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(proximaTela);
                }
            })
        ));
    }

    private void carregarAlbum() {
        final int idJogador = Session.getInstance().getJogador().getId();

        albumService.buscarTodasCartas(new AlbumService.CartasCallback() {
            @Override
            public void onSuccess(final Array<Carta> cartasBD) {
                albumService.buscarCartasJogador(idJogador, new AlbumService.CartasCallback() {
                    @Override
                    public void onSuccess(Array<Carta> cartasJogador) {
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
                                aplicarFiltro("todas");
                            }
                        });
                    }

                    @Override
                    public void onError(String erro) {
                        System.err.println("Erro ao carregar cartas do jogador: " + erro);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                lblCarregando.setText("Erro ao carregar album!");
                                lblCarregando.setColor(Color.RED);
                                lblCarregando.clearActions();
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
                        lblCarregando.setText("Erro ao carregar album!");
                        lblCarregando.setColor(Color.RED);
                        lblCarregando.clearActions();
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
                (filtro.equals("obtidas") && carta.isObtida())) {
                cartasFiltradas.add(carta);
            }
        }

        exibirCartas(cartasFiltradas);

        btnFiltroTodas.setColor(filtro.equals("todas") ? COR_SUCESSO : Color.WHITE);
        btnFiltroObtidas.setColor(filtro.equals("obtidas") ? COR_SUCESSO : Color.WHITE);
    }

    private void atualizarEstatisticas() {
        int total = todasCartas.size;
        int obtidas = 0;
        for (Carta c : todasCartas) {
            if (c.isObtida()) obtidas++;
        }
        float percentual = (float) obtidas / total * 100f;

        int pInt = (int) percentual;
        int pDecimal = (int) ((percentual - pInt) * 10);

        lblTotal.setText("Progresso: " + obtidas + "/" + total + " cartas (" + pInt + "." + pDecimal + "%)");

        Table progressBar = new Table();

        // Barra de fundo
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0.2f, 0.2f, 0.3f, 0.8f));
        bgPixmap.fill();
        Texture bgTexture = new Texture(bgPixmap);
        bgPixmap.dispose();

        // Barra de progresso preenchida
        Pixmap fillPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        fillPixmap.setColor(COR_SUCESSO);
        fillPixmap.fill();
        Texture fillTexture = new Texture(fillPixmap);
        fillPixmap.dispose();

        Image bgBar = new Image(bgTexture);
        Image fillBar = new Image(fillTexture);

        Stack stackBar = new Stack();
        stackBar.add(bgBar);

        Container<Image> fillContainer = new Container<Image>(fillBar);
        fillContainer.align(Align.left);
        fillContainer.width(480 * (percentual / 100f));
        stackBar.add(fillContainer);

        progressBar.add(stackBar).width(480).height(30);

        // Animação da barra
        fillContainer.addAction(Actions.sequence(
            Actions.sizeTo(0, 30, 0),
            Actions.sizeTo(480 * (percentual / 100f), 30, 1.2f)
        ));

        progressContainer.setActor(progressBar);
    }

    private void exibirCartas(Array<Carta> cartas) {
        gridCartas.clear();

        if (cartas.size == 0) {
            Label.LabelStyle vazioStyle = new Label.LabelStyle();
            vazioStyle.font = fonteCustomizada;
            vazioStyle.fontColor = new Color(0.5f, 0.5f, 0.6f, 1);

            Label lblVazio = new Label("Nenhuma carta encontrada", vazioStyle);
            lblVazio.setFontScale(1.8f);
            gridCartas.add(lblVazio).center();
            return;
        }

        int coluna = 0;
        for (Carta carta : cartas) {
            Table card = criarCardModerno(carta);
            gridCartas.add(card).width(LARGURA_CARTA).height(ALTURA_CARTA + 80);
            coluna++;
            if (coluna >= CARTAS_POR_LINHA) {
                gridCartas.row();
                coluna = 0;
            }
        }
    }

    private Table criarCardModerno(final Carta carta) {
        final Table card = new Table();

        Pixmap cardBg = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        cardBg.setColor(COR_CARD_BG);
        cardBg.fill();
        Texture cardTexture = new Texture(cardBg);
        cardBg.dispose();
        card.setBackground(new Image(cardTexture).getDrawable());

        card.pad(12);

        Stack stack = new Stack();
        Image img;

        if (carta.isObtida()) {
            try {
                String caminho = carta.getCaminhoImagem().replace("assets/", "");
                Texture textura = new Texture(Gdx.files.internal(caminho));
                img = new Image(textura);

                // Brilho sutil na carta obtida
                img.addAction(Actions.forever(
                    Actions.sequence(
                        Actions.alpha(0.95f, 1.5f),
                        Actions.alpha(1f, 1.5f)
                    )
                ));
            } catch (Exception e) {
                img = criarImagemPlaceholder(Color.DARK_GRAY);
            }
        } else {
            img = criarImagemPlaceholder(new Color(0.1f, 0.1f, 0.15f, 0.9f));

            Label.LabelStyle lockStyle = new Label.LabelStyle();
            lockStyle.font = fonteCustomizada;
            lockStyle.fontColor = new Color(0.4f, 0.4f, 0.5f, 1);

            Label lblLock = new Label("?", lockStyle);
            lblLock.setFontScale(4f);
            stack.add(lblLock);
        }

        stack.add(img);
        card.add(stack).width(LARGURA_CARTA - 24).height(ALTURA_CARTA - 50).row();

        Label.LabelStyle nomeStyle = new Label.LabelStyle();
        nomeStyle.font = fonteCustomizada;
        nomeStyle.fontColor = carta.isObtida() ? COR_TEXTO_CLARO : new Color(0.4f, 0.4f, 0.45f, 1);

        Label lblNome = new Label(carta.isObtida() ? carta.getNome() : "???", nomeStyle);
        lblNome.setAlignment(Align.center);
        lblNome.setFontScale(1.1f);
        lblNome.setWrap(true);
        card.add(lblNome).padTop(10).width(LARGURA_CARTA - 24).row();

        if (carta.isObtida()) {
            Label.LabelStyle qtdStyle = new Label.LabelStyle();
            qtdStyle.font = fonteCustomizada;
            qtdStyle.fontColor = COR_DOURADO;

            Label lblQtd = new Label("x" + carta.getQuantidade(), qtdStyle);
            lblQtd.setFontScale(1.2f);
            card.add(lblQtd).padTop(6);
        }

        // Efeito hover moderno
        card.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (carta.isObtida()) {
                    mostrarDetalhesCarta(carta);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                if (pointer == -1 && carta.isObtida()) {
                    card.addAction(Actions.sequence(
                        Actions.scaleTo(1.08f, 1.08f, 0.2f),
                        Actions.forever(
                            Actions.sequence(
                                Actions.scaleTo(1.1f, 1.1f, 0.5f),
                                Actions.scaleTo(1.08f, 1.08f, 0.5f)
                            )
                        )
                    ));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                if (pointer == -1) {
                    card.clearActions();
                    card.addAction(Actions.scaleTo(1f, 1f, 0.2f));
                }
            }
        });

        return card;
    }

    private Image criarImagemPlaceholder(Color cor) {
        Pixmap p = new Pixmap((int) (LARGURA_CARTA - 24), (int) (ALTURA_CARTA - 50), Pixmap.Format.RGBA8888);
        p.setColor(cor);
        p.fill();
        Texture t = new Texture(p);
        p.dispose();
        return new Image(t);
    }

    private void mostrarDetalhesCarta(Carta carta) {
        Dialog dialog = new Dialog("", skinUI);
        dialog.getTitleLabel().setFontScale(1.8f);

        Table content = new Table();
        content.pad(30);

        try {
            String caminho = carta.getCaminhoImagem().replace("assets/", "");
            Texture textura = new Texture(Gdx.files.internal(caminho));
            Image img = new Image(textura);
            content.add(img).width(350).height(450).row();
        } catch (Exception ignored) {}

        Label.LabelStyle detailStyle = new Label.LabelStyle();
        detailStyle.font = fonteCustomizada;
        detailStyle.fontColor = COR_SECUNDARIA;

        Label lblNome = new Label(carta.getNome(), detailStyle);
        lblNome.setFontScale(2f);
        content.add(lblNome).padTop(20).row();

        detailStyle.fontColor = COR_DOURADO;
        Label lblRaridade = new Label("Raridade: " + carta.getRaridade(), detailStyle);
        lblRaridade.setFontScale(1.3f);
        content.add(lblRaridade).padTop(12).row();

        detailStyle.fontColor = COR_PRIMARIA;
        Label lblCategoria = new Label("Categoria: " + carta.getCategoria(), detailStyle);
        lblCategoria.setFontScale(1.3f);
        content.add(lblCategoria).padTop(8).row();

        detailStyle.fontColor = COR_SUCESSO;
        Label lblQtd = new Label("Voce possui: " + carta.getQuantidade(), detailStyle);
        lblQtd.setFontScale(1.3f);
        content.add(lblQtd).padTop(12).row();

        dialog.getContentTable().add(content);

        TextButton btnFechar = criarBotaoModerno("FECHAR");
        dialog.button(btnFechar);

        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        desenharFundoGradiente();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        if (fonteCustomizada != null) fonteCustomizada.dispose();
        if (skinUI != null) skinUI.dispose();
        super.dispose();
    }
}
