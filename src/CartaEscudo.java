public class CartaEscudo extends Carta{
    //atributos de cartaEscudo

    private int qtd_escudo;

    //construtor de cartaEscudo
    public CartaEscudo(String nome, String descricao, int custo, int qtd_escudo) {
        super(nome, descricao, custo);
        this.qtd_escudo = qtd_escudo;
    }

    //metodo que usa a carta de escudo no heroi
    @Override
    public void usar(Heroi h, Inimigo i){
        h.ganharEscudo(qtd_escudo);
    }

}
