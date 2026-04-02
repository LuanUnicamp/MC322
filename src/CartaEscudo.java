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
        //System.out.println(h.getNome()+" ganhou "+this.qtd_escudo+" de escudo!");
        System.out.println("\n 🛡️  " + App.CIANO + "DEFESA ATIVADA: " + App.RESET + App.NEGRITO + h.getNome() + App.RESET);
        System.out.println("    Proteção: " + App.CIANO + "+" + this.qtd_escudo + " de Escudo" + App.RESET);
    }

    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate){
        i.ganharEscudo(qtd_escudo);
        //System.out.println(i.getNome()+" ganhou "+this.qtd_escudo+" de escudo!");
        System.out.println("\n 💠 " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " ergueu uma barreira!");
        System.out.println("    Escudo Inimigo: " + App.CIANO + "+" + this.qtd_escudo + " de proteção" + App.RESET);
        
        //se a regeneração passar da vida maxima, a vida se limita a vida maxima
        if((i.vida + qtd_escudo) > i.vidaMaxima){
            i.vida = i.vidaMaxima;
        } else{
            i.vida += qtd_escudo;
        }
        System.out.println("    Nota: O inimigo também estabilizou sua vitalidade.");
    }

    @Override
    public int getDano(){
        return 0;

    }

    @Override
    public int getEscudo(){
        return qtd_escudo;
    }

}
