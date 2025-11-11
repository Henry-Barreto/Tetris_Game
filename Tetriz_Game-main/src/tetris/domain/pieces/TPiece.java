package tetris.domain.pieces;

import tetris.domain.Posicao;
import tetris.domain.Tetromino;
import tetris.domain.TipoTetromino; // <-- Importar, se necessário
import java.awt.Color;

public class TPiece extends Tetromino {

    private final boolean[][][] formas;
    private int estadoRotacao;

    public TPiece() {
        this.posicao = new Posicao(4, 0);
        this.cor = Color.MAGENTA;
        this.tipo = TipoTetromino.T; // <-- MUDANÇA: Adicionado
        this.estadoRotacao = 0;

        // ... (resto do construtor) ...
        formas = new boolean[4][][];
        formas[0] = new boolean[][] { { false, true, false }, { true,  true, true  } };
        formas[1] = new boolean[][] { { true, false }, { true, true  }, { true, false } };
        formas[2] = new boolean[][] { { true,  true, true  }, { false, true, false } };
        formas[3] = new boolean[][] { { false, true }, { true,  true }, { false, true } };
        this.forma = formas[this.estadoRotacao];
    }

    @Override
    public void rotacionar() {
        estadoRotacao = (estadoRotacao + 1) % 4;
        this.forma = formas[estadoRotacao];
    }

    @Override
    public void rotacionarReverso() {
        estadoRotacao = (estadoRotacao - 1);
        if (estadoRotacao < 0) { estadoRotacao = 3; }
        this.forma = formas[estadoRotacao];
    }

    @Override
    public void readjustColor() {
        this.cor = Color.MAGENTA;
    }
}