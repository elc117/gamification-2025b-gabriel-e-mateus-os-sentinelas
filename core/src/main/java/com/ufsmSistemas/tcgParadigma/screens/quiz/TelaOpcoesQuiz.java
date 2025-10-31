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
import com.badlogic.gdx.utils.Timer;
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

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        stage.addActor(mainTable);

        Label.LabelStyle titleStyle = new Label.LabelStyle(font, new Color(1f, 0.85f, 0.3f, 1f));
        Label titulo = new Label("SELECIONE UMA CATEGORIA", titleStyle);
        titulo.setFontScale(1.2f);
        titulo.setAlignment(Align.center);
        mainTable.add(titulo).padBottom(30).colspan(2).row();

        String[] titulos = {
            "Ciência",
            "Matemática",
            "Programação",
            "Lideres e Política",
            "Filosofia e História",
            "Arte, Música e Entretenimento"
        };

        Color[] cores = {
            new Color(0.2f, 0.7f, 1f, 1f),
            new Color(1f, 0.4f, 0.4f, 1f),
            new Color(0.4f, 1f, 0.5f, 1f),
            new Color(1f, 0.8f, 0.2f, 1f),
            new Color(0.8f, 0.5f, 1f, 1f),
            new Color(1f, 0.5f, 0.8f, 1f)
        };

        final Table[] submenuAberto = {null};
        final Label[] labelSelecionado = {null};
        final Table[] cardAtivo = {null};

        Table leftColumn = new Table();
        Table rightColumn = new Table();

        for (int i = 0; i < titulos.length; i++) {
            String titulo_item = titulos[i];
            Color cor = cores[i];

            Table cardTable = createCategoryCard(titulo_item, cor, submenuAberto, labelSelecionado, cardAtivo);

            if (i % 2 == 0) leftColumn.add(cardTable).pad(12).width(320).height(70).row();
            else rightColumn.add(cardTable).pad(12).width(320).height(70).row();
        }

        mainTable.add(leftColumn).padRight(15).top();
        mainTable.add(rightColumn).padLeft(15).top();
    }

    private Table createCategoryCard(String titulo, Color cor, final Table[] submenuAberto, final Label[] labelSelecionado, final Table[] cardAtivo) {
        final Table card = new Table();
        card.setBackground(createRoundedBackground(new Color(0.15f, 0.15f, 0.2f, 0.9f)));

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, cor);
        final Label label = new Label(titulo, labelStyle);
        label.setFontScale(0.9f);
        label.setAlignment(Align.center);

        card.add(label).expand().fill().pad(8);

        final Table submenu = criarSubmenu(titulo, cor);
        submenu.setVisible(false);
        stage.addActor(submenu);

        final boolean[] dentroArea = {false};

        card.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                dentroArea[0] = true;
                card.setBackground(createRoundedBackground(new Color(0.2f, 0.2f, 0.3f, 1f)));

                if (submenuAberto[0] != null && submenuAberto[0] != submenu) submenuAberto[0].setVisible(false);
                if (labelSelecionado[0] != null && labelSelecionado[0] != label) labelSelecionado[0].setFontScale(0.9f);
                if (cardAtivo[0] != null && cardAtivo[0] != card) cardAtivo[0].setBackground(createRoundedBackground(new Color(0.15f, 0.15f, 0.2f, 0.9f)));

                label.setFontScale(1.0f);
                posicionarSubmenu(submenu, card);
                submenu.setVisible(true);
                submenu.toFront();
                submenuAberto[0] = submenu;
                labelSelecionado[0] = label;
                cardAtivo[0] = card;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (toActor != null && toActor.isDescendantOf(submenu)) return;

                dentroArea[0] = false;
                card.setBackground(createRoundedBackground(new Color(0.15f, 0.15f, 0.2f, 0.9f)));

                // Substituição do Thread por Timer.schedule
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (!dentroArea[0]) {
                            submenu.setVisible(false);
                            label.setFontScale(0.9f);
                            if (submenuAberto[0] == submenu) submenuAberto[0] = null;
                            if (labelSelecionado[0] == label) labelSelecionado[0] = null;
                            if (cardAtivo[0] == card) cardAtivo[0] = null;
                        }
                    }
                }, 0.1f); // 100ms delay
            }
        });

        submenu.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                dentroArea[0] = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (toActor != null && toActor.isDescendantOf(card)) return;

                dentroArea[0] = false;

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (!dentroArea[0]) {
                            submenu.setVisible(false);
                            label.setFontScale(0.9f);
                            card.setBackground(createRoundedBackground(new Color(0.15f, 0.15f, 0.2f, 0.9f)));
                            if (submenuAberto[0] == submenu) submenuAberto[0] = null;
                            if (labelSelecionado[0] == label) labelSelecionado[0] = null;
                            if (cardAtivo[0] == card) cardAtivo[0] = null;
                        }
                    }
                }, 0.1f); // 100ms delay
            }
        });

        return card;
    }

    private void posicionarSubmenu(Table submenu, Table card) {
        Vector2 cardPos = card.localToStageCoordinates(new Vector2(0, 0));

        float x = cardPos.x + card.getWidth() + 15;
        float y = cardPos.y + card.getHeight() / 2 - submenu.getHeight() / 2;

        if (x + submenu.getWidth() > stage.getWidth()) x = cardPos.x - submenu.getWidth() - 15;
        if (y < 0) y = 10;
        if (y + submenu.getHeight() > stage.getHeight()) y = stage.getHeight() - submenu.getHeight() - 10;

        submenu.setPosition(x, y);
    }

    private Table criarSubmenu(final String base, Color corBase) {
        Table submenu = new Table();
        submenu.setBackground(createRoundedBackground(new Color(0.1f, 0.1f, 0.15f, 0.95f)));
        submenu.pad(12);

        Label.LabelStyle headerStyle = new Label.LabelStyle(font, corBase);
        Label header = new Label("Dificuldade:", headerStyle);
        header.setFontScale(0.8f);
        submenu.add(header).padBottom(8).row();

        String[] niveis = {"Facil", "Média", "Difícil"};
        Color[] coresNivel = {
            new Color(0.4f, 1f, 0.4f, 1f),
            new Color(1f, 0.9f, 0.3f, 1f),
            new Color(1f, 0.3f, 0.3f, 1f)
        };

        for (int i = 0; i < niveis.length; i++) {
            final String nivel = niveis[i];
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
                    game.setScreen(new TelaQuiz(game, base, nivel.toLowerCase()));
                }
            });

            submenu.add(botao).width(130).height(38).padBottom(6).row();
        }

        return submenu;
    }

    private NinePatchDrawable createRoundedBackground(Color color) {
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
