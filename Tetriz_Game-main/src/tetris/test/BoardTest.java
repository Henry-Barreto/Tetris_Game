package tetris.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tetris.domain.Board;
import tetris.domain.Posicao;
import tetris.domain.Tetromino;
import tetris.domain.TipoTetromino; // <-- MUDANÇA: Importado
import tetris.domain.pieces.OPiece;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        board = new Board();
    }

    @Test
    void devePermitirPosicaoValida() {
        Tetromino peca = new OPiece();
        peca.setPosicao(new Posicao(5, 5));
        assertTrue(board.posicaoValida(peca));
    }

    @Test
    void deveDetectarPosicaoInvalidaForaDoLimite() {
        Tetromino peca = new OPiece();
        peca.setPosicao(new Posicao(-1, 5));
        assertFalse(board.posicaoValida(peca));
    }

    @Test
    void deveDetectarColisaoComOutraPeca() {
        // MUDANÇA: O grid agora é de TipoTetromino
        TipoTetromino[][] grid = board.getGrid();
        grid[5][5] = TipoTetromino.O; // Põe uma peça tipo O

        Tetromino peca = new OPiece();
        peca.setPosicao(new Posicao(4, 4));

        assertFalse(board.posicaoValida(peca), "Deveria detectar colisão em (5,5)");
    }

    @Test
    void deveEliminarLinhaCompleta() {
        // MUDANÇA: O grid agora é de TipoTetromino
        TipoTetromino[][] grid = board.getGrid();

        for (int x = 0; x < Board.LARGURA; x++) {
            // MUDANÇA: Preenche com peças tipo I
            grid[Board.ALTURA - 1][x] = TipoTetromino.I;
        }

        int eliminadas = board.eliminarLinhasCompletas();

        assertEquals(1, eliminadas, "Deveria ter eliminado exatamente 1 linha");
        // MUDANÇA: Verifica se a célula está nula
        assertNull(grid[Board.ALTURA - 1][0], "Célula (19,0) deveria estar vazia (null)");
    }
}