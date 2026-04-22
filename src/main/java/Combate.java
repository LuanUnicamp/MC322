import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Motor principal do jogo que gerencia o fluxo de turnos, aplicação de efeitos
 * e a lógica de combate entre o herói e o inimigo.
 */
public class Combate {
    public static final String RESET = "\u001B[0m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String CIANO = "\u001B[36m";
    public static final String ROXO = "\u001B[35m";
    public static final String NEGRITO = "\033[1m";
    
    /** Lista que armazena os efeitos ativos (Veneno, Regen) que devem ser processados no combate. */
    private ArrayList<Efeito> listaEfeitosCombate;

    private int barraEvolucao = 0;
    private boolean modoSeninAtivo = false;
    private boolean modoKuramaAtivo = false;
    private int turno = 0;
    private int regen = 0;
    private int transformacao = 0;

    /**
     * Construtor da classe Combate. <br>
     * <b>Comportamento</b>: Inicializa a lista de efeitos vazia para o início da partida.
     */
    public Combate(){
        listaEfeitosCombate= new ArrayList<>();
    }

    /**
     * Registra um novo efeito na lista de monitoramento do combate.
     * @param efeito O efeito a ser inscrito. <br>
     * <b>Comportamento</b>: Verifica se o efeito já existe na lista para evitar duplicatas antes de adicioná-lo.
     */
    public void inscreverEfeito(Efeito efeito){
        for(int i=0;i<listaEfeitosCombate.size();i++){
            Efeito efeitolista =listaEfeitosCombate.get(i);
            if(efeito.getNome().equals(efeitolista.getNome())){       
                return;
            }
        }
        listaEfeitosCombate.add(efeito);
    } 

    public void habilitarEvolucao(int tipo) {
        this.transformacao = tipo;
    }

    /**
     * Notifica os efeitos inscritos sobre a passagem do tempo no jogo.
     * @param num O código do momento do turno (0 para início, 1 para fim). <br>
     * <b>Comportamento</b>: Remove efeitos cujas durações expiraram e aciona a lógica interna dos efeitos ativos.
     */
    private void avisar(int num){
        for(int i=listaEfeitosCombate.size()-1;i>=0;i--){
            Efeito efeito =listaEfeitosCombate.get(i);
            
            if(efeito.acabou()){
                listaEfeitosCombate.remove(i);
                efeito.getEntidade().removerEfeito(efeito);
            }else{
                efeito.avisado(num);
            }

        }
       
    }

    /**
     * Gerencia o loop principal do duelo até que um dos lutadores seja derrotado.
     * @param heroiEscolhido O personagem controlado pelo jogador.
     * @param inimigoEscolhido O oponente controlado pelo sistema.
     * @param movimentosInimigo Lista de cartas disponíveis para o inimigo.
     * @param pilhaCompra Cartas ainda disponíveis para sorteio.
     * @param maoJogador Cartas atualmente na mão do jogador.
     * @param pilhaDescarte Cartas já utilizadas ou descartadas.
     * @return Uma string formatada indicando o vencedor e o desfecho da batalha. <br>
     * <b>Comportamento</b>: Controla a compra de cartas, o gasto de chakra, a interação com menus, 
     * a execução das jogadas de ambos os lados e o processamento de efeitos por turno.
     */
    public String rodarCombate(Heroi heroiEscolhido, Inimigo inimigoEscolhido, ArrayList<Carta> movimentosInimigo, ArrayList<Carta> pilhaCompra, ArrayList<Carta> maoJogador, ArrayList<Carta> pilhaDescarte ){
        int leitura;
        Scanner entrada = new Scanner(System.in);

        //o jogo acaba quando um dos dois oponentes tem estavivo() retornando false
        while(heroiEscolhido.estaVivo() && inimigoEscolhido.estaVivo()){
            this.turno++;
            String msgRegen = "";
            //publisher avisando inicio do turno
            avisar(0);

            //logica para caso a transformacao do modo historia estiver desbloqueada
            if(transformacao > 0){
                if(!modoSeninAtivo && !modoKuramaAtivo){
                    if(turno > 0 && turno < 5){
                        barraEvolucao+=25;
                        System.out.println(App.AMARELO + "⚡ Barra de Evolução: " + barraEvolucao + "%" + App.RESET);
                        if (barraEvolucao == 100) {
                            System.out.print(App.AMARELO + "Digite 'x' para liberar o poder: " + App.RESET);
                            String comando = entrada.next();
                            
                            if (comando.equals("x")) {
                                if (transformacao == 1){
                                    System.out.println(App.VERDE + "🌀 MODO SÁBIO ATIVADO!" + App.RESET);
                                    modoSeninAtivo = true;
                                } else if (transformacao == 2){
                                    System.out.println(App.VERMELHO + "🔥 MANTO DA KYUUBI LIBERADO!" + App.RESET);
                                    modoKuramaAtivo = true;
                                }
                            }
                        }
                    } else{
                        System.out.println(App.AMARELO + "⚡ Barra de Evolução: " + barraEvolucao + "%" + App.RESET);
                    }
                } else {
                    if (modoSeninAtivo) {
                        regen = 10;
                    } else {
                        regen = 5;
                    }
                    if (heroiEscolhido.getVida()<heroiEscolhido.getVidaMax()) {
                        heroiEscolhido.receberCura(regen);
                        if (heroiEscolhido.getVida()>heroiEscolhido.getVidaMax()) {
                            heroiEscolhido.setVida(heroiEscolhido.getVidaMax());
                        }
                        msgRegen = (App.VERDE + "✨ " + (modoSeninAtivo ? "Modo Senin" : "Manto da Kyuubi") + 
                           ": Regenerando +" + regen + " HP (" + 
                           heroiEscolhido.getVidaMax() + "/" + heroiEscolhido.getVidaMax() + ")" + App.RESET);
                    }
                }
            }
    
            int chakra = 4;
            //variável que indica quando o heroi não tem chackra suficiente para usar habilidade
            Boolean insuficiente = false;


            for(int k = 0; k < 3; k++){
                //se a pilha de compras estiver vazia e a pilha de descarte tiver cartas, as cartas são
                //adicionadas à pilha de compras e embaralhadas
                if(pilhaCompra.size()==0 && pilhaDescarte.size()>0){
                    for (int j = pilhaDescarte.size() - 1; j >= 0; j--) {
                        Carta cartaReciclada = pilhaDescarte.remove(j);
                        pilhaCompra.add(cartaReciclada);
                    }
                Collections.shuffle(pilhaCompra);
                }

                //se tiver cartas na pilha de compras e não tiver 3 cartas na mão do jogador, ele adiciona até
                //3 novas cartas conforme a necessidade
                if(pilhaCompra.size()>0 && maoJogador.size()<3){
                    Carta cartaComprada = pilhaCompra.remove(pilhaCompra.size() - 1);
                    maoJogador.add(cartaComprada);
                }
            }
            //decide o ataque do inimigo
            Collections.shuffle(movimentosInimigo);

            App.limparTela();
            App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo, this.transformacao, this.barraEvolucao, this.modoSeninAtivo, this.modoKuramaAtivo);
            if (!msgRegen.equals("")) {
                System.out.println("\n"+ msgRegen);
            }

            //enquanto tiver chackra disponível e os dois estiverem vivos, o turno é o mesmo
            while(chakra>0 && heroiEscolhido.estaVivo() && inimigoEscolhido.estaVivo()){
                leitura = entrada.nextInt();
                entrada.nextLine();
                
                
                //se a leitura for ultima opção, o chakra é zerado e o turno acaba. +3 porque
                //tem os menus de carta e efeito
                if(leitura == (maoJogador.size()+3)){
                    chakra = 0;
                } 

                //se a leitura estiver entre as opções de cartas a serem utilizadas
                else if(leitura > 0 && leitura <= maoJogador.size()){
                    Carta cartaSelecionada = maoJogador.get(leitura-1);

                    //se tem chakra para a habilidade, usa a carta e a descarta depois
                    if(chakra >= cartaSelecionada.getCusto()){
                        chakra -= cartaSelecionada.getCusto();

                        int danoBase = cartaSelecionada.getDano(); 
    
                        // Se a carta for de dano (seja área ou único), aplica o bônus
                        if (danoBase > 0 || cartaSelecionada instanceof CartaDanoArea) { 
                            int danoExtra = 0;
                            if (modoSeninAtivo) danoExtra = 5;
                            else if (modoKuramaAtivo) danoExtra = 10;

                            if (danoExtra > 0) {
                                inimigoEscolhido.receberDano(danoExtra);
                                System.out.println(App.CIANO + "⚔️ Bônus de " + (modoSeninAtivo ? "Senjutsu" : "Kurama") + ": +" + danoExtra + " de dano!" + App.RESET);
                            }
                        }

                        cartaSelecionada.usar_h(heroiEscolhido, inimigoEscolhido, this);
                        
                        System.out.println("\n[Pressione ENTER para continuar]");
                        entrada.nextLine(); 

                        Carta cartaDescartada = maoJogador.remove(leitura-1);
                        pilhaDescarte.add(cartaDescartada);
                    } else {
                        System.out.println("Chakra insuficiente para usar "+ cartaSelecionada.getNome() +"!");
                        insuficiente = true;
                    }
                }
                //caso o usuario chame o menu de cartas
                else if(leitura == (maoJogador.size()+1)){
                    App.menuCartas(maoJogador,entrada);
                    App.limparTela();
                    App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo, this.transformacao, this.barraEvolucao, this.modoSeninAtivo, this.modoKuramaAtivo);
                    entrada.nextLine();
                }
                //caso o usuario chame o menu de efeitos
                else if(leitura == (maoJogador.size()+2)){
                    App.menuVenenoRegen(listaEfeitosCombate,entrada);
                    entrada.nextLine();
                }
                else{
                    System.out.println("Opção Inválida!");
                    //para não chamar o display
                    insuficiente = true;
                }

                //caso o chackra seja insuficiente não vai limpar a tela e chamar display, para que a mensagem
                //de que chackra não é suficiente para a habilidade apareça no terminal
                if (!insuficiente) {
                    App.limparTela();
                    App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo, this.transformacao, this.barraEvolucao, this.modoSeninAtivo, this.modoKuramaAtivo);
                }
                insuficiente = false;

            }
            
           
            //no fim do turno as cartas da mão do jogador vao para a pilha de descarte
            for(int i = maoJogador.size()-1; i>=0; i--){
                Carta cartaDescartada = maoJogador.remove(i);
                pilhaDescarte.add(cartaDescartada);
            }

