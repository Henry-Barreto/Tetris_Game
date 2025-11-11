package tetris.ui;

import javax.swing.*;
import java.awt.*;
import tetris.domain.Posicao;
import tetris.domain.Tetromino;

/**
 * Painel que exibe a "próxima" peça.
 */
public class NextPiecePanel extends JPanel {

    private static final int TAM_CELULA = 20; // Um tamanho menor
    private Tetromino pecaProxima;

    public NextPiecePanel() {
        setPreferredSize(new Dimension(100, 100)); // Tamanho 5x5
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "NEXT",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Consolas", Font.BOLD, 14),
                Color.WHITE
        ));
    }

    public void atualizar(Tetromino peca) {
        this.pecaProxima = peca;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (pecaProxima == null) return;

        // Desenha a peça centralizada no painel
        boolean[][] forma = pecaProxima.getForma();
        Posicao pos = new Posicao(1, 1); // Posição fixa no painel
        g.setColor(pecaProxima.getCor());

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
}