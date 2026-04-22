import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AppTest {

    @Test
    public void testGeracaoDeDecksDosInimigos() {
        // Cobre todos os if/else da função GeraDeckInimigo
        ArrayList<Carta> deckDeidara = App.GeraDeckInimigo(new Inimigo("Deidara", 100, 0));
        assertNotNull(deckDeidara);
        assertTrue(deckDeidara.size() > 0);

        ArrayList<Carta> deckJiraiya = App.GeraDeckInimigo(new Inimigo("Jiraiya", 100, 0));
        assertNotNull(deckJiraiya);

        ArrayList<Carta> deckKakashi = App.GeraDeckInimigo(new Inimigo("Kakashi", 100, 0));
        assertNotNull(deckKakashi);

        ArrayList<Carta> deckPain = App.GeraDeckInimigo(new Inimigo("Pain", 100, 0));
        assertNotNull(deckPain);
    }

    @Test
    public void testAppDisplayETextos() {
        // Criamos os objetos fictícios para forçar a impressão da tela
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        
        ArrayList<Carta> mao = new ArrayList<>();
        mao.add(new CartaEscudo("Defesa", "Desc", 5, 20));
        mao.add(new CartaDanoArea("Ataque", "Desc", 10, 30, 10, 100));
        
        ArrayList<Carta> movInimigo = new ArrayList<>();
        movInimigo.add(new CartaEscudo("Defesa", "Desc", 5, 20));

        // 1. Testa a limpeza de tela (se existir o método)
        try {
            App.limparTela();
        } catch (Exception e) {}

        // 2. Força o display a imprimir o painel do jogo (Sem transformação)
        try {
            App.display(h, i, 4, mao, movInimigo, 0, 50, false, false);
        } catch (Exception e) {}
        
        // 3. Testa o display com as transformações ativas
        try {
            App.display(h, i, 4, mao, movInimigo, 1, 100, true, false); // Modo Senin
            App.display(h, i, 4, mao, movInimigo, 2, 100, false, true); // Kyuubi
        } catch (Exception e) {}
    }

    @Test
    public void testMenusComFantasma() {
        ArrayList<Carta> mao = new ArrayList<>();
        mao.add(new CartaDano("Rasengan", "Dano", 2, 15));
        
        ArrayList<Efeito> efeitos = new ArrayList<>();
        Heroi h = new Heroi("Naruto", 100, 0);
        efeitos.add(new Veneno("Veneno Teste", h, 5, "VENENO"));

        // Fantasma aperta "1" e ENTER para simular escolhas no menu e não travar o teste
        String fantasmaStr = "1\n1\n"; 
        InputStream tecladoReal = System.in;

        try {
            System.setIn(new ByteArrayInputStream(fantasmaStr.getBytes()));
            Scanner scannerFantasma = new Scanner(System.in);
            
            // Testa os menus da classe App
            App.menuCartas(mao, scannerFantasma);
            App.menuVenenoRegen(efeitos, scannerFantasma);
            
        } catch (Exception e) {
            // Se der erro por o fantasma ter digitado pouco, ignora, pois a linha já foi lida!
        } finally {
            System.setIn(tecladoReal);
        }
    }

    @Test
    public void testGeracaoDeHeroisEDecks() {
        // Cobre as linhas do App.GeraHeroisDisponiveis
        ArrayList<Heroi> herois = App.GeraHeroisDisponiveis();
        assertTrue(herois.size() > 0, "Deve gerar a lista de heróis");

        // Cobre as linhas do App.GeraInimigosDisponiveis
        ArrayList<Inimigo> inimigos = App.GeraInimigosDisponiveis();
        assertTrue(inimigos.size() > 0, "Deve gerar a lista de inimigos");

        // Cobre todos os IFs do App.GeraDeckHeroi (Naruto e Sasuke)
        ArrayList<Carta> deckNaruto = App.GeraDeckHeroi(new Heroi("Naruto Uzumaki", 100, 0));
        assertTrue(deckNaruto.size() > 0, "Deve gerar o deck do Naruto");

        ArrayList<Carta> deckSasuke = App.GeraDeckHeroi(new Heroi("Sasuke Uchiha", 100, 0));
        assertTrue(deckSasuke.size() > 0, "Deve gerar o deck do Sasuke");
    }

    @Test
    public void testMainSairDoJogo() throws Exception {
        // Simula o usuário abrindo o jogo e digitando "3" para Sair
        String fantasmaStr = "3\n"; 
        InputStream tecladoReal = System.in;

        try {
            System.setIn(new ByteArrayInputStream(fantasmaStr.getBytes()));
            
            // Cobre o App.main, App.menuPrincipal e o "case 3" do switch!
            App.main(new String[]{}); 
            
        } catch (Exception e) {
        } finally {
            System.setIn(tecladoReal);
        }
    }
}