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
    public static void display(Heroi h, Inimigo i, int chakra, ArrayList<Carta> maoJogador, ArrayList<Carta> movimentosInimigo){
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
        
        // chakra em destaque
        String chakraBarra = "● ".repeat(chakra) + "○ ".repeat(3 - chakra);
        System.out.println("  Energia: " + AMARELO + chakra + "/3 Chakra [" + chakraBarra + "]" + RESET);
        System.out.println("  Próximo golpe do inimigo: " + VERMELHO + movimentosInimigo.get(0).getNome() + RESET);
        System.out.println(CIANO + "╟────────────────────────────────────────────────────────────────╢" + RESET);

        // mão do Jogador Formatada
        for(int j = 0; j < maoJogador.size(); j++){
            System.out.println("  [" + (j+1) + "] " + NEGRITO + maoJogador.get(j).getNome() + RESET + 
                            " (" + AMARELO + maoJogador.get(j).getCusto() + " Chakra" + RESET + ")");
        }
        
        System.out.println(CIANO + "╟────────────────────────────────────────────────────────────────╢" + RESET);
        System.out.println("  [" + (maoJogador.size()+1) + "] ℹ️ Menu das Cartas | [" + (maoJogador.size()+2) + "] 🧪 Efeitos | [" + (maoJogador.size()+3) + "] ⌛ Encerrar Turno");
        System.out.println(CIANO + "╚════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.print("  Escolha sua ação: ");
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
                System.out.println("     Dreno: " + App.VERDE + "+" + (rv.getDano() / 2) + " Cura (50%)" + App.RESET);
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
                String corEfeito = e.getNome().contains("Veneno") ? ROXO : VERDE;
                String icone = e.getNome().contains("Veneno") ? "☣" : "✚";
                
                System.out.println(corEfeito + "  [" + (j+1) + "] " + icone + " " + NEGRITO + e.getNome().toUpperCase() + RESET);
                System.out.println("      Dono: " + NEGRITO + e.getEntidade().getNome() + RESET);
                System.out.println("      Status: " + corEfeito + e.getString() + RESET);
                System.out.println(ROXO + "      ──────────────────────────────────────────────────" + RESET);
            }
        }
        
        System.out.println(ROXO + "╚═══════════════════════════════════════════════════════════════╝" + RESET);
        System.out.print("\n " + CIANO + "Digite qualquer número para voltar ao combate: " + RESET);
        
        leitura.next(); 
    }
    
    public static void main(String[] args) throws Exception {

        //declarando e instanciando arraylist de herois
        ArrayList<Heroi> heroisDisponiveis = new ArrayList<>();
        heroisDisponiveis.add(new Heroi("Naruto Uzumaki", 100, 0));
        heroisDisponiveis.add(new Heroi("Sasuke Uchiha", 100, 0));

        //declarando e instanciando arraylist de inimigos
        ArrayList<Inimigo> inimigosDisponiveis = new ArrayList<>();
        inimigosDisponiveis.add(new Inimigo("Madara Uchiha", 120, 0));
        inimigosDisponiveis.add(new Inimigo("Pain", 100, 0));

         
        //declarando e instanciando arraylist de movimentos do inimigo
        ArrayList<Carta> movimentosInimigo = new ArrayList<>();
        movimentosInimigo.add(new CartaDanoVeneno("Shinra Tensei(aplica 5 de veneno)", "Usa Shinra Tensei para atacar.", 0, 25,5));
        movimentosInimigo.add(new CartaDano("Jutsu Bola de Fogo", "Usa Jutsu Bola de Fogo para atacar.", 0, 15));
        movimentosInimigo.add(new CartaEscudoRegen("Ninjutsu Médico(ganha 3 de regen)", "Usa Ninjutsu médico para recuperar vida.", 0, 15,3));
        movimentosInimigo.add(new CartaDano("Kirin", "Invoca um dragão feito de raios pra cima do inimigo.", 0, 20));
        movimentosInimigo.add(new CartaDanoVeneno("Edo Tensei", "Traz shinobis incríveis do passado de volta a vida!", 0,20 ,5));
        movimentosInimigo.add(new CartaEscudo("Rotação Hyuga", "Defesa absoluta dos Hyuga.", 0, 22));
        movimentosInimigo.add(new CartaDanoVeneno("Manda: Bote Venenoso", "A cobra gigante surge do solo para um ataque mortal.", 0, 22, 6));
        
        movimentosInimigo.add(new CartaDanoArea("Bijuudama", "Dispara uma esfera de chakra concentrado devastadora.", 0, 35,6,40));
        movimentosInimigo.add(new CartaCuraRegen("Caminho Naraka", "O Rei do Inferno restaura o corpo do usuário.", 0, 20, 5));
        movimentosInimigo.add(new CartaRouboVida("Corte da Samehada", "Drena o chakra do alvo", 3, 10, 10));
        movimentosInimigo.add(new CartaEscudoEspinhos("Manto de Chakra", "O chakra da Kurama protege e lança mini bombas bijuu no oponente.", 0, 15, 8));


        //declarando e instanciando a pilha de compra
        ArrayList<Carta> pilhaCompra = new ArrayList<>();
        pilhaCompra.add(new CartaDano("Rasengan","Usa o Razengan para atacar o inimigo." ,1, 12));
        pilhaCompra.add(new CartaDanoVeneno("Kurama (aplica 5 de veneno)","Usa a Kurama para atacar o inimigo.", 3, 20,5));
        pilhaCompra.add(new CartaEscudoRegen("Clone das sombras (ganha 3 de regen)","Usa o jutsu Clone das Sombras para ganhar escudo.", 2, 15,3));
        pilhaCompra.add(new CartaDano("Sharingan","Usa Sharingan para atacar o inimigo.", 2, 20));
        pilhaCompra.add(new CartaEscudo("Jutsu de Substituição","Usa jutsu de Substituição para ganhar escudo.", 1, 10));
        pilhaCompra.add(new CartaDano("Chidori", "Um ataque perfurante de alta precisão, conhecido tam,bém como o golpe dos mil pássaros.", 1, 12));
        pilhaCompra.add(new CartaDano("Jutsu Dragão de Água", "Estilo Água: Jutsu Dragao de Água!.", 2, 14));
        pilhaCompra.add(new CartaEscudo("Parede de terra","Cria uma muralha de terra pra se defender.", 1, 12));
        pilhaCompra.add(new CartaEscudoRegen("Defesa de Shukaku", "Areia densa que protege e estabiliza o usuário.", 3, 30, 2));


        //cinco novas cartas - cura esta aplicando o efeito regen de cura
        pilhaCompra.add(new CartaEscudoEspinhos("Susano'o perfeito","A defesa mais forte existente no mundo ninja com um ataque incrivel.", 3, 25,7)); 
        pilhaCompra.add(new CartaDanoArea("Gamabunta: Banho de Óleo", "Invoca o Chefe dos Sapos para esmagar e queimar o inimigo.", 3, 35, 6,40));
        pilhaCompra.add(new CartaCuraRegen("Katsuyu: Rede de Cura", "A lesma gigante divide o seu chakra para proteger e curar.", 2, 10, 6));
        pilhaCompra.add(new CartaSacrificio("Oito Portões Internos", "Ataques letais em troca de energia vital.", 3, 30, 20));
        pilhaCompra.add(new CartaRouboVida("Absorção de chakra", "Utiliza o caminho Preta para roubar energia vital.", 3, 10, 10));



        //embaralhando pilha de compra
        Collections.shuffle(pilhaCompra);

        //declarando a mão do jogador
        ArrayList<Carta> maoJogador = new ArrayList<>();
        //declarando a pilha de descarte 
        ArrayList<Carta> pilhaDescarte = new ArrayList<>();
        
        int leitura;
        Scanner entrada = new Scanner(System.in);

        limparTela();
        System.out.println(AMARELO + "╔════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(AMARELO + "║ " + NEGRITO + "           𖣘  SHINOBI LEGACY: O DUELO NINJA  𖣘          " + RESET + AMARELO + " ║" + RESET);
        System.out.println(AMARELO + "╚════════════════════════════════════════════════════════════╝" + RESET);

        System.out.println("\n" + CIANO + " [ SELEÇÃO DE HERÓI ] " + RESET);
        for(int i=0; i<heroisDisponiveis.size(); i++){
            System.out.println(AMARELO + " [" + (i+1) + "] " + RESET + heroisDisponiveis.get(i).getNome());
        }
        System.out.print("\n Escolha seu caminho: ");
        leitura = entrada.nextInt();
        Heroi heroiEscolhido = heroisDisponiveis.get(leitura-1);

        limparTela();
        System.out.println(VERMELHO + " [ SELEÇÃO DE ADVERSÁRIO ] " + RESET);
        for(int i=0; i<inimigosDisponiveis.size(); i++){
            System.out.println(AMARELO + " [" + (i+1) + "] " + RESET + inimigosDisponiveis.get(i).getNome());
        }
        System.out.print("\n Quem você irá enfrentar? ");
        leitura = entrada.nextInt();
        Inimigo inimigoEscolhido = inimigosDisponiveis.get(leitura-1);
        
        Combate combate = new Combate();
        
        //chama o metodo rodarCombate de combate que retorna o resultado do duelo
        System.out.println(combate.rodarCombate(heroiEscolhido, inimigoEscolhido, movimentosInimigo, pilhaCompra, maoJogador, pilhaDescarte));


    entrada.close();
}
}
