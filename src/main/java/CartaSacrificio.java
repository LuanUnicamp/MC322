public class CartaSacrificio extends Carta {
    private int danoAoAlvo;
    private int danoAoUsuario;

    //construtor
    public CartaSacrificio(String nome, String desc, int custo, int danoAlvo, int danoUsuario) {
        super(nome, desc, custo);
        this.danoAoAlvo = danoAlvo;
        this.danoAoUsuario = danoUsuario;
    }


    //metodo quando heroi usa a carta de rsacrificio
    @Override
    public void usar_h(Heroi h, Inimigo i, Combate combate) {
        //logica da carta e o que esta acontecendo 
        h.receberDano(this.danoAoUsuario);
        i.receberDano(this.danoAoAlvo);
        
        System.out.println("\n 💥 " + App.ROXO + App.NEGRITO + "TÉCNICA PROIBIDA: " + App.RESET + App.NEGRITO + nome + App.RESET);
        System.out.println("    " + i.getNome() + ": " + App.VERMELHO + "-" + this.danoAoAlvo + " HP (Impacto Direto)" + App.RESET);
        System.out.println("    " + h.getNome() + ": " + App.AMARELO + "-" + this.danoAoUsuario + " HP (Dano de Recuo)" + App.RESET);
    }


    //metodo quando inimigo usa a carta de rsacrificio
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
}