package com.ufsmSistemas.tcgParadigma;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.ufsmSistemas.tcgParadigma.data.DataBaseFactory;
import com.ufsmSistemas.tcgParadigma.data.DataBaseWASM;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(800, 600);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        DataBaseFactory.set(new DataBaseWASM()); // injeta banco Web
        return new Main();
    }
}
