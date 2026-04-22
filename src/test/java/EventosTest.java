import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

/**
 * Testa Escolha e Loja com os métodos reais do projeto.
 *
 * Escolha.apresentarEscolha(Scanner) retorna o índice base-0 da opção escolhida.
 *
 * Loja.perguntarLoja(Herois, Scanner) e Loja.iniciar(Herois, Scanner) usam Herois
 * (a classe de estado persistente do Modo História).
 */
public class EventosTest {

    // -----------------------------------------------------------------------
    // Testes de Escolha
    // -----------------------------------------------------------------------

    @Test
    public void testEscolhaRetornaIndiceZeroParaJiraiya() {
        // Digita "1" → índice 0 (Jiraiya)
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        Scanner scan = new Scanner(System.in);

        Escolha e = new Escolha();
        int resultado = e.apresentarEscolha(scan);

        assertEquals(0, resultado, "Opção 1 deve retornar índice 0 (Jiraiya)");
    }

    @Test
    public void testEscolhaRetornaIndiceUmParaKakashi() {
        // Digita "2" → índice 1 (Kakashi)
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        Scanner scan = new Scanner(System.in);

        Escolha e = new Escolha();
        int resultado = e.apresentarEscolha(scan);

        assertEquals(1, resultado, "Opção 2 deve retornar índice 1 (Kakashi)");
    }

    // -----------------------------------------------------------------------
    // Testes de Evento (imprimirFala não quebra com input vazio)
    // -----------------------------------------------------------------------

    @Test
    public void testEventoImprimirFalaNaoLancaExcecao() {
        System.setIn(new ByteArrayInputStream("\n".getBytes()));
        Scanner scan = new Scanner(System.in);
        Evento evento = new Evento();

        assertDoesNotThrow(() ->
            evento.imprimirFala("Naruto: 'Eu nunca desisto!'", scan),
            "imprimirFala não deve lançar exceção"
        );
    }

    // -----------------------------------------------------------------------
    // Testes de Loja (via Herois — classe de estado do Modo História)
    // -----------------------------------------------------------------------

    @Test
    public void testLojaComprarBandagemReduzMoedas() {
        // Herois inicia com getShinobiCoins() == 0; adicionamos via setShinobiCoins
        Herois estado = new Herois();
        estado.setShinobiCoins(100);
        estado.getNaruto().setVida(50); // vida baixa para poder comprar cura

        // Simula: comprar Bandagem (opção 1) depois Sair (última opção do menu da loja)
        // O menu da Loja varia; ajuste os números caso seu menu seja diferente.
        // 1 (Entrar) -> 1 (Comprar) -> \n (Enter de confirmação) -> 4 (Sair)
        System.setIn(new ByteArrayInputStream("1\n1\n\n4\n".getBytes()));
        Scanner scan = new Scanner(System.in);

        Loja loja = new Loja();
        loja.perguntarLoja(estado, scan);

        assertTrue(estado.getShinobiCoins() < 100,
            "As moedas devem diminuir após uma compra na loja");
    }

    @Test
    public void testLojaSairSemComprarNaoAlteraVida() {
        Herois estado = new Herois();
        estado.setShinobiCoins(100);
        int vidaAntes = estado.getNaruto().getVida();

        // Simula apenas: Sair da loja sem comprar nada
        // Ajuste o número de acordo com a opção "Sair" do seu menu
        // Apenas o 2 para dizer "Não" na pergunta inicial "Deseja visitar...?"
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        Scanner scan = new Scanner(System.in);

        Loja loja = new Loja();
        try {
            loja.perguntarLoja(estado, scan);
        } catch (Exception e) {
            // ignora fim de stream
        }

        assertEquals(vidaAntes, estado.getNaruto().getVida(),
            "A vida não deve mudar se o jogador sair sem comprar");
    }
}