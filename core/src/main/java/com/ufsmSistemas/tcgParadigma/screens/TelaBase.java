package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;

public abstract class TelaBase implements Screen {
    protected Stage stage;
    protected Skin skin;
    protected final Main game;
    protected SpriteBatch batch;
    protected Texture background;

    // Cores para gradiente de fundo
    protected Color corFundoTop = new Color(0.1f, 0.15f, 0.3f, 1);
    protected Color corFundoBottom = new Color(0.05f, 0.05f, 0.15f, 1);

    public TelaBase(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
    }

    /**
     * Desenha um gradiente de fundo
     */
    protected void desenharFundoGradiente() {
        Gdx.gl.glDisable(GL20.GL_TEXTURE_2D);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Aqui você pode usar ShapeRenderer se preferir
        // Por simplicidade, vamos limpar com uma cor base
        Gdx.gl.glClearColor(corFundoBottom.r, corFundoBottom.g, corFundoBottom.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        desenharFundoGradiente();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        if (skin != null) skin.dispose();
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
    }

}
