package com.ufsmSistemas.tcgParadigma.screens.album;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
    private Label lblProgresso;
    private TextButton btnVoltar;
    private TextButton btnFiltroTodas;
    private TextButton btnFiltroObtidas;
    private Skin skinUI;

    private Array<Carta> todasCartas;
    private String filtroAtual = "todas";

    private static final int CARTAS_POR_LINHA = 5;
    private static final float LARGURA_CARTA = 200f;
    private static final float ALTURA_CARTA = 280f;
    private static final float ESPACAMENTO = 18f;

    public TelaAlbum(Main game) {
        super(game);
        this.albumService = new AlbumService();
        this.todasCartas = new Array<Carta>();
        // Cores para tema de álbum - tons de azul escuro
        corFundoTop = new Color(0.12f, 0.15f, 0.25f, 1);
        corFundoBottom = new Color(0.05f, 0.07f, 0.12f, 1);
    }

    @Override
    public void show() {
        skinUI = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(20);
        stage.addActor(mainTable);

        // ======== CABEÇALHO ========
        Table headerTable = new Table();
        headerTable.setBackground(skinUI.getDrawable("default-round"));
        headerTable.pad(20);

        // Título
        Label titulo = new Label("📚 MEU ÁLBUM", skinUI);
        titulo.setFontScale(2.5f);
        titulo.setColor(new Color(0.9f, 0.7f, 1f, 1));

        // Animação no título
        titulo.addAction(Actions.forever(
            Actions.sequence(
                Actions.color(new Color(1f, 0.8f, 1f, 1), 1.5f),
                Actions.color(new Color(0.9f, 0.7f, 1f, 1), 1.5f)
            )
        ));

        // Estatísticas
        lblTotal = new Label("Carregando...", skinUI);
        lblTotal.setFontScale(1.2f);
        lblTotal.setColor(new Color(0.7f, 0.9f, 1f, 1));

        // Barra de progresso visual
        lblProgresso = new Label("", skinUI);
        lblProgresso.setFontScale(1f);
        lblProgresso.setColor(new Color(1f, 0.85f, 0.3f, 1));

        headerTable.add(titulo).padBottom(10).row();
        headerTable.add(lblTotal).padBottom(5).row();
        headerTable.add(lblProgresso).padBottom(5);

        mainTable.add(headerTable).colspan(4).padBottom(20).center().fillX().row();

        // ======== FILTROS ========
        Table filtrosTable = new Table();
        filtrosTable.pad(10);

        btnFiltroTodas = criarBotaoFiltro("📋 TODAS");
        btnFiltroObtidas = criarBotaoFiltro("✅ OBTIDAS");

        filtrosTable.add(btnFiltroTodas).width(150).height(50).pad(8);
        filtrosTable.add(btnFiltroObtidas).width(150).height(50).pad(8);

        mainTable.add(filtrosTable).colspan(4).padBottom(20).center().row();

        // ======== GRID DE CARTAS ========
        gridCartas = new Table();
        gridCartas.defaults().pad(ESPACAMENTO / 2);
        scrollPane = new ScrollPane(gridCartas, skinUI);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setOverscroll(false, false);
        mainTable.add(scrollPane).colspan(4).expand().fill().padBottom(20).row();

        // ======== LABEL DE CARREGAMENTO ========
        lblCarregando = new Label("🔄 Carregando cartas...", skinUI);
        lblCarregando.setFontScale(1.4f);
        lblCarregando.setColor(new Color(0.7f, 0.7f, 0.8f, 1));
        lblCarregando.addAction(Actions.forever(
            Actions.sequence(
                Actions.alpha(0.5f, 0.8f),
                Actions.alpha(1f, 0.8f)
            )
        ));
        gridCartas.add(lblCarregando).center();

        // ======== BOTÃO VOLTAR ========
        btnVoltar = new TextButton("← VOLTAR", skinUI);
        btnVoltar.getLabel().setFontScale(1.3f);
        btnVoltar.setColor(new Color(0.6f, 0.6f, 0.7f, 1));
        mainTable.add(btnVoltar).colspan(4).width(220).height(55).center().padTop(10);

        // ======== LISTENERS ========
        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Voltando para tela inicial...");
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

        // Efeitos hover
        adicionarEfeitoHover(btnVoltar);
        adicionarEfeitoHover(btnFiltroTodas);
        adicionarEfeitoHover(btnFiltroObtidas);

        // Animação de entrada
        mainTable.setColor(1, 1, 1, 0);
        mainTable.addAction(Actions.fadeIn(0.5f));

        carregarAlbum();
    }

    private TextButton criarBotaoFiltro(String texto) {
        TextButton btn = new TextButton(texto, skinUI);
        btn.getLabel().setFontScale(1.1f);
        return btn;
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
                                lblCarregando.setText("❌ Erro ao carregar álbum!");
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
                        lblCarregando.setText("❌ Erro ao carregar álbum!");
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

        // Atualiza cores dos botões
        Color corAtivo = new Color(0.4f, 0.8f, 0.4f, 1);
        Color corInativo = new Color(0.6f, 0.6f, 0.7f, 1);

        btnFiltroTodas.setColor(filtro.equals("todas") ? corAtivo : corInativo);
        btnFiltroObtidas.setColor(filtro.equals("obtidas") ? corAtivo : corInativo);
    }

    private void atualizarEstatisticas() {
        int total = todasCartas.size;
        int obtidas = 0;
        for (Carta c : todasCartas) {
            if (c.isObtida()) obtidas++;
        }
        float p = (float) obtidas / total * 100f;

        int pInt = (int) p;
        int pDecimal = (int) ((p - pInt) * 10);

        lblTotal.setText("📊 Progresso: " + obtidas + "/" + total + " cartas (" + pInt + "." + pDecimal + "%)");

        // Barra de progresso visual
        int barras = (int) (p / 5); // 20 barras = 100%
        StringBuilder barra = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            if (i < barras) {
                barra.append("█");
            } else {
                barra.append("░");
            }
        }
        lblProgresso.setText(barra.toString());
    }

    private void exibirCartas(Array<Carta> cartas) {
        gridCartas.clear();

        if (cartas.size == 0) {
            Label lblVazio = new Label("😕 Nenhuma carta encontrada", skinUI);
            lblVazio.setFontScale(1.5f);
            lblVazio.setColor(new Color(0.6f, 0.6f, 0.7f, 1));
            gridCartas.add(lblVazio).center();
            return;
        }

        int coluna = 0;
        for (Carta carta : cartas) {
            Table card = criarCardCarta(carta);
            gridCartas.add(card).width(LARGURA_CARTA).height(ALTURA_CARTA + 70);
            coluna++;
            if (coluna >= CARTAS_POR_LINHA) {
                gridCartas.row();
                coluna = 0;
            }
        }
    }

    private Table criarCardCarta(final Carta carta) {
        final Table card = new Table();
        card.setBackground(skinUI.getDrawable("default-round"));
        card.pad(10);

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
            Label lblLock = new Label("🔒", skinUI);
            lblLock.setFontScale(3f);
            lblLock.setColor(new Color(0.5f, 0.5f, 0.6f, 1));
            stack.add(lblLock);
        }

        stack.add(img);
        card.add(stack).width(LARGURA_CARTA - 20).height(ALTURA_CARTA - 50).row();

        Label lblNome = new Label(carta.isObtida() ? carta.getNome() : "???", skinUI);
        lblNome.setAlignment(Align.center);
        lblNome.setFontScale(0.9f);
        lblNome.setWrap(true);
        lblNome.setColor(carta.isObtida() ? new Color(1f, 1f, 1f, 1) : new Color(0.5f, 0.5f, 0.5f, 1));
        card.add(lblNome).padTop(8).width(LARGURA_CARTA - 20).row();

        if (carta.isObtida()) {
            Label lblQtd = new Label("x" + carta.getQuantidade(), skinUI);
            lblQtd.setFontScale(1f);
            lblQtd.setColor(new Color(1f, 0.85f, 0.3f, 1));
            card.add(lblQtd).padTop(5);
        }

        // Efeito hover no card
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
                    card.addAction(Actions.scaleTo(1.05f, 1.05f, 0.15f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                if (pointer == -1) {
                    card.addAction(Actions.scaleTo(1f, 1f, 0.15f));
                }
            }
        });

        return card;
    }

    private Image criarImagemPlaceholder(Color cor) {
        Pixmap p = new Pixmap((int) (LARGURA_CARTA - 20), (int) (ALTURA_CARTA - 50), Pixmap.Format.RGBA8888);
        p.setColor(cor);
        p.fill();
        Texture t = new Texture(p);
        p.dispose();
        return new Image(t);
    }

    private void mostrarDetalhesCarta(Carta carta) {
        Dialog dialog = new Dialog("", skinUI);
        dialog.getTitleLabel().setFontScale(1.5f);

        Table content = new Table();
        content.pad(25);

        try {
            String caminho = carta.getCaminhoImagem().replace("assets/", "");
            Texture textura = new Texture(Gdx.files.internal(caminho));
            Image img = new Image(textura);
            content.add(img).width(300).height(400).row();
        } catch (Exception ignored) {}

        Label lblNome = new Label(carta.getNome(), skinUI);
        lblNome.setFontScale(1.6f);
        lblNome.setColor(new Color(0.9f, 0.7f, 1f, 1));
        content.add(lblNome).padTop(15).row();

        Label lblRaridade = new Label("✨ Raridade: " + carta.getRaridade(), skinUI);
        lblRaridade.setFontScale(1.1f);
        lblRaridade.setColor(new Color(1f, 0.85f, 0.3f, 1));
        content.add(lblRaridade).padTop(8).row();

        Label lblCategoria = new Label("📂 Categoria: " + carta.getCategoria(), skinUI);
        lblCategoria.setFontScale(1.1f);
        lblCategoria.setColor(new Color(0.5f, 0.8f, 1f, 1));
        content.add(lblCategoria).padTop(5).row();

        Label lblQtd = new Label("💎 Você possui: " + carta.getQuantidade(), skinUI);
        lblQtd.setFontScale(1.1f);
        lblQtd.setColor(new Color(0.4f, 0.9f, 0.5f, 1));
        content.add(lblQtd).padTop(8).row();

        dialog.getContentTable().add(content);

        TextButton btnFechar = new TextButton("FECHAR", skinUI);
        btnFechar.getLabel().setFontScale(1.2f);
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
        if (skinUI != null) skinUI.dispose();
        super.dispose();
    }
}
