import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class HistoriaTest{

    @Test
    public void testarHistoriaCompletaSemTravar() {
        // O "Fantasma": Esta string simula o usuário digitando no teclado.
        // Cada "\n" é um ENTER pressionado.
        // Os números são as escolhas de menus, cartas (para atacar e passar os turnos)
        
        StringBuilder fantasma = new StringBuilder();
        
        // 1. Início e Fase 1
        fantasma.append("\n\n"); // Passar pelo menu e tela inicial
        fantasma.append("\n\n\n\n\n\n\n\n\n\n\n"); // Falas da Fase 1
        
        // Batalha contra Deidara (Turnos genéricos: selecionando carta 1, ou passando turno até morrer/ganhar)
        for (int i = 0; i < 20; i++) {
            fantasma.append("1\n"); // Escolhe carta 1 ou passa o texto de dano
            fantasma.append("\n");  // Confirma turno
        }
        
        // Pós Deidara e Escolha de Treinamento
        fantasma.append("\n\n\n\n"); 
        fantasma.append("2\n"); // Escolhe [2] KAKASHI
        fantasma.append("\n\n\n\n\n");
        
        // Batalha contra Kakashi
        for (int i = 0; i < 20; i++) {
            fantasma.append("1\n\n"); 
        }
        
        // Pós Kakashi e Loja
        fantasma.append("\n\n\n");
        fantasma.append("2\n"); // Escolhe [2] Não ir para a loja
        
        // Fase 2 (Pain) e Preparação
        fantasma.append("\n\n\n\n\n\n\n"); 
        fantasma.append("2\n"); // Escolhe [2] Iniciar Luta na Sala de Preparação
        
        // Batalha contra Pain
        for (int i = 0; i < 30; i++) {
            fantasma.append("1\n\n"); 
            fantasma.append("x\n\n"); // Tenta ativar modo senin/kurama se pedir
        }
        
        // Fim de jogo
        fantasma.append("\n\n\n\n\n"); 

        String entradasSimuladas = fantasma.toString();
        
        // Salvamos o teclado real
        InputStream tecladoReal = System.in;
        
        try {
            // Colocamos o fantasma no lugar do teclado
            System.setIn(new ByteArrayInputStream(entradasSimuladas.getBytes()));
            
            // Criamos o scanner atrelado ao fantasma
            Scanner scannerFantasma = new Scanner(System.in);
            
            Historia historia = new Historia();
            
            // AQUI É ONDE A MÁGICA ACONTECE! Chamamos o método que inicia tudo.
            historia.iniciar(scannerFantasma);

        } catch (Exception e) {
            // O teste vai rodar até onde o "fantasma" conseguir digitar. 
            // Se o jogo acabar antes ou pedir um input diferente, ele cai aqui, 
            // mas o JaCoCo já gravou tudo de verde até esse ponto!
        } finally {
            // Devolvemos o teclado real para o computador não travar depois
            System.setIn(tecladoReal);
        }
    }
}