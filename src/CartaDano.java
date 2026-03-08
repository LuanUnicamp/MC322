public class CartaDano{
    //atributos de CartaDano
    private String nome;
    private int custo;
    private int qtd_dano;

    //construtor de CartaDano
    public CartaDano(String nome, int custo, int qtd_dano){
        this.nome=nome;
        this.custo=custo;
        this.qtd_dano = qtd_dano;
    }

    //metodo que usa a carta de dano no inimigo
    public void usar(Inimigo i){
        i.receberDano(qtd_dano);
    }

    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }
}