package com.ufsmSistemas.tcgParadigma.utils;

import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntityAPI;
import com.ufsmSistemas.tcgParadigma.models.Carta;
import com.ufsmSistemas.tcgParadigma.models.Jogador;

public class CartaEJogador implements DataBaseEntityAPI {
    private Jogador jogador;
    private Carta carta;

    public CartaEJogador(Jogador jogador, Carta carta) {
        this.jogador = jogador;
        this.carta = carta;
    }

    @Override
    public JsonValue toJson() {
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("idJogador", new JsonValue(jogador.getId()));
        json.addChild("idCarta", new JsonValue(carta.getId()));

        return json;
    }

    @Override
    public JsonValue toJsonKey() {
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("idJogador", new JsonValue(jogador.getId()));
        json.addChild("idCarta",  new JsonValue(carta.getId()));
        return json;
    }

    @Override
    public void fromJson(JsonValue json) {
        if (json.has("jogador"))
            jogador.fromJson(json.get("jogador"));
        if (json.has("carta"))
            carta.fromJson(json.get("carta"));
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
