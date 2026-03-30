public abstract class Carta{

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