package tetris.ui;

import javax.swing.*;
import java.awt.*;
import tetris.domain.Posicao;
import tetris.domain.Tetromino;

/**
 * Painel que exibe a peça em "Hold".
 */
public class HoldPanel extends JPanel {

    private static final int TAM_CELULA = 20;
    private Tetromino pecaHold;

    public HoldPanel() {
        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "HOLD",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Consolas", Font.BOLD, 14),
                Color.WHITE
        ));
    }

    public void atualizar(Tetromino peca) {
        this.pecaHold = peca;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pecaHold == null) return;

        boolean[][] forma = pecaHold.getForma();
        Posicao pos = new Posicao(1, 1);
        g.setColor(pecaHold.getCor());

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