package com.ufsmSistemas.tcgParadigma.models;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntity;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Jogador implements DataBaseEntity {
    private int id;
    private String nome;
    private int pontos;
    private int quizesRespondidos;
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


    // Insert
    @Override
    public String getInsertSQL(){
        return "INSERT INTO jogador (nome, senha) VALUES (?, ?)";
    }
    @Override
    public void setInsertParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, nome);
        pstmt.setString(2, senha);
    }


    // Update
    @Override
    public String getUpdateSQL(){
        return "UPDATE jogador SET pontos=?, quizesRespondidos=?, quantidadeBoosterAbertos=? WHERE id = ?";
    }
    @Override
    public void setUpdateParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, pontos);
        pstmt.setInt(2, quizesRespondidos);
        pstmt.setInt(3, 0);
        pstmt.setInt(4, id);
    }


    // Select
    @Override
    public String getSelectSQL(){
        return "SELECT * FROM jogador WHERE id = ?";
    }
    @Override
    public void setSelectParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, id);
    }
}

