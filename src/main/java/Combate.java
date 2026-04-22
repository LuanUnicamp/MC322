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
     * Construtor padrão da classe Combate.
     * <br>
     * <b>Comportamento:</b> Inicializa a lista de monitoramento de efeitos (como Veneno ou Regeneração) 
     * como uma lista vazia para o início seguro da partida.
     */
    public Combate(){
        listaEfeitosCombate = new ArrayList<>();
    }

    /**
     * Registra um novo efeito na lista de monitoramento do combate.
     * <br>
     * <b>Comportamento:</b> Verifica se um efeito com o mesmo nome já existe na lista ativa. 
     * Caso não exista, ele adiciona o efeito, impedindo duplicatas que poderiam quebrar o balanceamento.
     * @param efeito O objeto {@link Efeito} que será adicionado à entidade.
     */
    public void inscreverEfeito(Efeito efeito){
        for(int i=0;i<listaEfeitosCombate.size();i++){
            Efeito efeitolista = listaEfeitosCombate.get(i);
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
     * Notifica todos os efeitos inscritos sobre a passagem do tempo dentro do turno.
     * <br>
     * <b>Comportamento:</b> Percorre a lista de efeitos ativos de trás para frente. 
     * Aciona a lógica interna de cada efeito e remove aqueles cujas durações já se esgotaram.
     * @param num Código de sinalização do momento no turno (0 para o início do turno, 
     * 1 para o fim do turno).
     */
    private void avisar(int num){
        for(int i=listaEfeitosCombate.size()-1;i>=0;i--){
            Efeito efeito = listaEfeitosCombate.get(i);
            
            if(efeito.acabou()){
                listaEfeitosCombate.remove(i);
                efeito.getEntidade().removerEfeito(efeito);
            }else{
                efeito.avisado(num);
            }
        }
    }

    /**
     * Gerencia o loop principal do duelo até que um dos lutadores seja derrotado (HP chegue a zero).
     * <br>
     * <b>Comportamento:</b> Controla a compra de cartas, evolução das transformações de modo história, 
     * a execução das jogadas pelo jogador (via console) e pelo inimigo (automáticas), além de 
     * desencadear notificações do publisher (efeitos) a cada início e fim de turno.
     * @param heroiEscolhido    O herói controlado pelo jogador.
     * @param inimigoEscolhido  O oponente controlado pelo sistema.
     * @param movimentosInimigo Lista de cartas com as possíveis ações do inimigo.
     * @param pilhaCompra       Lista de cartas ainda disponíveis para o jogador sacar.
     * @param maoJogador        Lista de cartas atualmente na mão do jogador.
     * @param pilhaDescarte     Lista de cartas já utilizadas ou descartadas pelo jogador.
     * @param entrada           Scanner compartilhado para leitura da entrada do usuário.
     * @return Uma {@link String} formatada e colorida indicando o vencedor e a mensagem de desfecho da batalha.
     */
    public String rodarCombate(Heroi heroiEscolhido, Inimigo inimigoEscolhido, 
                           ArrayList<Carta> movimentosInimigo, 
                           ArrayList<Carta> pilhaCompra, 
                           ArrayList<Carta> maoJogador, 
                           ArrayList<Carta> pilhaDescarte,
                           Scanner entrada) {

        int leitura;

        this.listaEfeitosCombate = new ArrayList<>();
        this.turno = 0;

        // o jogo acaba quando um dos dois oponentes tem estaVivo() retornando false
        while(heroiEscolhido.estaVivo() && inimigoEscolhido.estaVivo()){
            this.turno++;
            String msgRegen = "";
            // publisher avisando inicio do turno
            avisar(0);

            // lógica para caso a transformação do modo história estiver desbloqueada
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
            // variável que indica quando o herói não tem chakra suficiente para usar habilidade
            Boolean insuficiente = false;

            for(int k = 0; k < 3; k++){
                // se a pilha de compras estiver vazia e a pilha de descarte tiver cartas, recicla
                if(pilhaCompra.size()==0 && pilhaDescarte.size()>0){
                    for (int j = pilhaDescarte.size() - 1; j >= 0; j--) {
                        Carta cartaReciclada = pilhaDescarte.remove(j);
                        pilhaCompra.add(cartaReciclada);
                    }
                    Collections.shuffle(pilhaCompra);
                }

                // compra até 3 cartas se a mão estiver incompleta
                if(pilhaCompra.size()>0 && maoJogador.size()<3){
                    Carta cartaComprada = pilhaCompra.remove(pilhaCompra.size() - 1);
                    maoJogador.add(cartaComprada);
                }
            }
            // decide o ataque do inimigo
            Collections.shuffle(movimentosInimigo);

            App.limparTela();
            App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo, this.transformacao, this.barraEvolucao, this.modoSeninAtivo, this.modoKuramaAtivo);
            if (!msgRegen.equals("")) {
                System.out.println("\n"+ msgRegen);
            }

            // enquanto tiver chakra disponível e os dois estiverem vivos, o turno continua
            while(chakra>0 && heroiEscolhido.estaVivo() && inimigoEscolhido.estaVivo()){
                leitura = entrada.nextInt();
                entrada.nextLine();
                
                // se a leitura for a última opção, o chakra é zerado e o turno acaba
                if(leitura == (maoJogador.size()+3)){
                    chakra = 0;
                } 

                // se a leitura estiver entre as opções de cartas a serem utilizadas
                else if(leitura > 0 && leitura <= maoJogador.size()){
                    Carta cartaSelecionada = maoJogador.get(leitura-1);

                    if(chakra >= cartaSelecionada.getCusto()){
                        chakra -= cartaSelecionada.getCusto();

                        int danoBase = cartaSelecionada.getDano(); 
    
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
                // menu de cartas
                else if(leitura == (maoJogador.size()+1)){
                    App.menuCartas(maoJogador, entrada);
                    App.limparTela();
                    App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo, this.transformacao, this.barraEvolucao, this.modoSeninAtivo, this.modoKuramaAtivo);
                    entrada.nextLine();
                }
                // menu de efeitos
                else if(leitura == (maoJogador.size()+2)){
                    App.menuVenenoRegen(listaEfeitosCombate, entrada);
                    entrada.nextLine();
                }
                else{
                    System.out.println("Opção Inválida!");
                    insuficiente = true;
                }

                if (!insuficiente) {
                    App.limparTela();
                    App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo, this.transformacao, this.barraEvolucao, this.modoSeninAtivo, this.modoKuramaAtivo);
                }
                insuficiente = false;
            }
            
            // no fim do turno as cartas da mão do jogador vão para a pilha de descarte
            for(int i = maoJogador.size()-1; i>=0; i--){
                Carta cartaDescartada = maoJogador.remove(i);
                pilhaDescarte.add(cartaDescartada);
            }

            inimigoEscolhido.zeraEscudo();
            // inimigo ataca automaticamente no final de cada turno, mas só se estiver vivo
            if (inimigoEscolhido.getVida()>0) {
                movimentosInimigo.get(0).usar_i(inimigoEscolhido, heroiEscolhido, this);
                
                System.out.println("\n[Pressione ENTER para seu próximo turno]");
                entrada.nextLine();
            }

            // publisher avisando o fim do turno
            avisar(1);

            // escudo zerado depois de cada turno
            heroiEscolhido.zeraEscudo();
        }
        
        App.limparTela();
        System.out.println(AMARELO + "╔════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(AMARELO + "║ " + NEGRITO + "                    𖣘  FIM DA BATALHA  𖣘                  " + RESET + AMARELO + " ║" + RESET);
        System.out.println(AMARELO + "╚════════════════════════════════════════════════════════════╝" + RESET);

        if(heroiEscolhido.estaVivo()){
            App.exibirFinal(1);
            return AMARELO + NEGRITO + "             --- VITÓRIA! VOCÊ SE TORNOU UMA LENDA ---" + RESET;
        } else {
            App.exibirFinal(0);
            return ROXO+NEGRITO+"             --- DERROTA! O CICLO DE ÓDIO CONTINUA ---"+RESET;
        }
    }
    
    public ArrayList<Efeito> getListaEfeitos() {
        return this.listaEfeitosCombate;
    }
}