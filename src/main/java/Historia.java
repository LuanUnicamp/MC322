import java.util.Scanner;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Classe responsável por gerenciar a progressão do Modo História
 * (Shinobi Legacy: Saga Naruto Shippuden).
 * <br>
 * Utiliza uma estrutura de Árvore (através de {@link DefaultMutableTreeNode}) para
 * mapear as fases, bifurcações e eventos da campanha. A lógica de cada
 * segmento é delegada às classes especializadas {@link Herois}, {@link Batalha},
 * {@link Loja}, {@link Evento}, {@link Escolha} e {@link Fogueira}, mantendo
 * este arquivo como orquestrador do fluxo narrativo.
 * <br>
 * Todos os eventos são subtipos de {@link EventoBase}, o que permite que o
 * sistema de mapa trate batalhas, lojas e fogueiras de forma polimórfica.
 */
public class Historia {

    private DefaultMutableTreeNode arvoreHistoria;
    private DefaultMutableTreeNode noAtual;

    private Herois estado;

    private Batalha batalha;
    private Loja loja;
    private Evento evento;
    private Escolha escolha;
    private Fogueira fogueira;

    /**
     * Construtor da classe Historia.
     * Constrói a árvore narrativa, posiciona o nó inicial e instancia
     * os colaboradores especializados.
     */
    public Historia() {
        construirArvore();
        this.noAtual = (DefaultMutableTreeNode) arvoreHistoria.getChildAt(0);

        this.estado = new Herois();
        this.batalha = new Batalha();
        this.loja = new Loja();
        this.evento = new Evento();
        this.escolha = new Escolha();
        this.fogueira = new Fogueira();
    }

    /**
     * Constrói e vincula os nós da árvore de história.
     * Define o nó raiz e cria os nós filhos correspondentes
     * às fases, bifurcações e eventos intermediários.
     */
    private void construirArvore() {
        this.arvoreHistoria = new DefaultMutableTreeNode("Shinobi Legacy: Saga Naruto Shippuden");

        DefaultMutableTreeNode fase1 = new DefaultMutableTreeNode("Fase 1: O Sequestro de Gaara");
        arvoreHistoria.add(fase1);

        DefaultMutableTreeNode treinoJiraiya = new DefaultMutableTreeNode("Treinamento com Jiraiya");
        DefaultMutableTreeNode treinoKakashi = new DefaultMutableTreeNode("Treinamento com Kakashi");
        fase1.add(treinoJiraiya);
        fase1.add(treinoKakashi);

        // Fogueira após cada caminho de treinamento
        DefaultMutableTreeNode fogueiraCaminhoJ = new DefaultMutableTreeNode("Fogueira de Descanso");
        DefaultMutableTreeNode fogueiraCaminhoK = new DefaultMutableTreeNode("Fogueira de Descanso");
        treinoJiraiya.add(fogueiraCaminhoJ);
        treinoKakashi.add(fogueiraCaminhoK);

        // os 2 caminhos convergem para a fase 2
        DefaultMutableTreeNode fase2PainCaminhoJ = new DefaultMutableTreeNode("Fase 2: A Invasão de Pain");
        DefaultMutableTreeNode fase2PainCaminhoK = new DefaultMutableTreeNode("Fase 2: A Invasão de Pain");
        fogueiraCaminhoJ.add(fase2PainCaminhoJ);
        fogueiraCaminhoK.add(fase2PainCaminhoK);
    }

    /**
     * Exibe visualmente o "Pergaminho das Missões", servindo como mapa da campanha.
     * Desenha caminhos completados, caminhos abandonados e indica a posição
     * atual do jogador com a tag [VOCÊ].
     */
    public void mostrarMapa() {
        System.out.println("\n" + App.AMARELO + "            📜 PERGAMINHO DAS MISSÕES 📜" + App.RESET);
        System.out.println(App.CIANO + "╔══════════════════════════════════════════════════╗" + App.RESET);

        javax.swing.tree.TreeNode[] caminho = noAtual.getPath();

        String espacamento = "  ";
        DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) caminho[0];
        System.out.println(App.CIANO + "║" + App.RESET + espacamento + "𖣘 " + App.NEGRITO + raiz.getUserObject() + App.RESET);

