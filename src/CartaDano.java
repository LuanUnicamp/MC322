public class CartaDano extends Carta{
    //atributos de CartaDano
    protected int qtd_dano;

    //construtor de CartaDano
    public CartaDano(String nome, String descricao, int custo, int qtd_dano) {
        super(nome, descricao, custo);
        this.qtd_dano = qtd_dano;
    }

    //metodo que usa a carta de dano no inimigo
    @Override
    public void usar_h(Heroi h,Inimigo i,Combate combate){
        i.receberDano(qtd_dano);
        //System.out.println(h.getNome() + " atacou " + i.getNome() + " causando " + this.qtd_dano + " de dano!");
        System.out.println(" 💥 " + App.NEGRITO + h.getNome() + App.RESET + " atacou causando " + 
                       App.VERMELHO + this.qtd_dano + " de dano" + App.RESET + " em " + i.getNome() + "!");
    }

    //metodo que usa a carta de dano no inimigo
    @Override
    public void usar_i(Inimigo i,Heroi h,Combate combate){
        h.receberDano(qtd_dano);

        //System.out.println(i.getNome() + " atacou " + h.getNome() + " causando " + this.qtd_dano + " de dano!");
         System.out.println(" ⚠️ " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " contra-atacou! " + 
                       "Você sofreu " + App.VERMELHO + App.NEGRITO + this.qtd_dano + " de dano" + App.RESET + "!");
       
    }

    @Override
    public int getDano(){
        return qtd_dano;

    }

    @Override
    public int getEscudo(){
        return 0;
    }
}