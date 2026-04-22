import java.util.ArrayList;
import java.util.Scanner;

/**
 * Representa a Fogueira, um evento de descanso no mapa do Modo História.
 * <br>
 * Na fogueira o jogador pode escolher entre duas ações mutuamente exclusivas:
 * <ul>
 *   <li><b>Descansar</b> — recupera 30% da vida máxima.</li>
 *   <li><b>Forjar</b> — melhora uma das cartas do deck, reduzindo seu custo de chakra em 1.</li>
 * </ul>
 * <br>
 * <b>Padrão de Design: Strategy</b>
 * <br>
 * A interface {@link AcaoFogueira} define o contrato comum para qualquer ação que pode
 * ser executada na fogueira. As implementações concretas — {@link AcaoDescansar} e
 * {@link AcaoForjar} — encapsulam cada comportamento de forma independente.
 * A classe {@code Fogueira} age como <i>Context</i>: recebe a estratégia escolhida
 * pelo jogador e a executa sem conhecer seus detalhes internos.
 * Isso permite adicionar novas ações (ex: meditar, invocar aliado) sem alterar
 * a lógica principal da fogueira.
 * <br>
 */
public class Fogueira extends EventoBase {

    // -----------------------------------------------------------------------
    // Interface Strategy
    // -----------------------------------------------------------------------

    /**
     * Contrato da estratégia de ação na fogueira.
     * Toda nova ação deve implementar esta interface.
     */
    public interface AcaoFogueira {
        /**
         * Executa a ação sobre o estado do jogador.
         *
         * @param estado  Objeto {@link Herois} que será modificado.
         * @param entrada Scanner para interação com o jogador.
         */
        void executar(Herois estado, Scanner entrada);

        /** Nome curto exibido no menu da fogueira. */
        String getNome();

        /** Descrição completa exibida no menu da fogueira. */
        String getDescricao();
    }

    // -----------------------------------------------------------------------
    // Estratégia Concreta 1: Descansar
    // -----------------------------------------------------------------------

    /**
     * Estratégia que cura 30% da vida máxima do herói.
     */
    public static class AcaoDescansar implements AcaoFogueira {

        @Override
        public void executar(Herois estado, Scanner entrada) {
            int cura = (int) Math.ceil(estado.getVidaMaxima() * 0.30);
            int novaVida = Math.min(estado.getNaruto().getVida() + cura, estado.getVidaMaxima());
            estado.setNaruto(new Heroi(estado.getNaruto().getNome(), novaVida, estado.getNaruto().getEscudo()));
            System.out.println(App.VERDE + "\n🔥 Você descansou ao calor da fogueira..." + App.RESET);
            System.out.println(App.VERDE + "   +" + cura + " HP recuperados! Vida atual: "
                    + estado.getNaruto().getVida() + "/" + estado.getVidaMaxima() + App.RESET);
        }

        @Override
        public String getNome() { return "🛌 Descansar"; }

        @Override
        public String getDescricao() { return "Recupera 30% da sua vida máxima"; }
    }

    // -----------------------------------------------------------------------
    // Estratégia Concreta 2: Forjar carta
    // -----------------------------------------------------------------------

    /**
     * Estratégia que melhora uma carta do deck, reduzindo seu custo de chakra em 1
     * (mínimo de 1 chakra).
     */
    public static class AcaoForjar implements AcaoFogueira {

        @Override
        public void executar(Herois estado, Scanner entrada) {
            ArrayList<Carta> deck = estado.getDeckNaruto();
            if (deck.isEmpty()) {
                System.out.println(App.VERMELHO + "\n>>> Seu deck está vazio! Nada para forjar." + App.RESET);
                return;
            }

            System.out.println(App.AMARELO + "\n🔨 Qual carta deseja melhorar?" + App.RESET);
            for (int i = 0; i < deck.size(); i++) {
                Carta c = deck.get(i);
                System.out.printf("  [%d] %-30s  Custo: %d chakra%n", (i + 1), c.getNome(), c.getCusto());
            }
            System.out.print("Escolha: ");

            int idx = -1;
            try {
                idx = entrada.nextInt() - 1;
                entrada.nextLine();
            } catch (Exception e) {
                entrada.nextLine();
            }

            if (idx < 0 || idx >= deck.size()) {
                System.out.println(App.VERMELHO + ">>> Escolha inválida. Nada foi forjado." + App.RESET);
                return;
            }

            Carta original = deck.get(idx);
            int novoCusto = Math.max(1, original.getCusto() - 1);

            // reconstrói a carta com custo reduzido preservando o tipo
            Carta melhorada = forjarCarta(original, novoCusto);
            deck.set(idx, melhorada);

            System.out.println(App.VERDE + "\n🔥 " + original.getNome() + " foi forjada!" + App.RESET);
            System.out.println(App.VERDE + "   Custo: " + original.getCusto() + " → " + novoCusto + " chakra" + App.RESET);
        }

