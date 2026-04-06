/**
 * Representa uma carta de defesa que concede pontos de escudo imediatos e aplica um efeito de regeneração contínua.
 */
public class CartaEscudoRegen extends CartaEscudo{
    private int regen;

    /**
     * Inicializa os atributos da carta de escudo com regeneração.
     * @param nome O nome da carta.
     * @param descricao A descrição do efeito da carta.
     * @param custo O custo de chakra para usar a carta.
     * @param qtd_escudo A quantidade de escudo concedida pela carta.
     * @param regen A quantidade de cura regenerada por turno.
     */
    public CartaEscudoRegen(String nome, String descricao, int custo, int qtd_escudo, int regen){
        super(nome, descricao, custo, qtd_escudo);
        this.regen=regen;
    }

    /**
     * Executa a ação da carta quando utilizada pelo herói.
     * @param h O herói que está usando a carta.
     * @param i O inimigo presente no combate.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Concede escudo ao herói, cria um efeito de regeneração, aplica-o à entidade, inscreve o efeito no combate e exibe a mensagem no terminal.
     */
    @Override
    public void usar_h(Heroi h, Inimigo i,Combate combate){
        //logica da carta e o que esta acontecendo 
        h.ganharEscudo(qtd_escudo);
        Regen regen_heroi= new Regen("Técnica das cem forças", h, this.regen,"REGEN");
        h.aplicarEfeito(regen_heroi);
        combate.inscreverEfeito(regen_heroi);


        System.out.println("\n ✨ " + App.VERDE + "NINJUTSU MÉDICO: " + App.RESET + App.NEGRITO + h.getNome() + App.RESET);
        System.out.println("    Proteção: " + App.CIANO + "+" + this.qtd_escudo + " de Escudo" + App.RESET);
        System.out.println("    Vitalidade: " + App.VERDE + "Ativou " + regen_heroi.getNome() + " (+" + this.regen + " de Regen)" + App.RESET);

        
    }

    /**
     * Executa a ação da carta quando utilizada pelo inimigo.
     * @param i O inimigo que está usando a carta.
     * @param h O herói presente no combate.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Concede escudo ao inimigo, cria um efeito de regeneração, aplica-o à entidade, inscreve o efeito no combate e exibe a mensagem no terminal.
     */
    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate){
        //logica da carta e o que esta acontecendo 
        i.ganharEscudo(qtd_escudo);
        Regen regen_inimigo= new Regen("Células do Hashirama", i, this.regen,"REGEN");
        i.aplicarEfeito(regen_inimigo);
        combate.inscreverEfeito(regen_inimigo);

        System.out.println("\n 🌳 " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " usou uma técnica de regeneração!");
        System.out.println("    Defesa Inimiga: " + App.CIANO + "+" + this.qtd_escudo + " de Escudo" + App.RESET);
        System.out.println("    Efeito: " + App.VERDE + "Regeneração Celular ativada (+" + this.regen + " de Regen)" + App.RESET);
    }

    public int getRegen(){
        return regen;
    }

}