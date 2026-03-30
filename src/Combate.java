import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Combate {
    private ArrayList<Efeito> listaEfeitosCombate;

    //construtor
    public Combate(){
        listaEfeitosCombate= new ArrayList<>();
    }

    //metodo que inscreve os efeitos na lista de efeitos do Combate
    public void inscreverEfeito(Efeito efeito){
        for(int i=0;i<listaEfeitosCombate.size();i++){
            Efeito efeitolista =listaEfeitosCombate.get(i);
            if(efeito.getNome().equals(efeitolista.getNome())){       
                return;
            }
        }
        listaEfeitosCombate.add(efeito);
    } 

    //metodo que avisa todos os efeitos da lista de efeitos do combate sobre inicio ou fim de turno
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

    public String rodarCombate(Heroi heroiEscolhido, Inimigo inimigoEscolhido, ArrayList<Carta> movimentosInimigo, ArrayList<Carta> pilhaCompra, ArrayList<Carta> maoJogador, ArrayList<Carta> pilhaDescarte ){
        int leitura;
        Scanner entrada = new Scanner(System.in);

        //o jogo acaba quando um dos dois oponentes tem estavivo() retornando false
        while(heroiEscolhido.estaVivo() && inimigoEscolhido.estaVivo()){
   
            //puvlisher avisando inicio do turno
            avisar(0);
    
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

            App.limparTela();
            App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo);

            //enquanto tiver chackra disponível e os dois estiverem vivos, o turno é o mesmo
            while(chakra>0 && heroiEscolhido.estaVivo() && inimigoEscolhido.estaVivo()){
                leitura = entrada.nextInt();
                
                //se a leitura for ultima opção, o chakra é zerado e o turno acaba
                if(leitura == (maoJogador.size()+3)){
                    chakra = 0;
                } 

                //se a leitura estiver entre as opções de cartas a serem utilizadas
                else if(leitura > 0 && leitura <= maoJogador.size()){
                    Carta cartaSelecionada = maoJogador.get(leitura-1);

                    //se tem chakra para a habilidade, usa a carta e a descarta depois
                    if(chakra >= cartaSelecionada.getCusto()){
                        chakra -= cartaSelecionada.getCusto();
                        cartaSelecionada.usar_h(heroiEscolhido, inimigoEscolhido,this);
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
                    App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo);
                    App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo);
                }
                //caso o usuario chame o menu de efeitos
                else if(leitura == (maoJogador.size()+2)){
                    App.menuVenenoRegen(listaEfeitosCombate,entrada);
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
                    App.display(heroiEscolhido, inimigoEscolhido, chakra, maoJogador, movimentosInimigo);
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
                movimentosInimigo.get(0).usar_i(inimigoEscolhido, heroiEscolhido,this);
                System.out.println(movimentosInimigo.get(0).getNome());
            }

            //publisher avisando o fim do turno
            avisar(1);
            

            //escudo zerado depois de cada turno
            heroiEscolhido.zeraEscudo();
            inimigoEscolhido.zeraEscudo();

        }
        App.limparTela();
        System.out.println("𖣘 FIM DE JOGO! 𖣘");
        //quem permanecer vivo, vence a partida
        if(heroiEscolhido.estaVivo()){
            return heroiEscolhido.getNome() + " venceu!";
        } else{
            return inimigoEscolhido.getNome() + " venceu!";
        }

    }
}
