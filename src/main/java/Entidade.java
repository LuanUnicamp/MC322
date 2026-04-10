import java.util.ArrayList;

/**
 * Classe abstrata que representa uma entidade básica no jogo (Herói ou Inimigo). <br>
 * <b>Comportamento</b>: Gerencia os atributos vitais, a lista de efeitos ativos e a 
 * lógica de combate relacionada a dano, cura e defesa.
 */
public abstract class Entidade {

    protected String nome;
    protected int vida;
    protected int escudo;
    private ArrayList<Efeito> listaEfeitosEntidade;
    protected int vidaMax;
    protected boolean atordoado = false;


    /**
     * Inicializa uma nova entidade com nome, vida e escudo.
     * @param nome O nome da entidade.
     * @param vida A quantidade inicial e máxima de pontos de vida.
     * @param escudo A quantidade inicial de pontos de escudo.
     */
    public Entidade(String nome, int vida, int escudo) {
        this.nome = nome;
        this.vida = vida;
        this.escudo = escudo;
        this.listaEfeitosEntidade = new ArrayList<>();
        this.vidaMax=this.vida;
    }

    /**
     * Aplica um efeito de estado à entidade.
     * @param efeito O efeito a ser aplicado. <br>
     * <b>Comportamento</b>: Se a entidade já possuir um efeito com o mesmo nome, os acúmulos 
     * são somados. Caso contrário, o novo efeito é adicionado à lista.
     */
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

    /**
     * Processa o dano recebido pela entidade, considerando o escudo.
     * @param dano A quantidade de dano a ser aplicada. <br>
     * <b>Comportamento</b>: O dano reduz primeiro o escudo. Se o dano exceder o escudo, 
     * o restante é subtraído da vida. Garante que a vida não fique negativa.
     */
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


    /**
     * Incrementa o valor atual do escudo da entidade.
     * @param qtd_escudo A quantidade de escudo a ser adicionada.
     */
    public void ganharEscudo(int qtd_escudo){
        this.escudo += qtd_escudo;
    }

    

    /**
     * Verifica se a entidade ainda possui pontos de vida. <br>
     * <b>Comportamento</b>: Retorna verdadeiro se a vida for maior que zero.
     */
    public Boolean estaVivo(){
        if(vida > 0){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Restaura pontos de vida da entidade.
     * @param valor A quantidade de pontos de vida a ser curada. <br>
     * <b>Comportamento</b>: Adiciona o valor à vida atual, respeitando o limite do atributo vidaMax.
     */
    public void receberCura(int valor) {
        this.vida += valor;
        
        if (this.vida > this.vidaMax) {
            this.vida = this.vidaMax;
        }
        
    
    }

    /**
     * Garante que a vida da entidade não seja exibida como um valor negativo. <br>
     * <b>Comportamento</b>: Ajusta o valor de vida para zero caso ele seja menor que zero.
     */
    public void zeraVida(){
        if(vida < 0){
            vida = 0;
        }
    }

    /**
     * Remove todos os pontos de escudo da entidade. <br>
     * <b>Comportamento</b>: Atribui zero ao valor do escudo.
     */
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
    public int getVidaMax(){
        return vidaMax;
    }

    /**
     * Remove um efeito específico da lista de efeitos da entidade.
     * @param efeito O efeito a ser removido.
     */
    public void removerEfeito(Efeito efeito){
        listaEfeitosEntidade.remove(efeito);
    }
    

}