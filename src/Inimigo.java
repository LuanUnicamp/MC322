public class Inimigo {
    //atributos do inimigo
    private String nome;
    private int vida;
    private int escudo;
    private int dano;

    //construtor
    public Inimigo(String nome, int vida, int escudo, int dano){
        this.nome=nome;
        this.vida=vida;
        this.escudo=escudo;
        this.dano = dano;
    }

    //metodo que desconta o dano recebido no inimigo
    public void receberDano(int dano){
        //caso o inimigo tenha escudo
        if(escudo>0){
            //verificando se o dano aplicado é maior que o escudo do inimigo
            if(escudo >= dano){
                escudo -= dano;
            } else {
                vida -= (dano-escudo);
            }
        //caso não tenha escudo o dano é aplicado diretamente na vida do inimifo
        } else{
            vida -= dano;
        }
    }
    
    //método que o inimigo faz um ataque ao heroi
    public void atacar(Heroi h){
        h.receberDano(dano);
    }

    //metodo que verifica se o heroi esta vivo
    public Boolean estaVivo(){
        if(vida > 0){
            return true;
        } else{
            return false;
        }
    }

    //metodo que não deixa o inimigo ficar com vida negativa no display
    public void zeraVida(){
        if(vida < 0){
            vida = 0;
        }
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
