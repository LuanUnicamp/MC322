public class CartaDanoVeneno extends CartaDano{
    private int veneno;

    //construtor
    public CartaDanoVeneno(String nome, String descricao, int custo, int qtd_dano, int veneno){
        super(nome, descricao, custo, qtd_dano);
        this.veneno=veneno;
    }


    //metodo usar da carta dano que tem veneno, é aplicado o efeito na entidade e  inscrito na lista do combate
    @Override
    public void usar_h(Heroi h,Inimigo i,Combate combate){
        i.receberDano(qtd_dano);
        Veneno veneno_heroi=new Veneno(" Vontade do Fogo", i, this.veneno,"VENENO");

        i.aplicarEfeito(veneno_heroi);
        combate.inscreverEfeito(veneno_heroi);

       //System.out.println(h.getNome() + " atacou " + i.getNome() + " causando " + this.qtd_dano + " de dano e "+this.veneno+" de veneno!");

        System.out.println("\n ⚔️  " + App.NEGRITO + h.getNome() + App.RESET + " usou um golpe crítico!");
        System.out.println("    Dano Direto: " + App.VERMELHO + this.qtd_dano + App.RESET);
        System.out.println("    Efeito Aplicado: " + App.ROXO + "☣ Veneno (" + this.veneno + ")" + App.RESET + " em " + i.getNome());

       
    }

    //metodo usar da carta dano que tem veneno, é aplicado o efeito na entidade e  inscrito na lista do combate
    @Override
    public void usar_i(Inimigo i,Heroi h,Combate combate){
        h.receberDano(qtd_dano);
        Veneno veneno_inimigo = new Veneno(" Tsukuyomi Infinito", h, this.veneno,"VENENO");

        h.aplicarEfeito(veneno_inimigo);
        combate.inscreverEfeito(veneno_inimigo);

        //System.out.println(i.getNome() + " atacou " + h.getNome() + " causando " + this.qtd_dano + " de dano e "+this.veneno+" de veneno!");
        System.out.println("\n 💀 " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " executou um Jutsu Proibido!");
        System.out.println("    Dano Sofrido: " + App.VERMELHO + this.qtd_dano + App.RESET);
        System.out.println("    Estado Atual: " + App.ROXO + "☣ Envenenado (" + this.veneno + " de veneno)" + App.RESET);
       
        
    }

    public int getVeneno(){
        return veneno;
    }
    
}
