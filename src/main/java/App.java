import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe principal do sistema responsável por gerenciar a interface 
 * do usuário no terminal e a inicialização das partidas.
 */

public class App {

    /** Constantes de formatação ANSI utilizadas para colorir o texto no terminal. */
    public static final String RESET = "\u001B[0m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String CIANO = "\u001B[36m";
    public static final String ROXO = "\u001B[35m";
    public static final String NEGRITO = "\033[1m";

    /**
     * Exibe o estado atual do combate no terminal.
     * @param h O herói cujos atributos serão exibidos.
     * @param i O inimigo cujos atributos serão exibidos.
     * @param chakra A quantidade de energia disponível para o jogador.
     * @param maoJogador A lista de cartas disponíveis para uso no turno.
     * @param movimentosInimigo A lista de ações que o inimigo pretende realizar. <br>
     * <b>Comportamento</b>: Atualiza os valores de vida e imprime o painel visual com 
     * os status de ambos os personagens e as opções de comando.
     */
    public static void display(Heroi h, Inimigo i, int chakra, ArrayList<Carta> maoJogador, ArrayList<Carta> movimentosInimigo, int transformacao, int barra, boolean modoSenin, boolean modoKurama){
        h.zeraVida();
        i.zeraVida();
        
        System.out.println(CIANO + "╔════════════════════════ SHINOBI LEGACY ════════════════════════╗" + RESET);
        
        // status dos oponentes
        System.out.printf("  %-30s %s %30s  \n", 
            NEGRITO + h.getNome() + RESET, "vs", NEGRITO + i.getNome() + RESET);
        
        System.out.printf("  Vida: " + VERDE + "%-24d" + RESET + "      Vida: " + VERDE + "%d" + RESET + "  \n", 
            h.getVida(), i.getVida());
            
        System.out.printf("  Escudo: " + CIANO + "%-22d" + RESET + "      Escudo: " + CIANO + "%d" + RESET + "  \n", 
            h.getEscudo(), i.getEscudo());

        System.out.println(CIANO + "╠════════════════════════════════════════════════════════════════╣" + RESET);
        
        //chakra em destaque
        String chakraBarra = "● ".repeat(chakra) + "○ ".repeat(4 - chakra);
        System.out.println("  Energia: " + AMARELO + chakra + "/4 Chakra [" + chakraBarra + "]" + RESET);
        System.out.println("  Próximo golpe do inimigo: " + VERMELHO + movimentosInimigo.get(0).getNome() + RESET);
        Carta c=movimentosInimigo.get(0);

        if(c instanceof CartaDano) {
            System.out.println("  Dano: " + VERMELHO + c.getDano() + RESET);
        } 
        else if(c instanceof CartaEscudo) {
            System.out.println("  Escudo: " + CIANO + c.getEscudo() + RESET);
            if(c instanceof CartaEscudoRegen) {
                System.out.println("  Regen: " + VERDE + "+" + ((CartaEscudoRegen)c).getRegen() + " de regen" + RESET);
            }
        }
        else if(c instanceof CartaCuraRegen) {
            CartaCuraRegen cr = (CartaCuraRegen) c;
            System.out.println("  Cura: " + VERDE + "+" + cr.getCura() + " HP" + RESET);
            System.out.println("  Regen: " + VERDE + "+" + cr.getRegenCura() + " de regen" + RESET);
        }
        else if(c instanceof CartaDanoArea) {
            System.out.println("  Potencial: " + VERMELHO + c.getDano() + " (Crítico)" + RESET);
            System.out.println("  " + AMARELO + "🎲 Chance de falha na execução" + RESET);
        }
        else if(c instanceof CartaDanoVeneno) {
            CartaDanoVeneno cv = (CartaDanoVeneno) c;
            System.out.println("  Efeito: " + ROXO + "Veneno 🧪 (" + cv.getVeneno() + " de veneno)" + RESET);
            System.out.println("  " + ROXO + "Debuff acumulativo por tempo" + RESET);
        }
        else if(c instanceof CartaEscudoEspinhos) {
            CartaEscudoEspinhos ce = (CartaEscudoEspinhos) c;
            System.out.println("  Defesa: " + App.CIANO + "+" + ce.getEscudo() + " Escudo" + App.RESET);
            System.out.println("  Efeito: " + App.VERMELHO + "Espinhos (" + ce.getDano() + " Contra-ataque)" + App.RESET);
            System.out.println("  " + App.CIANO + "🛡️ O inimigo sofre dano ao te atacar!" + App.RESET);
        }
        else if(c instanceof CartaRouboVida) {
            CartaRouboVida rv = (CartaRouboVida) c;
            System.out.println("  Ataque: " + App.VERMELHO + "-" + rv.getDano() + " HP" + App.RESET);
            System.out.println("  " + App.VERMELHO + "🩸 Rouba a essência vital do alvo!" + App.RESET);
        }

        System.out.println(CIANO + "╟────────────────────────────────────────────────────────────────╢" + RESET);

        //mão do Jogador Formatada
        for(int j = 0; j < maoJogador.size(); j++){
            System.out.println("  [" + (j+1) + "] " + NEGRITO + maoJogador.get(j).getNome() + RESET + 
                            " (" + AMARELO + maoJogador.get(j).getCusto() + " Chakra" + RESET + ")");
        }
        
        System.out.println(CIANO + "╟────────────────────────────────────────────────────────────────╢" + RESET);
        System.out.println("  [" + (maoJogador.size()+1) + "] ℹ️  Menu das Cartas | [" + (maoJogador.size()+2) + "] 🧪 Efeitos | [" + (maoJogador.size()+3) + "] ⌛ Encerrar Turno");
        System.out.println(CIANO + "╚════════════════════════════════════════════════════════════════╝" + RESET);

        if (transformacao > 0) {
            System.out.println("\n" + AMARELO + "╔════════════════════════════════════════════════════════════╗" + RESET);
            if (modoSenin) {
                System.out.println(VERDE + "║ ✨ MODO SÁBIO ATIVO: +5 Dano / +10 Regen                   ║" + RESET);
            } else if (modoKurama) {
                System.out.println(VERMELHO + "║ 🦊 MANTO DA KYUUBI ATIVO: +10 Dano / +5 Regen             ║" + RESET);
            } else {
                //Desenha a barra de progresso visual para a transformacao
                String barraVisual = "█".repeat(barra / 10) + "░".repeat(10 - (barra / 10));
                System.out.println(AMARELO + "║ ⚡ TRANSFORMAÇÃO: [" + barraVisual + "] " + barra + "%                     ║" + RESET);
            }
            System.out.println(AMARELO + "╚════════════════════════════════════════════════════════════╝" + RESET);
        }

        System.out.print("  Escolha sua ação: ");
    }

    public static int menuPrincipal(Scanner leitura){
        System.out.println(AMARELO + "╔════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(AMARELO + "║ " + NEGRITO + "           𖣘  SHINOBI LEGACY: O DUELO NINJA  𖣘          " + RESET + AMARELO + "   ║" + RESET);
        System.out.println(AMARELO + "╚════════════════════════════════════════════════════════════╝" + RESET);

        System.out.println("\n[1] Modo História");
        System.out.println("[2] Batalha livre");
        System.out.println("[3] Sair");
        System.out.print("\nEscolha uma opção: ");

        return leitura.nextInt();
    }

    //declarando e instanciando arraylist de herois
    public static ArrayList<Heroi> GeraHeroisDisponiveis(){
        ArrayList<Heroi> heroisDisponiveis = new ArrayList<>();
        heroisDisponiveis.add(new Heroi("Naruto Uzumaki", 100, 0));
        heroisDisponiveis.add(new Heroi("Sasuke Uchiha", 100, 0));

        return heroisDisponiveis;
    }

    //declarando e instanciando arraylist de inimigos
    public static ArrayList<Inimigo> GeraInimigosDisponiveis(){
        ArrayList<Inimigo> inimigosDisponiveis = new ArrayList<>();
        inimigosDisponiveis.add(new Inimigo("Madara Uchiha", 120, 0));
        inimigosDisponiveis.add(new Inimigo("Pain", 100, 0));
        inimigosDisponiveis.add(new Inimigo("Deidara", 60, 0));

        return inimigosDisponiveis;
    }

    //gera deck baseado em qual inimigo for, cada um tem suas próprias habilidades (traz mais realidade pra historia)
    public static ArrayList<Carta> GeraDeckInimigo(Inimigo i){
        ArrayList<Carta> movimentos = new ArrayList<>();

        if (i.getNome().contains("Pain")) {
            movimentos.add(new CartaDanoVeneno("Shinra Tensei", "Repulsão divina.", 0, 25, 5));
            movimentos.add(new CartaCuraRegen("Caminho Naraka", "Rei do Inferno.", 0, 10, 5));
            movimentos.add(new CartaRouboVida("Absorção de Chakra (Preta)", "Drena energia.", 3, 10, 10));
        } 
        else if (i.getNome().contains("Madara")) {
            movimentos.add(new CartaDanoArea("Bijuudama", "Esfera devastadora.", 0, 35, 6, 40));
            movimentos.add(new CartaDano("Jutsu Bola de Fogo", "Clássico Uchiha.", 0, 15));
            movimentos.add(new CartaEscudoEspinhos("Susano'o Perfeito", "Muralha impenetrável.", 0, 25, 8));
        }
        else if (i.getNome().contains("Deidara")) {
            movimentos.add(new CartaDano("C1: Pássaros Explosivos", "Ataque rápido.", 0, 5));
            movimentos.add(new CartaDano("C2: Dragão de Argila", "Dragão que lança explosões ao alvo", 0, 10));
            movimentos.add(new CartaDanoArea("C3: Bomba Gigante", "Explosão em massa.", 0, 20, 10, 50));
            movimentos.add(new CartaEscudoEspinhos("Clone de Argila", "Usa clone de argila para se defender e explodir no inimigo", 0, 10, 5));
        }
        else if (i.getNome().contains("Jiraiya")) {
            movimentos.add(new CartaDano("Rasengan", "Ataque espiral de alta compressão.", 0, 15));
            movimentos.add(new CartaDano("Estilo Fogo: Chama do Dragão", "Uma rajada de fogo devastadora.", 0, 7));
            movimentos.add(new CartaEscudoEspinhos("Agulhas Jizo", "Endurece o cabelo como espinhos para defesa e contra-ataque.", 0, 8, 8));
            movimentos.add(new CartaEscudoRegen("Invocação: Sapo Escudo", "Invoca um sapo para defesa tática.", 0, 5, 3));
        }
        else if (i.getNome().contains("Kakashi")) {
            movimentos.add(new CartaDano("Raikiri (Corte Relâmpago)", "Um ataque perfurante letal de elemento raio.", 0, 15));
            movimentos.add(new CartaDano("Estilo Água: Dragão de Água", "Um dragão colossal feito de água esmaga o alvo.", 0, 10));
            movimentos.add(new CartaDanoVeneno("Invocação: Cães Ninjas", "Pakkun e os outros imobilizam e ferem o alvo.", 0, 8, 3));
            movimentos.add(new CartaEscudo("Estilo Terra: Parede de Lama", "Cria uma barreira impenetrável de pedra.", 0, 8));
            movimentos.add(new CartaEscudoRegen("Sharingan", "Lê perfeitamente os movimentos do adversário.", 0, 10, 2));
        }

        return movimentos;
    }

    //gera deck baseado em qual heroi for, cada um tem suas próprias habilidades (traz mais realidade pra historia)
    public static ArrayList<Carta> GeraDeckHeroi(Heroi h){
        ArrayList<Carta> pilhaCompra = new ArrayList<>();
        
        //cartas universais
        pilhaCompra.add(new CartaEscudo("Jutsu de Substituição", "Defesa básica.", 1, 10));

        //gera cartas do naruto
        if (h.getNome().contains("Naruto")) {
            pilhaCompra.add(new CartaDano("Rasengan", "Ataque espiral.", 1, 12));
            pilhaCompra.add(new CartaDanoVeneno("Kurama", "Chakra da Raposa.", 3, 20, 5));
            pilhaCompra.add(new CartaEscudoRegen("Clone das sombras", "Defesa em massa.", 2, 15, 3));
            pilhaCompra.add(new CartaDanoArea("Gamabunta: Banho de Óleo", "Invoca o Chefe dos Sapos.", 3, 35, 6, 40));
        } 
        //gera cartas do naruto
        else if (h.getNome().contains("Sasuke")) {
            pilhaCompra.add(new CartaDano("Chidori", "Golpe dos mil pássaros.", 1, 12));
            pilhaCompra.add(new CartaDano("Sharingan", "Lê os movimentos.", 2, 20));
            pilhaCompra.add(new CartaEscudoEspinhos("Susano'o perfeito", "Defesa absoluta.", 3, 25, 7));
            pilhaCompra.add(new CartaDano("Kirin", "Dragão de raios.", 0, 20));
        }

        return pilhaCompra;
    }

    //metodo que "limpa" o terminal para dar um efeito de que o status dos personagens está sendo atualizado
    public static void limparTela(){
        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }

    //metodo que mostra no display o menu das cartas
    public static void menuCartas(ArrayList<Carta> maoJogador, Scanner leitura){
        limparTela();
        System.out.println(CIANO + "╔═══════════════════ ♣ BIBLIOTECA DE JUTSUS ♣ ══════════════════╗" + RESET);
        
        for(int j = 0; j < maoJogador.size(); j++){
            Carta c = maoJogador.get(j);
            System.out.println(AMARELO + " [" + (j+1) + "] " + RESET + NEGRITO + c.getNome() + RESET);
            System.out.println("     " + c.getDescricao());
            System.out.println("     Custo: " + AMARELO + c.getCusto() + " Chakra" + RESET);
            
            
            if(c instanceof CartaDano) {
                System.out.println("     Dano: " + VERMELHO + c.getDano() + RESET);
            } 
            else if(c instanceof CartaEscudo) {
                System.out.println("     Escudo: " + CIANO + c.getEscudo() + RESET);
                if(c instanceof CartaEscudoRegen) {
                    System.out.println("     Regen: " + VERDE + "+" + ((CartaEscudoRegen)c).getRegen() + " de regen" + RESET);
                }
            }
            else if(c instanceof CartaCuraRegen) {
                CartaCuraRegen cr = (CartaCuraRegen) c;
                System.out.println("     Cura: " + VERDE + "+" + cr.getCura() + " HP" + RESET);
                System.out.println("     Regen: " + VERDE + "+" + cr.getRegenCura() + " de regen" + RESET);
            }
            else if(c instanceof CartaSacrificio) {
                System.out.println("     Impacto: " + VERMELHO + c.getDano() + " Dano" + RESET);
                System.out.println("     " + AMARELO + "⚠ CUIDADO: Dano colateral ao usuário" + RESET);
            }
            else if(c instanceof CartaDanoArea) {
                System.out.println("     Potencial: " + VERMELHO + c.getDano() + " (Crítico)" + RESET);
                System.out.println("     " + AMARELO + "🎲 Chance de falha na execução" + RESET);
            }
            else if(c instanceof CartaDanoVeneno) {
                CartaDanoVeneno cv = (CartaDanoVeneno) c;
                System.out.println("     Efeito: " + ROXO + "Veneno 🧪 (" + cv.getVeneno() + " de veneno)" + RESET);
                System.out.println("     " + ROXO + "Debuff acumulativo por tempo" + RESET);
            }
            else if(c instanceof CartaEscudoEspinhos) {
                CartaEscudoEspinhos ce = (CartaEscudoEspinhos) c;
                System.out.println("     Defesa: " + App.CIANO + "+" + ce.getEscudo() + " Escudo" + App.RESET);
                System.out.println("     Efeito: " + App.VERMELHO + "Espinhos (" + ce.getDano() + " Contra-ataque)" + App.RESET);
                System.out.println("     " + App.CIANO + "🛡️ O inimigo sofre dano ao te atacar!" + App.RESET);
            }
            else if(c instanceof CartaRouboVida) {
                CartaRouboVida rv = (CartaRouboVida) c;
                System.out.println("     Ataque: " + App.VERMELHO + "-" + rv.getDano() + " HP" + App.RESET);
                System.out.println("     " + App.VERMELHO + "🩸 Rouba a essência vital do alvo!" + App.RESET);
            }

            System.out.println(CIANO + "     ──────────────────────────────────────────────────" + RESET);
        }
        
        System.out.print("\n Digite qualquer número para voltar ao combate: ");
        leitura.next();
    }

    //metodo que mostra no display o menu dos efeitos
    public static void menuVenenoRegen(ArrayList<Efeito> listaEfeitos, Scanner leitura){
        limparTela();
        System.out.println(ROXO + "╔═══════════════════ ☣ ESTADOS E ALTERAÇÕES ✚ ══════════════════╗" + RESET);
        
        if (listaEfeitos.isEmpty()) {
            System.out.println(AMARELO + "      Nenhum efeito ativo no momento. O campo está limpo!" + RESET);
        } else {
            for(int j = 0; j < listaEfeitos.size(); j++){
                Efeito e = listaEfeitos.get(j);
                String corEfeito;
                String icone;
                if(e instanceof Veneno){
                    corEfeito=ROXO;
                    icone="☣";
                } else{
                    corEfeito=VERDE;
                    icone="✚";
                }
                
                
                System.out.println(corEfeito + "  [" + (j+1) + "] " + icone + " " + NEGRITO + e.getNome().toUpperCase() + RESET);
                System.out.println("      Dono: " + NEGRITO + e.getEntidade().getNome() + RESET);
                System.out.println(corEfeito +"      Acumulos: " + corEfeito + e.getAcumulos() + RESET);
                System.out.println(ROXO + "      ──────────────────────────────────────────────────" + RESET);
            }
        }
        
        System.out.println(ROXO + "╚═══════════════════════════════════════════════════════════════╝" + RESET);
        System.out.print("\n " + CIANO + "Digite qualquer número para voltar ao combate: " + RESET);
        
        leitura.next(); 
    }

    //metodo que chama a func iniciar do modo historia (para melhor organizacao e nao ficar tudo no app)
    public static void modoHistoria(Scanner entrada) {
        limparTela();
        Historia hist = new Historia();
        hist.iniciar(entrada);
    }


    public static void modoBatalha(Scanner entrada) {
        limparTela();
        int leitura;
        
        //gerando personagens, cartas, movimentos do inimigo
        ArrayList<Heroi> heroisDisponiveis = GeraHeroisDisponiveis();
        ArrayList<Inimigo> inimigosDisponiveis = GeraInimigosDisponiveis();

        //selecao do heroi
        System.out.println("\n" + CIANO + " [ SELEÇÃO DE HERÓI ] " + RESET);
        for(int i=0; i<heroisDisponiveis.size(); i++){
            System.out.println(AMARELO + " [" + (i+1) + "] " + RESET + heroisDisponiveis.get(i).getNome());
        }
        System.out.print("\n Escolha seu caminho: ");
        leitura = entrada.nextInt();
        Heroi heroiEscolhido = heroisDisponiveis.get(leitura-1);

        limparTela();

        //selecao do inimigo
        System.out.println(VERMELHO + " [ SELEÇÃO DE ADVERSÁRIO ] " + RESET);
        for(int i=0; i<inimigosDisponiveis.size(); i++){
            System.out.println(AMARELO + " [" + (i+1) + "] " + RESET + inimigosDisponiveis.get(i).getNome());
        }
        System.out.print("\n Quem você irá enfrentar? ");
        leitura = entrada.nextInt();
        Inimigo inimigoEscolhido = inimigosDisponiveis.get(leitura-1);

        ArrayList<Carta> pilhaCompra = GeraDeckHeroi(heroiEscolhido);
        ArrayList<Carta> movimentosInimigo = GeraDeckInimigo(inimigoEscolhido);

        // Prepara a mesa do jogador
        Collections.shuffle(pilhaCompra);
        ArrayList<Carta> maoJogador = new ArrayList<>();
        ArrayList<Carta> pilhaDescarte = new ArrayList<>();
        
        //inicia o combate
        Combate combate = new Combate();
        System.out.println(combate.rodarCombate(heroiEscolhido, inimigoEscolhido, movimentosInimigo, pilhaCompra, maoJogador, pilhaDescarte));
        //chama o metodo rodarCombate de combate que retorna o resultado do duelo
        System.out.println(combate.rodarCombate(heroiEscolhido, inimigoEscolhido, movimentosInimigo, pilhaCompra, maoJogador, pilhaDescarte));

    }
    
    public static void main(String[] args) throws Exception {

        Scanner entrada = new Scanner(System.in);
        limparTela();
        
        //o usuario podera escolher se quer jogar o modo historia um um combate livre antes de iniciar o game
        int escolha = menuPrincipal(entrada);

        switch(escolha){
        case 1:
            modoHistoria(entrada);
            break;
        case 2:
            modoBatalha(entrada);
            break;
        case 3:
            System.out.println("Até a próxima, Shinobi!");
            break;
        default:
            System.out.println("Opção inválida.");
        }


    entrada.close();
}
}
