package com.ufsmSistemas.tcgParadigma.screens.quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;

public class TelaOpcoesQuiz extends TelaBase {
    public TelaOpcoesQuiz(Main game) {
        super(game);
    }
    public BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/utf8.fnt"));

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Skin
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Tabela principal com background
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        stage.addActor(mainTable);

        // Título da tela
        Label.LabelStyle titleStyle = new Label.LabelStyle(font, new Color(1f, 0.85f, 0.3f, 1f));
        Label titulo = new Label("SELECIONE UMA CATEGORIA", titleStyle);
        titulo.setFontScale(1.2f);
        titulo.setAlignment(Align.center);
        mainTable.add(titulo).padBottom(30).colspan(2).row();

        // Categorias e ícones/cores
        String[] titulos = {
            "Ciência",
            "Matemática",
            "Programação",
            "Lideres e Política",
            "Filosofia e História",
            "Arte, Música e Entretenimento"
        };

        Color[] cores = {
            new Color(0.2f, 0.7f, 1f, 1f),      // Azul claro - Ciência
            new Color(1f, 0.4f, 0.4f, 1f),      // Vermelho - Matemática
            new Color(0.4f, 1f, 0.5f, 1f),      // Verde - Programação
            new Color(1f, 0.8f, 0.2f, 1f),      // Dourado - Líderes
            new Color(0.8f, 0.5f, 1f, 1f),      // Roxo - Filosofia
            new Color(1f, 0.5f, 0.8f, 1f)       // Rosa - Arte
        };

        final Table[] submenuAberto = {null};
        final Label[] labelSelecionado = {null};
        final Table[] cardAtivo = {null};

        // Criar duas colunas de categorias
        Table leftColumn = new Table();
        Table rightColumn = new Table();

        for (int i = 0; i < titulos.length; i++) {
            String titulo_item = titulos[i];
            Color cor = cores[i];

            // Card da categoria
            Table cardTable = createCategoryCard(titulo_item, cor, submenuAberto, labelSelecionado, cardAtivo);

            // Adiciona na coluna esquerda ou direita
            if (i % 2 == 0) {
                leftColumn.add(cardTable).pad(12).width(320).height(70).row();
            } else {
                rightColumn.add(cardTable).pad(12).width(320).height(70).row();
            }
        }

