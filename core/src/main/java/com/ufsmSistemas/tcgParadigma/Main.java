package com.ufsmSistemas.tcgParadigma;

import com.badlogic.gdx.Game;
import com.ufsmSistemas.tcgParadigma.data.DataBaseFactory;
import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseInterface;

public class Main extends Game {

    private DataBaseInterface db;

    @Override
    public void create() {
        // Pega a instância que o launcher configurou
        db = DataBaseFactory.get();
    }

    public DataBaseInterface getDatabase() {
        return db;
    }
}
