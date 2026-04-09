/**
 * Classe abstrata que define a base para os efeitos de estado do jogo. <br>
 * <b>Comportamento</b>: Define a estrutura para o padrão Observer, onde o efeito monitora 
 * os turnos do combate e aplica alterações na entidade associada enquanto possuir acúmulos.
 */
public abstract class Efeito {

    private String nome;
    private Entidade entidade;
    private int acumulos;
    private String tipo;

    /**
     * Inicializa as propriedades do efeito de estado.
     * @param nome O nome identificador do efeito.
     * @param entidade A entidade (Herói ou Inimigo) que está sob o efeito.
     * @param acumulos A duração ou intensidade inicial do efeito em turnos.
     * @param tipo A categoria do efeito (ex: "VENENO", "REGEN").
     */
    public Efeito(String nome, Entidade entidade, int acumulos,String tipo) {
        this.nome = nome;
        this.entidade = entidade;
        this.acumulos = acumulos;
        this.tipo=tipo;
    }

    /**
     * Gera uma representação textual do estado atual do efeito para exibição em menus. <br>
     * <b>Comportamento</b>: Retorna uma string contendo o nome e a quantidade de acúmulos restantes.
     */
    public String getString(){
        return "Acumulos:"+this.acumulos;
    }

    //getters
    public String getNome(){
        return nome;
    }

    public int getAcumulos(){
        return acumulos;
    }

    public Entidade getEntidade(){
        return entidade;
    }

    /**
     * Método de notificação chamado pelo combate para processar o efeito.
     * @param num O momento do turno em que a notificação ocorre (0 para início, 1 para fim). <br>
     * <b>Comportamento</b>: Implementa a lógica específica de cada efeito, como causar dano ou curar a entidade.
     */
    public abstract void avisado(int num);

    /**
     * Diminui a contagem de acúmulos em uma unidade. <br>
     * <b>Comportamento</b>: Utilizado para controlar a expiração do efeito ao longo dos turnos.
     */
    protected void reduzirAcumulos(){
        acumulos--;
    }

    /**
     * Verifica se o efeito não possui mais acúmulos ativos. <br>
     * <b>Comportamento</b>: Retorna verdadeiro se os acúmulos forem menores ou iguais a zero, sinalizando que deve ser removido.
     */
    public boolean acabou(){
        return acumulos <= 0;
    }
    
    /**
     * Aumenta a contagem de acúmulos do efeito.
     * @param valor A quantidade a ser adicionada aos acúmulos atuais.
     */
    protected void adicionarAcumulos(int valor){
        acumulos += valor;
    }

    //getter
    public String getTipo(){
        return tipo;

    }
        
}