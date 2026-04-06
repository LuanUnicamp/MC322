/**
 * Representa uma carta de ataque que causa dano direto a uma entidade alvo.
 */
public class CartaDano extends Carta{
    //atributos de CartaDano
    protected int qtd_dano;

    /**
     * Inicializa os atributos da carta de dano.
     * @param nome O nome da carta.
     * @param descricao A descrição do efeito da carta.
     * @param custo O custo de chakra para usar a carta.
     * @param qtd_dano A quantidade de dano que a carta causará.
     */
    public CartaDano(String nome, String descricao, int custo, int qtd_dano) {
        super(nome, descricao, custo);
        this.qtd_dano = qtd_dano;
    }

    /**
     * Executa a ação da carta quando utilizada pelo herói.
     * @param h O herói que está usando a carta.
     * @param i O inimigo alvo que receberá o dano.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Reduz a vida do inimigo com base na quantidade de dano da carta e exibe a mensagem do ataque no terminal.
     */
    @Override
    public void usar_h(Heroi h,Inimigo i,Combate combate){
        i.receberDano(qtd_dano);
        
        System.out.println(" 💥 " + App.NEGRITO + h.getNome() + App.RESET + " atacou causando " + App.VERMELHO + this.qtd_dano + " de dano" + App.RESET + " em " + i.getNome() + "!");
    }

    /**
     * Executa a ação da carta quando utilizada pelo inimigo.
     * @param i O inimigo que está usando a carta.
     * @param h O herói alvo que receberá o dano.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Reduz a vida do herói com base na quantidade de dano da carta e exibe a mensagem de contra-ataque no terminal.
     */
    @Override
    public void usar_i(Inimigo i,Heroi h,Combate combate){
        h.receberDano(qtd_dano);

        System.out.println(" ⚠️ " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " contra-atacou! " + "Você sofreu " + App.VERMELHO + App.NEGRITO + this.qtd_dano + " de dano" + App.RESET + "!");
       
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