import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Heroi naruto = new Heroi("Naruto Uzumaki", 40, 0); //instancia de heroi
        Inimigo madara = new Inimigo("Madara Uchiha", 30, 0, 15); //instancia de inimigo
        CartaDano razengan = new CartaDano("Razengan", 1, 12); //instancia de uma carta de dano
        CartaDano kurama = new CartaDano("Kurama", 2, 20); //instancia de uma carta de dano
        CartaEscudo clone = new CartaEscudo("Clone das sombras", 1, 10); //instancia de uma carta de escudo

        int leitura;
        Scanner entrada = new Scanner(System.in);


        while(naruto.estaVivo() && madara.estaVivo()){
            int chakra = 3;
            System.out.println(naruto.getNome()+" ("+naruto.getVida()+") de vida ("+naruto.getEscudo()+") de escudo");
            System.out.println("vs");
            System.out.println(madara.getNome()+" ("+madara.getVida()+") de vida ("+madara.getEscudo()+") de escudo");

            System.out.println(chakra + "/3 de Energia disponível");
            System.out.println("1 - Usar carta Razengan");
            System.out.println("2 - Usar carta Kurama");
            System.out.println("3 - Usar carta Jutsu Clone das Sombras");
            System.out.println("4 - Encerrar turno");
            System.out.println("Escolha: ");

            while(chakra>0){
                leitura = entrada.nextInt();
                if (leitura == 1) {
                    chakra--;
                    razengan.usar(madara);
                }else if(leitura == 2){
                    if(chakra >= 2){
                        chakra -= 2;
                        kurama.usar(madara);
                    } else {
                        System.out.println("Você não tem chakra suficiente para usar esse jutsu");
                    }
                }else if(leitura == 3){
                    chakra--;
                    clone.usar(naruto);
                }else if(leitura == 4){
                    chakra = 0;
                } else{
                System.out.println("Opção inválida");
                }
        }
        madara.atacar(naruto);
    }
    if(naruto.estaVivo()){
        System.out.println("Naruto venceu!");
    } else{
        System.out.println("Madara venceu!");
    }
    entrada.close();
}
}
