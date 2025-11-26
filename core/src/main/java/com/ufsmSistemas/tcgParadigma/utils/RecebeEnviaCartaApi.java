package com.ufsmSistemas.tcgParadigma.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;
import com.ufsmSistemas.tcgParadigma.interfaces.ResponseCallback;
import com.ufsmSistemas.tcgParadigma.models.Carta;
import com.ufsmSistemas.tcgParadigma.models.Jogador;

public class RecebeEnviaCartaApi {

    private DataBaseAPI api = new DataBaseAPI();

    public interface CartaCallback {
        void onCartaRecebida(Carta carta);
    }

    public void receberCartaApi(Carta cartaMolde, CartaCallback callback) {
        api.select(cartaMolde, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                try {
                    cartaMolde.fromJson(response);
                    if (callback != null) {
                        Gdx.app.postRunnable(() -> callback.onCartaRecebida(cartaMolde));
                    }
                } catch (Exception e) {
                    System.err.println("[Booster] ERRO ao processar carta: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.err.println("[Booster] Erro ao carregar carta: " + errorMessage);
            }
        }, "/get");
    }

    public void atualizaCartaApi(Carta cartaAtualizada, Jogador jogador) {
        CartaEJogador cartaJogador = new CartaEJogador(jogador, cartaAtualizada);
        DataBaseAPI.update(cartaJogador);
    }
}
