public class CartaEscudoRegen extends CartaEscudo{
    private int regen;

    //construtor
    public CartaEscudoRegen(String nome, String descricao, int custo, int qtd_escudo, int regen){
        super(nome, descricao, custo, qtd_escudo);
        this.regen=regen;
    }

    //metodo usar da carta escudo que tem regen, é aplicado o efeito na entidade e  inscrito na lista do combate
    @Override
    public void usar_h(Heroi h, Inimigo i,Combate combate){
        h.ganharEscudo(qtd_escudo);
        Regen regen_heroi= new Regen("Regen Técnica das cem forças", h, this.regen,"REGEN");

        h.aplicarEfeito(regen_heroi);
        combate.inscreverEfeito(regen_heroi);

        System.out.println(h.getNome()+" ganhou "+this.qtd_escudo+" de escudo e "+this.regen+" de regen!");

        
    }

    //metodo usar da carta escudo que tem regen, é aplicado o efeito na entidade e  inscrito na lista do combate
    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate){
        i.ganharEscudo(qtd_escudo);
        Regen regen_inimigo= new Regen("Regen Células do Hashirama", i, this.regen,"REGEN");

        i.aplicarEfeito(regen_inimigo);
        combate.inscreverEfeito(regen_inimigo);

        System.out.println(i.getNome()+" ganhou "+this.qtd_escudo+" de escudo e "+this.regen+" de regen!");
        

    }
    public int getRegen(){
        return regen;
    }

}
