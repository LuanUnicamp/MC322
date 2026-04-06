/**
 * Classe abstrata que define a estrutura base para todas as cartas do jogo. <br>
 * <b>Comportamento</b>: Estabelece os atributos comuns e força as classes filhas 
 * a implementarem as lógicas específicas de uso no combate.
 */
public abstract class Carta{
    public static final String RESET = "\u001B[0m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String CIANO = "\u001B[36m";
    public static final String ROXO = "\u001B[35m";
    public static final String NEGRITO = "\033[1m";

    protected String nome;
    protected String descricao;
    protected int custo;

    /**
     * Inicializa os atributos base de uma carta.
     * @param nome O nome de exibição da carta.
     * @param descricao O texto explicativo do efeito da carta.
     * @param custo A quantidade de chakra exigida para ativar a carta.
     */
    public Carta(String nome, String descricao, int custo) {
        this.nome = nome;
        this.descricao = descricao;
        this.custo = custo;
    }

    /**
     * Executa a ação da carta a partir da jogada do herói.
     * @param h A entidade do herói que está usando a carta.
     * @param i A entidade do inimigo alvo.
     * @param combate A instância do gerenciador de combate para registro de efeitos. <br>
     * <b>Comportamento</b>: Aplica alterações de status (dano, escudo, cura) nas entidades ou inscreve novos efeitos no combate.
     */
    public abstract void usar_h(Heroi h, Inimigo i,Combate combate);

    /**
     * Executa a ação da carta a partir da jogada do inimigo.
     * @param i A entidade do inimigo que está usando a carta.
     * @param h A entidade do herói alvo.
     * @param combate A instância do gerenciador de combate para registro de efeitos. <br>
     * <b>Comportamento</b>: Semelhante ao uso do herói, mas com a perspectiva e os alvos invertidos.
     */
    public abstract void usar_i(Inimigo i, Heroi h,Combate combate);
    
    public abstract int getDano();

    public abstract int getEscudo();

    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }

    public String getDescricao(){
        return descricao;
    }
}