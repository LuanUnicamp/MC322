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
        Veneno veneno_heroi=new Veneno("Vontade do Fogo", i, this.veneno);

        i.aplicarEfeito(veneno_heroi);
        combate.inscreverEfeito(veneno_heroi);

        //App.venenoAplicado(veneno_heroi);
    }

    //metodo que usa a carta de dano no inimigo
    @Override
    public void usar_i(Inimigo i,Heroi h,Combate combate){
        h.receberDano(qtd_dano);
        Veneno veneno_inimigo = new Veneno("Tsukuyomi Infinito", h, this.veneno);

        h.aplicarEfeito(veneno_inimigo);
        combate.inscreverEfeito(veneno_inimigo);

        //App.venenoAplicado(veneno_inimigo);
    }

    public int getVeneno(){
        return veneno;
    }
    
}
