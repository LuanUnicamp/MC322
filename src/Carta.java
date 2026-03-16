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
    public abstract void usar(Heroi h, Inimigo i);

    public String getNome(){
        return nome;
    }

    public int getCusto(){
        return custo;
    }
}