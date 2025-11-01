package com.ufsmSistemas.tcgParadigma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import com.ufsmSistemas.tcgParadigma.screens.TelaMenu;

public class Main extends Game {
    @Override
    public void create() {
        Gdx.app.log("Main", "🚀 Iniciando aplicação...");
        setScreen(new TelaMenu(Main.this));
    }
}
