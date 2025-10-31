package com.ufsmSistemas.tcgParadigma.interfaces;

import com.badlogic.gdx.utils.JsonValue;

public interface DataBaseEntityAPI {
    JsonValue toJson();
    JsonValue toJsonKey();

    void fromJson(JsonValue json);
    void setId(int id);
    int getId();
}
