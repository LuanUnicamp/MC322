public class CartaCuraRegen extends Carta {
    private int qtd_cura;
    private int regenCura;

    //construtor
    public CartaCuraRegen(String nome, String descricao, int custo, int qtd_cura,int regenCura) {
        super(nome, descricao, custo);
        this.qtd_cura=qtd_cura;
        this.regenCura=regenCura;
    }


    //metodo quando heroi usa a carta de cura com Regen
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

    //metodo quando inimigo  usa a carta de cura com Regen
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