        for (int i = 1; i < caminho.length; i++) {
            DefaultMutableTreeNode pai = (DefaultMutableTreeNode) caminho[i - 1];
            DefaultMutableTreeNode noCaminho = (DefaultMutableTreeNode) caminho[i];

            System.out.println(App.CIANO + "║" + App.RESET + espacamento + " │");

            int numFilhos = pai.getChildCount();

            for (int j = 0; j < numFilhos; j++) {
                DefaultMutableTreeNode irmao = (DefaultMutableTreeNode) pai.getChildAt(j);
                String nomeIrmao = (String) irmao.getUserObject();
                String prefixoGalho = (j == numFilhos - 1) ? " └── " : " ├── ";

                // ícone por tipo de nó
                String icone = "⚔️ ";
                if (nomeIrmao.contains("Fogueira")) icone = "🔥 ";
                else if (nomeIrmao.contains("Loja") || nomeIrmao.contains("Mercado")) icone = "🏪 ";

                if (irmao == noCaminho) {
                    if (i == caminho.length - 1) {
                        System.out.println(App.CIANO + "║" + App.RESET + espacamento + prefixoGalho + icone + App.NEGRITO + nomeIrmao + App.VERMELHO + " ◄ [VOCÊ]" + App.RESET);
                    } else {
                        System.out.println(App.CIANO + "║" + App.RESET + espacamento + prefixoGalho + "✅ " + nomeIrmao);
                    }
                } else {
                    System.out.println(App.CIANO + "║" + App.RESET + espacamento + prefixoGalho + "❌ " + nomeIrmao + App.AMARELO + " (Caminho Abandonado)" + App.RESET);
                }
            }
            espacamento += "     ";
        }

        if (noAtual.getChildCount() > 0) {
            System.out.println(App.CIANO + "║" + App.RESET + espacamento + " │");
            System.out.println(App.CIANO + "║" + App.RESET + espacamento + " └── ☁️  " + App.AMARELO + "Destino Oculto..." + App.RESET);
        }

