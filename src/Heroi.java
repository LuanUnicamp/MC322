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
    public void receberDano(int dano){
        //caso o heroi tenha escudo
        if(escudo>0){
            //verificando se o dano aplicado é maior que o escudo do heroi
            if(escudo >= dano){
                escudo -= dano;
            } else {
                vida -= (dano-escudo);
                escudo = 0;
            }
        //caso não tenha escudo o dano é aplicado diretamente na vida do heroi
        } else{
            vida -= dano;
        }
    }
    
    //metodo que da escudo para o heroi
    public void ganharEscudo(int qtd_escudo){
        this.escudo += qtd_escudo;
    }

    //metodo que verifica se o heroi esta vivo
    public Boolean estaVivo(){
        if(vida > 0){
            return true;
        } else{
            return false;
        }
    }

    //metodo que não deixa o heroi ficar com vida negativa no display
    public void zeraVida(){
        if(vida < 0){
            vida = 0;
        }
    }

    //metodo que zera o escudo do heroi a cada turno
    public void zeraEscudo(){
        escudo = 0;
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
