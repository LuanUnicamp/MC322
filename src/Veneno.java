public class Veneno extends Efeito{

    public Veneno(String nome, Entidade entidade, int acumulos){
        super(nome,entidade,acumulos);

    }

    @Override
    public void avisado(int num){
        if(num==1){
            if(getAcumulos()>0){
                getEntidade().receberDano(getAcumulos());
                reduzirAcumulos();
            }

        }
    }
    
}
