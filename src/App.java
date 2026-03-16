import java.util.Scanner;

public class App {

    //metodo que contem o display que atualiza a cada nova entrada do usuário
    public static void display(Heroi naruto, Inimigo madara, int chakra){
        naruto.zeraVida();
        madara.zeraVida();
        System.out.println("======----======----======----======----======----======");
        System.out.println(naruto.getNome()+" ("+naruto.getVida()+") de vida ("+naruto.getEscudo()+") de escudo");
        System.out.println("vs");
        System.out.println(madara.getNome()+" ("+madara.getVida()+") de vida ("+madara.getEscudo()+") de escudo");
        System.out.println(chakra + "/3 de Energia disponível");
        System.out.println("1 - Usar carta Razengan" + " (1 de Chakra)");
        System.out.println("2 - Usar carta Kurama" + " (2 de Chakra)");
        System.out.println("3 - Usar carta Jutsu Clone das Sombras" + " (1 de Chakra)");
        System.out.println("4 - Encerrar turno");
        System.out.println("Escolha: ");
        System.out.println("======----======----======----======----======----======");
    }

    //metodo que "limpa" o terminal para dar um efeito de que o status dos personagens está sendo atualizado
    public static void limparTela(){
        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        Heroi naruto = new Heroi("Naruto Uzumaki", 40, 0); //instancia de heroi
        Inimigo madara = new Inimigo("Madara Uchiha", 50, 0, 25); //instancia de inimigo
        CartaDano razengan = new CartaDano("Razengan","ataque razengan" ,1, 12); //instancia de uma carta de dano
        CartaDano kurama = new CartaDano("Kurama","ataque kurama", 2, 20); //instancia de uma carta de dano
        CartaEscudo clone = new CartaEscudo("Clone das sombras","escudo clone das sombras", 1, 10); //instancia de uma carta de escudo

        int leitura;
        Scanner entrada = new Scanner(System.in);

        //o jogo acaba quando um dos dois oponentes tem estavivo() retornando false
        while(naruto.estaVivo() && madara.estaVivo()){
            int chakra = 3;
            ///variável que indica quando o heroi não tem chackra suficiente para usar habilidade
            Boolean insuficiente = false;
            limparTela();
            display(naruto, madara, chakra);

            //enquanto tiver chackra disponível e os dois estiverem vivos, o turno é o mesmo
            while(chakra>0 && naruto.estaVivo() && madara.estaVivo()){
                leitura = entrada.nextInt();
                //switch case para mapear a escolha do usuário
                switch (leitura) {
                    case 1:
                        chakra--;
                        razengan.usar(naruto,madara);
                        break;
                    case 2:
                        if(chakra >= 2){
                            chakra -= 2;
                            kurama.usar(naruto,madara);
                        } else{
                            System.out.println("Chakra insuficiente para invocar Kurama!");
                            insuficiente = true;
                        }
                        break;
                    case 3:
                        chakra--;
                        clone.usar(naruto,madara);
                        break;
                    case 4:
                        chakra = 0;
                        break;
                    default:
                        System.out.println("Opção Inválida!");
                }
                //caso o chackra seja insuficiente não vai limpar a tela e chamar display, para que a mensagem
                //de que chackra não é suficiente para a habilidade apareça no terminal
                if (!insuficiente) {
                    limparTela();
                    display(naruto, madara, chakra);
                }
                insuficiente = false;

        }
        //inimigo ataca automaticamente no final de cada turno, mas só se ele estiver vivo
        if (madara.getVida()>0) {
            madara.atacar(naruto);
        }
        //escudo zerado depois de cada turno
        naruto.zeraEscudo();
    }
    //quem permanecer vivo, vence a partida
    if(naruto.estaVivo()){
        System.out.println("Naruto Uzumaki venceu!");
    } else{
        System.out.println("Madara Uchiha venceu!");
    }
    entrada.close();
}
}
