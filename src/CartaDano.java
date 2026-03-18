public class CartaDano extends Carta{
    //atributos de CartaDano
    private int qtd_dano;

    //construtor de CartaDano
    public CartaDano(String nome, String descricao, int custo, int qtd_dano) {
        super(nome, descricao, custo);
        this.qtd_dano = qtd_dano;
    }

    //metodo que usa a carta de dano no inimigo
    @Override
    public void usar_h(Heroi h,Inimigo i){
        i.receberDano(qtd_dano);
    }

    //metodo que usa a carta de dano no inimigo
    @Override
    public void usar_i(Inimigo i,Heroi h){
        h.receberDano(qtd_dano);
    }
}