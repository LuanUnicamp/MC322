import java.util.Random;

/**
 * Representa uma carta de ataque em área que possui uma chance de causar dano crítico 
 * ou falhar, resultando em um dano reduzido.
 */
public class CartaDanoArea extends Carta {
    private int danoCritico;
    private int danoFalha;
    private int chanceSucesso;
    private Random random = new Random();

    /**
     * Inicializa os atributos da carta de dano em área.
     * @param nome O nome da carta.
     * @param descricao A descrição do efeito da carta.
     * @param custo O custo de chakra para usar a carta.
     * @param critico O valor do dano caso o ataque seja bem-sucedido.
     * @param falha O valor do dano caso o ataque falhe.
     * @param chance A porcentagem de chance (0 a 100) do ataque ser um sucesso crítico.
     */
    public CartaDanoArea(String nome, String descricao, int custo, int critico, int falha, int chance) {
        super(nome, descricao, custo);
        this.danoCritico = critico;
        this.danoFalha = falha;
        this.chanceSucesso = chance;
    }

    /**
     * Executa a ação da carta quando utilizada pelo herói.
     * @param h O herói que está usando a carta.
     * @param i O inimigo alvo que receberá o dano.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Calcula aleatoriamente se o ataque será crítico ou falho com base na chance de sucesso, 
     * aplica o dano correspondente ao inimigo e exibe o resultado no terminal.
     */
    @Override
    public void usar_h(Heroi h, Inimigo i, Combate combate) {
        //logica da carta e o que esta acontecendo 
        System.out.println("\n 🌀 " + App.CIANO + "ESTILO DANO EM ÁREA: " + App.RESET + App.NEGRITO + nome + App.RESET);
        
        if (random.nextInt(100) < this.chanceSucesso) {
            i.receberDano(this.danoCritico);
            System.out.println("    " + App.VERDE + "SUCESSO TOTAL! 🔥 " + App.RESET + "A técnica atingiu o ápice!");
            System.out.println("    Dano Crítico: " + App.VERMELHO + "-" + this.danoCritico + " HP em " + i.getNome() + App.RESET);
        } else {
            i.receberDano(this.danoFalha);
            System.out.println("    " + App.AMARELO + "FALHA NA EXECUÇÃO... 💨" + App.RESET + " A energia se dispersou.");
            System.out.println("    Dano Reduzido: " + App.VERMELHO + "-" + this.danoFalha + " HP" + App.RESET);
        }
    }


    /**
     * Executa a ação da carta quando utilizada pelo inimigo.
     * @param i O inimigo que está usando a carta.
     * @param h O herói alvo que receberá o dano.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Calcula aleatoriamente se o ataque será crítico ou falho com base na chance de sucesso, 
     * aplica o dano correspondente ao herói e exibe o resultado no terminal.
     */
    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate) {
        //logica da carta e o que esta acontecendo 
        System.out.println("\n 🌪️ " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " lançou um ataque massivo!");

        if (random.nextInt(100) < this.chanceSucesso) {
            h.receberDano(this.danoCritico);
            System.out.println("    " + App.VERMELHO + "GOLPE CRÍTICO! Você recebeu " + this.danoCritico + " de dano!" + App.RESET);
        } else {
            h.receberDano(this.danoFalha);
            System.out.println("    " + App.AMARELO + "Você esquivou parcialmente! Recebeu apenas " + this.danoFalha + " de dano." + App.RESET);
        }
    }

    @Override public int getDano() { return this.danoCritico; }
    @Override public int getEscudo() { return 0; }
}