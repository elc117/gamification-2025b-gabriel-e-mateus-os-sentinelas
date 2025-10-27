package classes;

public class Batalha {
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador vencedor;
    
    // Getters
    public Jogador getJogador1() {
        return jogador1;
    }
    public Jogador getJogador2() {
        return jogador2;
    }
    public Jogador getVencedor() {
        return vencedor;
    }
    // Setters
    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }
    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }
    public void setVencedor(Jogador vencedor) {
        this.vencedor = vencedor;
    }
}
