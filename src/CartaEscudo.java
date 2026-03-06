public class CartaEscudo {
    private String nome;
    private int custo;
    private int qtd_escudo;

    public CartaEscudo(String nome, int custo, int qtd_escudo){
        this.nome=nome;
        this.custo=custo;
        this.qtd_escudo = qtd_escudo;
    }

    public String usar(Heroi h){
        h.ganharEscudo(qtd_escudo);
        return qtd_escudo + "de escudo atribuido ao heroi";
    }

    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }
}
