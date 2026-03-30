public class CartaEscudo extends Carta{
    //atributos de cartaEscudo
    protected int qtd_escudo;

    //construtor de cartaEscudo
    public CartaEscudo(String nome, String descricao, int custo, int qtd_escudo) {
        super(nome, descricao, custo);
        this.qtd_escudo = qtd_escudo;
    }

    //metodo que usa a carta de escudo no heroi
    @Override
    public void usar_h(Heroi h, Inimigo i,Combate combate){
        h.ganharEscudo(qtd_escudo);
    }

    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate){
        i.ganharEscudo(qtd_escudo);
    }

}
