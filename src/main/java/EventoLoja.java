import java.util.Scanner;

/**
 * Evento de loja no mapa do Modo História.
 * <br>
 * Herda de {@link EventoBase} e delega toda a lógica de compra à classe {@link Loja},
 * integrando o Mercado Shinobi como um nó válido na árvore de eventos de {@link Historia}.
 * <br>
 * <b>Padrão de Design:</b> Template Method (via {@link EventoBase}) — {@code iniciar}
 * define que a loja nunca causa game over (sempre retorna {@code true}), enquanto
 * {@link Loja} cuida dos detalhes da interface de compra.
 */
public class EventoLoja extends EventoBase {

    private final Loja loja;

    /**
     * Construtor do evento de loja.
     */
    public EventoLoja() {
        this.loja = new Loja();
    }

    /**
     * Abre o Mercado Shinobi e permite ao jogador gastar moedas.
     * A loja nunca resulta em game over.
     *
     * @param estado  Objeto {@link Herois} com inventário e moedas do jogador.
     * @param entrada Objeto {@link Scanner} para interação com o menu.
     * @return sempre {@code true}, pois a loja não elimina o jogador.
     */
    @Override
    public boolean iniciar(Herois estado, Scanner entrada) {
        loja.abrirLoja(estado, entrada);
        return true;
    }
}