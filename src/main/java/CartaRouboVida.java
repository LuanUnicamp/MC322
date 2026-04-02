
public class CartaRouboVida extends Carta {
    private int dano;
    private int cura;

    public CartaRouboVida(String nome, String descricao, int custo, int dano, int cura) {
        super(nome, descricao, custo);
        this.dano = dano;
        this.cura = cura;
    }

    //metodo quando heroi usa a carta de roubo de vida
    @Override
    public void usar_h(Heroi h, Inimigo i, Combate combate) {
        //logica da carta e o que esta acontecendo 
        i.receberDano(this.dano);
        h.receberCura(this.cura);

        System.out.println("\n 🩸 " + App.CIANO + "ROUBANDO VIDA: " + App.RESET + App.NEGRITO + h.getNome() + App.RESET);
        System.out.println("    Ataque: " + App.VERMELHO + "-" + this.dano + " de HP de " + i.getNome() + App.RESET);
        System.out.println("    Absorção: " + App.VERDE + "+" + this.cura + " de Vida para você" + App.RESET);
    
    }

    //metodo quando inimigo usa a carta de escudo com espinho
    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate) {
        //logica da carta e o que esta acontecendo 
        h.receberDano(this.dano);
        i.receberCura(this.cura);
        
        System.out.println("\n 🧛 " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " usou uma técnica proibida!");
        System.out.println("    Impacto: " + App.VERMELHO + "Você perdeu " + this.dano + " de HP" + App.RESET);
        System.out.println("    Recuperação: " + App.VERDE + i.getNome() + " regenerou " + this.cura + " de vida" + App.RESET);
    }
    

    @Override public int getDano() { return this.dano; }
    @Override public int getEscudo() { return 0; }
}