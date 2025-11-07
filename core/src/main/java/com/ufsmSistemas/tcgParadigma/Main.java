package com.ufsmSistemas.tcgParadigma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ufsmSistemas.tcgParadigma.screens.TelaMenu;

public class Main extends Game {
    public static Music musicaFundo;
    @Override
    public void create() {
        Gdx.app.log("Main", "🚀 Iniciando aplicação...");

        musicaFundo = Gdx.audio.newMusic(Gdx.files.internal("Audio/priscilaViolao.ogg"));
        musicaFundo.setLooping(true);
        musicaFundo.setVolume(0.5f);


        // ⚠️ Pode precisar esperar clique no HTML5
        musicaFundo.play();

        setScreen(new TelaMenu(Main.this));
    }

    @Override
    public void dispose() {
        if (musicaFundo != null) musicaFundo.dispose();
        super.dispose();
    }
}
