public abstract class Efeito {

    private String nome;
    private Entidade entidade;
    private int acumulos;
    private String tipo;

    //construtor
    public Efeito(String nome, Entidade entidade, int acumulos,String tipo) {
        this.nome = nome;
        this.entidade = entidade;
        this.acumulos = acumulos;
        this.tipo=tipo;
    }

    //metodo usado no menu de efeitos
    public String getString(){
        return "Efeito! Nome:"+this.nome+" | Acumulos:"+this.acumulos;
    }

    public String getNome(){
        return nome;
    }

    public int getAcumulos(){
        return acumulos;
    }

    public Entidade getEntidade(){
        return entidade;
    }
    public abstract void avisado(int num);

    protected void reduzirAcumulos(){
        acumulos--;
    }

    public boolean acabou(){
        return acumulos <= 0;
    }
    
    protected void adicionarAcumulos(int valor){
        acumulos += valor;
    }
    public String getTipo(){
        return tipo;

    }
        
}
