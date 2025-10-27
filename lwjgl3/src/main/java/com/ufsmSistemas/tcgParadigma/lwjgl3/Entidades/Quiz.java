package classes;

import java.util.List;

public class Quiz {
    private String tema;
    private int nivelDificuldade;
    private String imagemHost;
    private int pontosGanhos;
    private int bonusFimQuiz;
    private List<Pergunta> perguntaList;

    // Getters
    public String getTema() {
        return tema;
    }
    public int getNivelDificuldade() {
        return nivelDificuldade;
    }
    public String getImagemHost() {
        return imagemHost;
    }
    public int getPontosGanhos() {
        return pontosGanhos;
    }
    public int getBonusFimQuiz() {
        return bonusFimQuiz;
    }
    public List<Pergunta> getPerguntaList() {
        return perguntaList;
    }

    // Setters
    public void setTema(String tema) {
        this.tema = tema;
    }
    public void setNivelDificuldade(int nivelDificuldade) {
        this.nivelDificuldade = nivelDificuldade;
    }
    public void setImagemHost(String imagemHost) {
        this.imagemHost = imagemHost;
    }
    public void setPontosGanhos(int pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }
    public void setBonusFimQuiz(int bonusFimQuiz) {
        this.bonusFimQuiz = bonusFimQuiz;
    }
    public void setPerguntaList(List<Pergunta> perguntaList) {
        this.perguntaList = perguntaList;
    }
}
