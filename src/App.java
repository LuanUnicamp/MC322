import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Heroi h = new Heroi("Heroi", 40, 3); //instancia de heroi
        Inimigo i = new Inimigo("Inimigo", 20, 0); //instancia de inimigo
        CartaDano cd = new CartaDano("carta dano 1", 1); //instancia de uma carta de dano
        CartaEscudo ce = new CartaEscudo("carta escudo 1", 2); //instancia de uma carta de escudo

        int leitura;
        Scanner entrada = new Scanner(System.in);


        while(h.getVida()>0 && i.getVida()>0){
            int energia_disponivel=3;
            System.out.println(h.getNome()+" "+h.getVida()+" de vida "+h.getEscudo()+" de escudo");
            System.out.println("vs");
            System.out.println(i.getNome()+" "+i.getVida()+" de vida "+i.getEscudo()+" de escudo");

            System.out.println(energia_disponivel+"de Energia disponível");
            System.out.println("1 - Usar carta de dano");
            System.out.println("2 - Usar carta de escudo");
            System.out.println("3 - Encerrar turno");
            System.out.println("Escolha: ");
            if(energia_disponivel>0){
                leitura=entrada.nextInt();
                if (leitura==1) {
                    if(energia_disponivel-1>0){
                        energia_disponivel--;
                    }
                }else if(leitura==2){
                    energia_disponivel-=2;

                }else if(leitura==3){
                    //turno encerrado
                }
            }else if(energia_disponivel==0){
                //turno encerrado
            }

        }
    }
}
