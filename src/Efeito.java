public abstract class Efeito {

    private String nome;
    private Entidade entidade;
    private int acumulos;


    public Efeito(String nome, Entidade entidade, int acumulos) {
        this.nome = nome;
        this.entidade = entidade;
        this.acumulos = acumulos;
    }


    public String getString(){
        return "Efeito! Nome:"+this.nome+" Acumulos:"+this.acumulos;
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
        
}
