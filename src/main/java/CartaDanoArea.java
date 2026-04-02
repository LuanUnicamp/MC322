import java.util.Random;


public class CartaDanoArea extends Carta {
    private int danoCritico;
    private int danoFalha;
    private int chanceSucesso;
    private Random random = new Random();

    //construtor
    public CartaDanoArea(String nome, String descricao, int custo, int critico, int falha, int chance) {
        super(nome, descricao, custo);
        this.danoCritico = critico;
        this.danoFalha = falha;
        this.chanceSucesso = chance;
    }

    //metodo quando heroi usa a carta de dano em area
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


    //metodo quando inimigo usa a carta de dano em area
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