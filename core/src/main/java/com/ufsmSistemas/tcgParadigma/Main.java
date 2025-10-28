package com.ufsmSistemas.tcgParadigma;

import com.badlogic.gdx.Game;
import com.ufsmSistemas.tcgParadigma.Screens.FirstScreen;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new FirstScreen());
    }
}
