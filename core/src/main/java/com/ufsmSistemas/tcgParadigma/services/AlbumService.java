package com.ufsmSistemas.tcgParadigma.services;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.interfaces.ResponseCallback;
import com.ufsmSistemas.tcgParadigma.models.Carta;

public class AlbumService {
    private static final String BASE_URL = "https://mateuscardoso.pythonanywhere.com/api/";

    public interface CartasCallback {
        void onSuccess(Array<Carta> cartas);
        void onError(String erro);
    }

    public void buscarTodasCartas(final CartasCallback callback) {
        String url = BASE_URL + "cartas/todas";

        APIservice.sendGetRequest(url, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                Array<Carta> cartas = new Array<Carta>();
                try {
                    if (response.isArray()) {
                        for (JsonValue cartaJson : response) {
                            Carta carta = new Carta();
                            carta.fromJson(cartaJson);
                            cartas.add(carta);
                        }
                    }
                    callback.onSuccess(cartas);
                } catch (Exception e) {
                    System.err.println("[AlbumService] Erro ao processar cartas: " + e.getMessage());
                    e.printStackTrace();
                    callback.onError("Erro ao processar cartas: " + e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.err.println("[AlbumService] Erro: " + errorMessage);
                callback.onError(errorMessage);
            }
        });
    }

    public void buscarCartasJogador(int idJogador, final CartasCallback callback) {
        String url = BASE_URL + "album/jogador";

        // Criar JSON com idJogador
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("idJogador", new JsonValue(idJogador));

        APIservice.sendRequest(url, json, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                Array<Carta> cartas = new Array<Carta>();
                try {
                    if (response.isArray()) {
                        for (JsonValue cartaJson : response) {
                            Carta carta = new Carta();
                            carta.setId(cartaJson.getInt("idCarta"));
                            carta.setNome(cartaJson.getString("nome"));
                            carta.setCaminhoImagem(cartaJson.getString("caminhoImagem"));
                            carta.setRaridade(cartaJson.getString("raridade"));
                            carta.setCategoria(cartaJson.getString("categoria"));
                            carta.setQuantidade(cartaJson.getInt("quantidade"));
                            carta.setObtida(true);
                            cartas.add(carta);
                        }
                    }
                    callback.onSuccess(cartas);
                } catch (Exception e) {
                    System.err.println("[AlbumService] Erro ao processar cartas do jogador: " + e.getMessage());
                    e.printStackTrace();
                    callback.onError("Erro ao processar cartas: " + e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.err.println("[AlbumService] Erro: " + errorMessage);
                callback.onError(errorMessage);
            }
        });
    }

    public void buscarEstatisticas(int idJogador, final EstatisticasCallback callback) {
        String url = BASE_URL + "album/stats";

        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("idJogador", new JsonValue(idJogador));

        APIservice.sendRequest(url, json, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                try {
                    int total = response.getInt("total");
                    int obtidas = response.getInt("obtidas");
                    float porcentagem = response.getFloat("porcentagem");

                    callback.onEstatisticas(total, obtidas, porcentagem);
                } catch (Exception e) {
                    System.err.println("[AlbumService] Erro ao processar estatísticas: " + e.getMessage());
                    callback.onErro("Erro ao processar estatísticas");
                }
            }

            @Override
            public void onError(String errorMessage) {
                callback.onErro(errorMessage);
            }
        });
    }

    public interface EstatisticasCallback {
        void onEstatisticas(int total, int obtidas, float porcentagem);
        void onErro(String erro);
    }
}
