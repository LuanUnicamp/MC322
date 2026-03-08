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
        CartaDano razengan = new CartaDano("Razengan", 1, 12); //instancia de uma carta de dano
        CartaDano kurama = new CartaDano("Kurama", 2, 20); //instancia de uma carta de dano
        CartaEscudo clone = new CartaEscudo("Clone das sombras", 1, 10); //instancia de uma carta de escudo

        int leitura;
        Scanner entrada = new Scanner(System.in);

        //o jogo acaba quando um dos dois oponentes tem estavivo() retornando false
        while(naruto.estaVivo() && madara.estaVivo()){
            int chakra = 3;
            Boolean insuficiente = false;
            limparTela();
            display(naruto, madara, chakra);

            while(chakra>0 && naruto.estaVivo() && madara.estaVivo()){
                leitura = entrada.nextInt();
                switch (leitura) {
                    case 1:
                        chakra--;
                        razengan.usar(madara);
                        break;
                    case 2:
                        if(chakra >= 2){
                            chakra -= 2;
                            kurama.usar(madara);
                        } else{
                            System.out.println("Chakra insuficiente para invocar Kurama!");
                            insuficiente = true;
                        }
                        break;
                    case 3:
                        chakra--;
                        clone.usar(naruto);
                        break;
                    case 4:
                        chakra = 0;
                        break;
                    default:
                        System.out.println("Opção Inválida!");
                }
                if (!insuficiente) {
                    limparTela();
                    display(naruto, madara, chakra);
                }
                insuficiente = false;

        }
        if (madara.getVida()>0) {
            madara.atacar(naruto);
        }
        naruto.zeraEscudo();
    }
    if(naruto.estaVivo()){
        System.out.println("Naruto Uzumaki venceu!");
    } else{
        System.out.println("Madara Uchiha venceu!");
    }
    entrada.close();
}
}
