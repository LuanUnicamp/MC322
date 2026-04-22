import java.util.Scanner;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe responsável por gerenciar a progressão do Modo História (Shinobi Legacy: Saga Naruto Shippuden).
 * <br>
 * Utiliza uma estrutura de Árvore (através da classe {@link DefaultMutableTreeNode}) para 
 * mapear as fases, opções de treinamentos e bifurcações narrativas do jogador, além de 
 * controlar os itens consumíveis e moedas ganhas nas campanhas.
 */
public class Historia {

    private DefaultMutableTreeNode arvoreHistoria;
    private DefaultMutableTreeNode noAtual;

    //heroi especifico para o modo historia
    private Heroi naruto;
    private ArrayList<Carta> deckNaruto;

    //itens do jogo
    private int shinobiCoins;
    private int vidaMaxima;
    private int qtdBandagem;
    private int qtdShuriken;
    private int qtdBandana;

    //0 = sem transformacao, 1=modo senin, 2=modo kurama
    private int transformação = 0;

    /**
     * Construtor da classe Historia.
     * <br>
     * <b>Comportamento:</b> Dispara a construção da árvore narrativa, inicializa o nó inicial, 
     * cria o perfil do Naruto (Genin) para o começo da jornada, configura seu Deck (baralho) 
     * básico inicial e zera/configura os inventários de moedas, curas e armas.
     */
    public Historia() {
        construirArvore();
        //no atual comeca no primeiro filho da raiz(arvore historia)
        this.noAtual = (DefaultMutableTreeNode) arvoreHistoria.getChildAt(0);

        //cria personagem e deck específico para o modo história e eles vão evoluindo com o progresso do usuário
        this.naruto = new Heroi("Naruto (Genin)", 50, 0);
        this.deckNaruto = new ArrayList<>();

        //para controlar o funcionamento dos itens
        this.vidaMaxima = 50; 
        this.shinobiCoins = 0;
        this.qtdBandagem = 0;
        this.qtdShuriken = 0;
        this.qtdBandana = 0;

        //habilidades básicas do personagem ao iniciar
        this.deckNaruto.add(new CartaEscudo("Jutsu Clone das Sombras", "Naruto gera muitos clones para se proteger do inimigo", 2, 10));
        this.deckNaruto.add(new CartaDano("Rasengan", "Principal jutsu de ataque de Naruto", 3, 15));
        this.deckNaruto.add(new CartaDano("Taijutsu", "Ataque físico ao inimigo", 1, 7));
        this.deckNaruto.add(new CartaEscudo("Jutsu de Substituição", "Ao ser atacado se substitui por um tronco para se defender", 1, 5));

    }

    /**
     * Constrói e vincula os nós da árvore de história.
     * <br>
     * <b>Comportamento:</b> Define o nó raiz e cria os nós filhos correspondentes às fases 
     * (ex: O Sequestro de Gaara, Invasão de Pain) e às diferentes escolhas narrativas 
     * (como treinar com Jiraiya ou com Kakashi).
     */

    private void construirArvore(){
        //nó raiz
        this.arvoreHistoria = new DefaultMutableTreeNode("Shinobi Legacy: Saga Naruto Shippuden");

        //inicio - fase 1
        DefaultMutableTreeNode fase1 = new DefaultMutableTreeNode("Fase 1: O Sequestro de Gaara");
        arvoreHistoria.add(fase1);

        //Para aprender novas habilidades, naruto passa por treinamentos, ele escolhe qual treinamento quiser
        DefaultMutableTreeNode treinoJiraiya = new DefaultMutableTreeNode("Treinamento com Jiraiya");
        DefaultMutableTreeNode treinoKakashi = new DefaultMutableTreeNode("Treinamento com Kakashi");
        //2 nós filhos do nó fase 1
        fase1.add(treinoJiraiya);
        fase1.add(treinoKakashi);

        //os 2 nós levam a um evento comum "fase 2", que na verdade são nós independentes, um é filho de
        //treinoJiraya e o outro de treinoKakashi, mas causa a impressão de que os nós se juntam em uma fase comum
        DefaultMutableTreeNode fase2PainCaminhoJ = new DefaultMutableTreeNode("Fase 2: A Invasão de Pain");
        DefaultMutableTreeNode fase2PainCaminhoK = new DefaultMutableTreeNode("Fase 2: A Invasão de Pain");
        treinoJiraiya.add(fase2PainCaminhoJ);
        treinoKakashi.add(fase2PainCaminhoK);
    }

