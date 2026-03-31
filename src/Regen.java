public class Regen extends Efeito {

    public Regen(String nome, Entidade entidade, int acumulos, String tipo){
        super(nome,entidade,acumulos,tipo);

    }

    //metodo que indica que foi avisado e faz o que o efeito esta proposto a fazer nesse momento
    @Override
    public void avisado(int num){
        if(num==0){
            if(getAcumulos()>0){
                getEntidade().ganharEscudo(getAcumulos());
                reduzirAcumulos();
            }

        }
    }
    
}
