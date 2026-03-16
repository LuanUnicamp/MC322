public abstract class Entidade {

    protected String nome;
    protected int vida;
    protected int escudo;

    public Entidade(String nome, int vida, int escudo) {
        this.nome = nome;
        this.vida = vida;
        this.escudo = escudo;
    }

    //metodo que desconta o dano recebido
    public void receberDano(int dano){
        //caso o tenha escudo
        if(escudo>0){
            //verificando se o dano aplicado é maior que o escudo 
            if(escudo >= dano){
                escudo -= dano;
            } else {
                vida -= (dano-escudo);
                escudo = 0;
            }
        //caso não tenha escudo o dano é aplicado diretamente na vida
        } else{
            vida -= dano;
        }
    }


    //metodo que da escudo
    public void ganharEscudo(int qtd_escudo){
        this.escudo += qtd_escudo;
    }

    //metodo que verifica se  esta vivo
    public Boolean estaVivo(){
        if(vida > 0){
            return true;
        } else{
            return false;
        }
    }

    //metodo que não deixa ficar com vida negativa no display
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