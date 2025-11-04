package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntityAPI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.models.Carta;
import com.ufsmSistemas.tcgParadigma.models.Jogador;

public class DataBaseAPI {

    private static final String BASE_URL = "https://mateuscardoso.pythonanywhere.com/api/";

    public interface ResponseCallback {
        void onResponse(JsonValue response);

        void onError(String errorMessage);
    }

    public static void insert(final DataBaseEntityAPI entity) {
        String endpoint = getEndpoint(entity) + "/insert";
        JsonValue json = entity.toJson();
        sendRequest(endpoint, json, new ResponseCallback() {
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
        String endpoint = getEndpoint(entity) + "/update";
        JsonValue json = entity.toJson();
        sendRequest(endpoint, json, new ResponseCallback() {
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
        String endpoint = getEndpoint(entity) + "/get";
        JsonValue json = entity.toJsonKey();

        sendRequest(endpoint, json, new ResponseCallback() {
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

    private static String getEndpoint(DataBaseEntityAPI entity) {
        if (entity instanceof Jogador) return BASE_URL + "jogador";
        else if (entity instanceof Carta) return BASE_URL + "carta";
        return BASE_URL + "generic";
    }

    private static void sendRequest(final String url, JsonValue json, final ResponseCallback callback) {
        String jsonString = buildJsonString(json);
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
            }
        });
    }

    private static String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

    private static String buildJsonString(JsonValue json) {
        if (json == null) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        JsonValue child = json.child;
        boolean first = true;

        while (child != null) {
            if (!first) {
                sb.append(",");
            }
            first = false;

            // Nome do campo
            sb.append("\"").append(escapeJson(child.name)).append("\":");

            // Valor do campo
            appendJsonValue(sb, child);

            child = child.next;
        }

        sb.append("}");
        return sb.toString();


    }

    private static void appendJsonValue(StringBuilder sb, JsonValue value) {
        if (value.isString()) {
            sb.append("\"").append(escapeJson(value.asString())).append("\"");
        } else if (value.isLong()) {
            sb.append(value.asLong());
        } else if (value.isDouble()) {
            sb.append(value.asDouble());
        } else if (value.isBoolean()) {
            sb.append(value.asBoolean());
        } else if (value.isNull()) {
            sb.append("null");
        } else if (value.isObject()) {
            sb.append(buildJsonString(value));
        } else if (value.isArray()) {
            sb.append("[");
            JsonValue arrayChild = value.child;
            boolean firstArray = true;
            while (arrayChild != null) {
                if (!firstArray) sb.append(",");
                firstArray = false;
                appendJsonValue(sb, arrayChild);
                arrayChild = arrayChild.next;
            }
            sb.append("]");
        } else {
            // fallback: valor genérico
            sb.append("\"").append(escapeJson(value.asString())).append("\"");
        }
    }

}
