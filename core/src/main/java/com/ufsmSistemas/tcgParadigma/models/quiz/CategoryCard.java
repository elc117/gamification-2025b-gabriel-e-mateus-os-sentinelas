package com.ufsmSistemas.tcgParadigma.models.quiz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.enums.CategoriaQuiz;
import com.ufsmSistemas.tcgParadigma.enums.DificuldadeQuiz;
import com.ufsmSistemas.tcgParadigma.screens.quiz.TelaQuiz;

public class CategoryCard extends Table {
    private Table submenu;
    private boolean expanded = false;
    private Color categoryColor;
    private Color baseColor;
    private Color hoverColor;

    public CategoryCard(final Main game, BitmapFont font, final CategoriaQuiz categoria) {
        // ✅ Agora getCor() já retorna LibGDX Color
        categoryColor = categoria.getCor();

        // Cores base e hover
        baseColor = new Color(categoryColor).mul(0.3f, 0.3f, 0.3f, 1f);
        hoverColor = new Color(categoryColor).mul(0.5f, 0.5f, 0.5f, 1f);

        setBackground(createGradientBackground(baseColor, categoryColor));

        // Container do título com ícone decorativo
        Table headerTable = new Table();
        headerTable.pad(25, 20, 25, 20);

        // Barra decorativa colorida no topo
        Table topBar = new Table();
        topBar.setBackground(createRoundedBackground(categoryColor));
        headerTable.add(topBar).height(5).width(300).padBottom(15).row();

        // Título da categoria
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        Label label = new Label(categoria.getTitulo(), labelStyle);
        label.setFontScale(1.1f);
        label.setAlignment(Align.center);
        label.setWrap(true);
        headerTable.add(label).width(300).center().padBottom(10).row();

        // Indicador "clique para expandir"
        Label.LabelStyle hintStyle = new Label.LabelStyle();
        hintStyle.font = font;
        hintStyle.fontColor = new Color(1f, 1f, 1f, 0.6f);
        Label hint = new Label("▶ clique para selecionar", hintStyle);
        hint.setFontScale(0.6f);
        hint.setAlignment(Align.center);
        hint.setName("hint");
        headerTable.add(hint).center();

        add(headerTable).width(340).left();

        // Submenu de dificuldades (inicialmente oculto) - ao lado
        submenu = new Table();
        submenu.setBackground(createRoundedBackground(new Color(0, 0, 0, 0.5f)));
        submenu.pad(20);
        submenu.setVisible(false);

        // Título do submenu
        Label.LabelStyle submenuTitleStyle = new Label.LabelStyle();
        submenuTitleStyle.font = font;
        submenuTitleStyle.fontColor = new Color(1f, 1f, 1f, 0.9f);
        Label submenuTitle = new Label("Dificuldade:", submenuTitleStyle);
        submenuTitle.setFontScale(0.7f);
        submenu.add(submenuTitle).padRight(10);

        // Criar botões de dificuldade horizontalmente
        for (final DificuldadeQuiz dificuldade : DificuldadeQuiz.values()) {
            // Agora getCor() já retorna LibGDX Color
            Color gdxColor = dificuldade.getCor();

            TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
            btnStyle.font = font;
            btnStyle.fontColor = Color.WHITE;

            // Fundo do botão com a cor da dificuldade
            Color btnBaseColor = new Color(gdxColor).mul(0.8f, 0.8f, 0.8f, 1f);
            btnStyle.up = createRoundedBackground(btnBaseColor);

            Color btnHoverColor = new Color(gdxColor).mul(1.1f, 1.1f, 1.1f, 1f);
            btnStyle.over = createRoundedBackground(btnHoverColor);

            Color btnPressedColor = new Color(gdxColor).mul(0.6f, 0.6f, 0.6f, 1f);
            btnStyle.down = createRoundedBackground(btnPressedColor);

            TextButton botao = new TextButton(dificuldade.getNome(), btnStyle);
            botao.getLabel().setFontScale(0.75f);
            botao.pad(10, 18, 10, 18);

            botao.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new TelaQuiz(game, categoria.getTitulo(), dificuldade.name().toLowerCase()));
                }
            });

            submenu.add(botao).width(130).height(90).padLeft(10);
        }

        add(submenu).left();

        // Listener para click no card
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleExpansion();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                if (!expanded) {
                    setBackground(createGradientBackground(hoverColor, categoryColor));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                if (!expanded) {
                    setBackground(createGradientBackground(baseColor, categoryColor));
                }
            }
        });
    }

    private void toggleExpansion() {
        expanded = !expanded;

        Label hint = (Label) findActor("hint");

        if (expanded) {
            // Expandir
            submenu.setVisible(true);
            submenu.clearActions();
            submenu.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.fadeIn(0.3f)
            ));
            setBackground(createGradientBackground(hoverColor, categoryColor));
            if (hint != null) hint.setText("◀ fechar");
        } else {
            // Recolher
            submenu.clearActions();
            submenu.addAction(Actions.sequence(
                Actions.fadeOut(0.2f),
                Actions.run(() -> submenu.setVisible(false))
            ));
            setBackground(createGradientBackground(baseColor, categoryColor));
            if (hint != null) hint.setText("▶ clique para selecionar");
        }
    }

    private NinePatchDrawable createRoundedBackground(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new NinePatchDrawable(new NinePatch(texture, 0, 0, 0, 0));
    }

    private NinePatchDrawable createGradientBackground(Color color1, Color color2) {
        int width = 1;
        int height = 100;
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < height; y++) {
            float ratio = (float) y / height;
            float r = color1.r + (color2.r - color1.r) * ratio;
            float g = color1.g + (color2.g - color1.g) * ratio;
            float b = color1.b + (color2.b - color1.b) * ratio;
            pixmap.setColor(r, g, b, 0.95f);
            pixmap.drawLine(0, y, width - 1, y);
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new NinePatchDrawable(new NinePatch(texture, 0, 0, 0, 0));
    }
}
