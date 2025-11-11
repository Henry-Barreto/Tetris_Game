package tetris.domain.pieces;

import tetris.domain.Posicao;
import tetris.domain.Tetromino;
import tetris.domain.TipoTetromino;

import java.awt.Color;

public class ZPiece extends Tetromino {

    private final boolean[][][] formas;
    private int estadoRotacao;

    public ZPiece() {
        this.posicao = new Posicao(4, 0);
        this.cor = Color.RED;
        this.tipo = TipoTetromino.Z;
        this.estadoRotacao = 0;

        formas = new boolean[2][][]; // Só 2 estados

        // Estado 0 (Horizontal)
        formas[0] = new boolean[][]{
                {true, true, false},
                {false, true, true}
        };

        // Estado 1 (Vertical)
        formas[1] = new boolean[][]{
                {false, true},
                {true, true},
                {true, false}
        };

        this.forma = formas[this.estadoRotacao];
    }

    @Override
    public void rotacionar() {
        estadoRotacao = (estadoRotacao + 1) % 2;
        this.forma = formas[estadoRotacao];
    }

    @Override
    public void rotacionarReverso() {
        estadoRotacao = (estadoRotacao - 1);
        if (estadoRotacao < 0) {
            estadoRotacao = 1;
        }
        this.forma = formas[estadoRotacao];
    }

    @Override
    public void readjustColor() {
        this.cor = Color.RED;
    }

}