package tetris.ui;

import tetris.domain.Jogador;
import tetris.domain.Partida;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Tenta carregar a partida
            Partida partida = tetris.io.SaveManager.carregar();

            if (partida == null) {
                // Se não houver save, cria uma nova partida
                Jogador jogador = new Jogador("Jogador 1");
                partida = new Partida(jogador);
            }

            

            // O resto continua igual
            TelaPrincipal tela = new TelaPrincipal(partida);
            tela.iniciar();
        });
    }
}