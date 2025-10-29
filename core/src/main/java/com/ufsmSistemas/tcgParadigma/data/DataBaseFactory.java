package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseInterface;

public class DataBaseFactory {

    private static DataBaseInterface instance;

    public static DataBaseInterface get() {
        if (instance == null) {
            throw new IllegalStateException(
                "DataBase not set! Call set() from the platform launcher.");
        }
        return instance;
    }

    public static void set(DataBaseInterface db) {
        instance = db;
    }
}
