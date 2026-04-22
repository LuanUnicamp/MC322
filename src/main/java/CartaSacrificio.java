/**
 * Representa uma carta de ataque devastador que causa dano ao alvo, 
 * mas também inflige dano de recuo ao próprio usuário.
 */
public class CartaSacrificio extends Carta {
    private int danoAoAlvo;
    private int danoAoUsuario;

    /**
     * Inicializa os atributos da carta de sacrifício.
     * @param nome O nome da carta.
     * @param desc A descrição do efeito da carta.
     * @param custo O custo de chakra para usar a carta.
     * @param danoAlvo A quantidade de dano causada ao oponente.
     * @param danoUsuario A quantidade de dano sofrida pelo usuário como consequência.
     */
    public CartaSacrificio(String nome, String desc, int custo, int danoAlvo, int danoUsuario) {
        super(nome, desc, custo);
        this.danoAoAlvo = danoAlvo;
        this.danoAoUsuario = danoUsuario;
    }


    /**
     * Executa a ação da carta quando utilizada pelo herói.
     * @param h O herói que está usando a carta e sofrerá o dano de recuo.
     * @param i O inimigo alvo que receberá o dano principal.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Reduz a vida do herói (dano de recuo) e a vida do inimigo (impacto direto), 
     * exibindo as consequências de ambas as partes no terminal.
     */
    @Override
    public void usar_h(Heroi h, Inimigo i, Combate combate) {
        //logica da carta e o que esta acontecendo 
        h.receberDano(this.danoAoUsuario);
        i.receberDano(this.danoAoAlvo);
        
        System.out.println("\n 💥 " + App.ROXO + App.NEGRITO + "TÉCNICA PROIBIDA: " + App.RESET + App.NEGRITO + nome + App.RESET);
        System.out.println("    " + i.getNome() + ": " + App.VERMELHO + "-" + this.danoAoAlvo + " HP (Impacto Direto)" + App.RESET);
        System.out.println("    " + h.getNome() + ": " + App.AMARELO + "-" + this.danoAoUsuario + " HP (Dano de Recuo)" + App.RESET);
    }


    /**
     * Executa a ação da carta quando utilizada pelo inimigo.
     * @param i O inimigo que está usando a carta e sofrerá o dano de recuo.
     * @param h O herói alvo que receberá o dano principal.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Reduz a vida do inimigo (auto-dano) e a vida do herói (alvo atingido), 
     * exibindo o aviso da técnica arriscada no terminal.
     */
    @Override
    public void usar_i(Inimigo i, Heroi h, Combate combate) {
        //logica da carta e o que esta acontecendo 
        i.receberDano(this.danoAoUsuario);
        h.receberDano(this.danoAoAlvo);
        
        System.out.println(VERMELHO + i.getNome() + " sacrificou parte da própria vida para te ferir com " + 
                           danoAoAlvo + " de dano!" + RESET);

        System.out.println("\n 💀 " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " ignorou a própria segurança!");
        System.out.println("    Alvo atingido: " + App.VERMELHO + "Você recebeu " + this.danoAoAlvo + " de dano!" + App.RESET);
        System.out.println("    Auto-dano: " + App.AMARELO + i.getNome() + " também perdeu " + this.danoAoUsuario + " de HP" + App.RESET);
    }

    @Override public int getDano() { return this.danoAoAlvo; }
    @Override public int getEscudo() { return 0; }

    public int getDanoAoUsuario(){
        return this.danoAoUsuario;
    }
}