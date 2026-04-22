import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Evento de batalha genérico do mapa, utilizável em qualquer nó da árvore de história.
 * <br>
 * Herda de {@link EventoBase} e encapsula a sequência completa de um combate:
 * montagem do deck do inimigo, execução via {@link Combate} e, em caso de vitória,
 * distribuição de recompensas: moedas e a escolha de uma carta para adicionar ao deck.
 * <br>
 * <b>Padrão de Design:</b> Template Method (via {@link EventoBase}) — o método
 * {@code iniciar} define o fluxo fixo (combate → recompensa), enquanto os detalhes
 * do inimigo e das cartas de recompensa são configurados no construtor.
 */
public class EventoBatalha extends EventoBase {

    private final String nomeInimigo;
    private final int vidaInimigo;
    private final int moedas;
    private final ArrayList<Carta> cartasRecompensa;

    /**
     * Cria um evento de batalha configurado com inimigo e recompensas específicos.
     *
     * @param nomeInimigo      Nome do inimigo que aparecerá no combate.
     * @param vidaInimigo      Pontos de vida iniciais do inimigo.
     * @param moedas           Shinobi Coins concedidas ao jogador em caso de vitória.
     * @param cartasRecompensa Lista de cartas que serão oferecidas como recompensa.
     */
    public EventoBatalha(String nomeInimigo, int vidaInimigo, int moedas,
                         ArrayList<Carta> cartasRecompensa) {
        this.nomeInimigo = nomeInimigo;
        this.vidaInimigo = vidaInimigo;
        this.moedas = moedas;
        this.cartasRecompensa = cartasRecompensa;
    }

    /**
     * Executa o combate e, se o jogador vencer, oferece recompensas.
     *
     * @param estado  Objeto {@link Herois} com o estado atual do jogador.
     * @param entrada Objeto {@link Scanner} para controle do fluxo de entrada.
     * @return {@code true} se o jogador venceu, {@code false} se perdeu (game over).
     */
    @Override
    public boolean iniciar(Herois estado, Scanner entrada) {
        Inimigo inimigo = new Inimigo(nomeInimigo, vidaInimigo, 0);
        ArrayList<Carta> deckInimigo = App.GeraDeckInimigo(inimigo);

        ArrayList<Carta> deckParaLuta = new ArrayList<>(estado.getDeckNaruto());
        Collections.shuffle(deckParaLuta);

        Combate arena = new Combate();
        String resultado = arena.rodarCombate(estado.getNaruto(), inimigo, deckInimigo,
                deckParaLuta, new ArrayList<>(), new ArrayList<>(), entrada);

        System.out.println("\n" + resultado);

        if (estado.getNaruto().getVida() <= 0) {
            System.out.println("\n💀 GAME OVER: Você foi derrotado por " + nomeInimigo + "...");
            System.out.println("[Pressione ENTER para voltar ao menu]");
            entrada.nextLine();
            return false;
        }

        // recompensa de moedas
        estado.setShinobiCoins(estado.getShinobiCoins() + moedas);
        System.out.println(App.VERDE + "\n🪙  Você ganhou " + moedas + " Shinobi Coins!" + App.RESET);

        // recompensa de carta
        oferecerCartaRecompensa(estado, entrada);

        System.out.println("\n[Pressione ENTER para continuar]");
        entrada.nextLine();
        return true;
    }

    /**
     * Exibe até 3 cartas de recompensa aleatórias e permite ao jogador adicionar
     * uma delas ao deck, ou pular a escolha.
     *
     * @param estado  Objeto {@link Herois} cujo deck será atualizado se o jogador escolher uma carta.
     * @param entrada Objeto {@link Scanner} para capturar a escolha.
     */
    private void oferecerCartaRecompensa(Herois estado, Scanner entrada) {
        if (cartasRecompensa == null || cartasRecompensa.isEmpty()) return;

        Collections.shuffle(cartasRecompensa);
        int qtd = Math.min(3, cartasRecompensa.size());

        System.out.println(App.AMARELO + "\n╔════════════════════════════════════════════════════════╗" + App.RESET);
        System.out.println(App.AMARELO + "║" + App.RESET + App.NEGRITO + "             🎴  RECOMPENSA: ESCOLHA UMA CARTA           " + App.RESET + App.AMARELO + "║" + App.RESET);
        System.out.println(App.AMARELO + "╠════════════════════════════════════════════════════════╣" + App.RESET);

        for (int i = 0; i < qtd; i++) {
            Carta c = cartasRecompensa.get(i);
            String tipo = "";
            String detalhe = "";
            if (c instanceof CartaDano) {
                tipo = App.VERMELHO + "[Ataque]" + App.RESET;
                detalhe = "Dano: " + c.getDano();
            } else if (c instanceof CartaEscudo) {
                tipo = App.CIANO + "[Defesa]" + App.RESET;
                detalhe = "Escudo: " + c.getEscudo();
            } else {
                tipo = App.VERDE + "[Especial]" + App.RESET;
                detalhe = "Custo: " + c.getCusto();
            }
            System.out.printf(App.AMARELO + "║" + App.RESET + "  [%d] %-28s %s  %-8s" + App.AMARELO + " ║%n" + App.RESET,
                    (i + 1), c.getNome(), tipo, detalhe);
            System.out.printf(App.AMARELO + "║" + App.RESET + "      %-52s" + App.AMARELO + " ║%n" + App.RESET,
                    c.getDescricao().length() > 52 ? c.getDescricao().substring(0, 49) + "..." : c.getDescricao());
            System.out.printf(App.AMARELO + "║" + App.RESET + "      Custo Chakra: %-34d" + App.AMARELO + " ║%n" + App.RESET,
                    c.getCusto());
        }

        System.out.println(App.AMARELO + "║" + App.RESET + "  [" + (qtd + 1) + "] Pular — não quero nenhuma carta              " + App.AMARELO + "║" + App.RESET);
        System.out.println(App.AMARELO + "╚════════════════════════════════════════════════════════╝" + App.RESET);
        System.out.print("Sua escolha: ");

        int escolha = -1;
        try {
            escolha = entrada.nextInt();
            entrada.nextLine();
        } catch (Exception e) {
            entrada.nextLine();
        }

        if (escolha >= 1 && escolha <= qtd) {
            Carta cartaEscolhida = cartasRecompensa.get(escolha - 1);
            estado.getDeckNaruto().add(cartaEscolhida);
            cartasRecompensa.remove(cartaEscolhida);
            System.out.println(App.VERDE + ">>> " + cartaEscolhida.getNome() + " adicionada ao seu deck!" + App.RESET);
        } else {
            System.out.println(App.AMARELO + ">>> Você pulou a recompensa de carta." + App.RESET);
        }
    }
}