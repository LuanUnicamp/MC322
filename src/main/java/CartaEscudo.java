/**
 * Representa uma carta de defesa que concede pontos de escudo imediatos à entidade que a utilizar.
 */
public class CartaEscudo extends Carta{
    //atributos de cartaEscudo
    protected int qtd_escudo;

    /**
     * Inicializa os atributos da carta de escudo.
     * @param nome O nome da carta.
     * @param descricao A descrição do efeito da carta.
     * @param custo O custo de chakra para usar a carta.
     * @param qtd_escudo A quantidade de escudo concedida pela carta.
     */
    public CartaEscudo(String nome, String descricao, int custo, int qtd_escudo) {
        super(nome, descricao, custo);
        this.qtd_escudo = qtd_escudo;
    }

    /**
     * Executa a ação da carta quando utilizada pelo herói.
     * @param h O herói que está usando a carta e receberá o escudo.
     * @param i O inimigo presente no combate.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Adiciona o valor de proteção ao escudo atual do herói e exibe a mensagem correspondente no terminal.
     */
    @Override
    public void usar_h(Heroi h, Inimigo i,Combate combate){
        //logica da carta e o que esta acontecendo 
        h.ganharEscudo(qtd_escudo);
        System.out.println("\n 🛡️  " + App.CIANO + "DEFESA ATIVADA: " + App.RESET + App.NEGRITO + h.getNome() + App.RESET);
        System.out.println("    Proteção: " + App.CIANO + "+" + this.qtd_escudo + " de Escudo" + App.RESET);
    }

    /**
     * Executa a ação da carta quando utilizada pelo inimigo.
     * @param i O inimigo que está usando a carta e receberá o escudo.
     * @param h O herói presente no combate.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Adiciona o valor de proteção ao escudo do inimigo, recupera a vitalidade do inimigo sem ultrapassar a vida máxima, e exibe as ações no terminal.
     */
    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate){
        //logica da carta e o que esta acontecendo 
        i.ganharEscudo(qtd_escudo);
        System.out.println("\n 💠 " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " ergueu uma barreira!");
        System.out.println("    Escudo Inimigo: " + App.CIANO + "+" + this.qtd_escudo + " de proteção" + App.RESET);
        
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