import java.util.Scanner;

/**
 * Responsável por apresentar as bifurcações narrativas do Modo História
 * ao jogador e capturar a decisão de qual caminho seguir.
 * <br>
 * Atualmente gerencia a escolha de sensei (Jiraiya ou Kakashi) após
 * a conclusão da Fase 1, podendo ser estendida para outras bifurcações futuras.
 */
public class Escolha {

    /**
     * Exibe o menu de escolha de sensei e retorna o índice (base 0) da opção selecionada.
     * <br>
     * <b>Comportamento:</b> Apresenta as duas opções de treinamento — Jiraiya (Invocação e
     * Resistência) e Kakashi (Natureza de Chakra e Dano) — e aguarda a decisão do jogador.
     * O valor retornado é usado por {@link Historia} para avançar ao nó correto da árvore.
     *
     * @param entrada Objeto {@link Scanner} para capturar a opção digitada pelo jogador.
     * @return Índice base 0 da escolha feita (0 = Jiraiya, 1 = Kakashi).
     */
    public int apresentarEscolha(Scanner entrada) {
        App.limparTela();
        System.out.println(App.CIANO + "╔" + "═".repeat(60) + "╗" + App.RESET);
        System.out.println(App.CIANO + "║" + App.RESET + App.NEGRITO + App.AMARELO + "              📜 QUEM SERÁ O SEU NOVO SENSEI?               " + App.RESET + App.CIANO + "║" + App.RESET);
        System.out.println(App.CIANO + "╠" + "═".repeat(60) + "╣" + App.RESET);
        System.out.println(App.CIANO + "║" + App.RESET + "  [1] JIRAIYA (Foco em Invocação e Resistência)             " + App.CIANO + "║" + App.RESET);
        System.out.println(App.CIANO + "║" + App.RESET + "  [2] KAKASHI (Foco em Natureza de Chakra e Dano)           " + App.CIANO + "║" + App.RESET);
        System.out.println(App.CIANO + "╚" + "═".repeat(60) + "╝" + App.RESET);

        System.out.print("\nEscolha seu caminho: ");
        int escolha = entrada.nextInt();
        entrada.nextLine();

        // retorna índice base 0 para uso direto em getChildAt()
        return escolha - 1;
    }
}