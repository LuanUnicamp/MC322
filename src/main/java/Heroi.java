/**
 * Representa o personagem controlado pelo jogador no sistema de combate. <br>
 * <b>Comportamento</b>: Estende as funcionalidades básicas de uma Entidade, permitindo 
 * que o jogador utilize jutsus e gerencie recursos durante o duelo.
 */
public class Heroi extends Entidade {

    /**
     * Inicializa um novo herói com os atributos de combate definidos.
     * @param nome O nome do herói.
     * @param vida A quantidade inicial de pontos de vida.
     * @param escudo A quantidade inicial de pontos de escudo.
     */
    public Heroi(String nome, int vida, int escudo) {
        super(nome, vida, escudo);
    }

}