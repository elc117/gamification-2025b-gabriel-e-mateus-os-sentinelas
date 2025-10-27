package classes;

public class Carta {
    private int id;
    private String nome;
    private int vida;
    private int forca;
    private int vidaAtual;
    private String caminhoImagem;
    private String descricao;
    private String raridade;
    private Float pullRate;
    private String categoria;
    private boolean isObtida;
    private int quantidade;

    // Getters
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public int getVida() {
        return vida;
    }
    public int getForca() {
        return forca;
    }
    public String getCaminhoImagem() {
        return caminhoImagem;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getRaridade() {
        return raridade;
    }
    public Float getPullRate() {
        return pullRate;
    }
    public String getCategoria() {
        return categoria;
    }
    public int getVidaAtual() {
        return vidaAtual;
    }
    public boolean isObtida() {
        return isObtida;
    }
    public int getQuantidade() {
        return quantidade;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public void setForca(int forca) {
        this.forca = forca;
    }
    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setRaridade(String raridade) {
        this.raridade = raridade;
    }
    public void setPullRate(Float pullRate) {
        this.pullRate = pullRate;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }
    public void setObtida(boolean isObtida) {
        this.isObtida = isObtida;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}