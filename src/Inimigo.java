public class Inimigo extends Entidade{
    //atributos do inimigo
    private int dano;

    //construtor
    public Inimigo(String nome, int vida, int escudo, int dano){
        super(nome, vida, escudo);
        this.dano=dano;
    }
    
    //método que o inimigo faz um ataque ao heroi
    public void atacar(Heroi h){
        h.receberDano(dano);
    }

}
