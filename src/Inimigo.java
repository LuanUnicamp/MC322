public class Inimigo {
    private String nome;
    private int vida;
    private int escudo;

    public Inimigo(String nome, int vida, int escudo){
        this.nome=nome;
        this.vida=vida;
        this.escudo=escudo;
    }

    public String recebeDano(int dano){
        this.vida-=dano;
        return "inimigo recebeu dano"+nome+vida+escudo;
    }
    
    public String atacar(int ataque){
        return "inimigo atacou";
    }

    public String estaVivo(){
        return "esta vivvo?";
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getEscudo() {
        return escudo;
    }
}
