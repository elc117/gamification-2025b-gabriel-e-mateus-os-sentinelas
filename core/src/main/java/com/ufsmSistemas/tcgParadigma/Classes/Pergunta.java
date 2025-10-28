package com.ufsmSistemas.tcgParadigma.Classes;

import java.util.List;

public class Pergunta {
    private String enunciado;
    private List<String> alternativas;
    private int respostaCerta;
    private int respostaEscolhida;

    // Getters
    public String getEnunciado() {
        return enunciado;
    }
    public List<String> getAlternativas() {
        return alternativas;
    }
    public int getRespostaCerta() {
        return respostaCerta;
    }
    public int getRespostaEscolhida() {
        return respostaEscolhida;
    }

    // Setters
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
    public void setAlternativas(List<String> alternativas) {
        this.alternativas = alternativas;
    }
    public void setRespostaCerta(int respostaCerta) {
        this.respostaCerta = respostaCerta;
    }
    public void setRespostaEscolhida(int respostaEscolhida) {
        this.respostaEscolhida = respostaEscolhida;
    }
}