        mainTable.add(leftColumn).padRight(15).top();
        mainTable.add(rightColumn).padLeft(15).top();
    }

    private Table createCategoryCard(String titulo, Color cor, Table[] submenuAberto, Label[] labelSelecionado, Table[] cardAtivo) {
        Table card = new Table();
        card.setBackground(createRoundedBackground(new Color(0.15f, 0.15f, 0.2f, 0.9f)));

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, cor);
        Label label = new Label(titulo, labelStyle);
        label.setFontScale(0.9f);
        label.setAlignment(Align.center);

        card.add(label).expand().fill().pad(8);

        // Submenu
        Table submenu = criarSubmenu(titulo, cor);
        submenu.setVisible(false);
        stage.addActor(submenu);

        // Variável para controlar se está dentro da área (card + submenu)
        final boolean[] dentroArea = {false};

        // Hover/Click no card
        card.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                dentroArea[0] = true;
                card.setBackground(createRoundedBackground(new Color(0.2f, 0.2f, 0.3f, 1f)));

                // Fecha submenu anterior
                if (submenuAberto[0] != null && submenuAberto[0] != submenu) {
                    submenuAberto[0].setVisible(false);
                }

                // Reseta label anterior
                if (labelSelecionado[0] != null && labelSelecionado[0] != label) {
                    labelSelecionado[0].setFontScale(0.9f);
                }

                // Reseta card anterior
                if (cardAtivo[0] != null && cardAtivo[0] != card) {
                    cardAtivo[0].setBackground(createRoundedBackground(new Color(0.15f, 0.15f, 0.2f, 0.9f)));
                }

                // Abre submenu atual
                label.setFontScale(1.0f);
                posicionarSubmenu(submenu, card);
                submenu.setVisible(true);
                submenu.toFront(); // Traz para frente
                submenuAberto[0] = submenu;
                labelSelecionado[0] = label;
                cardAtivo[0] = card;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Verifica se está indo para o submenu
                if (toActor != null && toActor.isDescendantOf(submenu)) {
                    return; // Não fecha se foi para o submenu
                }

                dentroArea[0] = false;
                card.setBackground(createRoundedBackground(new Color(0.15f, 0.15f, 0.2f, 0.9f)));

                // Aguarda um pouco antes de fechar
                new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}

                    Gdx.app.postRunnable(() -> {
                        // Verifica se o mouse não está no submenu
                        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                        Actor hit = submenu.hit(mousePos.x - submenu.getX(), mousePos.y - submenu.getY(), true);
                        if (!dentroArea[0] && hit == null) {
                            submenu.setVisible(false);
                            label.setFontScale(0.9f);
                            if (submenuAberto[0] == submenu) submenuAberto[0] = null;
                            if (labelSelecionado[0] == label) labelSelecionado[0] = null;
                            if (cardAtivo[0] == card) cardAtivo[0] = null;
                        }
                    });
                }).start();
            }
        });

        // Listener do submenu - mantém aberto quando mouse está nele
        submenu.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                dentroArea[0] = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Verifica se está voltando para o card
                if (toActor != null && toActor.isDescendantOf(card)) {
                    return; // Não fecha se foi para o card
                }

                dentroArea[0] = false;

                // Aguarda antes de fechar
                new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}

                    Gdx.app.postRunnable(() -> {
                        if (!dentroArea[0]) {
                            submenu.setVisible(false);
                            label.setFontScale(0.9f);
                            card.setBackground(createRoundedBackground(new Color(0.15f, 0.15f, 0.2f, 0.9f)));
                            if (submenuAberto[0] == submenu) submenuAberto[0] = null;
                            if (labelSelecionado[0] == label) labelSelecionado[0] = null;
                            if (cardAtivo[0] == card) cardAtivo[0] = null;
                        }
                    });
                }).start();
            }
        });

        return card;
    }

    private void posicionarSubmenu(Table submenu, Table card) {
        // Pega a posição absoluta do card no stage
        Vector2 cardPos = card.localToStageCoordinates(new Vector2(0, 0));

        // Calcula posição para aparecer à direita do card
        float x = cardPos.x + card.getWidth() + 15;
        float y = cardPos.y + card.getHeight() / 2 - submenu.getHeight() / 2;

        // Ajusta se sair da tela pela direita
        if (x + submenu.getWidth() > stage.getWidth()) {
            x = cardPos.x - submenu.getWidth() - 15; // Coloca à esquerda
        }

        // Ajusta se sair da tela por baixo
        if (y < 0) {
            y = 10;
        }

        // Ajusta se sair da tela por cima
        if (y + submenu.getHeight() > stage.getHeight()) {
            y = stage.getHeight() - submenu.getHeight() - 10;
        }

        submenu.setPosition(x, y);
    }

    private Table criarSubmenu(String base, Color corBase) {
        Table submenu = new Table();
        submenu.setBackground(createRoundedBackground(new Color(0.1f, 0.1f, 0.15f, 0.95f)));
        submenu.pad(12);

        Label.LabelStyle headerStyle = new Label.LabelStyle(font, corBase);
        Label header = new Label("Dificuldade:", headerStyle);
        header.setFontScale(0.8f);
        submenu.add(header).padBottom(8).row();

        String[] niveis = {"Facil", "Média", "Difícil"};
        Color[] coresNivel = {
            new Color(0.4f, 1f, 0.4f, 1f),   // Verde - Fácil
            new Color(1f, 0.9f, 0.3f, 1f),   // Amarelo - Médio
            new Color(1f, 0.3f, 0.3f, 1f)    // Vermelho - Difícil
        };

        for (int i = 0; i < niveis.length; i++) {
            String nivel = niveis[i];
            Color corNivel = coresNivel[i];

            TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
            btnStyle.font = font;
            btnStyle.fontColor = Color.WHITE;
            btnStyle.up = createRoundedBackground(new Color(0.2f, 0.2f, 0.25f, 1f));
            btnStyle.over = createRoundedBackground(corNivel.cpy().mul(1f, 1f, 1f, 0.3f));
            btnStyle.down = createRoundedBackground(corNivel.cpy().mul(0.8f, 0.8f, 0.8f, 0.5f));

            TextButton botao = new TextButton(nivel, btnStyle);
            botao.getLabel().setFontScale(0.8f);

            botao.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Abrindo " + base + " com dificuldade: " + nivel);
                    game.setScreen(new TelaQuiz(game, base, nivel.toLowerCase()));
                }
            });

            submenu.add(botao).width(130).height(38).padBottom(6).row();
        }

        return submenu;
    }

    private NinePatchDrawable createRoundedBackground(Color color) {
        // Cria uma textura 1x1 com a cor especificada
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        NinePatch patch = new NinePatch(texture, 0, 0, 0, 0);
        return new NinePatchDrawable(patch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
