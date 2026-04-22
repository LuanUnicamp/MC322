import java.util.Scanner;

/**
 * Classe abstrata que representa um evento genérico no mapa do Modo História.
 * <br>
 * Todos os tipos de evento — batalhas, lojas, fogueiras, escolhas narrativas —
 * herdam desta classe e implementam o método {@link #iniciar(Herois, Scanner)},
 * que encapsula a lógica específica de cada nó do mapa.
 * <br>
 * O sistema de mapa em {@link Historia} trata todos os eventos polimorficamente
 * por meio desta interface comum, verificando após cada execução se o jogador
 * ainda está vivo para decidir se a progressão continua.
 * <br>
 * <b>Padrão de Design:</b> Template Method — define o esqueleto do fluxo de um
 * evento (iniciar → verificar derrota) enquanto delega os detalhes às subclasses.
 */
public abstract class EventoBase {

    /**
     * Inicia a execução do evento, aplicando seus efeitos sobre o estado do jogador.
     * <br>
     * Cada subclasse implementa este método com sua lógica própria: uma batalha
     * roda o combate; uma loja abre o menu de compras; uma fogueira oferece
     * descanso ou melhoria de carta.
     *
     * @param estado  Objeto {@link Herois} com o estado atual do jogador (vida, deck, moedas, itens).
     * @param entrada Objeto {@link Scanner} para leitura da entrada do usuário.
     * @return {@code true} se o jogador sobreviveu ao evento e pode continuar;
     *         {@code false} se o jogador foi derrotado (game over).
     */
    public abstract boolean iniciar(Herois estado, Scanner entrada);
}