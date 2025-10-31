package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseInterfaceAPI;

public class DataBaseFactory {

    private static DataBaseInterfaceAPI instance;

    // Retorna a instância única
    public static DataBaseInterfaceAPI get() {
        if (instance == null) {
            throw new IllegalStateException(
                "DataBase not set! Call set() from the platform launcher.");
        }
        return instance;
    }

    // Configura a instância (chamado pelo launcher da plataforma)
    public static void set(DataBaseInterfaceAPI db) {
        instance = db;
    }
}
