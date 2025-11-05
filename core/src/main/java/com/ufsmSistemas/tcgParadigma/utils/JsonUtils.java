package com.ufsmSistemas.tcgParadigma.utils;

import com.badlogic.gdx.utils.JsonValue;

public class JsonUtils {
    private static String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

    public static String buildJsonString(JsonValue json) {
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
