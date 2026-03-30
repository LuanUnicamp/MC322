public class CartaEscudoRegen extends CartaEscudo{
    private int regen;

    public CartaEscudoRegen(String nome, String descricao, int custo, int qtd_escudo, int regen){
        super(nome, descricao, custo, qtd_escudo);
        this.regen=regen;
    }

    //metodo que usa a carta de escudo no heroi
    @Override
    public void usar_h(Heroi h, Inimigo i,Combate combate){
        h.ganharEscudo(qtd_escudo);
        Regen regen_heroi= new Regen("Regen Técnica das cem forças", h, this.regen);

        h.aplicarEfeito(regen_heroi);
        combate.inscreverEfeito(regen_heroi);

        //App.regenAplicado(regen_heroi);
    }

    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate){
        i.ganharEscudo(qtd_escudo);
        Regen regen_inimigo= new Regen("Regen Células do Hashirama", i, this.regen);

        i.aplicarEfeito(regen_inimigo);
        combate.inscreverEfeito(regen_inimigo);
        
        //App.regenAplicado(regen_inimigo);

    }
    public int getRegen(){
        return regen;
    }

}
