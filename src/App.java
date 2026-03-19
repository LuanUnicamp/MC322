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
        System.out.println(chakra + "/3 de Energia disponível");

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
        inimigosDisponiveis.add(new Inimigo("Madara Uchiha", 120, 0, 25));
        inimigosDisponiveis.add(new Inimigo("Pain", 100, 0, 25));

         
        //declarando e instanciando arraylist de movimentos do inimigo
        ArrayList<Carta> movimentosInimigo = new ArrayList<>();
        movimentosInimigo.add(new CartaDano("Shinra Tensei", "Usa Shinra Tensei para atacar ", 0, 25));
        movimentosInimigo.add(new CartaDano("Jutsu Bola de Fogo", "Usa Jutsu Bola de Fogo para atacar ", 0, 15));
        movimentosInimigo.add(new CartaEscudo("Ninjutsu Médico", "Usa Ninjutsu médico para recuperar vida", 0, 15));


        //declarando e instanciando a pilha de compra
        ArrayList<Carta> pilhaCompra = new ArrayList<>();
        pilhaCompra.add(new CartaDano("Razengan","Usa o Razengan para atacar o inimigo." ,1, 12));
        pilhaCompra.add(new CartaDano("Kurama","Usa a Kurama para atacar o inimigo.", 2, 20));
        pilhaCompra.add(new CartaEscudo("Clone das sombras","Usa o jutsu Clone das Sombras para ganhar escudo.", 1, 15));
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
        
        //o jogo acaba quando um dos dois oponentes tem estavivo() retornando false
        while(heroiEscolhido.estaVivo() && inimigoEscolhido.estaVivo()){
            int chakra = 3;
            ///variável que indica quando o heroi não tem chackra suficiente para usar habilidade
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

            limparTela();
            display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo);

            //enquanto tiver chackra disponível e os dois estiverem vivos, o turno é o mesmo
            while(chakra>0 && heroiEscolhido.estaVivo() && inimigoEscolhido.estaVivo()){
                leitura = entrada.nextInt();
                
                //se a leitura for ultima opção, o chakra é zerado e o turno acaba
                if(leitura == (maoJogador.size()+1)){
                    chakra = 0;
                } 

                //se a leitura estiver entre as opções de cartas a serem utilizadas
                else if(leitura > 0 && leitura <= maoJogador.size()){
                    Carta cartaSelecionada = maoJogador.get(leitura-1);

                    //se tem chakra para a habilidade, usa a carta e a descarta depois
                    if(chakra >= cartaSelecionada.getCusto()){
                        chakra -= cartaSelecionada.getCusto();
                        cartaSelecionada.usar_h(heroiEscolhido, inimigoEscolhido);
                        Carta cartaDescartada = maoJogador.remove(leitura-1);
                        pilhaDescarte.add(cartaDescartada);
                    } else {
                        System.out.println("Chakra insuficiente para usar "+ cartaSelecionada.getNome() +"!");
                        insuficiente = true;
                    }
                }
                //caso o usuario chame o menu de cartas
                else if(leitura == (maoJogador.size()+2)){
                    menuCartas(maoJogador,entrada);
                    limparTela();
                    display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo);
                }
                else{
                    System.out.println("Opção Inválida!");
                    //para não chamar o display
                    insuficiente = true;
                }

                //caso o chackra seja insuficiente não vai limpar a tela e chamar display, para que a mensagem
                //de que chackra não é suficiente para a habilidade apareça no terminal
                if (!insuficiente) {
                    limparTela();
                    display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo);
                }
                insuficiente = false;

        }

        //no fim do turno as cartas da mão do jogador vao para a pilha de descarte
        for(int i = maoJogador.size()-1; i>=0; i--){
            Carta cartaDescartada = maoJogador.remove(i);
            pilhaDescarte.add(cartaDescartada);
        }

        //inimigo ataca automaticamente no final de cada turno, mas só se ele estiver vivo
        if (inimigoEscolhido.getVida()>0) {
            movimentosInimigo.get(0).usar_i(inimigoEscolhido, heroiEscolhido);
        }
        //escudo zerado depois de cada turno
        heroiEscolhido.zeraEscudo();
    }
    //quem permanecer vivo, vence a partida
    if(heroiEscolhido.estaVivo()){
        System.out.println(heroiEscolhido.getNome() + " venceu!");
    } else{
        System.out.println(inimigoEscolhido.getNome() + " venceu!");
    }

    entrada.close();
}
}
