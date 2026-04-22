/**
 * Representa o oponente controlado pelo sistema durante o combate. <br>
 * <b>Comportamento</b>: Estende as funcionalidades de Entidade e mantém um registro 
 * da vida máxima para cálculos de cura e regeneração durante o duelo.
 */
public class Inimigo extends Entidade{

    /**
     * Inicializa um novo inimigo com os atributos de combate definidos.
     * @param nome O nome do inimigo.
     * @param vida A quantidade inicial de pontos de vida.
     * @param escudo A quantidade inicial de pontos de escudo.
     */
    public Inimigo(String nome, int vida, int escudo){
        super(nome, vida, escudo);
    }

}