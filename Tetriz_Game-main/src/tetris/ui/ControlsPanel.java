package tetris.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Painel que exibe a lista de comandos do jogo.
 */
public class ControlsPanel extends JPanel {

    public ControlsPanel() {
        setBackground(Color.BLACK);
        // Adiciona um título ao painel
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "CONTROLES",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Consolas", Font.BOLD, 14),
                Color.WHITE
        ));

        // Layout: 0 = "quantas linhas precisar", 2 colunas, 10px hgap, 5px vgap
        setLayout(new GridLayout(0, 2, 10, 5));

        // Adiciona os comandos
        // (Este é um método auxiliar que criamos abaixo)
        addControl("←", "Mover Esquerda");
        addControl("→", "Mover Direita");
        addControl("↓", "Acelerar");
        addControl("↑", "Rotacionar");
        addControl("ESPAÇO", "Hard Drop");
        addControl("C", "Segurar (Hold)");
        addControl("P", "Pausar");
    }

    /**
     * Método auxiliar para criar e adicionar os labels de controle,
     * garantindo um estilo consistente.
     */
    private void addControl(String key, String action) {
        // Label da Tecla (ex: "↑")
        JLabel keyLabel = new JLabel(key);
        keyLabel.setForeground(Color.CYAN); // Cor de destaque
        keyLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        keyLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza

        // Label da Ação (ex: "Rotacionar")
        JLabel actionLabel = new JLabel(action);
        actionLabel.setForeground(Color.WHITE);
        actionLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

        // Adiciona os dois labels ao grid
        add(keyLabel);
        add(actionLabel);
    }
}