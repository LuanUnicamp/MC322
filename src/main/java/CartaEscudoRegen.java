public class CartaEscudoRegen extends CartaEscudo{
    private int regen;

    //construtor
    public CartaEscudoRegen(String nome, String descricao, int custo, int qtd_escudo, int regen){
        super(nome, descricao, custo, qtd_escudo);
        this.regen=regen;
    }

    //metodo usar da carta escudo que tem regen, é aplicado o efeito na entidade e  inscrito na lista do combate
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

    //metodo usar da carta escudo que tem regen, é aplicado o efeito na entidade e  inscrito na lista do combate
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
