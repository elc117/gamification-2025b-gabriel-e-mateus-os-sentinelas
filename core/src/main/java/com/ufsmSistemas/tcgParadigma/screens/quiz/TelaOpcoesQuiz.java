package com.ufsmSistemas.tcgParadigma.screens.quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.enums.CategoriaQuiz;
import com.ufsmSistemas.tcgParadigma.models.quiz.CategoryCard;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;

public class TelaOpcoesQuiz extends TelaBase {
    public BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/utf8.fnt"));

    public TelaOpcoesQuiz(Main game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Fundo
        Table background = new Table();
        background.setFillParent(true);
        Pixmap bgPixmap = new Pixmap(1, 2, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0.05f, 0.08f, 0.12f, 1f));
        bgPixmap.drawPixel(0, 0);
        bgPixmap.setColor(new Color(0.10f, 0.13f, 0.18f, 1f));
        bgPixmap.drawPixel(0, 1);
        background.setBackground(new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(new Texture(bgPixmap)));
        bgPixmap.dispose();
        stage.addActor(background);

        // Container principal
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(40, 30, 40, 30);
        stage.addActor(mainTable);

        // Header com título e subtítulo
        Table headerTable = new Table();

        Label.LabelStyle titleStyle = new Label.LabelStyle(font, new Color(1f, 0.95f, 0.7f, 1f));
        Label titulo = new Label("QUIZ DE CONHECIMENTOS", titleStyle);
        titulo.setFontScale(1.5f);
        titulo.setAlignment(Align.center);
        headerTable.add(titulo).padBottom(10).row();

        Label.LabelStyle subtitleStyle = new Label.LabelStyle(font, new Color(0.7f, 0.8f, 0.9f, 0.8f));
        Label subtitulo = new Label("Clique em uma categoria para escolher a dificuldade", subtitleStyle);
        subtitulo.setFontScale(0.75f);
        subtitulo.setAlignment(Align.center);
        headerTable.add(subtitulo).row();

        mainTable.add(headerTable).padBottom(30).row();

        // Container dos cards com scroll
        Table cardsContainer = new Table();
        cardsContainer.pad(10);

        // Layout em coluna única (cards alinhados verticalmente)
        for (CategoriaQuiz categoria : CategoriaQuiz.values()) {
            CategoryCard card = new CategoryCard(game, font, categoria);
            cardsContainer.add(card).pad(10).width(780).height(130).row();
        }

        // ScrollPane com scroll habilitado
        final ScrollPane scrollPane = new ScrollPane(cardsContainer);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false); // Apenas scroll vertical
        scrollPane.setOverscroll(false, false);
        scrollPane.setSmoothScrolling(true);
        scrollPane.setFlickScroll(true);
        scrollPane.setScrollbarsVisible(true);
        scrollPane.setVariableSizeKnobs(false);

        // Habilitar scroll com a roda do mouse
        scrollPane.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                scrollPane.setScrollY(scrollPane.getScrollY() + amountY * 40); // 40 = velocidade do scroll
                return true;
            }
        });

        mainTable.add(scrollPane).expand().fill();
    }
}
