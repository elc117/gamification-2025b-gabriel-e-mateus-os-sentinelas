package com.ufsmSistemas.tcgParadigma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ufsmSistemas.tcgParadigma.screens.TelaMenu;

public class Main extends Game {

    @Override
    public void create() {
        Gdx.app.log("Main", "🚀 Iniciando aplicação...");
        setScreen(new TelaMenu(Main.this));
    }
}
