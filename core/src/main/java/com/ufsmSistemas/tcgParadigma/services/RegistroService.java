package com.ufsmSistemas.tcgParadigma.services;

import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;
import com.ufsmSistemas.tcgParadigma.interfaces.RegistroCallback;
import com.ufsmSistemas.tcgParadigma.models.Jogador;

public class RegistroService {
    public static void registrarJogador(String nome, String senha, final RegistroCallback callback) {
        try {
            Jogador jogador = new Jogador(nome, senha);
            DataBaseAPI.insert(jogador);
            callback.onSuccess("Jogador criado com sucesso!");

        } catch (Exception e) {
            callback.onFailure("Erro ao criar jogador: " + e.getMessage());
        }
    }
}
