public class CartaDano{
    private String nome;
    private int custo;

    public CartaDano(String nome, int custo){
        this.nome=nome;
        this.custo=custo;
    }

    public String usar(){
        return "causou dano"+nome+custo;
    }

    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }
}