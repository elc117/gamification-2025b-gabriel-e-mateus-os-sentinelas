package com.ufsmSistemas.tcgParadigma.models.quiz;

import java.util.List;

public class Pergunta {
    private String enunciado;
    private List<String> respostasErradas;
    private String respostaCerta;
    private int respostaEscolhida;

    public Pergunta(String enunciado, String respostaCerta, List<String> respostasErradas){
        this.enunciado = enunciado;
        this.respostasErradas = respostasErradas;
        this.respostaCerta = respostaCerta;
    }

    public String getEnunciado() {
        return enunciado;
    }
    public String getRespostaCerta() {
        return respostaCerta;
    }
    public List<String> getRespostaErrada(){return respostasErradas;}

    public void setAlternativas(List<String> respostasErradas) {
        this.respostasErradas = respostasErradas;
    }
    public void setRespostaCerta(String respostaCerta) {
        this.respostaCerta = respostaCerta;
    }
}
