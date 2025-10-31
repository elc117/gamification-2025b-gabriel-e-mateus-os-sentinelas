package com.ufsmSistemas.tcgParadigma.models;

import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntityAPI;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Jogador implements DataBaseEntityAPI {
    private int id;
    private String nome;
    private int pontos;
    private int quizesRespondidos;
    private int quantidadeBoosterAbertos;
    private String senha;

    // Getters
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public int getPontos() {
        return pontos;
    }
    public int getQuizesRespondidos() {
        return quizesRespondidos;
    }
    public String getSenha() {
        return senha;
    }
    public int getQuantidadeBoosterAbertos() {
        return quantidadeBoosterAbertos;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    public void setQuizesRespondidos(int quizesRespondidos) {
        this.quizesRespondidos = quizesRespondidos;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setQuantidadeBoosterAbertos(int quantidadeBoosterAbertos) {
        this.quantidadeBoosterAbertos = quantidadeBoosterAbertos;
    }


    @Override
    public JsonValue toJson() { // Converte os dados do objeto para um JsonValue que será salvo no banco de dados pela API
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("id", new JsonValue(id));
        json.addChild("nome", new JsonValue(nome));
        json.addChild("senha", new JsonValue(senha));
        json.addChild("pontos", new JsonValue(pontos));
        json.addChild("quizesRespondidos", new JsonValue(quizesRespondidos));
        json.addChild("quantidadeBoosterAbertos", new JsonValue(quantidadeBoosterAbertos));
        return json;
    }

    @Override
    public JsonValue toJsonKey() { // Retorna apenas o id do objeto para ser usado no select
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("id", new JsonValue(id));
        return json;
    }

    @Override
    public void fromJson(JsonValue json) { // Converte os dados do JsonValue para os atributos do objeto
        id = json.getInt("id");
        nome = json.getString("nome");
        senha = json.getString("senha");
        pontos = json.getInt("pontos");
        quizesRespondidos = json.getInt("quizesRespondidos");
        quantidadeBoosterAbertos = json.getInt("quantidadeBoosterAbertos");
    }
}

