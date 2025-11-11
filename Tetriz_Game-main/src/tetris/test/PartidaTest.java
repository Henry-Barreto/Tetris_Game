package tetris.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tetris.domain.Board;
import tetris.domain.Jogador;
import tetris.domain.Partida;
import tetris.domain.Posicao;
import tetris.domain.TipoTetromino; // <-- Import necessário

public class PartidaTest {

    private Partida partida;

    @BeforeEach
    void setup() {
        Jogador jogador = new Jogador("Tester");
        partida = new Partida(jogador);
    }

    @Test
    void deveInicializarPartidaCorretamente() {
        assertNotNull(partida.getJogador());
        assertNotNull(partida.getBoard());
        assertNotNull(partida.getSistemaPontuacao());
        assertNotNull(partida.getPecaAtual());
        assertFalse(partida.isGameOver());
    }

    @Test
    void deveExecutarTickSemGameOver() {
        partida.tick();
        assertFalse(partida.isGameOver(), "Tick não deveria causar Game Over");
    }

    @Test
    void deveAtivarGameOverQuandoPecaNovaNaoCouber() {
        Board board = partida.getBoard();

        // MUDANÇA AQUI: O grid agora é de TipoTetromino[][]
        TipoTetromino[][] grid = board.getGrid();

        // Preenche a linha 1 (onde a peça nasce)
        for (int x = 0; x < Board.LARGURA; x++) {
            // MUDANÇA AQUI: Preenche com um tipo, não 'true'
            grid[1][x] = TipoTetromino.I;
        }

        // Força a peça atual a colidir
        partida.getPecaAtual().setPosicao(new Posicao(4, 0));

        partida.tick();

        assertTrue(partida.isGameOver(), "Deveria ser Game Over");
    }
}