/**
 * Representa uma carta de ataque que, além de causar dano direto, aplica um efeito contínuo de veneno no alvo.
 */
public class CartaDanoVeneno extends CartaDano{
    private int veneno;

    /**
     * Inicializa os atributos da carta de dano com veneno.
     * @param nome O nome da carta.
     * @param descricao A descrição do efeito da carta.
     * @param custo O custo de chakra para usar a carta.
     * @param qtd_dano A quantidade de dano base causado.
     * @param veneno A quantidade de acúmulos de veneno aplicados ao alvo.
     */
    public CartaDanoVeneno(String nome, String descricao, int custo, int qtd_dano, int veneno){
        super(nome, descricao, custo, qtd_dano);
        this.veneno=veneno;
    }


    /**
     * Executa a ação da carta quando utilizada pelo herói.
     * @param h O herói que está usando a carta.
     * @param i O inimigo alvo que receberá o dano e o veneno.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Causa dano direto ao inimigo, cria o efeito de veneno, aplica-o à entidade e o inscreve na lista do combate, exibindo as ações no terminal.
     */
    @Override
    public void usar_h(Heroi h,Inimigo i,Combate combate){
        i.receberDano(qtd_dano);
        Veneno veneno_heroi=new Veneno(" Vontade do Fogo", i, this.veneno,"VENENO");
        i.aplicarEfeito(veneno_heroi);
        combate.inscreverEfeito(veneno_heroi);

        //aviso do que esta acontecendo 
        System.out.println("\n ⚔️  " + App.NEGRITO + h.getNome() + App.RESET + " usou um golpe crítico!");
        System.out.println("    Dano Direto: " + App.VERMELHO + this.qtd_dano + App.RESET);
        System.out.println("    Efeito Aplicado: " + App.ROXO + "☣ Veneno (" + this.veneno + ")" + App.RESET + " em " + i.getNome());
    }

    /**
     * Executa a ação da carta quando utilizada pelo inimigo.
     * @param i O inimigo que está usando a carta.
     * @param h O herói alvo que receberá o dano e o veneno.
     * @param combate A instância do gerenciador de combate. <br>
     * <b>Comportamento</b>: Causa dano direto ao herói, cria o efeito de veneno, aplica-o à entidade e o inscreve na lista do combate, exibindo as ações no terminal.
     */
    @Override
    public void usar_i(Inimigo i,Heroi h,Combate combate){
        h.receberDano(qtd_dano);
        Veneno veneno_inimigo = new Veneno(" Tsukuyomi Infinito", h, this.veneno,"VENENO");
        h.aplicarEfeito(veneno_inimigo);
        combate.inscreverEfeito(veneno_inimigo);

        //aviso do que esta acontecendo 
        System.out.println("\n 💀 " + App.VERMELHO + App.NEGRITO + i.getNome() + App.RESET + " executou um Jutsu Proibido!");
        System.out.println("    Dano Sofrido: " + App.VERMELHO + this.qtd_dano + App.RESET);
        System.out.println("    Estado Atual: " + App.ROXO + "☣ Envenenado (" + this.veneno + " de veneno)" + App.RESET);
    }

    public int getVeneno(){
        return veneno;
    }
    
}