            inimigoEscolhido.zeraEscudo();
            //inimigo ataca automaticamente no final de cada turno, mas só se ele estiver vivo
            if (inimigoEscolhido.getVida()>0) {
                movimentosInimigo.get(0).usar_i(inimigoEscolhido, heroiEscolhido,this);
                
                System.out.println("\n[Pressione ENTER para seu próximo turno]");
                entrada.nextLine();
                
            }

            //publisher avisando o fim do turno
            avisar(1);
            

            //escudo zerado depois de cada turno
            heroiEscolhido.zeraEscudo();

        }
        
        App.limparTela();
        System.out.println(AMARELO + "╔════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(AMARELO + "║ " + NEGRITO + "                    𖣘  FIM DA BATALHA  𖣘                  " + RESET + AMARELO + " ║" + RESET);
        System.out.println(AMARELO + "╚════════════════════════════════════════════════════════════╝" + RESET);

        if(heroiEscolhido.estaVivo()){
            return "\n " + VERDE + "VITORIA! " + RESET + heroiEscolhido.getNome() + " provou seu valor como Ninja!";
        } else {
            return "\n " + VERMELHO + "DERROTA... " + RESET + heroiEscolhido.getNome() + " caiu diante de " + inimigoEscolhido.getNome() + ".";
        }

    }
}