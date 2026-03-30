public class Regen extends Efeito {

    public Regen(String nome, Entidade entidade, int acumulos){
        super(nome,entidade,acumulos);

    }

    @Override
    public void avisado(int num){
        if(num==0){
            getEntidade().ganharEscudo(getAcumulos());
            reduzirAcumulos();
        }
    }
    
}
