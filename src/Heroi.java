public class Heroi extends Entidade {

    //construtor
    public Heroi(String nome, int vida, int escudo) {
        super(nome, vida, escudo);
    }

    //metodo que zera o escudo do heroi a cada turno
    public void zeraEscudo(){
        escudo = 0;
    }


}
