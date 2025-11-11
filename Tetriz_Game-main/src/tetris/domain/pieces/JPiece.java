package tetris.domain.pieces;

import tetris.domain.Posicao;
import tetris.domain.Tetromino;
import tetris.domain.TipoTetromino;

import java.awt.Color;

public class JPiece extends Tetromino {

    private final boolean[][][] formas;
    private int estadoRotacao;

    public JPiece() {
        this.posicao = new Posicao(4, 0);
        this.cor = Color.BLUE;
        this.tipo = TipoTetromino.J;
        this.estadoRotacao = 0;

        formas = new boolean[4][][];

        // Estado 0
        formas[0] = new boolean[][]{
                {true, false, false},
                {true, true, true}
        };

        // Estado 1
        formas[1] = new boolean[][]{
                {true, true},
                {true, false},
                {true, false}
        };

        // Estado 2
        formas[2] = new boolean[][]{
                {true, true, true},
                {false, false, true}
        };

        // Estado 3
        formas[3] = new boolean[][]{
                {false, true},
                {false, true},
                {true, true}
        };

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
        if (estadoRotacao < 0) {
            estadoRotacao = 3;
        }
        this.forma = formas[estadoRotacao];
    }

    @Override
    public void readjustColor() {
        this.cor = Color.BLUE;
    }

}