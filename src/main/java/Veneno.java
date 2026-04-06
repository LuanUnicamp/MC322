/**
 * Representa um efeito negativo de veneno que causa dano contínuo à entidade afetada. <br>
 * <b>Comportamento</b>: Diferente da regeneração, o veneno é processado no final de cada turno 
 * (notificação 1), reduzindo a vida da entidade com base na intensidade atual e, em seguida, 
 * diminuindo um acúmulo.
 */
public class Veneno extends Efeito{

    /**
     * Inicializa o efeito de veneno.
     * @param nome O nome da técnica ou substância venenosa.
     * @param entidade A entidade que sofrerá o dano periódico.
     * @param acumulos A duração ou intensidade inicial do veneno.
     * @param tipo O rótulo identificador do tipo de efeito ("VENENO").
     */
    public Veneno(String nome, Entidade entidade, int acumulos, String tipo){
        super(nome,entidade,acumulos,tipo);

    }

    /**
     * Implementação da lógica de atualização do dano por veneno.
     * @param num O código do momento do turno (0 para início, 1 para fim). <br>
     * <b>Comportamento</b>: Se a notificação for de fim de turno (1), a entidade recebe 
     * dano equivalente aos acúmulos atuais e um acúmulo é consumido da contagem total.
     */
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