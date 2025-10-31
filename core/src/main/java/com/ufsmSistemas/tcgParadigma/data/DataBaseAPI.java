package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntityAPI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.models.Jogador;

public class DataBaseAPI {

    private static final String BASE_URL = "https://mateuscardoso.pythonanywhere.com/api/";

    public static void insert(final DataBaseEntityAPI entity) {
        String endpoint = getEndpoint(entity) + "/insert";
        JsonValue json = entity.toJson();
        sendRequest(endpoint, json, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                int id = response.getInt("id");
                entity.setId(id);
                Gdx.app.log("API", entity.getClass().getSimpleName() + " inserido com ID " + id);
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
        });
    }

    public void select(final DataBaseEntityAPI entity) {
        String endpoint = getEndpoint(entity) + "/get";
        JsonValue json = entity.toJsonKey();
        sendRequest(endpoint, json, new ResponseCallback() {
            @Override
            public void onResponse(JsonValue response) {
                entity.fromJson(response);
                Gdx.app.log("API", entity.getClass().getSimpleName() + " carregado");
            }
        });
    }

    private static String getEndpoint(DataBaseEntityAPI entity) {
        if (entity instanceof Jogador) return BASE_URL + "jogador";
        return BASE_URL + "generic";
    }

    private static void sendRequest(final String url, JsonValue json, final ResponseCallback callback) {
        // Converte JsonValue para String JSON manualmente (GWT-safe)
        String jsonString = buildJsonString(json);

        Gdx.app.log("API", "Enviando para " + url + ": " + jsonString);

        // Cria requisição HTTP de forma mais simples
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
                }
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("API", "Erro ao acessar " + url + ": " + t.getMessage());
            }

            @Override
            public void cancelled() {
                Gdx.app.log("API", "Requisição cancelada");
            }
        });
    }

    /**
     * Converte JsonValue para String JSON de forma GWT-safe
     */
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
            // Fallback para número
            sb.append(value.asString());
        }
    }

    private static String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

    public interface ResponseCallback {
        void onResponse(JsonValue response);
    }
}
