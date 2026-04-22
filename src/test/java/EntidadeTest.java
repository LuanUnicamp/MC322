import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa a lógica de Entidade/Herói usando apenas métodos reais do projeto:
 * receberCura(), receberDano(), estaVivo(), getVida(), getEscudo(), zeraEscudo().
 */
public class EntidadeTest {

    @Test
    public void testCuraSimples() {
        Heroi h = new Heroi("Naruto", 50, 0);
        h.setVida(20);
        h.receberCura(15);
        assertEquals(35, h.getVida(), "Cura simples deveria somar à vida atual");
    }

    @Test
    public void testCuraNaoUltrapassaVidaMaxima() {
        Heroi h = new Heroi("Naruto", 50, 0);
        h.setVida(20);
        h.receberCura(999);
        assertEquals(50, h.getVida(), "A cura não deve ultrapassar a vidaMax");
    }

    @Test
    public void testDanoSemEscudo() {
        Heroi h = new Heroi("Naruto", 100, 0);
        h.receberDano(30);
        assertEquals(70, h.getVida(), "Dano sem escudo deve reduzir a vida diretamente");
    }

    @Test
    public void testDanoComEscudoInsuficiente() {
        Heroi h = new Heroi("Naruto", 100, 10);
        h.receberDano(25); // 10 de escudo absorve, 15 vai para a vida
        assertEquals(0, h.getEscudo(), "Escudo deve ser zerado após absorção");
        assertEquals(85, h.getVida(), "Vida deve perder o excedente do escudo");
    }

    @Test
    public void testDanoComEscudoSuficiente() {
        Heroi h = new Heroi("Naruto", 100, 30);
        h.receberDano(20); // escudo absorve tudo
        assertEquals(10, h.getEscudo(), "Escudo deve ser reduzido pelo dano");
        assertEquals(100, h.getVida(), "Vida não deve ser afetada quando escudo absorve tudo");
    }

    @Test
    public void testMorteQuandoVidaZera() {
        Heroi h = new Heroi("Naruto", 10, 0);
        assertTrue(h.estaVivo(), "Herói deveria estar vivo com 10 de vida");
        h.receberDano(15);
        assertFalse(h.estaVivo(), "Herói deveria estar morto após dano letal");
        assertEquals(0, h.getVida(), "Vida não pode ser negativa");
    }

    @Test
    public void testSetVidaNaoUltrapassaMax() {
        Heroi h = new Heroi("Naruto", 50, 0);
        h.setVida(200);
        assertEquals(50, h.getVida(), "setVida não deve permitir ultrapassar vidaMax");
    }

    @Test
    public void testSetVidaNaoFicaNegativa() {
        Heroi h = new Heroi("Naruto", 50, 0);
        h.setVida(-10);
        assertEquals(0, h.getVida(), "setVida não deve permitir valor negativo");
    }

    @Test
    public void testZeraEscudo() {
        Heroi h = new Heroi("Naruto", 100, 30);
        h.zeraEscudo();
        assertEquals(0, h.getEscudo(), "zeraEscudo deve zerar o escudo completamente");
    }

    @Test
    public void testGanharEscudo() {
        Heroi h = new Heroi("Naruto", 100, 0);
        h.ganharEscudo(20);
        assertEquals(20, h.getEscudo(), "ganharEscudo deve somar ao escudo atual");
    }
}