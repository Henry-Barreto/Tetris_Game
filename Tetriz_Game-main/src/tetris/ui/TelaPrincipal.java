package tetris.ui;

import javax.swing.*;
import java.awt.*;
import tetris.domain.Partida;
import tetris.engine.GameEngine;
import tetris.engine.InputHandler;
import tetris.engine.AudioManager; // Import para a música
// Importa os novos painéis
import tetris.ui.NextPiecePanel;
import tetris.ui.HoldPanel;
import tetris.ui.ControlsPanel;

/**
 * Janela principal (JFrame) que orquestra toda a UI.
 */
public class TelaPrincipal extends JFrame {

    // Atributos do domínio e engine
    private final Partida partida;
    private final GameEngine engine;

    // Atributos dos painéis da UI
    private final GamePanel gamePanel;
    private final ScorePanel scorePanel;
    private final NextPiecePanel nextPiecePanel;
    private final HoldPanel holdPanel;
    private final ControlsPanel controlsPanel; // Painel de controles

    // Atributos de funcionalidade
    private final AudioManager audioManager;
    private Timer timerUI;

    public TelaPrincipal(Partida partida) {
        super("Tetris - Projeto Java");
        this.partida = partida;

        // Inicializa o motor do jogo
        engine = new GameEngine(partida);

        // Inicializa os painéis
        gamePanel = new GamePanel(partida.getBoard(), partida.getPecaAtual());
        scorePanel = new ScorePanel();
        nextPiecePanel = new NextPiecePanel();
        holdPanel = new HoldPanel();
        controlsPanel = new ControlsPanel(); // Inicializa o novo painel

        // Inicializa a música
        audioManager = new AudioManager("TetrisMusic.wav"); // Lembre-se de usar o nome correto do seu .wav

        // Cria um painel lateral para organizar os placares e controles
        JPanel painelLeste = new JPanel();
        painelLeste.setLayout(new BoxLayout(painelLeste, BoxLayout.Y_AXIS));
        painelLeste.setBackground(Color.BLACK);
        painelLeste.add(scorePanel);
        painelLeste.add(Box.createRigidArea(new Dimension(0, 20))); // Espaço
        painelLeste.add(nextPiecePanel);
        painelLeste.add(Box.createRigidArea(new Dimension(0, 20))); // Espaço
        painelLeste.add(holdPanel);
        painelLeste.add(Box.createRigidArea(new Dimension(0, 20))); // Espaço
        painelLeste.add(controlsPanel); // Adiciona o painel de controles
        painelLeste.add(Box.createVerticalGlue()); // Empurra tudo para cima

        // Configura o Layout da Janela
        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        add(painelLeste, BorderLayout.EAST);

        // Configurações da Janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        // Registra o "Ouvinte" de Teclado
        addKeyListener(new InputHandler(partida));
        setFocusable(true);

        // Timer da UI (atualiza a tela a cada 30ms)
        timerUI = new Timer(30, e -> atualizarTela());
    }

    /**
     * Inicia o motor do jogo, o Timer da UI e a música.
     * Torna a janela visível.
     */
    public void iniciar() {
        engine.iniciar();
        timerUI.start();
        audioManager.play(); // Toca a música
        setVisible(true);
    }

    /**
     * Método chamado pelo 'timerUI' repetidamente.
     * Puxa os dados da 'partida' e passa para os painéis da UI.
     */
    private void atualizarTela() {

        // ESTA É A CHAMADA CORRETA (com 4 argumentos)
        gamePanel.atualizar(
                partida.getBoard(),
                partida.getPecaAtual(),
                partida.getPosPecaFantasma(),
                partida.isPausado()
        );

        // Atualiza os outros painéis
        scorePanel.atualizar(partida.getSistemaPontuacao());
        nextPiecePanel.atualizar(partida.getPecaProxima());
        holdPanel.atualizar(partida.getPecaHold());

        // Verifica se o jogo terminou
        if (partida.isGameOver()) {
            timerUI.stop();
            audioManager.stop(); // Para a música

            JOptionPane.showMessageDialog(
                    this,
                    "Game Over!\nPontuação final: " + partida.getSistemaPontuacao().getPontos(),
                    "Fim de Jogo",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
        }
    } // <-- Fim do método atualizarTela

} // <-- Fim da classe TelaPrincipal