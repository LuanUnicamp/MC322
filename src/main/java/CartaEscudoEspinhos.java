/**
 * Representa uma carta de defesa que, além de conceder proteção ao usuário, 
 * causa dano imediato de contra-ataque (espinhos) ao oponente.
 */
public class CartaEscudoEspinhos extends Carta {
    private int qtdEscudo;
    private int danoReflexo;

    /**
     * Inicializa os atributos da carta de escudo com espinhos.
     * @param nome O nome da carta.
     * @param desc A descrição do efeito da carta.
     * @param custo O custo de chakra para usar a carta.
     * @param escudo A quantidade de proteção gerada.
     * @param dano O valor do dano refletido contra o oponente.
     */
    public CartaEscudoEspinhos(String nome, String desc, int custo, int escudo, int dano) {
        super(nome, desc, custo);
        this.qtdEscudo = escudo;
        this.danoReflexo = dano;
    }

    /**
     * Executa a ação da carta quando utilizada pelo herói.
     * @param h O herói que está usando a carta.
     * @param i O inimigo alvo que receberá o dano refletido.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Adiciona os pontos de escudo ao herói, causa o dano de contra-ataque diretamente na vida do inimigo e exibe a mensagem no terminal.
     */
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

    /**
     * Executa a ação da carta quando utilizada pelo inimigo.
     * @param i O inimigo que está usando a carta.
     * @param h O herói alvo que receberá o dano refletido.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Adiciona os pontos de escudo ao inimigo, causa o dano de contra-ataque diretamente na vida do herói e exibe a mensagem no terminal.
     */
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