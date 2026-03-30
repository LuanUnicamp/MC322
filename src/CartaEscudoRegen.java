public class CartaEscudoRegen extends CartaEscudo{
    private int regen;

    public CartaEscudoRegen(String nome, String descricao, int custo, int qtd_escudo, int regen){
        super(nome, descricao, custo, qtd_escudo);
        this.regen=regen;
    }

    //metodo que usa a carta de escudo no heroi
    @Override
    public void usar_h(Heroi h, Inimigo i,Combate combate){
        super.usar_h(h, i, combate);
        Regen regen_heroi= new Regen("Técnica das cem forças", h, this.regen);
        combate.inscreverEfeito(regen_heroi);
        System.out.println("O regen "+regen_heroi.getNome()+"foi aplicado!");
    }

    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate){
        super.usar_i(i, h, combate);
        Regen regen_inimigo= new Regen("Células do Hashirama", i, this.regen);
        combate.inscreverEfeito(regen_inimigo);
        System.out.println("O regen "+regen_inimigo.getNome()+"foi aplicado!");

    }
    public int getRegen(){
        return regen;
    }

}
