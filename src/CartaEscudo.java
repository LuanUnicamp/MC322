public class CartaEscudo {
    //atributos de cartaEscudo
    private String nome;
    private int custo;
    private int qtd_escudo;

    //construtor de cartaEscudo
    public CartaEscudo(String nome, int custo, int qtd_escudo){
        this.nome=nome;
        this.custo=custo;
        this.qtd_escudo = qtd_escudo;
    }

    //metodo que usa a carta de escudo no heroi
    public void usar(Heroi h){
        h.ganharEscudo(qtd_escudo);
    }

    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }
}
