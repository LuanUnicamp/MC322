import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntidadeTest {

    @Test
    public void testReceberCuraNaoUltrapassaVidaMax() {
        // Criamos um herói com 50 de vida (que define vidaMax como 50)
        Heroi h = new Heroi("Naruto", 50, 0);
        
        // Simulamos que ele perdeu vida
        h.setVida(30);
        
        // Aplicamos uma cura de 30 (30 + 30 = 60)
        h.receberCura(30);
        
        // O teste verifica se a vida travou em 50 (vidaMax) e não foi para 60
        assertEquals(50, h.getVida(), "A cura não deve ultrapassar a vida máxima.");
    }

    @Test
    public void testReceberDanoComEscudo() {
        // Naruto com 50 de vida e 20 de escudo
        Heroi h = new Heroi("Naruto", 50, 20);
        
        // Pain dá 15 de dano
        h.receberDano(15);
        
        // O escudo deve absorver tudo: 20 - 15 = 5
        assertEquals(5, h.getEscudo());
        assertEquals(50, h.getVida());
    }

    @Test
    public void testReceberDanoMaiorQueEscudo() {
        // Naruto com 50 de vida e 10 de escudo
        Heroi h = new Heroi("Naruto", 50, 10);
        
        // Dano de 25: 10 tirado do escudo, 15 tirado da vida
        h.receberDano(25);
        
        assertEquals(0, h.getEscudo());
        assertEquals(35, h.getVida());
    }

    @Test
    public void testEstaVivo() {
        Heroi h = new Heroi("Naruto", 10, 0);
        assertTrue(h.estaVivo());
        
        h.receberDano(10);
        assertFalse(h.estaVivo());
    }
}