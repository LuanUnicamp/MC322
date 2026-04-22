import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntidadeTest {

    @Test
    public void testReceberCuraNaoUltrapassaVidaMax() {
        // Criamos um herói com 50 de vida (que define vidaMax como 50)
        Heroi h = new Heroi("Naruto", 50, 0);
                
        h.setVida(30);
        
        h.receberCura(30);
        
        assertEquals(50, h.getVida(), "A cura não deve ultrapassar a vida máxima.");
    }

    @Test
    public void testReceberDanoComEscudo() {
        Heroi h = new Heroi("Naruto", 50, 20);
        
        h.receberDano(15);
        
        assertEquals(5, h.getEscudo());
        assertEquals(50, h.getVida());
    }

    @Test
    public void testReceberDanoMaiorQueEscudo() {
        Heroi h = new Heroi("Naruto", 50, 10);
        
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