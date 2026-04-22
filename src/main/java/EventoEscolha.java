import java.util.Scanner;

/**
 * Evento de escolha narrativa no mapa do Modo História.
 * <br>
 * Herda de {@link EventoBase} e delega a apresentação da bifurcação à classe
 * {@link Escolha}, integrando decisões do jogador como nós válidos na árvore de eventos.
 * Retorna o índice (base 0) da opção escolhida por meio de {@link #getUltimaEscolha()},
 * permitindo que {@link Historia} navegue para o nó filho correto.
 * <br>
 * <b>Padrão de Design:</b> Template Method (via {@link EventoBase}).
 */
public class EventoEscolha extends EventoBase {

    private final Escolha escolha;
    private int ultimaEscolha = 0;

    /**
     * Construtor do evento de escolha.
     */
    public EventoEscolha() {
        this.escolha = new Escolha();
    }

    /**
     * Apresenta a bifurcação narrativa e registra a decisão do jogador.
     * Escolhas nunca resultam em game over diretamente.
     *
     * @param estado  Objeto {@link Herois} com o estado atual do jogador.
     * @param entrada Objeto {@link Scanner} para capturar a decisão.
     * @return sempre {@code true}.
     */
    @Override
    public boolean iniciar(Herois estado, Scanner entrada) {
        ultimaEscolha = escolha.apresentarEscolha(entrada);
        return true;
    }

    /**
     * Retorna o índice base 0 da última escolha feita pelo jogador,
     * para uso na navegação da árvore de eventos.
     *
     * @return índice da opção escolhida.
     */
    public int getUltimaEscolha() {
        return ultimaEscolha;
    }
}