package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntity;
import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseInterface;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

public class DataBaseWASM implements DataBaseInterface {

    @JsType(isNative = true, namespace = "database")
    private static class JSDatabase {
        @JsMethod
        public static native void execSQL(String sql);
    }

    @Override
    public void insert(DataBaseEntity entity) {
        if (entity instanceof Jogador) {
            Jogador jogador = (Jogador) entity;
            String nome = jogador.getNome().replace("'", "''");
            String senha = jogador.getSenha().replace("'", "''");
            String sql = "INSERT INTO jogador (nome, senha) VALUES ('" + nome + "', '" + senha + "');";
            JSDatabase.execSQL(sql);
        }
    }
}