    /**
     * Imprime um trecho do diálogo da história no console e pausa a execução até 
     * que o usuário decida prosseguir.
     * * @param texto   A mensagem, contexto ou fala do personagem a ser exibida.
     * @param entrada Objeto {@link Scanner} utilizado para capturar a tecla [ENTER] do usuário.
     */

    private void imprimirFala(String texto, Scanner entrada) {
        System.out.println(texto);
        entrada.nextLine(); 
    }

    /**
     * Exibe visualmente o "Pergaminho das Missões", servindo como um mapa da campanha.
     * <br>
     * <b>Comportamento:</b> Baseado no caminho percorrido desde a raiz até o nó atual do jogador, 
     * desenha caminhos (branches) completados, caminhos abandonados e indica a posição atual com a 
     * tag.
     */
    public void mostrarMapa() {
        System.out.println("\n" + App.AMARELO + "            📜 PERGAMINHO DAS MISSÕES 📜" + App.RESET);
        System.out.println(App.CIANO + "╔══════════════════════════════════════════════════╗" + App.RESET);

        //pega do inicio ate onde o jogador esta
        javax.swing.tree.TreeNode[] caminho = noAtual.getPath();
        
        String espacamento = "  "; 
        //raiz (titulo)
        DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) caminho[0];
        System.out.println(App.CIANO + "║" + App.RESET + espacamento + "𖣘 " + App.NEGRITO + raiz.getUserObject() + App.RESET);

