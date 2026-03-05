public class Heroi {
    private String nome;
    private int vida;
    private int escudo;

    public Heroi(String nome, int vida, int escudo){
        this.nome=nome;
        this.vida=vida;
        this.escudo=escudo;
    }

    public String recebeDano(int dano){
        if(this.escudo>0){
            this.escudo-=dano;
        }else{
            this.vida-=dano;
        }
        
        return "inimigo recebeu dano"+nome+vida+escudo;
    }

    public String recebeEscudo(){
        return "heroi recebeu escudo";
    }

    public String estaVivo(){
        return "esta vivo?";
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
