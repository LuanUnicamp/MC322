import java.util.Scanner;

/**
 * Responsável pelos momentos narrativos e pela preparação imediatamente
 * anterior a cada batalha do Modo História.
 * <br>
 * Encapsula a exibição de falas dos personagens e o menu da Sala de
 * Preparação, onde o jogador pode consumir itens antes de lutar.
 */
public class Evento {

    /**
     * Imprime um trecho de diálogo ou narração no console e pausa a execução
     * até que o usuário pressione ENTER para prosseguir.
     *
     * @param texto   A mensagem, contexto ou fala do personagem a ser exibida.
     * @param entrada Objeto {@link Scanner} utilizado para capturar o ENTER do usuário.
     */
    public void imprimirFala(String texto, Scanner entrada) {
        System.out.println(texto);
        entrada.nextLine();
    }

    /**
     * Inicia a sequência da "Sala de Preparação" imediatamente antes de uma
     * batalha começar.
     * <br>
     * <b>Comportamento:</b> Permite ao usuário visualizar seus recursos e o HP do
     * inimigo. O jogador pode abrir a mochila para usar itens de cura, proteção
     * ou lançar uma Shuriken surpresa no oponente antes dos turnos iniciarem.
     *
     * @param estado          Objeto {@link Herois} com o inventário e status atuais do jogador.
     * @param nomeInimigo     Nome do inimigo que será enfrentado.
     * @param vidaInimigoBase Vida inicial do inimigo (pode ser reduzida pela Shuriken).
     * @param entrada         Objeto {@link Scanner} para navegar nos menus.
     * @return A vida final do inimigo após a sala de preparação (já descontando Shuriken, se usada).
     */
    public int salaDePreparacao(Herois estado, String nomeInimigo, int vidaInimigoBase, Scanner entrada) {
        boolean vaiLutar = false;
        int escudoAcumulado = 0;
        int limiteBandana = 2;
        boolean usouShuriken = false;
        int vidaInimigo = vidaInimigoBase;

        while (!vaiLutar) {
            App.limparTela();
            System.out.println(App.VERMELHO + "╔════════════════════════════════════════════════════════╗" + App.RESET);
            System.out.println(App.VERMELHO + "║" + App.RESET + App.NEGRITO + "                  ⚔️  SALA DE PREPARAÇÃO                " + App.RESET + App.VERMELHO + "║" + App.RESET);
            System.out.println(App.VERMELHO + "╚════════════════════════════════════════════════════════╝" + App.RESET);
            System.out.println("  Inimigo: " + App.VERMELHO + nomeInimigo + App.RESET + " | HP: " + vidaInimigo);
            System.out.println("  Naruto : " + App.AMARELO + "HP " + estado.getNaruto().getVida() + "/" + estado.getVidaMaxima() + App.RESET + " | Escudo Inicial: " + escudoAcumulado);
            System.out.println("\n[1] 🎒 Abrir Mochila");
            System.out.println("[2] ⚔️ Iniciar Luta");
            System.out.print("Sua escolha: ");

            int op = entrada.nextInt();
            entrada.nextLine();

            if (op == 1) {
                boolean naMochila = true;
                while (naMochila) {
                    App.limparTela();
                    System.out.println(App.AMARELO + "========== 🎒 SUA MOCHILA ==========" + App.RESET);
                    System.out.println("[1] 🩹 Bandagem (" + estado.getQtdBandagem() + "x) - Cura 20 HP");
                    System.out.println("[2] 🗡️ Shuriken (" + estado.getQtdShuriken() + "x) - Tira 10 HP do Inimigo agora!");
                    System.out.println("[3] 🥷 Bandana  (" + estado.getQtdBandana() + "x) - +5 Escudo (Máx por luta: 2)");
                    System.out.println("[4] 🔙 Fechar Mochila");
                    System.out.print("Escolha um item para usar: ");
                    int item = entrada.nextInt();
                    entrada.nextLine();

                    if (item == 1) {
                        if (estado.getQtdBandagem() > 0 && estado.getNaruto().getVida() < estado.getVidaMaxima()) {
                            estado.setQtdBandagem(estado.getQtdBandagem() - 1);
                            int novaVida = Math.min(estado.getNaruto().getVida() + 20, estado.getVidaMaxima());
                            estado.setNaruto(new Heroi(estado.getNaruto().getNome(), novaVida, estado.getNaruto().getEscudo()));
                            System.out.println(App.VERDE + ">>> Bandagem usada! HP recuperado." + App.RESET);
                        } else {
                            System.out.println(App.VERMELHO + ">>> Sem bandagens ou HP já está cheio!" + App.RESET);
                        }
                    } else if (item == 2) {
                        if (estado.getQtdShuriken() > 0 && !usouShuriken) {
                            estado.setQtdShuriken(estado.getQtdShuriken() - 1);
                            usouShuriken = true;
                            vidaInimigo -= 10;
                            System.out.println(App.VERDE + ">>> Ataque Surpresa! " + nomeInimigo + " perdeu 10 HP antes da luta!" + App.RESET);
                        } else {
                            System.out.println(App.VERMELHO + ">>> Sem shurikens ou já arremessou nesta batalha!" + App.RESET);
                        }
                    } else if (item == 3) {
                        if (estado.getQtdBandana() > 0 && limiteBandana > 0) {
                            estado.setQtdBandana(estado.getQtdBandana() - 1);
                            limiteBandana--;
                            escudoAcumulado += 5;
                            System.out.println(App.VERDE + ">>> Bandana equipada! Escudo inicial +5." + App.RESET);
                        } else {
                            System.out.println(App.VERMELHO + ">>> Sem bandanas ou limite de 2 atingido!" + App.RESET);
                        }
                    } else if (item == 4) {
                        naMochila = false;
                    }

                    if (naMochila) {
                        System.out.println("[Pressione ENTER]");
                        entrada.nextLine();
                    }
                }
            } else if (op == 2) {
                vaiLutar = true;
            }
        }

        // aplica o escudo acumulado pelas bandanas ao Naruto
        estado.setNaruto(new Heroi(estado.getNaruto().getNome(), estado.getNaruto().getVida(), escudoAcumulado));

        return vidaInimigo;
    }
}