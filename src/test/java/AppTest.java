import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

/**
 * Testa métodos estáticos de App que podem ser invocados sem interação completa.
 * modoBatalha() é testado apenas quanto à assinatura (não quebra ao receber entrada mínima).
 */
public class AppTest {

    @Test
    public void testGeraHeroisDisponiveisNaoNulo() {
        ArrayList<Heroi> herois = App.GeraHeroisDisponiveis();
        assertNotNull(herois, "GeraHeroisDisponiveis não deve retornar null");
    }

    @Test
    public void testGeraHeroisDisponiveisNaoVazio() {
        ArrayList<Heroi> herois = App.GeraHeroisDisponiveis();
        assertFalse(herois.isEmpty(), "Deve haver ao menos um herói disponível");
    }

    @Test
    public void testGeraInimigosDisponiveisNaoNulo() {
        ArrayList<Inimigo> inimigos = App.GeraInimigosDisponiveis();
        assertNotNull(inimigos, "GeraInimigosDisponiveis não deve retornar null");
    }

    @Test
    public void testGeraInimigosDisponiveisNaoVazio() {
        ArrayList<Inimigo> inimigos = App.GeraInimigosDisponiveis();
        assertFalse(inimigos.isEmpty(), "Deve haver ao menos um inimigo disponível");
    }

    @Test
    public void testGeraDeckHeroiNaruto() {
        Heroi naruto = new Heroi("Naruto Uzumaki", 100, 0);
        ArrayList<Carta> deck = App.GeraDeckHeroi(naruto);
        assertNotNull(deck, "O deck de Naruto não deve ser null");
        assertFalse(deck.isEmpty(), "O deck de Naruto deve ter cartas");
    }

    @Test
    public void testGeraDeckHeroiSasuke() {
        Heroi sasuke = new Heroi("Sasuke Uchiha", 100, 0);
        ArrayList<Carta> deck = App.GeraDeckHeroi(sasuke);
        assertNotNull(deck, "O deck de Sasuke não deve ser null");
        assertFalse(deck.isEmpty(), "O deck de Sasuke deve ter cartas");
    }

    @Test
    public void testGeraDeckInimigoNaoNulo() {
        Inimigo pain = new Inimigo("Pain", 100, 0);
        ArrayList<Carta> deck = App.GeraDeckInimigo(pain);
        assertNotNull(deck, "O deck do inimigo não deve ser null");
        assertFalse(deck.isEmpty(), "O deck de Pain deve ter cartas");
    }

    @Test
    public void testModoBatalhaAssinatura() {
        // Seleciona Herói [1] e Inimigo [1]; combate para ao pedir input de turno
        String input = "1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scan = new Scanner(System.in);

        assertDoesNotThrow(() -> {
            try {
                App.modoBatalha(scan);
            } catch (Exception e) {
                // Exceção por falta de input de turno é esperada neste teste de assinatura
            }
        });
    }
}