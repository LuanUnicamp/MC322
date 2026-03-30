public class CartaDanoVeneno extends CartaDano{
    private int veneno;

    public CartaDanoVeneno(String nome, String descricao, int custo, int qtd_dano, int veneno){
        super(nome, descricao, custo, qtd_dano);
        this.veneno=veneno;
    }


    //metodo que usa a carta de dano no inimigo
    @Override
    public void usar_h(Heroi h,Inimigo i,Combate combate){
        i.receberDano(qtd_dano);
        Veneno efeito=new Veneno("VENENO", i, this.veneno);

        combate.inscreverEfeito(efeito);

        System.out.println("O veneno"+efeito.getNome()+"foi aplicado!");
    }

    //metodo que usa a carta de dano no inimigo
    @Override
    public void usar_i(Inimigo i,Heroi h,Combate combate){
        h.receberDano(qtd_dano);
        Veneno efeito = new Veneno("VENENO", h, this.veneno);

        //h.aplicarEfeito(veneno_inimigo);
        combate.inscreverEfeito(efeito);

        System.out.println("O veneno"+efeito.getNome()+"foi aplicado!");
    }

    public int getVeneno(){
        return veneno;
    }
    
}
