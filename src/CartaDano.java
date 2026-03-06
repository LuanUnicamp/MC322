public class CartaDano{
    //atributos de CartaDano
    private String nome;
    private int custo;

    //construtor de CartaDano
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