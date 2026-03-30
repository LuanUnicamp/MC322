import java.util.ArrayList;

public abstract class Entidade {

    protected String nome;
    protected int vida;
    protected int escudo;
    private ArrayList<Efeito> listaEfeitosEntidade;

    public Entidade(String nome, int vida, int escudo) {
        this.nome = nome;
        this.vida = vida;
        this.escudo = escudo;
        this.listaEfeitosEntidade = new ArrayList<>();
    }


    public void aplicarEfeito(Efeito efeito){
        for(int i=0;i<listaEfeitosEntidade.size();i++){
            Efeito efeitolista =listaEfeitosEntidade.get(i);
            if(efeito.getNome().equals(efeitolista.getNome())){
                efeitolista.adicionarAcumulos(efeito.getAcumulos());
                return;
            }
        }
        listaEfeitosEntidade.add(efeito);
    }

    //metodo que descona o dano recebido
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
        }else{
            if(vida - dano>=0){
                vida-=dano;
            }else{
                vida=0;
            }
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

    public void zeraEscudo() {
        this.escudo = 0;
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
    public void removerEfeito(Efeito efeito){
        listaEfeitosEntidade.remove(efeito);
    }

}