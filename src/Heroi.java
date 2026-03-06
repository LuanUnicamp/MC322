public class Heroi {
    //atributos do heroi
    private String nome;
    private int vida;
    private int escudo;

    //construtor
    public Heroi(String nome, int vida, int escudo){
        this.nome=nome;
        this.vida=vida;
        this.escudo=escudo;
    }

    //metodo que desconta o dano recebido no heroi
    public String receberDano(int dano){
        int vida_inicial = vida;
        //caso o heroi tenha escudo
        if(escudo>0){
            //verificando se o dano aplicado é maior que o escudo do heroi
            if(escudo >= dano){
                escudo -= dano;
            } else {
                vida -= (dano-escudo);
            }
        //caso não tenha escudo o dano é aplicado diretamente na vida do heroi
        } else{
            vida -= dano;
        }
        return "heroi recebeu" + (vida_inicial-vida) + " de dano";
    }
    
    //metodo que da escudo para o heroi
    public String ganharEscudo(int qtd_escudo){
        this.escudo = qtd_escudo;
        return "heroi recebeu" +this.escudo+" de escudo";
    }

    //metodo que verifica se o heroi esta vivo
    public Boolean estaVivo(){
        if(vida > 0){
            return true;
        } else{
            return false;
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
