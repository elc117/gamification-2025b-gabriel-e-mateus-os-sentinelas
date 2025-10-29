package com.ufsmSistemas.tcgParadigma.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.data.DataBaseFactory;
import com.ufsmSistemas.tcgParadigma.data.DataBaseJDBC;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        DataBaseFactory.set(new DataBaseJDBC()); // injeta banco Desktop

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("TParadigmaCG");
        config.setWindowedMode(640, 480);
        config.useVsync(true);

        new Lwjgl3Application(new Main(), config);
    }
}
