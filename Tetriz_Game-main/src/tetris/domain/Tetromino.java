package tetris.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;
import tetris.domain.pieces.*;

public abstract class Tetromino implements Serializable {

    protected boolean[][] forma;
    protected transient Color cor;
    protected Posicao posicao;
    protected TipoTetromino tipo; // <-- MUDANÇA: Adicionado

    public abstract void rotacionar();
    public abstract void rotacionarReverso();
    public abstract void readjustColor();

    public void moverBaixo() {
        this.posicao = this.posicao.moverParaBaixo();
    }

    public void moverEsquerda() {
        this.posicao = this.posicao.moverParaEsquerda();
    }

    public void moverDireita() {
        this.posicao = this.posicao.moverParaDireita();
    }

    // Getters
    public boolean[][] getForma() { return forma; }
    public Color getCor() { return cor; }
    public Posicao getPosicao() { return posicao; }

    // MUDANÇA: Adicionado
    public TipoTetromino getTipo() {
        return tipo;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public static Tetromino gerarAleatorio() {
        TipoTetromino[] tipos = TipoTetromino.values();
        TipoTetromino tipoAleatorio = tipos[new Random().nextInt(tipos.length)];

        switch (tipoAleatorio) {
            case I: return new IPiece();
            case O: return new OPiece();
            case T: return new TPiece();
            case S: return new SPiece();
            case Z: return new ZPiece();
            case J: return new JPiece();
            case L: return new LPiece();
            default: return new OPiece();
        }
    }
}