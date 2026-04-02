public class CartaEscudoEspinhos extends Carta {
    private int qtdEscudo;
    private int danoReflexo;

    public CartaEscudoEspinhos(String nome, String desc, int custo, int escudo, int dano) {
        super(nome, desc, custo);
        this.qtdEscudo = escudo;
        this.danoReflexo = dano;
    }

    //metodo quando heroi usa a carta de escudo com espinho
    @Override
    public void usar_h(Heroi h, Inimigo i, Combate combate) {
       //logica da carta e o que esta acontecendo 
        h.ganharEscudo(this.qtdEscudo);
        i.receberDano(this.danoReflexo);

        System.out.println("\n 🛡️ " + CIANO + nome + ": " + RESET + h.getNome() + " se protegeu!");
        System.out.println("    Defesa: " + CIANO + "+" + qtdEscudo + " de Escudo" + RESET);
        System.out.println("    Contra-ataque: " + VERMELHO + danoReflexo + " de dano em " + i.getNome() + RESET);
    }

    //metodo quando heroi usa a carta de escudo com espinho
    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate) {
        //logica da carta e o que esta acontecendo 
        i.ganharEscudo(this.qtdEscudo);
        h.receberDano(this.danoReflexo);

        System.out.println("\n 🛡️ " + VERMELHO + i.getNome() + RESET + " usou " + nome + "!");
        System.out.println("    O inimigo ganhou " + qtdEscudo + " de escudo e você sofreu " + danoReflexo + " de contra-ataque!");
    }

    @Override public int getDano() { return danoReflexo; }
    @Override public int getEscudo() { return qtdEscudo; }
}