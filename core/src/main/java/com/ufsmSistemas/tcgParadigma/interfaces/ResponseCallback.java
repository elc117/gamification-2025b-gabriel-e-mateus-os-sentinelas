package com.ufsmSistemas.tcgParadigma.interfaces;

import com.badlogic.gdx.utils.JsonValue;

public interface ResponseCallback {
    void onResponse(JsonValue response);
    void onError(String errorMessage);
}
