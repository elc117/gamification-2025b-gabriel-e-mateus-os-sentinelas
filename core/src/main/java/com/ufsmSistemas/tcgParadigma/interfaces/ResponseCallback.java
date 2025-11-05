package com.ufsmSistemas.tcgParadigma.interfaces;

import com.badlogic.gdx.utils.JsonValue;

public interface ResponseCallback { // Callback para receber respostas da API
    void onResponse(JsonValue response);
    void onError(String errorMessage);
}
