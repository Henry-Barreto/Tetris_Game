package tetris.domain;

import java.io.Serializable;
import java.util.Arrays;

public class Board implements Serializable {

    public static final int LARGURA = 10;
    public static final int ALTURA = 20;

    // MUDANÇA: O grid agora armazena o TIPO da peça, ou null
    private TipoTetromino[][] grid;

    public Board() {
        // MUDANÇA: O grid agora é de TipoTetromino
        this.grid = new TipoTetromino[ALTURA][LARGURA];
    }

    // MUDANÇA: O tipo de retorno
    public TipoTetromino[][] getGrid() { return grid; }

    public boolean posicaoValida(Tetromino t) {
        boolean[][] forma = t.getForma();
        Posicao p = t.getPosicao();

        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j]) {
                    int x = p.getX() + j;
                    int y = p.getY() + i;

                    if (x < 0 || x >= LARGURA || y >= ALTURA) {
                        return false;
                    }

                    // MUDANÇA: Verifica se a célula não é nula
                    if (y >= 0 && grid[y][x] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void fixarPeca(Tetromino t) {
        boolean[][] forma = t.getForma();
        Posicao p = t.getPosicao();

        // MUDANÇA: Pega o tipo da peça
        TipoTetromino tipoDaPeca = t.getTipo();

        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j]) {
                    int x = p.getX() + j;
                    int y = p.getY() + i;
                    if (y >= 0 && y < ALTURA && x >= 0 && x < LARGURA) {
                        // MUDANÇA: Salva o TIPO, não 'true'
                        grid[y][x] = tipoDaPeca;
                    }
                }
            }
        }
    }

    public int eliminarLinhasCompletas() {
        int eliminadas = 0;
        for (int y = ALTURA - 1; y >= 0; y--) {
            if (linhaCompleta(y)) {
                eliminarLinha(y);
                eliminadas++;
                y++;
            }
        }
        return eliminadas;
    }

    private boolean linhaCompleta(int y) {
        for (int x = 0; x < LARGURA; x++) {
            // MUDANÇA: Verifica se é nulo
            if (grid[y][x] == null) {
                return false;
            }
        }
        return true;
    }

    private void eliminarLinha(int y) {
        for (int i = y; i > 0; i--) {
            System.arraycopy(grid[i - 1], 0, grid[i], 0, LARGURA);
        }
        // MUDANÇA: Preenche com 'null' (vazio)
        Arrays.fill(grid[0], null);
    }
}