import java.util.ArrayList;

/**
 * Gerencia o estado persistente do herói Naruto e seu inventário
 * ao longo de toda a progressão do Modo História.
 * <br>
 * Centraliza em um único objeto todos os dados que evoluem entre as fases:
 * vida, deck, moedas, itens consumíveis e transformação desbloqueada.
 */
public class Herois {

    private Heroi naruto;
    private ArrayList<Carta> deckNaruto;

    private int shinobiCoins;
    private int vidaMaxima;
    private int qtdBandagem;
    private int qtdShuriken;
    private int qtdBandana;

    // 0 = sem transformacao, 1 = modo senin, 2 = modo kurama
    private int transformacao;

    /**
     * Construtor da classe Herois.
     * <br>
     * <b>Comportamento:</b> Cria o perfil inicial do Naruto (Genin), monta seu deck
     * básico de quatro cartas e zera todos os contadores de moedas e itens.
     */
    public Herois() {
        this.naruto = new Heroi("Naruto (Genin)", 50, 0);
        this.deckNaruto = new ArrayList<>();

        this.vidaMaxima = 50;
        this.shinobiCoins = 0;
        this.qtdBandagem = 0;
        this.qtdShuriken = 0;
        this.qtdBandana = 0;
        this.transformacao = 0;

        // habilidades básicas do personagem ao iniciar
        this.deckNaruto.add(new CartaEscudo("Jutsu Clone das Sombras", "Naruto gera muitos clones para se proteger do inimigo", 2, 10));
        this.deckNaruto.add(new CartaDano("Rasengan", "Principal jutsu de ataque de Naruto", 3, 15));
        this.deckNaruto.add(new CartaDano("Taijutsu", "Ataque físico ao inimigo", 1, 7));
        this.deckNaruto.add(new CartaEscudo("Jutsu de Substituição", "Ao ser atacado se substitui por um tronco para se defender", 1, 5));
    }

    // --- GETTERS ---

    public Heroi getNaruto() {
        return naruto;
    }

    public ArrayList<Carta> getDeckNaruto() {
        return deckNaruto;
    }

    public int getShinobiCoins() {
        return shinobiCoins;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getQtdBandagem() {
        return qtdBandagem;
    }

    public int getQtdShuriken() {
        return qtdShuriken;
    }

    public int getQtdBandana() {
        return qtdBandana;
    }

    public int getTransformacao() {
        return transformacao;
    }

    // --- SETTERS ---

    public void setNaruto(Heroi naruto) {
        this.naruto = naruto;
    }

    public void setShinobiCoins(int shinobiCoins) {
        this.shinobiCoins = shinobiCoins;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public void setQtdBandagem(int qtdBandagem) {
        this.qtdBandagem = qtdBandagem;
    }

    public void setQtdShuriken(int qtdShuriken) {
        this.qtdShuriken = qtdShuriken;
    }

    public void setQtdBandana(int qtdBandana) {
        this.qtdBandana = qtdBandana;
    }

    public void setTransformacao(int transformacao) {
        this.transformacao = transformacao;
    }
}