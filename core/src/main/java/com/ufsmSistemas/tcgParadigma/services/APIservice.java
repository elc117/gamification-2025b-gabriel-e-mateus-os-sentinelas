package com.ufsmSistemas.tcgParadigma.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntityAPI;
import com.ufsmSistemas.tcgParadigma.interfaces.ResponseCallback;
import com.ufsmSistemas.tcgParadigma.models.Carta;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import com.ufsmSistemas.tcgParadigma.utils.JsonUtils;

public class APIservice {
    private static final String BASE_URL = "https://mateuscardoso.pythonanywhere.com/api/";

    public static String getEndpoint(DataBaseEntityAPI entity) {
        if (entity instanceof Jogador) return BASE_URL + "jogador";
        else if (entity instanceof Carta) return BASE_URL + "carta";
        return BASE_URL + "generic";
    }

    /**
     * Envia requisição POST com JSON
     */
    public static void sendRequest(final String url, JsonValue json, final ResponseCallback callback) {
        String jsonString = JsonUtils.buildJsonString(json);
        System.out.println("JSON: " + jsonString);

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(url);
        request.setHeader("Content-Type", "application/json");
        request.setContent(jsonString);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                try {
                    String responseString = httpResponse.getResultAsString();
                    Gdx.app.log("API", "Resposta recebida: " + responseString);
                    JsonValue response = new JsonReader().parse(responseString);
                    callback.onResponse(response);
                } catch (Exception e) {
                    Gdx.app.error("API", "Erro ao processar resposta: " + e.getMessage());
                    callback.onError("Erro ao processar resposta: " + e.getMessage());
                }
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("API", "Erro ao acessar " + url + ": " + t.getMessage());
                callback.onError("Erro ao acessar " + url + ": " + t.getMessage());
            }

            @Override
            public void cancelled() {
                Gdx.app.log("API", "Requisição cancelada");
                callback.onError("Requisição cancelada");
            }
        });
    }

    /**
     * Envia requisição GET (sem body)
     */
    public static void sendGetRequest(final String url, final ResponseCallback callback) {
        System.out.println("GET Request: " + url);

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(url);
        request.setHeader("Content-Type", "application/json");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                try {
                    String responseString = httpResponse.getResultAsString();
                    Gdx.app.log("API", "Resposta GET recebida: " + responseString);
                    JsonValue response = new JsonReader().parse(responseString);
                    callback.onResponse(response);
                } catch (Exception e) {
                    Gdx.app.error("API", "Erro ao processar resposta GET: " + e.getMessage());
                    callback.onError("Erro ao processar resposta: " + e.getMessage());
                }
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("API", "Erro ao acessar " + url + ": " + t.getMessage());
                callback.onError("Erro ao acessar " + url + ": " + t.getMessage());
            }

            @Override
            public void cancelled() {
                Gdx.app.log("API", "Requisição GET cancelada");
                callback.onError("Requisição cancelada");
            }
        });
    }
}
