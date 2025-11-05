package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntityAPI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.interfaces.ResponseCallback;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import com.ufsmSistemas.tcgParadigma.services.APIservice;

public class DataBaseAPI {
    public static void insert(final DataBaseEntityAPI entity) {
        String endpoint = APIservice.getEndpoint(entity) + "/insert";
        JsonValue json = entity.toJson();

        APIservice.sendRequest(endpoint, json, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                if (entity instanceof Jogador) {
                    int id = response.getInt("id");
                    entity.setId(id);
                    Gdx.app.log("API", entity.getClass().getSimpleName() + " inserido com ID " + id);
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    public static void update(final DataBaseEntityAPI entity) {
        String endpoint = APIservice.getEndpoint(entity) + "/update";
        JsonValue json = entity.toJson();

        APIservice.sendRequest(endpoint, json, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                Gdx.app.log("API", entity.getClass().getSimpleName() + " atualizado com sucesso");
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    public static void select(final DataBaseEntityAPI entity, final ResponseCallback extraCallback) {
        String endpoint = APIservice.getEndpoint(entity) + "/get";
        JsonValue json = entity.toJsonKey();

        APIservice.sendRequest(endpoint, json, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                entity.fromJson(response);
                System.out.println("API " + entity.getClass().getSimpleName() + " carregado");

                if (extraCallback != null) {
                    extraCallback.onResponse(response);
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("Erro na requisição: " + errorMessage);
                if (extraCallback != null) {
                    extraCallback.onError(errorMessage);
                }
            }
        });
    }

    public static void select(final DataBaseEntityAPI entity) {
        select(entity, null);
    }
}