        //percorre degraus da historia
        for (int i = 1; i < caminho.length; i++) {
            DefaultMutableTreeNode pai = (DefaultMutableTreeNode) caminho[i - 1];
            DefaultMutableTreeNode noCaminho = (DefaultMutableTreeNode) caminho[i];

            System.out.println(App.CIANO + "║" + App.RESET + espacamento + " │");

            int numFilhos = pai.getChildCount();
            
            //varre as opcoes
            for (int j = 0; j < numFilhos; j++) {
                DefaultMutableTreeNode irmao = (DefaultMutableTreeNode) pai.getChildAt(j);
                String nomeIrmao = (String) irmao.getUserObject();

                String prefixoGalho = (j == numFilhos - 1) ? " └── " : " ├── ";

                //verifica se e a opcao que o jogador esdcolheu
                if (irmao == noCaminho) {
                    if (i == caminho.length - 1) { 
                        //onde o jogador esta
                        System.out.println(App.CIANO + "║" + App.RESET + espacamento + prefixoGalho + "📍 " + App.NEGRITO + nomeIrmao + App.VERMELHO + " ◄ [VOCÊ]" + App.RESET);
                    } else { 
                        //fase que ele ja passou
                        System.out.println(App.CIANO + "║" + App.RESET + espacamento + prefixoGalho + "✅ " + nomeIrmao);
                    }
                } 
                else {
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
     * Questiona o jogador via console se ele deseja pausar o progresso na missão 
     * para visitar o Mercado Shinobi.
     * * @param entrada Objeto {@link Scanner} para ler a opção do usuário. 
     * Se a escolha for 1, o método invoca {@link #abrirLoja(Scanner)}.
     */
    private void perguntarLoja(Scanner entrada) {
        System.out.println("\n" + App.CIANO + "╔══════════════════════════════════════════════════╗" + App.RESET);
        System.out.println(App.CIANO + "║" + App.RESET + " Deseja visitar o " + App.AMARELO + "Mercado Shinobi" + App.RESET + " antes de seguir? " + App.CIANO + "║" + App.RESET);
        System.out.println(App.CIANO + "╚══════════════════════════════════════════════════╝" + App.RESET);
        System.out.println("[1] Sim, gastar Shinobi Coins");
        System.out.println("[2] Não, focar na missão");
        System.out.print("Escolha: ");
        int esc = entrada.nextInt();
        entrada.nextLine(); 
        
        if(esc == 1) {
            abrirLoja(entrada);
        }
    }

    /**
     * Renderiza o menu do Mercado Shinobi, onde o jogador pode trocar suas "Shinobi Coins" 
     * por itens consumíveis (Bandagens, Shurikens ou Bandanas).
     * * @param entrada Objeto {@link Scanner} para capturar as interações de compra.
     */
    private void abrirLoja(Scanner entrada) {
        boolean contComprar = true;
        while(contComprar) {
            App.limparTela();
            System.out.println(App.AMARELO + "╔════════════════════════════════════════════════════════╗" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + App.NEGRITO + "                   🏪  MERCADO SHINOBI                   " + App.RESET + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "╠════════════════════════════════════════════════════════╣" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " 💰 Moedas: " + App.VERDE + shinobiCoins + " Shinobi Coins" + App.RESET + "                              " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "╠════════════════════════════════════════════════════════╣" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " [1] 🩹 Bandagem (Cura 20 HP)               - 40 Coins " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " [2] 🗡️ Shuriken (Tira 10 HP do Inimigo)    - 50 Coins " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " [3] 🥷 Bandana (+5 de Escudo Inicial)      - 30 Coins " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "║" + App.RESET + " [4] 🚪 Sair da Loja                                   " + App.AMARELO + "║" + App.RESET);
            System.out.println(App.AMARELO + "╚════════════════════════════════════════════════════════╝" + App.RESET);
            System.out.print("Sua escolha: ");
            int opcao = entrada.nextInt();
            entrada.nextLine();

            if (opcao == 1 && shinobiCoins >= 40) { shinobiCoins -= 40; qtdBandagem++; System.out.println(App.VERDE + ">>> Bandagem guardada na mochila!" + App.RESET); }
            else if (opcao == 2 && shinobiCoins >= 50) { shinobiCoins -= 50; qtdShuriken++; System.out.println(App.VERDE + ">>> Shuriken guardada na mochila!" + App.RESET); }
            else if (opcao == 3 && shinobiCoins >= 30) { shinobiCoins -= 30; qtdBandana++; System.out.println(App.VERDE + ">>> Bandana guardada na mochila!" + App.RESET); }
            else if (opcao == 4) { contComprar = false; }
            else { System.out.println(App.VERMELHO + ">>> Moedas insuficientes ou opção inválida!" + App.RESET); }

            if(contComprar) { System.out.println("[Pressione ENTER para continuar comprando]"); entrada.nextLine(); }
        }
    }

    /**
     * Inicia a sequência da "Sala de Preparação" imediatamente antes de uma batalha iniciar.
     * <br>
     * <b>Comportamento:</b> Permite ao usuário visualizar seus recursos (Vida / Escudo) e a Vida 
     * do inimigo. O usuário pode abrir a mochila para consumir itens de cura e proteção, ou usar 
     * itens de ataque surpresa (Shuriken) no oponente antes mesmo do embate começar em turnos.
     * * @param entrada         Objeto {@link Scanner} para navegar nos menus da mochila e iniciar a luta.
     * @param inimigoOriginal A instância do inimigo que será enfrentado.
     * @return O objeto {@link Inimigo} possivelmente modificado (caso tenha sofrido dano da Shuriken).
     */
    private Inimigo salaDePreparacao(Scanner entrada, Inimigo inimigoOriginal) {
        boolean vaiLutar = false;
        int escudoAcumulado = 0;
        int limiteBandana = 2; //limita a uma bandana
        boolean usouShuriken = false; //limita a 1 shuriken
        
        String nomeInimigo = inimigoOriginal.getNome();
        int vidaInimigo = inimigoOriginal.getVida(); 
        
        while(!vaiLutar) {
            App.limparTela();
            System.out.println(App.VERMELHO + "╔════════════════════════════════════════════════════════╗" + App.RESET);
            System.out.println(App.VERMELHO + "║" + App.RESET + App.NEGRITO + "                  ⚔️  SALA DE PREPARAÇÃO                " + App.RESET + App.VERMELHO + "║" + App.RESET);
            System.out.println(App.VERMELHO + "╚════════════════════════════════════════════════════════╝" + App.RESET);
            System.out.println("  Inimigo: " + App.VERMELHO + nomeInimigo + App.RESET + " | HP: " + vidaInimigo);
            System.out.println("  Naruto : " + App.AMARELO + "HP " + naruto.getVida() + "/" + vidaMaxima + App.RESET + " | Escudo Inicial: " + escudoAcumulado);
            System.out.println("\n[1] 🎒 Abrir Mochila");
            System.out.println("[2] ⚔️ Iniciar Luta");
            System.out.print("Sua escolha: ");
            
            int op = entrada.nextInt();
            entrada.nextLine();

            if (op == 1) {
                boolean naMochila = true;
                while(naMochila) {
                    App.limparTela();
                    System.out.println(App.AMARELO + "========== 🎒 SUA MOCHILA ==========" + App.RESET);
                    System.out.println("[1] 🩹 Bandagem (" + qtdBandagem + "x) - Cura 20 HP");
                    System.out.println("[2] 🗡️ Shuriken (" + qtdShuriken + "x) - Tira 10 HP do Inimigo agora!");
                    System.out.println("[3] 🥷 Bandana  (" + qtdBandana + "x) - +5 Escudo (Máx por luta: 2)");
                    System.out.println("[4] 🔙 Fechar Mochila");
                    System.out.print("Escolha um item para usar: ");
                    int item = entrada.nextInt();
                    entrada.nextLine();
                    
                    if (item == 1) {
                        if (qtdBandagem > 0 && naruto.getVida() < vidaMaxima) {
                            qtdBandagem--;
                            int novaVida = Math.min(naruto.getVida() + 20, vidaMaxima);
                            naruto = new Heroi(naruto.getNome(), novaVida, naruto.getEscudo());
                            System.out.println(App.VERDE + ">>> Bandagem usada! HP recuperado." + App.RESET);
                        } else { System.out.println(App.VERMELHO + ">>> Sem bandagens ou HP já está cheio!" + App.RESET); }
                    } 
                    else if (item == 2) {
                        if (qtdShuriken > 0 && !usouShuriken) {
                            qtdShuriken--;
                            usouShuriken = true;
                            vidaInimigo -= 10;
                            System.out.println(App.VERDE + ">>> Ataque Surpresa! " + nomeInimigo + " perdeu 10 HP antes da luta!" + App.RESET);
                        } else { System.out.println(App.VERMELHO + ">>> Sem shurikens ou já arremessou nesta batalha!" + App.RESET); }
                    }
                    else if (item == 3) {
                        if (qtdBandana > 0 && limiteBandana > 0) {
                            qtdBandana--;
                            limiteBandana--;
                            escudoAcumulado += 5;
                            System.out.println(App.VERDE + ">>> Bandana equipada! Escudo inicial +5." + App.RESET);
                        } else { System.out.println(App.VERMELHO + ">>> Sem bandanas ou limite de 2 atingido!" + App.RESET); }
                    }
                    else if (item == 4) { naMochila = false; }
                    
                    if(naMochila) { System.out.println("[Pressione ENTER]"); entrada.nextLine(); }
                }
            } else if (op == 2) {
                vaiLutar = true;
            }
        }
        
        //aplica o escudo pronaruto
        this.naruto = new Heroi(this.naruto.getNome(), this.naruto.getVida(), escudoAcumulado);
        
        //atualiza a vi dado inimigo se usou shuriken
        return new Inimigo(nomeInimigo, vidaInimigo, 0);
    }

    //rodar historia
    public void iniciar(Scanner entrada){
        System.out.println("\n" + App.CIANO + "╔" + "═".repeat(60) + "╗" + App.RESET);
        System.out.println(App.CIANO + "║" + App.RESET + App.NEGRITO + App.AMARELO + "             📜 MODO HISTÓRIA: SAGA SHIPPUDEN               " + App.RESET + App.CIANO + "║" + App.RESET);
        System.out.println(App.CIANO + "╚" + "═".repeat(60) + "╝" + App.RESET);

        boolean acabou = false;

        entrada.nextLine();

        while(!acabou){

            mostrarMapa();
            System.out.println("\n[Pressione ENTER para iniciar a fase]");
            entrada.nextLine();

            App.limparTela();

            String nomeFase = (String) noAtual.getUserObject();

            //titulo da fase
            System.out.println("\n" + App.CIANO + "╔" + "═".repeat(60) + "╗" + App.RESET);
            System.out.println(App.CIANO + "║" + App.RESET + App.NEGRITO + App.AMARELO + "               ⚔️  " + nomeFase.toUpperCase() + "              " + App.RESET + App.CIANO + "║" + App.RESET);
            System.out.println(App.CIANO + "╚" + "═".repeat(60) + "╝" + App.RESET + "\n");

            //fase 1
            if(nomeFase.equals("Fase 1: O Sequestro de Gaara")){
                System.out.println("\n" + App.AMARELO + "[Pressione ENTER para iniciar a introdução]" + App.RESET);
                entrada.nextLine();
                
                imprimirFala(App.CIANO + "📖 O vento uiva pelos desfiladeiros da Vila Oculta da Areia..." + App.RESET, entrada);
                imprimirFala(App.CIANO + "📖 O céu noturno é subitamente iluminado por clarões ensurdecedores." + App.RESET, entrada);
                imprimirFala(App.CIANO + "📖 Kankuro cai de joelhos na areia. Suas marionetes estão em pedaços." + App.RESET, entrada);
                
                System.out.println();

                imprimirFala(App.VERMELHO + "Deidara" + App.RESET + " (sorrindo): 'A arte é uma explosão! E o seu Kazekage é a minha obra-prima de hoje.'", entrada);
                imprimirFala(App.ROXO + "Sasori" + App.RESET + " (impaciente): 'Não me faça esperar, Deidara. Odeio esperar e odeio fazer os outros esperarem.'", entrada);
                
                imprimirFala(App.CIANO + "\n📖 Os dois membros da Akatsuki desaparecem na escuridão, levando Gaara..." + App.RESET, entrada);
                
                System.out.println(App.CIANO + "📖 [ Alguns dias depois, nos portões de Konoha... ]\n" + App.RESET);
                entrada.nextLine();

                imprimirFala(App.VERDE + "Kakashi" + App.RESET + ": 'A situação é crítica. A Akatsuki não age sem um plano impecável.'", entrada);
                imprimirFala(App.VERDE + "Kakashi" + App.RESET + ": 'Precisamos ser táticos e calcular cada movimento.'", entrada);
            

                imprimirFala(App.AMARELO + "Naruto" + App.RESET + " (cerrando os punhos): 'Eu não dou a mínima para os planos deles, Kakashi-sensei!'", entrada);
                imprimirFala(App.AMARELO + "Naruto" + App.RESET + ": 'O Gaara sofreu a vida toda com a mesma dor que eu... Eu vou trazê-lo de volta!'", entrada);

                imprimirFala(App.VERDE + "Kakashi" + App.RESET + ": 'Vou atrás de Sasori, Naruto. Cuide do Deidara!'", entrada);

                System.out.println("\n" + App.VERMELHO + "🔥 PREPARE-SE: A perseguição começou! 🔥" + App.RESET);
                
                
                System.out.println("\n" + App.AMARELO + "⚔️  INICIANDO BATALHA: NARUTO vs DEIDARA ⚔️" + App.RESET);
                System.out.println("[Pressione ENTER para lutar]");
                entrada.nextLine();

                //preparação paraa a batalha naruto x deidara
                Inimigo deidara = new Inimigo("Deidara", 60, 0);
                ArrayList<Carta> deckDeidara = App.GeraDeckInimigo(deidara);

                ArrayList<Carta> deckParaLuta = new ArrayList<>(this.deckNaruto);
                Collections.shuffle(deckParaLuta);
                Collections.shuffle(deckDeidara);
                
                
                ArrayList<Carta> mao = new ArrayList<>();
                ArrayList<Carta> descarte = new ArrayList<>();

                Combate arena = new Combate();
                String resultado = arena.rodarCombate(this.naruto, deidara, deckDeidara, deckParaLuta, mao, descarte);
                
                System.out.println("\n" + resultado);

                if (this.naruto.getVida() <= 0) {
                    System.out.println("\n💀 GAME OVER: A Akatsuki escapou com o Kazekage...");
                    System.out.println("[Pressione ENTER para voltar ao menu]");
                    entrada.nextLine();
                    acabou = true;
                    continue;
                } 
                else {
                    System.out.println("\n" + App.VERDE + "🌟 Deidara é derrotado, e Naruto consegue resgatar Gaara 🌟" + App.RESET);
                    int recompensa = 50;
                    shinobiCoins += recompensa;
                    System.out.println(App.VERDE + "\n🪙  Parabéns, você ganhou " + recompensa + " shinobi coins 🪙" + App.RESET);

                    System.out.println("\n[Pressione ENTER para continuar a história]");
                    entrada.nextLine();
                    App.limparTela();

                    imprimirFala(App.CIANO + "📖 Naruto sabe que as próximas batalhas serão contra a elite da Akatsuki." + App.RESET, entrada);
                    imprimirFala(App.CIANO + "📖 Para se tornar mais forte, ele deve escolher um mestre para um treino intensivo." + App.RESET, entrada);
                    
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

                    this.noAtual = (DefaultMutableTreeNode) noAtual.getChildAt(escolha - 1);
                    
                    if (escolha == 1){
                        imprimirFala("\n" + App.ROXO + "Jiraiya" + App.RESET + ": 'Prepare o seu espírito, Naruto! Vamos ao Monte Myoboku!'", entrada);
                        App.limparTela();
                    } else {
                        imprimirFala("\n" + App.VERDE + "Kakashi" + App.RESET + ": 'Espero que esteja pronto para um treino de elite. Vamos nessa!'", entrada);
                        App.limparTela();
                    }
                    continue;
                }
            }
            //se ele escolher treinar com o jiraya
            else if (nomeFase.equals("Treinamento com Jiraiya")){
                imprimirFala(App.CIANO + "📖 Naruto e Jiraiya viajam para o Monte Myoboku, a terra dos sapos..." + App.RESET, entrada);
                System.out.println();
                imprimirFala(App.ROXO + "Jiraiya" + App.RESET + ": 'Para dominar a invocação, você precisa extrair o chakra da Raposa!'" , entrada);
                imprimirFala(App.ROXO + "Jiraiya" + App.RESET + " (invocando um sapo gigante): 'Tente sobreviver aos meus ataques!'" , entrada);
                imprimirFala(App.AMARELO + "Naruto" + App.RESET + ": 'Eu não vou recuar! Vou dominar esse jutsu agora mesmo!'" , entrada);
                
                System.out.println("\n" + App.AMARELO + "⚔️  INICIANDO BATALHA: NARUTO vs JIRAYA ⚔️" + App.RESET);
                System.out.println("[Pressione ENTER para lutar]");
                entrada.nextLine();

                Inimigo jiraiya = new Inimigo("Jiraiya", 70, 0);
                ArrayList<Carta> deckJiraiya = App.GeraDeckInimigo(jiraiya); 

                ArrayList<Carta> deckParaLuta = new ArrayList<>(this.deckNaruto);
                Collections.shuffle(deckParaLuta);
                
                Combate arena = new Combate();
                String resultado = arena.rodarCombate(this.naruto, jiraiya, deckJiraiya, deckParaLuta, new ArrayList<>(), new ArrayList<>());
                System.out.println("\n" + resultado);

                if (this.naruto.getVida() <= 0) {
                    System.out.println("\n💀 Jiraiya: 'Ainda falta muito, garoto...' GAME OVER.");
                    entrada.nextLine();
                    acabou = true;
                    continue;
                } else {
                    System.out.println(App.VERDE + "\n🌟 TREINO CONCLUÍDO! Jiraiya aprova sua força. 🌟" + App.RESET);
                    entrada.nextLine();

                    int recompensa = 70;
                    shinobiCoins += recompensa;
                    System.out.println(App.VERDE + "🪙  Parabéns, você ganhou " + recompensa + " shinobi coins 🪙");
                    entrada.nextLine();

                    int vidaAtual = this.naruto.getVida();
                    this.naruto = new Heroi("Naruto (Sábio)", vidaAtual, 0);
                    vidaMaxima = 80;
                    this.deckNaruto.add(new CartaDanoArea("Jutsu de Invocação: Gamabunta", "Convoca o Chefe dos Sapos!", 3, 25, 4, 75));
                    this.transformação = 1; //Desbloqueia modo sabio

                    for (int i = 0; i < this.deckNaruto.size(); i++) {
                        Carta cartaAtual = this.deckNaruto.get(i);
                        
                        //rasengan deixa de custar 3 para custar 2
                        if (cartaAtual.getNome().equals("Rasengan")) {
                            this.deckNaruto.set(i, new CartaDano("Rasengan", "Principal jutsu de ataque de Naruto", 2, 15));
                        } 
                        //clone das sombras deixa de custar 2 para custar 1 
                        else if (cartaAtual.getNome().equals("Jutsu Clone das Sombras")) {
                            this.deckNaruto.set(i, new CartaEscudo("Jutsu Clone das Sombras", "Naruto gera muitos clones para se proteger do inimigo", 1, 10));
                        }
                        //remove jutsu de substituicao do baralho
                        else if (cartaAtual.getNome().equals("Jutsu de Substituição")) {
                            this.deckNaruto.remove(i);
                            i--; 
                        }
                    }

                    System.out.println(App.VERDE + ">>> Transformação desbloqueada: MODO SÁBIO 🌀" + App.RESET);
                    System.out.println(App.VERDE + ">>> Você aprendeu: Jutsu de Invocação: Gamabunta! 🐸" + App.RESET);
                    System.out.println(App.VERDE + ">>> Sua vida máxima aumentou para 80!" + App.RESET);

                    imprimirFala("\n[Pressione ENTER para continuar]", entrada);

                    imprimirFala(App.CIANO + "📖 Após o treinamento, Naruto decide retornar à Aldeia da Folha passando pela Aldeia da Névoa" + App.RESET, entrada);
                    imprimirFala(App.CIANO + "📖 Lá ele se depara com uma loja, onde guerreiros ninja costumam frequentar entre as missões." + App.RESET, entrada);

                    perguntarLoja(entrada);
                    App.limparTela();
                }
            }
            //se ele escolher treinar com o kakashi
            else if(nomeFase.equals("Treinamento com Kakashi")){
                imprimirFala(App.VERDE + "Kakashi:" + App.RESET + " 'Vamos focar na sua natureza de chakra: o Vento.'", entrada);
                imprimirFala(App.VERDE + "Kakashi:" + App.RESET + " 'Vamos intensificar o poder do seu rasengan!'", entrada);
                
                System.out.println("\n" + App.AMARELO + "⚔️  INICIANDO BATALHA: NARUTO vs KAKASHI ⚔️" + App.RESET);
                System.out.println("[Pressione ENTER para lutar]");
                entrada.nextLine();

                Inimigo kakashi = new Inimigo("Kakashi", 70, 0);
                ArrayList<Carta> deckKakashi = App.GeraDeckInimigo(kakashi); 

                ArrayList<Carta> deckParaLuta = new ArrayList<>(this.deckNaruto);
                Collections.shuffle(deckParaLuta);
                
                Combate arena = new Combate();
                String resultado = arena.rodarCombate(this.naruto, kakashi, deckKakashi, deckParaLuta, new ArrayList<>(), new ArrayList<>());
                System.out.println("\n" + resultado);

                if (this.naruto.getVida() <= 0) {
                    System.out.println("\n💀 Kakashi: 'Você perdeu a concentração...' GAME OVER.");
                    entrada.nextLine();
                    acabou = true;
                    continue;
                } else {
                    System.out.println("\n🌟 TREINO CONCLUÍDO! Você cortou a cachoeira ao meio! 🌟");
                    
                    int recompensa = 80;
                    shinobiCoins += recompensa;
                    System.out.println(App.VERDE + "\n🪙 Parabéns, você ganhou " + recompensa + " shinobi coins 🪙");
                    entrada.nextLine();

                    int vidaAtual = this.naruto.getVida();
                    this.naruto = new Heroi("Naruto (Manto da Kyuubi)", vidaAtual, 0);
                    vidaMaxima = 80;
                    this.deckNaruto.add(new CartaDano("Rasenshuriken", "Lâminas de vento destrutivas", 3, 35));
                    this.transformação = 2; //Desbloqueia modo kurama incompleto

                    for (int i = 0; i < this.deckNaruto.size(); i++) {
                        Carta cartaAtual = this.deckNaruto.get(i);
                        
                        //rasengan deixa de custar 3 para custar 2
                        if (cartaAtual.getNome().equals("Rasengan")) {
                            this.deckNaruto.set(i, new CartaDano("Rasengan", "Principal jutsu de ataque de Naruto", 2, 15));
                        } 
                        //clone das sombras deixa de custar 2 para custar 1 
                        else if (cartaAtual.getNome().equals("Jutsu Clone das Sombras")) {
                            this.deckNaruto.set(i, new CartaEscudo("Jutsu Clone das Sombras", "Naruto gera muitos clones para se proteger do inimigo", 1, 10));
                        }
                        //remove jutsu de substituicao do baralho
                        else if (cartaAtual.getNome().equals("Jutsu de Substituição")) {
                            this.deckNaruto.remove(i);
                            i--; 
                        }
                    }
                    
                    System.out.println(App.VERDE + ">>> Transformação desbloqueada: MANTO DA KYUUBI 🔥" + App.RESET);
                    System.out.println(App.VERDE + ">>> Você aprendeu: Rasenshuriken! 💨" + App.RESET);
                    System.out.println(App.VERDE + ">>> Sua vida máxima aumentou para 80!" + App.RESET);

                    imprimirFala("\n[Pressione ENTER para continuar]", entrada);

                    imprimirFala(App.CIANO + "📖 Após o treinamento, Naruto decide retornar à Aldeia da Folha passando pela Aldeia da Névoa" + App.RESET, entrada);
                    imprimirFala(App.CIANO + "📖 Lá ele se depara com uma loja, onde guerreiros ninja costumam frequentar entre as missões." + App.RESET, entrada);

                    perguntarLoja(entrada);
                    App.limparTela();
                }
            }

            //fase 2
            else if(nomeFase.equals("Fase 2: A Invasão de Pain")){
                System.out.println("\n[Pressione ENTER para avançar]");
                entrada.nextLine();

                imprimirFala(App.CIANO + "\n📖 De volta à Vila da Folha, o cenário é de destruição total...", entrada);
                imprimirFala(App.CIANO + "📖 O silêncio de Konoha é ensurdecedor..." + App.RESET, entrada);
                imprimirFala(App.CIANO + "📖 Uma cratera gigante substituiu o centro da Aldeia." + App.RESET, entrada);

                imprimirFala(App.ROXO + "\nPain" + App.RESET + " (flutuando nos céus): 'O mundo deve conhecer a dor.'", entrada);
                
                imprimirFala(App.CIANO + "\n📖 Uma nuvem de fumaça explode no centro da cratera..." + App.RESET, entrada);
                imprimirFala(App.CIANO + "📖 Naruto surge em meio ao caos para salvar Konoha." + App.RESET, entrada);

                imprimirFala(App.AMARELO + "\nNaruto" + App.RESET + ": 'Eu vou acabar com você... e trazer a paz do meu jeito!'", entrada);
                
                System.out.println("\n" + App.VERMELHO + "╔" + "═".repeat(60) + "╗" + App.RESET);
                System.out.println(App.VERMELHO + "║" + App.RESET + App.NEGRITO + "               ☠️  BATALHA FINAL: A DOR PARA TRAZER A CURA           " + App.VERMELHO + "║" + App.RESET);
                System.out.println(App.VERMELHO + "╚" + "═".repeat(60) + "╝" + App.RESET);

                System.out.println("\n" + App.ROXO + "⚔️  COMBATE FINAL: NARUTO vs PAIN" + App.RESET);
                
                Inimigo pain = new Inimigo("Pain", 120, 0); 
                pain = salaDePreparacao(entrada, pain);

                ArrayList<Carta> deckPain = App.GeraDeckInimigo(pain); 

                ArrayList<Carta> deckParaLuta = new ArrayList<>(this.deckNaruto);
                Collections.shuffle(deckParaLuta);
                
                Combate arena = new Combate();

                arena.habilitarEvolucao(this.transformação);

                String resultado = arena.rodarCombate(this.naruto, pain, deckPain, deckParaLuta, new ArrayList<>(), new ArrayList<>());
                System.out.println("\n" + resultado);

                if (this.naruto.getVida() <= 0) {
                    System.out.println("\n💀 GAME OVER: A Vila da Folha foi aniquilada...");
                    System.out.println("[Pressione ENTER para voltar ao menu]");
                    entrada.nextLine();
                    acabou = true;
                    continue;
                } else {
                    System.out.println("\n🌟 VITÓRIA ÉPICA! A PAZ FOI RESTAURADA! 🌟");
                    imprimirFala("O corpo de Pain cai no chão. A ameaça da Akatsuki foi parada.", entrada);
                    imprimirFala("Os moradores de Konoha comemoram. Naruto finalmente é reconhecido como o Herói da Vila!", entrada);
                    
                    System.out.println("\n" + App.AMARELO + "=== FIM DO MODO HISTÓRIA ===" + App.RESET);
                    System.out.println("Parabéns por zerar a demonstração do Shinobi Legacy!");
                    imprimirFala("[Pressione ENTER para retornar ao menu principal]", entrada);
                    
                    
                    acabou = true; 
                    continue;
                }
            }

            int qtdFilhos = noAtual.getChildCount();
            
            //se o nó atual não tem mais filhos, acabou a historia construida ate agora
            if (qtdFilhos == 0) {
                System.out.println("\n[ Fim do Conteúdo Atual da História. Pressione ENTER para sair ]");
                entrada.nextLine();
                acabou = true; 
            } 
            //Se tiver só 1 filho (caminho linear no futuro, ex: numa prox fase 3)
            else if (qtdFilhos == 1) {
                noAtual = (DefaultMutableTreeNode) noAtual.getChildAt(0); 
            }
            
            
        }
    }
}
