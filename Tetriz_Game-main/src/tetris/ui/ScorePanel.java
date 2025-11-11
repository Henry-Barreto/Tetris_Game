package tetris.ui;

import javax.swing.*;
import java.awt.*;
import tetris.domain.SistemaPontuacao;

public class ScorePanel extends JPanel {

    private final JLabel lblPontuacao;
    private final JLabel lblNivel;
    private final JLabel lblHighScore;

    public ScorePanel() {
        setLayout(new GridLayout(3, 1));
        setPreferredSize(new Dimension(200, 90));
        setBackground(Color.BLACK);

        lblPontuacao = new JLabel("Pontuação: 0");
        lblNivel = new JLabel("Nível: 1");
        lblHighScore = new JLabel("High Score: 0");

        lblPontuacao.setForeground(Color.WHITE);
        lblNivel.setForeground(Color.WHITE);
        lblHighScore.setForeground(Color.CYAN);
        Font font = new Font("Consolas", Font.BOLD, 16);
        lblPontuacao.setFont(font);
        lblNivel.setFont(font);

        add(lblPontuacao);
        add(lblNivel);
        add(lblHighScore);
    }

    public void atualizar(SistemaPontuacao sistema) {
        lblPontuacao.setText("Pontuação: " + sistema.getPontos());
        lblNivel.setText("Nível: " + sistema.getNivel());
    }
}