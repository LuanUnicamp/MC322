import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class App {

    //metodo que contem o display que atualiza a cada nova entrada do usuário
    public static void display(Heroi h, Inimigo i, int chakra, ArrayList<Carta> maoJogador, ArrayList<Carta> movimentosInimigo){
        h.zeraVida();
        i.zeraVida();
        System.out.println("======----======----======----======----======----======");
        System.out.println(h.getNome()+" ("+h.getVida()+") de vida ("+h.getEscudo()+") de escudo");
        System.out.println("vs");
        System.out.println(i.getNome()+" ("+i.getVida()+") de vida ("+i.getEscudo()+") de escudo");
        System.out.println(chakra + "/3 de chakra disponível");

        for(int j = 0; j < maoJogador.size(); j++){
            System.out.println("[" + (j+1) + "] Usar carta " + maoJogador.get(j).getNome() + " (" + maoJogador.get(j).getCusto() + " de Chakra)");
        }
        System.out.println("[" + (maoJogador.size()+1) + "] Encerrar turno");
        System.out.println("[" + (maoJogador.size()+2) + "] Menu das Cartas");
        System.out.println("O inimigo usará: "+ movimentosInimigo.get(0).getNome());
        System.out.println("Escolha: ");
        System.out.println("======----======----======----======----======----======");
    }

    //metodo que "limpa" o terminal para dar um efeito de que o status dos personagens está sendo atualizado
    public static void limparTela(){
        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }

    public static void menuCartas(ArrayList<Carta> maoJogador, Scanner leitura){
        limparTela();
        System.out.println("======----======----♣ Menu das Cartas ♣---======----======");
        for(int j = 0; j < maoJogador.size(); j++){
            System.out.println("[" + (j+1) + "]" + maoJogador.get(j).getNome());
            System.out.println("Descrição: "+maoJogador.get(j).getDescricao());
            System.out.println("Custo: "+maoJogador.get(j).getCusto());
            System.out.println("_____________________________________________________");
        }
        System.out.println("Digite qualquer número para voltar:");
        leitura.nextInt();
        System.out.println("======----======----======----======----======----======");
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
        movimentosInimigo.add(new CartaDanoVeneno("Shinra Tensei(aplica 5 de veneno)", "Usa Shinra Tensei para atacar ", 0, 25,5));
        movimentosInimigo.add(new CartaDano("Jutsu Bola de Fogo", "Usa Jutsu Bola de Fogo para atacar ", 0, 15));
        movimentosInimigo.add(new CartaEscudoRegen("Ninjutsu Médico(ganha 3 de regen)", "Usa Ninjutsu médico para recuperar vida", 0, 15,3));


        //declarando e instanciando a pilha de compra
        ArrayList<Carta> pilhaCompra = new ArrayList<>();
        pilhaCompra.add(new CartaDano("Razengan","Usa o Razengan para atacar o inimigo." ,1, 12));
        pilhaCompra.add(new CartaDanoVeneno("Kurama (aplica 5 de veneno)","Usa a Kurama para atacar o inimigo.", 3, 20,5));
        pilhaCompra.add(new CartaEscudoRegen("Clone das sombras (ganha 3 de regen)","Usa o jutsu Clone das Sombras para ganhar escudo.", 2, 15,3));
        pilhaCompra.add(new CartaDano("Sharingan","Usa Sharingan para atacar o inimigo", 2, 20));
        pilhaCompra.add(new CartaEscudo("Jutsu de Substituição","Usa jutsu de Substituição para ganhar escudo", 1, 10));
        //embaralhando pilha de compra
        Collections.shuffle(pilhaCompra);

        //declarando a mão do jogador
        ArrayList<Carta> maoJogador = new ArrayList<>();
        //declarando a pilha de descarte 
        ArrayList<Carta> pilhaDescarte = new ArrayList<>();
        
        int leitura;
        Scanner entrada = new Scanner(System.in);

        limparTela();
        System.out.println("𖣘 Seja Bem-Vindo(a) ao Shinobi Legacy!! 𖣘");
        System.out.println("Escolha um heroi para iniciar:");
        //menu de herois
        for(int i=0; i<heroisDisponiveis.size(); i++){
            System.out.println("[" + (i+1) + "] " + heroisDisponiveis.get(i).getNome());
        }
        leitura = entrada.nextInt();
        Heroi heroiEscolhido = heroisDisponiveis.get(leitura-1);

        limparTela();
        System.out.println("𖣘 Seja Bem-Vindo(a) ao Shinobi Legacy!! 𖣘");
        System.out.println("Escolha um inimigo para combater:");
        //menu de inimigos
        for(int i=0; i<inimigosDisponiveis.size(); i++){
            System.out.println("[" + (i+1) + "] " + inimigosDisponiveis.get(i).getNome());
        }
        leitura = entrada.nextInt();
        Inimigo inimigoEscolhido = inimigosDisponiveis.get(leitura-1);
        Combate combate = new Combate();
        
        System.out.println(combate.rodarCombate(heroiEscolhido, inimigoEscolhido, movimentosInimigo, pilhaCompra, maoJogador, pilhaDescarte));


    entrada.close();
}
}
