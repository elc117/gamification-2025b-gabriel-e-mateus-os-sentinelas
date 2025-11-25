package com.ufsmSistemas.tcgParadigma.services;

import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;
import com.ufsmSistemas.tcgParadigma.data.Session;
import com.ufsmSistemas.tcgParadigma.interfaces.ResponseCallback;
import com.ufsmSistemas.tcgParadigma.models.Jogador;

public class LoginService {

    public interface LoginCallback {
        void onSuccess(Jogador jogador);
        void onFailure(String errorMessage);
    }

    public void autenticar(String nome, final String senha, final LoginCallback callback) {
        final Jogador jogador = new Jogador(nome, senha);
        DataBaseAPI api = new DataBaseAPI();
        api.select(jogador, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                try {
                    if (response.has("erro")) {
                        String msgErro = response.getString("erro");
                        callback.onFailure(msgErro);
                        System.out.println("⚠ Erro no login: " + msgErro);
                        return;
                    }

                    jogador.fromJson(response);

                    if (jogador.getSenha() == null || !jogador.getSenha().equals(senha)) {
                        callback.onFailure("Usuário ou senha incorretos.");
                        return;
                    }

                    Session.getInstance().setJogador(jogador);
                    Session.getInstance().setLogado(true);
                    callback.onSuccess(jogador);

                } catch (Exception e) {
                    callback.onFailure("Erro ao processar resposta: " + e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                callback.onFailure("Usuário ou senha incorretos."); // erro genérico
            }
        }, "/get");
    }
}
