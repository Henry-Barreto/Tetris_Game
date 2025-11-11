package tetris.ui;

import javax.swing.*;
import java.awt.*;
import tetris.domain.Board;
import tetris.domain.Posicao;
import tetris.domain.Tetromino;
import tetris.domain.TipoTetromino;

/**
 * Renderização visual do Tabuleiro, da peça atual,
 * da "Ghost Piece" e da tela de Pausa.
 */
public class GamePanel extends JPanel {

    private static final int TAM_CELULA = 30;

    // Atributos de estado
    private Board board;
    private Tetromino pecaAtual;

    // ATRIBUTOS QUE FALTAVAM:
    private Posicao posPecaFantasma; // Para a "Ghost Piece"
    private boolean pausado = false;      // Para a tela de "Pausado"

    public GamePanel(Board board, Tetromino pecaAtual) {
        this.board = board;
        this.pecaAtual = pecaAtual;
        // O posPecaFantasma e pausado são inicializados via 'atualizar'

        setPreferredSize(new Dimension(
                Board.LARGURA * TAM_CELULA,
                Board.ALTURA * TAM_CELULA
        ));
        setBackground(Color.BLACK);
    }

    /**
     * Método 'atualizar' com a assinatura correta (4 argumentos).
     */
    public void atualizar(Board board, Tetromino pecaAtual, Posicao posPecaFantasma, boolean pausado) {
        this.board = board;
        this.pecaAtual = pecaAtual;
        this.posPecaFantasma = posPecaFantasma; // Agora 'this.posPecaFantasma' existe
        this.pausado = pausado;               // Agora 'this.pausado' existe
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharPecasFixas(g);
        desenharPecaFantasma(g); // Desenha o "fantasma"
        desenharPecaAtual(g);
        desenharGrid(g);

        // Desenha a sobreposição de pausa
        if (this.pausado) {
            desenharPausa(g);
        }
    }

    /**
     * Desenha a tela de pausa translúcida.
     */
    private void desenharPausa(Graphics g) {
        // Retângulo semi-transparente
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Texto "PAUSADO"
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 40));

        String texto = "PAUSADO";
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(texto)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g.drawString(texto, x, y);
    }

    /**
     * Desenha a "Ghost Piece" (prévia da queda).
     */
    private void desenharPecaFantasma(Graphics g) {
        if (pecaAtual == null || posPecaFantasma == null) return;

        boolean[][] forma = pecaAtual.getForma();

        // Cor transparente (Alpha)
        Color cor = pecaAtual.getCor();
        g.setColor(new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 120));

        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j]) {
                    int px = (posPecaFantasma.getX() + j) * TAM_CELULA;
                    int py = (posPecaFantasma.getY() + i) * TAM_CELULA;
                    g.fillRect(px, py, TAM_CELULA, TAM_CELULA);
                }
            }
        }
    }

    // --- Métodos de desenho que você já tinha ---

    private void desenharPecasFixas(Graphics g) {
        TipoTetromino[][] grid = board.getGrid();
        for (int y = 0; y < Board.ALTURA; y++) {
            for (int x = 0; x < Board.LARGURA; x++) {
                TipoTetromino tipo = grid[y][x];
                if (tipo != null) {
                    g.setColor(getColorFromTipo(tipo));
                    g.fillRect(x * TAM_CELULA, y * TAM_CELULA, TAM_CELULA, TAM_CELULA);
                }
            }
        }
    }

    private void desenharPecaAtual(Graphics g) {
        if (pecaAtual == null) return;

        boolean[][] forma = pecaAtual.getForma();
        Posicao pos = pecaAtual.getPosicao();
        g.setColor(pecaAtual.getCor());

        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j]) {
                    int px = (pos.getX() + j) * TAM_CELULA;
                    int py = (pos.getY() + i) * TAM_CELULA;
                    g.fillRect(px, py, TAM_CELULA, TAM_CELULA);
                }
            }
        }
    }

    private void desenharGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x <= Board.LARGURA; x++) {
            g.drawLine(x * TAM_CELULA, 0, x * TAM_CELULA, Board.ALTURA * TAM_CELULA);
        }
        for (int y = 0; y <= Board.ALTURA; y++) {
            g.drawLine(0, y * TAM_CELULA, Board.LARGURA * TAM_CELULA, y * TAM_CELULA);
        }
    }

    private Color getColorFromTipo(TipoTetromino tipo) {
        switch (tipo) {
            case I: return Color.CYAN;
            case O: return Color.YELLOW;
            case T: return Color.MAGENTA;
            case S: return Color.GREEN;
            case Z: return Color.RED;
            case J: return Color.BLUE;
            case L: return Color.ORANGE;
            default: return Color.GRAY;
        }
    }
}