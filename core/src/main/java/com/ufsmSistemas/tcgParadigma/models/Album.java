package com.ufsmSistemas.tcgParadigma.models;

import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntityAPI;

public abstract class Album implements DataBaseEntityAPI {
    private int idJogador;
    private int idCarta;
    private int quantidadeCartas;

    // Getters
    public int getQuantidadeCartas() {
        return quantidadeCartas;
    }
    public int getIdUsuario() {
        return idJogador;
    }
    public int getIdCarta() {
        return idCarta;
    }

    // Setters
    public void setQuantidadeCartas(int quantidadeCartas) {
        this.quantidadeCartas = quantidadeCartas;
    }
    public void setIdUsuario(int idUsuario) {
        this.idJogador = idUsuario;
    }
    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    @Override
    public JsonValue toJson() {
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("idJogador", new JsonValue(idJogador));
        json.addChild("idCarta", new JsonValue(idCarta));
        json.addChild("quantidadeCartas", new JsonValue(quantidadeCartas));
        return json;
    }

    @Override
    public void fromJson(JsonValue json) {
        idJogador = json.getInt("idJogador");
        idCarta = json.getInt("idCarta");
        quantidadeCartas = json.getInt("quantidadeCartas");
    }

    @Override
    public JsonValue toJsonKey() {
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("idJogador", new JsonValue(idJogador));
        json.addChild("idCarta", new JsonValue(idCarta));
        return json;
    }
}