        /**
         * Reconstrói uma carta existente com um novo custo de chakra, preservando
         * o tipo concreto (CartaDano, CartaEscudo, etc.) e os demais atributos.
         *
         * @param original  Carta original a ser melhorada.
         * @param novoCusto Novo custo de chakra após a melhoria.
         * @return Nova instância da carta com custo reduzido.
         */
        private Carta forjarCarta(Carta original, int novoCusto) {
            if (original instanceof CartaDanoArea) {
                CartaDanoArea c = (CartaDanoArea) original;
                return new CartaDanoArea(c.getNome() + "+", c.getDescricao(), novoCusto,
                        c.getDano(), c.getDanoFalha(),c.getChanceSucesso());
            } else if (original instanceof CartaDanoVeneno) {
                CartaDanoVeneno c = (CartaDanoVeneno) original;
                return new CartaDanoVeneno(c.getNome() + "+", c.getDescricao(), novoCusto,
                        c.getDano(), c.getVeneno());
            } else if (original instanceof CartaSacrificio) {
                CartaSacrificio c = (CartaSacrificio) original;
                return new CartaSacrificio(c.getNome() + "+", c.getDescricao(), novoCusto,
                        c.getDano(),c.getDanoAoUsuario());
            } else if (original instanceof CartaRouboVida) {
                CartaRouboVida c = (CartaRouboVida) original;
                return new CartaRouboVida(c.getNome() + "+", c.getDescricao(), novoCusto,
                        c.getDano(),c.getCura());
            } else if (original instanceof CartaDano) {
                CartaDano c = (CartaDano) original;
                return new CartaDano(c.getNome() + "+", c.getDescricao(), novoCusto, c.getDano());
            } else if (original instanceof CartaEscudoRegen) {
                CartaEscudoRegen c = (CartaEscudoRegen) original;
                return new CartaEscudoRegen(c.getNome() + "+", c.getDescricao(), novoCusto,
                        c.getEscudo(), c.getRegen());
            } else if (original instanceof CartaEscudoEspinhos) {
                CartaEscudoEspinhos c = (CartaEscudoEspinhos) original;
                return new CartaEscudoEspinhos(c.getNome() + "+", c.getDescricao(), novoCusto,
                        c.getEscudo(), c.getDano());
            } else if (original instanceof CartaEscudo) {
                CartaEscudo c = (CartaEscudo) original;
                return new CartaEscudo(c.getNome() + "+", c.getDescricao(), novoCusto, c.getEscudo());
            } else if (original instanceof CartaCuraRegen) {
                CartaCuraRegen c = (CartaCuraRegen) original;
                return new CartaCuraRegen(c.getNome() + "+", c.getDescricao(), novoCusto,
                        c.getCura(), c.getRegenCura());
            }
            // fallback genérico: retorna a carta original sem mudanças
            return original;
        }

        @Override
        public String getNome() { return "🔨 Forjar Carta"; }

        @Override
        public String getDescricao() { return "Reduz o custo de chakra de uma carta em 1"; }
    }

    // -----------------------------------------------------------------------
    // Context (Fogueira)
    // -----------------------------------------------------------------------

    /** Estratégias disponíveis na fogueira. */
    private final AcaoFogueira[] acoes;

    /**
     * Construtor da fogueira. Registra as estratégias disponíveis.
     */
    public Fogueira() {
        this.acoes = new AcaoFogueira[]{
                new AcaoDescansar(),
                new AcaoForjar()
        };
    }

    /**
     * Exibe o menu da fogueira, recebe a escolha do jogador e delega a execução
     * à estratégia selecionada.
     *
     * @param estado  Objeto {@link Herois} que será modificado pela ação escolhida.
     * @param entrada Objeto {@link Scanner} para navegar no menu.
     * @return sempre {@code true} — a fogueira nunca causa game over.
     */
    @Override
    public boolean iniciar(Herois estado, Scanner entrada) {
        App.limparTela();
        System.out.println(App.AMARELO + "╔════════════════════════════════════════════════════════╗" + App.RESET);
        System.out.println(App.AMARELO + "║" + App.RESET + App.NEGRITO + "                  🔥  FOGUEIRA DE DESCANSO               " + App.RESET + App.AMARELO + "║" + App.RESET);
        System.out.println(App.AMARELO + "╠════════════════════════════════════════════════════════╣" + App.RESET);
        System.out.println(App.AMARELO + "║" + App.RESET + "  HP atual: " + App.VERDE + estado.getNaruto().getVida()
                + "/" + estado.getVidaMaxima() + App.RESET
                + "   |   Deck: " + estado.getDeckNaruto().size() + " cartas"
                + "                    " + App.AMARELO + "║" + App.RESET);
        System.out.println(App.AMARELO + "╠════════════════════════════════════════════════════════╣" + App.RESET);

        for (int i = 0; i < acoes.length; i++) {
            System.out.printf(App.AMARELO + "║" + App.RESET + "  [%d] %-20s  %s%n" + App.RESET,
                    (i + 1), acoes[i].getNome(), "");
            System.out.printf(App.AMARELO + "║" + App.RESET + "       %-52s" + App.AMARELO + " ║%n" + App.RESET,
                    acoes[i].getDescricao());
        }

        System.out.println(App.AMARELO + "╚════════════════════════════════════════════════════════╝" + App.RESET);
        System.out.print("Sua escolha: ");

        int op = -1;
        try {
            op = entrada.nextInt() - 1;
            entrada.nextLine();
        } catch (Exception e) {
            entrada.nextLine();
        }

        if (op >= 0 && op < acoes.length) {
            acoes[op].executar(estado, entrada);
        } else {
            System.out.println(App.VERMELHO + ">>> Opção inválida. A fogueira apaga sem te ajudar..." + App.RESET);
        }

        System.out.println("\n[Pressione ENTER para continuar]");
        entrada.nextLine();
        return true;
    }
}