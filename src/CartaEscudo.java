public class CartaEscudo {
    private String nome;
    private int custo;

    public CartaEscudo(String nome, int custo){
        this.nome=nome;
        this.custo=custo;
    }

    public String usar(){
        return "deu escudo"+nome+custo;
    }

    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }
}
