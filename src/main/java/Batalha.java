import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Encapsula a execução dos combates narrativos do Modo História.
 * <br>
 * Herda de {@link EventoBase}, tornando cada batalha um evento válido do mapa.
 * Após cada vitória, além de Shinobi Coins, o jogador recebe a oportunidade de
 * escolher uma carta para adicionar ao seu deck (sistema de recompensa de cartas).
 * <br>
 * <b>Padrão de Design:</b> Template Method (via {@link EventoBase}) — o esqueleto
 * do fluxo (combate → recompensa → progressão) é definido aqui, e os detalhes
 * narrativos ficam nos métodos especializados.
 */
public class Batalha extends EventoBase {

    /**
     * Implementação do método abstrato de {@link EventoBase}.
     * Batalhas narrativas usam métodos especializados (executarFase1, etc.).
     * Este método serve como fallback para uso polimórfico genérico.
     *
     * @param estado  Objeto {@link Herois} com o estado atual do jogador.
     * @param entrada Objeto {@link Scanner} para controle do fluxo de entrada.
     * @return {@code true} por padrão.
     */
    @Override
    public boolean iniciar(Herois estado, Scanner entrada) {
        return true;
    }

    // -----------------------------------------------------------------------
    // Pool de cartas de recompensa
    // -----------------------------------------------------------------------

    /**
     * Constrói e retorna o pool de cartas que podem ser oferecidas como
     * recompensa após qualquer vitória no modo história.
     *
     * @return Lista com todas as cartas candidatas a recompensa.
     */
    private ArrayList<Carta> poolCartasRecompensa(ArrayList<Carta> deckNaruto) {

        ArrayList<Carta> pool = new ArrayList<>();
        
        pool.add(new CartaDano("Golpe Preciso", "Um ataque rápido e certeiro", 1, 10));
        pool.add(new CartaDano("Soco Poderoso", "Ataque físico com força total", 2, 18));
        pool.add(new CartaEscudo("Defesa Ninja", "Posição defensiva clássica ninja", 1, 8));
        pool.add(new CartaDanoVeneno("Shuriken Envenenada", "Shuriken com veneno paralisante", 2, 10, 4));
        pool.add(new CartaCuraRegen("Pílula Ninja", "Recuperação de energia ninja", 2, 10, 3));
        pool.add(new CartaDanoArea("Chuva de Kunais", "Ataque em área com múltiplas kunais", 3, 20, 3, 35));
        pool.add(new CartaEscudoRegen("Selos de Cura", "Selos que regeneram vida lentamente", 2, 8, 5));
        pool.add(new CartaEscudoEspinhos("Armadura de Chakra", "Escudo que reflete dano ao atacante", 3, 12, 6));
        pool.add(new CartaRouboVida("Drenagem de Chakra", "Rouba a força vital do oponente", 2, 10,10));
        pool.add(new CartaSacrificio("Explosão Kamikaze", "Dano massivo, mas dói em você também", 2, 25, 8));
        pool.removeIf(cartaNoPool -> deckNaruto.stream().anyMatch(cartaNoDeck -> cartaNoDeck.getNome().equals(cartaNoPool.getNome())));
        
        return pool;
    }

