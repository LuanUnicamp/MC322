import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;

/**
 * Testa cada tipo de carta do projeto usando apenas classes e métodos que existem:
 * CartaDano, CartaDanoArea, CartaCuraRegen, CartaEscudoEspinhos, CartaRouboVida,
 * CartaSacrificio, CartaDanoVeneno, CartaEscudo, CartaEscudoRegen.
 */
public class CartasTest {

    private void prepararInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    // --- CartaDano ---

    @Test
    public void testCartaDanoHeroi() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaDano ataque = new CartaDano("Rasengan", "Ataque básico", 1, 20);
        ataque.usar_h(h, i, c);

        assertEquals(80, i.getVida(), "Inimigo deve perder 20 de vida");
    }

    @Test
    public void testCartaDanoInimigo() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaDano ataqueInimigo = new CartaDano("Ataque de Pain", "Dano", 0, 25);
        ataqueInimigo.usar_i(i, h, c);

        assertEquals(75, h.getVida(), "Herói deve perder 25 de vida quando inimigo ataca");
    }

    // --- CartaDanoArea ---

    @Test
    public void testCartaDanoAreaComCemPorCentoDeAcerto() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        // 100% de chance garante que o dano sempre é aplicado
        CartaDanoArea area = new CartaDanoArea("Gamabunta", "Dano área", 3, 30, 10, 100);
        area.usar_h(h, i, c);

        assertEquals(70, i.getVida(), "Com 100% de chance, o inimigo deve perder 30 de vida");
    }

    // --- CartaCuraRegen ---

    @Test
    public void testCartaCuraRegenCuraImediata() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        h.setVida(50);

        CartaCuraRegen cura = new CartaCuraRegen("Caminho Naraka", "Cura e regen", 0, 20, 5);
        cura.usar_h(h, i, c);

        assertEquals(70, h.getVida(), "A cura imediata deve restaurar 20 HP");
    }

    @Test
    public void testCartaCuraRegenInscreveEfeito() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        h.setVida(50);

        CartaCuraRegen cura = new CartaCuraRegen("Caminho Naraka", "Cura e regen", 0, 20, 5);
        cura.usar_h(h, i, c);

        assertFalse(c.getListaEfeitos().isEmpty(), "A regeneração deve ser inscrita na lista de efeitos do combate");
    }

    // --- CartaEscudoEspinhos ---

    @Test
    public void testCartaEscudoEspinhosAplicaEscudo() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaEscudoEspinhos espinhos = new CartaEscudoEspinhos("Susano'o", "Defesa", 3, 15, 10);
        espinhos.usar_h(h, i, c);

        assertEquals(15, h.getEscudo(), "O escudo do herói deve ser 15");
    }

    @Test
    public void testCartaEscudoEspinhosDanoImediato() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaEscudoEspinhos espinhos = new CartaEscudoEspinhos("Susano'o", "Defesa", 3, 15, 10);
        espinhos.usar_h(h, i, c);

        assertEquals(90, i.getVida(), "Inimigo deve sofrer 10 de dano de espinhos imediato");
    }

    // --- CartaRouboVida ---

    @Test
    public void testCartaRouboVidaDrenaInimigo() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        h.setVida(50);

        CartaRouboVida roubo = new CartaRouboVida("Roubo de Chakra", "Drena vida", 3, 20, 20);
        roubo.usar_h(h, i, c);

        assertEquals(80, i.getVida(), "Inimigo deve perder 20 de vida");
    }

    @Test
    public void testCartaRouboVidaCuraHeroi() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        h.setVida(50);

        CartaRouboVida roubo = new CartaRouboVida("Roubo de Chakra", "Drena vida", 3, 20, 20);
        roubo.usar_h(h, i, c);

        assertEquals(70, h.getVida(), "Herói deve recuperar 20 de vida roubada");
    }

    // --- CartaSacrificio ---

    @Test
    public void testCartaSacrificioAtacaInimigo() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaSacrificio sac = new CartaSacrificio("8 Portões", "Dano massivo", 3, 40, 20);
        sac.usar_h(h, i, c);

        assertEquals(60, i.getVida(), "Inimigo deve receber 40 de dano");
    }

    @Test
    public void testCartaSacrificioRecuoNoHeroi() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaSacrificio sac = new CartaSacrificio("8 Portões", "Dano massivo", 3, 40, 20);
        sac.usar_h(h, i, c);

        assertEquals(80, h.getVida(), "Herói deve perder 20 de vida como dano de recuo");
    }

    // --- CartaDanoVeneno ---

    @Test
    public void testCartaDanoVenenoDanoImediato() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaDanoVeneno veneno = new CartaDanoVeneno("Shuriken Venenosa", "Envenena", 2, 10, 5);
        veneno.usar_h(h, i, c);

        assertEquals(90, i.getVida(), "Inimigo deve perder 10 de vida imediatamente");
    }

    @Test
    public void testCartaDanoVenenoInscreveEfeito() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaDanoVeneno veneno = new CartaDanoVeneno("Shuriken Venenosa", "Envenena", 2, 10, 5);
        veneno.usar_h(h, i, c);

        assertEquals(1, c.getListaEfeitos().size(), "O veneno deve ser inscrito no motor de combate");
    }

    // --- CartaEscudo ---

    @Test
    public void testCartaEscudoAplicaEscudo() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaEscudo escudo = new CartaEscudo("Jutsu de Substituição", "Defesa básica", 1, 10);
        escudo.usar_h(h, i, c);

        assertEquals(10, h.getEscudo(), "Herói deve ganhar 10 de escudo");
    }

    // --- CartaEscudoRegen ---

    @Test
    public void testCartaEscudoRegenAplicaEscudo() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaEscudoRegen escudoRegen = new CartaEscudoRegen("Clone das Sombras", "Defesa com regen", 2, 15, 3);
        escudoRegen.usar_h(h, i, c);

        assertEquals(15, h.getEscudo(), "Herói deve ganhar 15 de escudo");
    }

    @Test
    public void testCartaEscudoRegenInscreveEfeito() {
        prepararInput("\n");
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();

        CartaEscudoRegen escudoRegen = new CartaEscudoRegen("Clone das Sombras", "Defesa com regen", 2, 15, 3);
        escudoRegen.usar_h(h, i, c);

        assertFalse(c.getListaEfeitos().isEmpty(), "O efeito de regen deve ser inscrito na lista de efeitos");
    }
}