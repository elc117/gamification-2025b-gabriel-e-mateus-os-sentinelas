package com.ufsmSistemas.tcgParadigma.Classes;

import java.util.List;

public class Pergunta {
    private String enunciado;
    private List<String> respostasErradas;
    private String respostaCerta;
    private int respostaEscolhida;

    //Construtor
    public Pergunta(String enunciado, String respostaCerta, List<String> respostasErradas){
        this.enunciado = enunciado;
        this.respostasErradas = respostasErradas;
        this.respostaCerta = respostaCerta;
    }

    // Getters
    public String getEnunciado() {
        return enunciado;
    }
    public String getRespostaCerta() {
        return respostaCerta;
    }
    public List<String> getRespostaErrada(){return respostasErradas;}
    public int getRespostaEscolhida() {
        return respostaEscolhida;
    }

    // Setters
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
    public void setAlternativas(List<String> respostasErradas) {
        this.respostasErradas = respostasErradas;
    }
    public void setRespostaCerta(String respostaCerta) {
        this.respostaCerta = respostaCerta;
    }
    public void setRespostaEscolhida(int respostaEscolhida) {
        this.respostaEscolhida = respostaEscolhida;
    }
}
