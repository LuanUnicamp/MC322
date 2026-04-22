import java.util.Scanner;

/**
 * Representa o Mercado Shinobi, onde o jogador pode trocar suas
 * Shinobi Coins por itens consumíveis (Bandagens, Shurikens e Bandanas).
 * <br>
 * Opera diretamente sobre o objeto {@link Herois} para debitar moedas
 * e incrementar o inventário do jogador.
 */
public class Loja {

    /**
     * Questiona o jogador via console se ele deseja visitar o Mercado Shinobi
     * antes de prosseguir com a missão.
     *
     * @param estado  Objeto {@link Herois} com o inventário e moedas do jogador.
     * @param entrada Objeto {@link Scanner} para ler a opção do usuário.
     *                Se a escolha for 1, invoca {@link #abrirLoja(Herois, Scanner)}.
     */
    public void perguntarLoja(Herois estado, Scanner entrada) {
        System.out.println("\n" + App.CIANO + "╔══════════════════════════════════════════════════╗" + App.RESET);
        System.out.println(App.CIANO + "║" + App.RESET + " Deseja visitar o " + App.AMARELO + "Mercado Shinobi" + App.RESET + " antes de seguir? " + App.CIANO + "║" + App.RESET);
        System.out.println(App.CIANO + "╚══════════════════════════════════════════════════╝" + App.RESET);
        System.out.println("[1] Sim, gastar Shinobi Coins");
        System.out.println("[2] Não, focar na missão");
        System.out.print("Escolha: ");
        int esc = entrada.nextInt();
        entrada.nextLine();

        if (esc == 1) {
            abrirLoja(estado, entrada);
        }
    }

    /**
     * Renderiza o menu do Mercado Shinobi e processa as compras do jogador
     * enquanto ele não decidir sair.
     *
     * @param estado  Objeto {@link Herois} cujo inventário e saldo de moedas serão atualizados.
     * @param entrada Objeto {@link Scanner} para capturar as interações de compra.
     */
    public void abrirLoja(Herois estado, Scanner entrada) {
        boolean contComprar = true;
        while (contComprar) {
            App.limparTela();
            System.out.println(App.AMARELO + "╔════════════════════════════════════════════════════════╗" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + App.NEGRITO + "                   🏪  MERCADO SHINOBI                   " + App.RESET + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "╠════════════════════════════════════════════════════════╣" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " 💰 Moedas: " + App.VERDE + estado.getShinobiCoins() + " Shinobi Coins" + App.RESET + "                              " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "╠════════════════════════════════════════════════════════╣" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " [1] 🩹 Bandagem (Cura 20 HP)               - 40 Coins " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " [2] 🗡️ Shuriken (Tira 10 HP do Inimigo)    - 50 Coins " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " [3] 🥷 Bandana (+5 de Escudo Inicial)      - 30 Coins " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " [4] 🚪 Sair da Loja                                   " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "╚════════════════════════════════════════════════════════╝" + App.RESET);
            System.out.print("Sua escolha: ");
            int opcao = entrada.nextInt();
            entrada.nextLine();

            if (opcao == 1 && estado.getShinobiCoins() >= 40) {
                estado.setShinobiCoins(estado.getShinobiCoins() - 40);
                estado.setQtdBandagem(estado.getQtdBandagem() + 1);
                System.out.println(App.VERDE + ">>> Bandagem guardada na mochila!" + App.RESET);
            } else if (opcao == 2 && estado.getShinobiCoins() >= 50) {
                estado.setShinobiCoins(estado.getShinobiCoins() - 50);
                estado.setQtdShuriken(estado.getQtdShuriken() + 1);
                System.out.println(App.VERDE + ">>> Shuriken guardada na mochila!" + App.RESET);
            } else if (opcao == 3 && estado.getShinobiCoins() >= 30) {
                estado.setShinobiCoins(estado.getShinobiCoins() - 30);
                estado.setQtdBandana(estado.getQtdBandana() + 1);
                System.out.println(App.VERDE + ">>> Bandana guardada na mochila!" + App.RESET);
            } else if (opcao == 4) {
                contComprar = false;
            } else {
                System.out.println(App.VERMELHO + ">>> Moedas insuficientes ou opção inválida!" + App.RESET);
            }

            if (contComprar) {
                System.out.println("[Pressione ENTER para continuar comprando]");
                entrada.nextLine();
            }
        }
    }
}