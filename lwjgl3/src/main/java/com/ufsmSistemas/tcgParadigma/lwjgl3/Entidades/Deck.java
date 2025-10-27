package classes;

import java.util.List;

public class Deck {
    private int id;
    private int limiteCartas;
    private List<Carta> cartaList;

    // Getters
    public int getId() {
        return id;
    }
    public int getLimiteCartas() {
        return limiteCartas;
    }
    public List<Carta> getCartaList() {
        return cartaList;
    }
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setLimiteCartas(int limiteCartas) {
        this.limiteCartas = limiteCartas;
    }
    public void setCartaList(List<Carta> cartaList) {
        this.cartaList = cartaList;
    }
}