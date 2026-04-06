/**
 * Representa uma carta que cura o usuário imediatamente e aplica um efeito de regeneração contínua.
 */
public class CartaCuraRegen extends Carta {
    private int qtd_cura;
    private int regenCura;

    /**
     * Inicializa os atributos da carta de cura com regeneração.
     * @param nome O nome da carta.
     * @param descricao A descrição do efeito da carta.
     * @param custo O custo de chakra para usar a carta.
     * @param qtd_cura A quantidade de cura imediata recebida.
     * @param regenCura A quantidade de cura regenerada por turno.
     */
    public CartaCuraRegen(String nome, String descricao, int custo, int qtd_cura,int regenCura) {
        super(nome, descricao, custo);
        this.qtd_cura=qtd_cura;
        this.regenCura=regenCura;
    }


    /**
     * Executa a ação da carta quando utilizada pelo herói.
     * @param h O herói que está usando a carta e receberá a cura.
     * @param i O inimigo presente no combate.
     * @param combate A instância do gerenciador de combate para inscrever o efeito. <br>
     * <b>Comportamento</b>: Cura a vida do herói imediatamente, instancia um novo efeito de regeneração, 
     * aplica-o ao herói, inscreve o efeito no combate e exibe a mensagem da ação no terminal.
     */
    @Override
    public void usar_h(Heroi h, Inimigo i, Combate combate) {
        //logica da carta e do padrao observer
        h.receberCura(this.qtd_cura);
        Regen regen_heroi = new Regen("Ninjutsu Médico Contínuo", h, this.regenCura, "REGEN");
        h.aplicarEfeito(regen_heroi);
        combate.inscreverEfeito(regen_heroi);

        //aviso do que esta acontecendo 
        System.out.println("\n ✨ " + VERDE + "ARTE MÉDICA: " + RESET + NEGRITO + h.getNome() + RESET);
        System.out.println("    Cura Imediata: " + VERDE + "+" + this.qtd_cura + " HP" + RESET);
        System.out.println("    Efeito Ativo: " + VERDE + regen_heroi.getNome() + " (+" + this.regenCura + " de regen de cura)" + RESET);
    }

    /**
     * Executa a ação da carta quando utilizada pelo inimigo.
     * @param i O inimigo que está usando a carta e receberá a cura.
     * @param h O herói presente no combate.
     * @param combate A instância do gerenciador de combate para inscrever o efeito. <br>
     * <b>Comportamento</b>: Cura a vida do inimigo imediatamente, instancia um novo efeito de regeneração, 
     * aplica-o ao inimigo, inscreve o efeito no combate e exibe a mensagem da ação no terminal.
     */
    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate) {
        //logica da carta e do padrao observer
        i.receberCura(this.qtd_cura);
        Regen regen_inimigo = new Regen("Regeneração Celular", i, this.regenCura, "REGEN");
        i.aplicarEfeito(regen_inimigo);
        combate.inscreverEfeito(regen_inimigo);

        //aviso do que esta acontecendo 
        System.out.println("\n 🌿 " + VERMELHO + NEGRITO + i.getNome() + RESET + " usou uma técnica de restauração!");
        System.out.println("    Recuperação: " + VERDE + "+" + this.qtd_cura + " HP imediatos" + RESET);
        System.out.println("    Efeito: " + VERDE + "Regeneração ativada (+" + this.regenCura + " de regen de cura)" + RESET);
    }

    @Override public int getDano() { return 0; }
    @Override public int getEscudo() { return 0; }

    public int getCura(){
        return qtd_cura;
    }

    public int getRegenCura(){
        return regenCura;
    }
}