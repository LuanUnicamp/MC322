import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartasTest {

    // --- CARTAS BÁSICAS ---

    @Test
    public void testCartaDanoArea() {
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        CartaDanoArea area = new CartaDanoArea("Gamabunta", "Dano em área", 10, 30, 10, 100);
        
        area.usar_h(h, i, c);
        assertEquals(70, i.getVida());
    }

    @Test
    public void testCartaCuraRegen() {
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        h.setVida(50); 
        CartaCuraRegen cura = new CartaCuraRegen("Pílula", "Cura e regen", 5, 20, 5);
        
        cura.usar_h(h, i, c);
        assertEquals(70, h.getVida());
    }

    @Test
    public void testCartaEscudo() {
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        CartaEscudo escudo = new CartaEscudo("Escudo de Areia", "Proteção", 5, 20);
        
        escudo.usar_h(h, i, c);
        assertEquals(20, h.getEscudo());
    }

    @Test
    public void testCartaSacrificio() {
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        CartaSacrificio carta = new CartaSacrificio("Selo Consumidor", "Dano mútuo", 10, 40, 20);
        
        carta.usar_h(h, i, c);
        assertEquals(60, i.getVida(), "Inimigo perde 40");
        assertEquals(80, h.getVida(), "Herói perde 20 de recuo");
    }

    @Test
    public void testCartaRouboVida() {
        Heroi h = new Heroi("Naruto", 100, 0); 
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        
        h.setVida(50); // Naruto começa com 50
        CartaRouboVida carta = new CartaRouboVida("Fome de Chakra", "Drena vida", 5, 20, 20);
        
        carta.usar_h(h, i, c);
        
        
        assertEquals(80, i.getVida(), "Inimigo deveria ter 80 de vida");
        assertEquals(70, h.getVida(), "Herói deveria ter 70 de vida");
    }

    @Test
    public void testCartaDanoVeneno() {
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        CartaDanoVeneno carta = new CartaDanoVeneno("Kunai Envenenada", "Dano + Veneno", 5, 10, 5);
        
        carta.usar_h(h, i, c);
        assertEquals(90, i.getVida(), "Dano direto de 10");
        assertEquals(5, carta.getVeneno());
    }

    @Test
    public void testLogicaDeEfeitosECombate() {
        Heroi h = new Heroi("Naruto", 100, 0);
        Inimigo i = new Inimigo("Pain", 100, 0);
        Combate c = new Combate();
        
       
        Veneno v = new Veneno("Veneno Letal", i, 10, "VENENO");
        i.aplicarEfeito(v); 
        v.avisado(1); 
        v.avisado(0);
        
    
        Regen r = new Regen("Cura Contínua", h, 10, "REGEN");
        h.aplicarEfeito(r);
        r.avisado(1); 
        r.avisado(0);
        
        
        c.inscreverEfeito(v);
        c.inscreverEfeito(r);
        c.inscreverEfeito(v); 

        // Testa as transformações
        c.habilitarEvolucao(1);
        c.habilitarEvolucao(2);
    }
}