package com.ufsmSistemas.tcgParadigma.models;

import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntityAPI;

public class Jogador implements DataBaseEntityAPI {
    private int id;
    private String nome;
    private int pontos;
    private int quizesRespondidos;
    private int quantidadeBoosterAbertos;
    private String senha;

    public Jogador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.pontos = 0;
        this.quizesRespondidos = 0;
        this.quantidadeBoosterAbertos = 0;
    }

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
    public JsonValue toJsonKey() {
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("nome", new JsonValue(nome));
        json.addChild("senha", new JsonValue(senha));
        return json;
    }


    @Override
    public void fromJson(JsonValue json) {
        if (json == null) return;

        if (json.has("erro")) {
            System.out.println("⚠ Erro ao carregar jogador: " + json.getString("erro"));
            return;
        }

        if (json.has("id")) id = json.getInt("id");
        if (json.has("nome")) nome = json.getString("nome");
        if (json.has("senha")) senha = json.getString("senha");
        if (json.has("pontos")) pontos = json.getInt("pontos");
        if (json.has("quizesRespondidos")) quizesRespondidos = json.getInt("quizesRespondidos");
        if (json.has("quantidadeBoosterAbertos")) quantidadeBoosterAbertos = json.getInt("quantidadeBoosterAbertos");
    }

}

