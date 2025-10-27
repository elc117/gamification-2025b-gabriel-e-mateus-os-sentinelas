package classes;

import java.util.List;

public class Album {
    private List<Carta> cartaList;
    private int quantidadeCartas;

    // Getters
    public List<Carta> getCartaList() {
        return cartaList;
    }
    public int getQuantidadeCartas() {
        return quantidadeCartas;
    }

    // Setters
    public void setCartaList(List<Carta> cartaList) {
        this.cartaList = cartaList;
    }
    public void setQuantidadeCartas(int quantidadeCartas) {
        this.quantidadeCartas = quantidadeCartas;
    }
}