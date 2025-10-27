package classes;

public class Jogador {
    private int id;
    private String nome;
    private int pontos;
    private int cartasObtidas;
    private int quizesRespondidos;

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
    public int getCartasObtidas() {
        return cartasObtidas;
    }
    public int getQuizesRespondidos() {
        return quizesRespondidos;
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
    public void setCartasObtidas(int cartasObtidas) {
        this.cartasObtidas = cartasObtidas;
    }
    public void setQuizesRespondidos(int quizesRespondidos) {
        this.quizesRespondidos = quizesRespondidos;
    }

}

