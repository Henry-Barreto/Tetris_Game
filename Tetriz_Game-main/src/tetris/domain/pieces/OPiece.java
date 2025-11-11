package tetris.domain.pieces;

import tetris.domain.Posicao;
import tetris.domain.Tetromino;
import tetris.domain.TipoTetromino;

import java.awt.Color;

public class OPiece extends Tetromino {

    public OPiece() {
        this.posicao = new Posicao(4, 0);
        this.cor = Color.YELLOW;
        this.tipo = TipoTetromino.O;
        this.forma = new boolean[][]{
                {true, true},
                {true, true}
        };
    }

    @Override
    public void rotacionar() {
        // Não faz nada
    }

    @Override
    public void rotacionarReverso() {
        // Não faz nada
    }

    @Override
    public void readjustColor() {
        this.cor = Color.YELLOW;
    }

}