package tetris.engine;

import tetris.domain.Partida;
import tetris.io.SaveManager;
import tetris.io.ReplayManager;
import tetris.io.HighScoreManager;

// Imports necessários para o ReplayManager e Snapshot
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa a interface Runnable para ser executada em uma thread
 * dedicada (pela ThreadLoop). Coordena o ciclo de atualização (tick).
 */
public class GameEngine implements Runnable {

    private final Partida partida;
    private boolean rodando = false;
    private ThreadLoop threadLoop;

    // Lista para o histórico do Replay
    private List<Partida> historico;

    public GameEngine(Partida partida) {
        this.partida = partida;
        this.historico = new ArrayList<>(); // Inicializa a lista
    }

    /**
     * Inicia o loop principal do jogo em uma nova thread.
     * ESTE É O MÉTODO CORRETO!
     */
    public void iniciar() {
        if (!rodando) {
            rodando = true;
            // 'this' é a própria GameEngine, que é uma Runnable
            threadLoop = new ThreadLoop(this);
            threadLoop.start(); // Inicia a thread
            System.out.println("[ENGINE] Loop iniciado");
        }
    }

    /**
     * Para o loop principal do jogo.
     */
    public void parar() {
        rodando = false;
        if (threadLoop != null) {
            threadLoop.parar(); // Sinaliza para a thread parar
        }
        HighScoreManager.salvar(partida.getSistemaPontuacao().getHighScore());

        // Salva o jogo ou o replay
        if (!partida.isGameOver()) {
            SaveManager.salvar(partida);
            System.out.println("[ENGINE] Loop encerrado e partida salva.");
        } else {
            SaveManager.limparSave();
            System.out.println("[ENGINE] Game Over. Save limpo.");

            // Salva o replay
            ReplayManager.salvarReplay(historico);
        }
    }

    /**
     * Cria um "snapshot" (cópia profunda) da partida para o replay,
     * corrigindo a cor 'transient'.
     */
    private Partida criarSnapshot(Partida p) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(p);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Partida copia = (Partida) ois.readObject();
            ois.close();

            // Corrige a cor da peça copiada
            if (copia.getPecaAtual() != null) {
                copia.getPecaAtual().readjustColor();
            }

            return copia;
        } catch (Exception e) {
            e.printStackTrace(); // Mostra o erro real no console
            return null; // Falha ao copiar
        }
    }

    /**
     * Este é o método que a ThreadLoop chamará repetidamente.
     */
    @Override
    public void run() {
        if (!rodando) return;

        if (partida.isGameOver()) {
            parar();
            System.out.println("[ENGINE] Game Over!");
            return;
        }

        // Adiciona o frame ANTES de atualizar
        historico.add(criarSnapshot(partida));

        // Executa o 'tick' do domínio (isso faz a peça cair)
        if (partida.isPausado() || !rodando) {
            return;
        }
        // FIM DA MUDANÇA

        if (partida.isGameOver()) {
            parar();
            System.out.println("[ENGINE] Game Over!");
            return;
        }

        Partida snapshot = criarSnapshot(partida);
        if (snapshot != null) {
            historico.add(snapshot);
        }

        partida.tick();
    }
}