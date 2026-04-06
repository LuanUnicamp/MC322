/**
 * Representa um efeito de cura contínua que restaura pontos de vida da entidade a cada turno. <br>
 * <b>Comportamento</b>: No início de cada turno (notificação 0), a entidade recupera 
 * pontos de vida baseados na intensidade atual do efeito, e a duração (acúmulos) é reduzida.
 */
public class RegenCura extends Efeito {

    /**
     * Inicializa o efeito de regeneração de vida.
     * @param nome O nome identificador da cura contínua.
     * @param entidade A entidade que receberá a restauração de vida.
     * @param acumulos A duração ou intensidade inicial do efeito.
     * @param tipo O rótulo identificador da categoria do efeito.
     */
    public RegenCura(String nome, Entidade entidade, int acumulos, String tipo){
        super(nome,entidade,acumulos,tipo);

    }

    /**
     * Implementação da lógica de atualização da cura por turno.
     * @param num O código do momento do turno (0 para início, 1 para fim). <br>
     * <b>Comportamento</b>: Se a notificação for de início de turno (0), a entidade 
     * recebe cura equivalente aos acúmulos atuais e um acúmulo é consumido.
     */
    @Override
    public void avisado(int num){
        if(num==0){
            if(getAcumulos()>0){
                getEntidade().receberCura(getAcumulos());
                reduzirAcumulos();
            }

        }
    }
    
}