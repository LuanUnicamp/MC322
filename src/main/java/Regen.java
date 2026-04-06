/**
 * Representa um efeito de regeneração contínua que bonifica a entidade a cada turno. <br>
 * <b>Comportamento</b>: No início de cada turno (notificação 0), concede pontos de escudo 
 * à entidade baseados na quantidade atual de acúmulos e, em seguida, reduz a intensidade do efeito.
 */
public class Regen extends Efeito {

    /**
     * Inicializa o efeito de regeneração.
     * @param nome O nome da técnica de regeneração.
     * @param entidade A entidade que receberá os benefícios.
     * @param acumulos A duração/intensidade inicial do efeito.
     * @param tipo O rótulo identificador do tipo de efeito ("REGEN").
     */
    public Regen(String nome, Entidade entidade, int acumulos, String tipo){
        super(nome,entidade,acumulos,tipo);

    }

    /**
     * Implementação da lógica de atualização do efeito de regeneração.
     * @param num O código do momento do turno (0 para início, 1 para fim). <br>
     * <b>Comportamento</b>: Se a notificação for de início de turno (0), a entidade ganha 
     * escudo equivalente aos acúmulos atuais e um acúmulo é consumido.
     */
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