    /**
     * Oferece até 3 cartas aleatórias ao jogador e permite escolher uma para
     * adicionar ao deck, ou pular.
     *
     * @param estado  Objeto {@link Herois} cujo deck será atualizado.
     * @param entrada Objeto {@link Scanner} para capturar a escolha.
     */
    private void oferecerCartaRecompensa(Herois estado, Scanner entrada) {
        
        ArrayList<Carta> pool = poolCartasRecompensa(estado.getDeckNaruto());
        Collections.shuffle(pool);
        int qtd = Math.min(3, pool.size());

        System.out.println(App.AMARELO + "\n╔════════════════════════════════════════════════════════╗" + App.RESET);
        System.out.println(App.AMARELO + "║" + App.RESET + App.NEGRITO + "             🎴  RECOMPENSA: ESCOLHA UMA CARTA           " + App.RESET + App.AMARELO + "║" + App.RESET);
        System.out.println(App.AMARELO + "╠════════════════════════════════════════════════════════╣" + App.RESET);

        for (int i = 0; i < qtd; i++) {
            Carta c = pool.get(i);
            String tipo;
            if (c instanceof CartaDano || c instanceof CartaDanoArea || c instanceof CartaDanoVeneno
                    || c instanceof CartaSacrificio || c instanceof CartaRouboVida) {
                tipo = App.VERMELHO + "[Ataque] " + App.RESET;
            } else if (c instanceof CartaEscudo || c instanceof CartaEscudoRegen || c instanceof CartaEscudoEspinhos) {
                tipo = App.CIANO + "[Defesa] " + App.RESET;
            } else {
                tipo = App.VERDE + "[Suporte]" + App.RESET;
            }
            System.out.printf(App.AMARELO + "║" + App.RESET + "  [%d] %-28s %s Custo: %d" + App.AMARELO + "  ║%n" + App.RESET,
                    (i + 1), c.getNome(), tipo, c.getCusto());
            String desc = c.getDescricao();
            if (desc.length() > 54) desc = desc.substring(0, 51) + "...";
            System.out.printf(App.AMARELO + "║" + App.RESET + "       %-54s" + App.AMARELO + "║%n" + App.RESET, desc);
        }

        System.out.println(App.AMARELO + "║" + App.RESET + "  [" + (qtd + 1) + "] Pular — não quero nenhuma carta" +
                "                       " + App.AMARELO + "║" + App.RESET);
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
            Carta cartaEscolhida = pool.get(escolha - 1);
            estado.getDeckNaruto().add(cartaEscolhida);
            pool.remove(cartaEscolhida);
            System.out.println(App.VERDE + ">>> " + cartaEscolhida.getNome() + " adicionada ao seu deck!" + App.RESET);
        } else {
            System.out.println(App.AMARELO + ">>> Você pulou a recompensa de carta." + App.RESET);
        }
    }

    // -----------------------------------------------------------------------
    // Batalhas narrativas da História
    // -----------------------------------------------------------------------

    /**
     * Executa o combate da Fase 1: Naruto vs Deidara.
     * Em caso de vitória concede 50 Shinobi Coins e oferece carta de recompensa.
     *
     * @param estado  Objeto {@link Herois} com o estado atual do jogador.
     * @param entrada Objeto {@link Scanner} para controle do fluxo de entrada.
     * @return {@code true} se o jogador venceu, {@code false} se perdeu (game over).
     */
    public boolean executarFase1(Herois estado, Scanner entrada) {
        Inimigo deidara = new Inimigo("Deidara", 60, 0);
        ArrayList<Carta> deckDeidara = App.GeraDeckInimigo(deidara);

        ArrayList<Carta> deckParaLuta = new ArrayList<>(estado.getDeckNaruto());
        Collections.shuffle(deckParaLuta);
        Collections.shuffle(deckDeidara);

        ArrayList<Carta> mao = new ArrayList<>();
        ArrayList<Carta> descarte = new ArrayList<>();

        Combate arena = new Combate();
        String resultado = arena.rodarCombate(estado.getNaruto(), deidara, deckDeidara,
                deckParaLuta, mao, descarte, entrada);

        System.out.println("\n" + resultado);

        if (estado.getNaruto().getVida() <= 0) {
            System.out.println("\n💀 GAME OVER: A Akatsuki escapou com o Kazekage...");
            System.out.println("[Pressione ENTER para voltar ao menu]");
            entrada.nextLine();
            return false;
        }

        System.out.println("\n" + App.VERDE + "🌟 Deidara é derrotado, e Naruto consegue resgatar Gaara 🌟" + App.RESET);
        int recompensa = 50;
        estado.setShinobiCoins(estado.getShinobiCoins() + recompensa);
        System.out.println(App.VERDE + "\n🪙  Parabéns, você ganhou " + recompensa + " Shinobi Coins 🪙" + App.RESET);

        oferecerCartaRecompensa(estado, entrada);

        System.out.println("\n[Pressione ENTER para continuar a história]");
        entrada.nextLine();
        return true;
    }

    /**
     * Executa o treinamento: Naruto vs Jiraiya.
     * Em caso de vitória concede 70 moedas, oferece carta de recompensa,
     * evolui o herói para "Naruto (Sábio)" e desbloqueia a transformação Modo Sábio.
     *
     * @param estado  Objeto {@link Herois} com o estado atual do jogador.
     * @param entrada Objeto {@link Scanner} para controle do fluxo de entrada.
     * @return {@code true} se o jogador venceu, {@code false} se perdeu (game over).
     */
    public boolean executarTreinoJiraiya(Herois estado, Scanner entrada) {
        Inimigo jiraiya = new Inimigo("Jiraiya", 70, 0);
        ArrayList<Carta> deckJiraiya = App.GeraDeckInimigo(jiraiya);

        ArrayList<Carta> deckParaLuta = new ArrayList<>(estado.getDeckNaruto());
        Collections.shuffle(deckParaLuta);

        Combate arena = new Combate();
        String resultado = arena.rodarCombate(estado.getNaruto(), jiraiya, deckJiraiya,
                deckParaLuta, new ArrayList<>(), new ArrayList<>(), entrada);

        System.out.println("\n" + resultado);

        if (estado.getNaruto().getVida() <= 0) {
            System.out.println("\n💀 Jiraiya: 'Ainda falta muito, garoto...' GAME OVER.");
            entrada.nextLine();
            return false;
        }

        System.out.println(App.VERDE + "\n🌟 TREINO CONCLUÍDO! Jiraiya aprova sua força. 🌟" + App.RESET);
        int recompensa = 70;
        estado.setShinobiCoins(estado.getShinobiCoins() + recompensa);
        System.out.println(App.VERDE + "🪙  Parabéns, você ganhou " + recompensa + " Shinobi Coins 🪙" + App.RESET);

        oferecerCartaRecompensa(estado, entrada);
        entrada.nextLine();

        int vidaAtual = estado.getNaruto().getVida();
        estado.setNaruto(new Heroi("Naruto (Sábio)", vidaAtual, 0));
        estado.setVidaMaxima(80);

        estado.getDeckNaruto().add(new CartaDanoArea("Jutsu de Invocação: Gamabunta",
                "Convoca o Chefe dos Sapos!", 3, 25, 4, 75));
        estado.setTransformacao(1);

        atualizarDeckPosTransformacao(estado);

        System.out.println(App.VERDE + ">>> Transformação desbloqueada: MODO SÁBIO 🌀" + App.RESET);
        System.out.println(App.VERDE + ">>> Você aprendeu: Jutsu de Invocação: Gamabunta! 🐸" + App.RESET);
        System.out.println(App.VERDE + ">>> Sua vida máxima aumentou para 80!" + App.RESET);

        return true;
    }

    /**
     * Executa o treinamento: Naruto vs Kakashi.
     * Em caso de vitória concede 80 moedas, oferece carta de recompensa,
     * evolui o herói para "Naruto (Manto da Kyuubi)" e desbloqueia a transformação.
     *
     * @param estado  Objeto {@link Herois} com o estado atual do jogador.
     * @param entrada Objeto {@link Scanner} para controle do fluxo de entrada.
     * @return {@code true} se o jogador venceu, {@code false} se perdeu (game over).
     */
    public boolean executarTreinoKakashi(Herois estado, Scanner entrada) {
        Inimigo kakashi = new Inimigo("Kakashi", 70, 0);
        ArrayList<Carta> deckKakashi = App.GeraDeckInimigo(kakashi);

        ArrayList<Carta> deckParaLuta = new ArrayList<>(estado.getDeckNaruto());
        Collections.shuffle(deckParaLuta);

        Combate arena = new Combate();
        String resultado = arena.rodarCombate(estado.getNaruto(), kakashi, deckKakashi,
                deckParaLuta, new ArrayList<>(), new ArrayList<>(), entrada);

        System.out.println("\n" + resultado);

        if (estado.getNaruto().getVida() <= 0) {
            System.out.println("\n💀 Kakashi: 'Você perdeu a concentração...' GAME OVER.");
            entrada.nextLine();
            return false;
        }

        System.out.println("\n🌟 TREINO CONCLUÍDO! Você cortou a cachoeira ao meio! 🌟");
        int recompensa = 80;
        estado.setShinobiCoins(estado.getShinobiCoins() + recompensa);
        System.out.println(App.VERDE + "\n🪙 Parabéns, você ganhou " + recompensa + " Shinobi Coins 🪙" + App.RESET);

        oferecerCartaRecompensa(estado, entrada);
        entrada.nextLine();

        int vidaAtual = estado.getNaruto().getVida();
        estado.setNaruto(new Heroi("Naruto (Manto da Kyuubi)", vidaAtual, 0));
        estado.setVidaMaxima(80);

        estado.getDeckNaruto().add(new CartaDano("Rasenshuriken",
                "Lâminas de vento destrutivas", 3, 35));
        estado.setTransformacao(2);

        atualizarDeckPosTransformacao(estado);

        System.out.println(App.VERDE + ">>> Transformação desbloqueada: MANTO DA KYUUBI 🔥" + App.RESET);
        System.out.println(App.VERDE + ">>> Você aprendeu: Rasenshuriken! 💨" + App.RESET);
        System.out.println(App.VERDE + ">>> Sua vida máxima aumentou para 80!" + App.RESET);

        return true;
    }

    /**
     * Executa o combate final: Naruto vs Pain (Fase 2).
     * Antes de iniciar, delega à {@link Evento} a Sala de Preparação.
     * Em caso de vitória exibe o encerramento épico da história.
     *
     * @param estado  Objeto {@link Herois} com o estado atual do jogador.
     * @param entrada Objeto {@link Scanner} para controle do fluxo de entrada.
     * @return {@code true} se o jogador venceu, {@code false} se perdeu (game over).
     */
    public boolean executarFase2Pain(Herois estado, Scanner entrada) {
        Evento evento = new Evento();

        int vidaPain = evento.salaDePreparacao(estado, "Pain", 120, entrada);
        Inimigo pain = new Inimigo("Pain", vidaPain, 0);

        ArrayList<Carta> deckPain = App.GeraDeckInimigo(pain);

        ArrayList<Carta> deckParaLuta = new ArrayList<>(estado.getDeckNaruto());
        Collections.shuffle(deckParaLuta);

        Combate arena = new Combate();
        arena.habilitarEvolucao(estado.getTransformacao());

        String resultado = arena.rodarCombate(estado.getNaruto(), pain, deckPain,
                deckParaLuta, new ArrayList<>(), new ArrayList<>(), entrada);

        System.out.println("\n" + resultado);

        if (estado.getNaruto().getVida() <= 0) {
            System.out.println("\n💀 GAME OVER: A Vila da Folha foi aniquilada...");
            System.out.println("[Pressione ENTER para voltar ao menu]");
            entrada.nextLine();
            return false;
        }

        System.out.println("\n🌟 VITÓRIA ÉPICA! A PAZ FOI RESTAURADA! 🌟");
        evento.imprimirFala("O corpo de Pain cai no chão. A ameaça da Akatsuki foi parada.", entrada);
        evento.imprimirFala("Os moradores de Konoha comemoram. Naruto finalmente é reconhecido como o Herói da Vila!", entrada);

        System.out.println("\n" + App.AMARELO + "=== FIM DO MODO HISTÓRIA ===" + App.RESET);
        System.out.println("Parabéns por zerar a demonstração do Shinobi Legacy!");
        evento.imprimirFala("[Pressione ENTER para retornar ao menu principal]", entrada);

        return true;
    }

    /**
     * Aplica as evoluções de deck comuns aos dois caminhos de treinamento.
     *
     * @param estado Objeto {@link Herois} cujo deck será modificado in-place.
     */
    private void atualizarDeckPosTransformacao(Herois estado) {
        ArrayList<Carta> deck = estado.getDeckNaruto();
        for (int i = 0; i < deck.size(); i++) {
            Carta cartaAtual = deck.get(i);

            if (cartaAtual.getNome().equals("Rasengan")) {
                deck.set(i, new CartaDano("Rasengan", "Principal jutsu de ataque de Naruto", 2, 15));
            } else if (cartaAtual.getNome().equals("Jutsu Clone das Sombras")) {
                deck.set(i, new CartaEscudo("Jutsu Clone das Sombras",
                        "Naruto gera muitos clones para se proteger do inimigo", 1, 10));
            } else if (cartaAtual.getNome().equals("Jutsu de Substituição")) {
                deck.remove(i);
                i--;
            }
        }
    }
}