        System.out.println(App.CIANO + "╚══════════════════════════════════════════════════╝" + App.RESET);
    }

    /**
     * Gerencia o loop principal do Modo História, percorrendo a árvore de fases
     * e delegando cada segmento ao colaborador adequado.
     *
     * @param entrada Objeto {@link Scanner} compartilhado para toda a entrada do usuário.
     */
    public void iniciar(Scanner entrada) {
        System.out.println("\n" + App.CIANO + "╔" + "═".repeat(60) + "╗" + App.RESET);
        System.out.println(App.CIANO + "║" + App.RESET + App.NEGRITO + App.AMARELO + "             📜 MODO HISTÓRIA: SAGA SHIPPUDEN               " + App.RESET + App.CIANO + "║" + App.RESET);
        System.out.println(App.CIANO + "╚" + "═".repeat(60) + "╝" + App.RESET);

        boolean acabou = false;
        entrada.nextLine();

        while (!acabou) {

            mostrarMapa();
            System.out.println("\n[Pressione ENTER para iniciar a fase]");
            entrada.nextLine();

            App.limparTela();

            String nomeFase = (String) noAtual.getUserObject();

            System.out.println("\n" + App.CIANO + "╔" + "═".repeat(60) + "╗" + App.RESET);
            System.out.println(App.CIANO + "║" + App.RESET + App.NEGRITO + App.AMARELO + "               ⚔️  " + nomeFase.toUpperCase() + "              " + App.RESET + App.CIANO + "║" + App.RESET);
            System.out.println(App.CIANO + "╚" + "═".repeat(60) + "╝" + App.RESET + "\n");

            // --- FASE 1: O Sequestro de Gaara ---
            if (nomeFase.equals("Fase 1: O Sequestro de Gaara")) {

                System.out.println("\n" + App.AMARELO + "[Pressione ENTER para iniciar a introdução]" + App.RESET);
                entrada.nextLine();

                evento.imprimirFala(App.CIANO + "📖 O vento uiva pelos desfiladeiros da Vila Oculta da Areia..." + App.RESET, entrada);
                evento.imprimirFala(App.CIANO + "📖 O céu noturno é subitamente iluminado por clarões ensurdecedores." + App.RESET, entrada);
                evento.imprimirFala(App.CIANO + "📖 Kankuro cai de joelhos na areia. Suas marionetes estão em pedaços." + App.RESET, entrada);

                System.out.println();

                evento.imprimirFala(App.VERMELHO + "Deidara" + App.RESET + " (sorrindo): 'A arte é uma explosão! E o seu Kazekage é a minha obra-prima de hoje.'", entrada);
                evento.imprimirFala(App.ROXO + "Sasori" + App.RESET + " (impaciente): 'Não me faça esperar, Deidara. Odeio esperar e odeio fazer os outros esperarem.'", entrada);

                evento.imprimirFala(App.CIANO + "\n📖 Os dois membros da Akatsuki desaparecem na escuridão, levando Gaara..." + App.RESET, entrada);

                System.out.println(App.CIANO + "📖 [ Alguns dias depois, nos portões de Konoha... ]\n" + App.RESET);
                entrada.nextLine();

                evento.imprimirFala(App.VERDE + "Kakashi" + App.RESET + ": 'A situação é crítica. A Akatsuki não age sem um plano impecável.'", entrada);
                evento.imprimirFala(App.AMARELO + "Naruto" + App.RESET + " (cerrando os punhos): 'Eu não dou a mínima para os planos deles, Kakashi-sensei!'", entrada);
                evento.imprimirFala(App.AMARELO + "Naruto" + App.RESET + ": 'O Gaara sofreu a vida toda com a mesma dor que eu... Eu vou trazê-lo de volta!'", entrada);

                System.out.println("\n" + App.VERMELHO + "🔥 PREPARE-SE: A perseguição começou! 🔥" + App.RESET);
                System.out.println("\n" + App.AMARELO + "⚔️  INICIANDO BATALHA: NARUTO vs DEIDARA ⚔️" + App.RESET);
                System.out.println("[Pressione ENTER para lutar]");
                entrada.nextLine();

                boolean venceu = batalha.executarFase1(estado, entrada);
                if (!venceu) {
                    acabou = true;
                    continue;
                }

                App.limparTela();

                evento.imprimirFala(App.CIANO + "📖 Naruto sabe que as próximas batalhas serão contra a elite da Akatsuki." + App.RESET, entrada);
                evento.imprimirFala(App.CIANO + "📖 Para se tornar mais forte, ele deve escolher um mestre para um treino intensivo." + App.RESET, entrada);

                App.limparTela();

                int indiceEscolha = escolha.apresentarEscolha(entrada);
                this.noAtual = (DefaultMutableTreeNode) noAtual.getChildAt(indiceEscolha);

                if (indiceEscolha == 0) {
                    evento.imprimirFala("\n" + App.ROXO + "Jiraiya" + App.RESET + ": 'Prepare o seu espírito, Naruto! Vamos ao Monte Myoboku!'", entrada);
                } else {
                    evento.imprimirFala("\n" + App.VERDE + "Kakashi" + App.RESET + ": 'Espero que esteja pronto para um treino de elite. Vamos nessa!'", entrada);
                }
                App.limparTela();
                continue;
            }

            // --- TREINAMENTO COM JIRAIYA ---
            else if (nomeFase.equals("Treinamento com Jiraiya")) {
                evento.imprimirFala(App.CIANO + "📖 Naruto e Jiraiya viajam para o Monte Myoboku, a terra dos sapos..." + App.RESET, entrada);
                evento.imprimirFala(App.ROXO + "Jiraiya" + App.RESET + ": 'Para dominar a invocação, você precisa extrair o chakra da Raposa!'", entrada);

                System.out.println("\n" + App.AMARELO + "⚔️  INICIANDO BATALHA: NARUTO vs JIRAYA ⚔️" + App.RESET);
                System.out.println("[Pressione ENTER para lutar]");
                entrada.nextLine();

                boolean venceu = batalha.executarTreinoJiraiya(estado, entrada);
                if (!venceu) {
                    acabou = true;
                    continue;
                }

                evento.imprimirFala("\n[Pressione ENTER para continuar]", entrada);
                evento.imprimirFala(App.CIANO + "📖 Após o treino, Naruto decide descansar antes da missão final..." + App.RESET, entrada);

                // avança para a Fogueira
                this.noAtual = (DefaultMutableTreeNode) noAtual.getChildAt(0);
                App.limparTela();
                continue;
            }

            // --- TREINAMENTO COM KAKASHI ---
            else if (nomeFase.equals("Treinamento com Kakashi")) {
                evento.imprimirFala(App.VERDE + "Kakashi:" + App.RESET + " 'Vamos focar na sua natureza de chakra: o Vento.'", entrada);

                System.out.println("\n" + App.AMARELO + "⚔️  INICIANDO BATALHA: NARUTO vs KAKASHI ⚔️" + App.RESET);
                System.out.println("[Pressione ENTER para lutar]");
                entrada.nextLine();

                boolean venceu = batalha.executarTreinoKakashi(estado, entrada);
                if (!venceu) {
                    acabou = true;
                    continue;
                }

                evento.imprimirFala("\n[Pressione ENTER para continuar]", entrada);
                evento.imprimirFala(App.CIANO + "📖 Após o treino, Naruto acende uma fogueira para recuperar as forças..." + App.RESET, entrada);

                // avança para a Fogueira
                this.noAtual = (DefaultMutableTreeNode) noAtual.getChildAt(0);
                App.limparTela();
                continue;
            }

            // --- FOGUEIRA DE DESCANSO ---
            else if (nomeFase.equals("Fogueira de Descanso")) {
                evento.imprimirFala(App.AMARELO + "📖 Naruto encontra um abrigo e acende uma fogueira para a noite..." + App.RESET, entrada);
                evento.imprimirFala(App.AMARELO + "Naruto" + App.RESET + ": 'Esse é meu momento antes da batalha final. O que devo fazer?'", entrada);

                fogueira.iniciar(estado, entrada);

                // oferecer loja também antes da fase final
                loja.perguntarLoja(estado, entrada);
                App.limparTela();
            }

            // --- FASE 2: A Invasão de Pain ---
            else if (nomeFase.equals("Fase 2: A Invasão de Pain")) {
                System.out.println("\n[Pressione ENTER para avançar]");
                entrada.nextLine();

                evento.imprimirFala(App.CIANO + "\n📖 De volta à Vila da Folha, o cenário é de destruição total...", entrada);
                evento.imprimirFala(App.CIANO + "📖 Uma cratera gigante substituiu o centro da Aldeia." + App.RESET, entrada);
                evento.imprimirFala(App.ROXO + "\nPain" + App.RESET + " (flutuando nos céus): 'O mundo deve conhecer a dor.'", entrada);
                evento.imprimirFala(App.AMARELO + "\nNaruto" + App.RESET + ": 'Eu vou acabar com você... e trazer a paz do meu jeito!'", entrada);

                System.out.println("\n" + App.VERMELHO + "╔" + "═".repeat(60) + "╗" + App.RESET);
                System.out.println(App.VERMELHO + "║" + App.RESET + App.NEGRITO + "               ☠️  BATALHA FINAL: NARUTO vs PAIN           " + App.VERMELHO + "║" + App.RESET);
                System.out.println(App.VERMELHO + "╚" + "═".repeat(60) + "╝" + App.RESET);

                boolean venceu = batalha.executarFase2Pain(estado, entrada);
                if (!venceu) {
                    acabou = true;
                    continue;
                }

                acabou = true;
                continue;
            }

            // --- navegação na árvore ---
            int qtdFilhos = noAtual.getChildCount();

            if (qtdFilhos == 0) {
                System.out.println("\n[ Fim do Conteúdo Atual da História. Pressione ENTER para sair ]");
                entrada.nextLine();
                acabou = true;
            } else if (qtdFilhos == 1) {
                noAtual = (DefaultMutableTreeNode) noAtual.getChildAt(0);
            }
        }
    }
}