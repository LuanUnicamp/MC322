public abstract class Carta{
    public static final String RESET = "\u001B[0m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String CIANO = "\u001B[36m";
    public static final String ROXO = "\u001B[35m";
    public static final String NEGRITO = "\033[1m";

    protected String nome;
    protected String descricao;
    protected int custo;

    //construtor
    public Carta(String nome, String descricao, int custo) {
        this.nome = nome;
        this.descricao = descricao;
        this.custo = custo;
    }

    //metodo que utiliza a carta
    public abstract void usar_h(Heroi h, Inimigo i,Combate combate);

    public abstract void usar_i(Inimigo i, Heroi h,Combate combate);
    
    public abstract int getDano();

    public abstract int getEscudo();

    //getters
    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }
    public String getDescricao(){
        return descricao;
    }
}