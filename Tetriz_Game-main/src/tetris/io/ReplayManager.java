package tetris.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import tetris.domain.Partida;

/**
 * Gerencia a gravação (serialização) e reprodução (desserialização)
 * de um histórico completo de frames da partida.
 */
public class ReplayManager {

    private static final String REPLAY_FILE = "replay_tetris.dat";

    /**
     * Salva a lista completa de "frames" (snapshots) da partida.
     */
    public static void salvarReplay(List<Partida> historico) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(REPLAY_FILE))) {

            oos.writeObject(historico);
            System.out.println("[REPLAY] Replay salvo com " + historico.size() + " frames.");

        } catch (IOException e) {
            System.err.println("[REPLAY] Erro ao salvar: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de "frames" do arquivo de replay.
     */
    @SuppressWarnings("unchecked") // Necessário para o cast da lista
    public static List<Partida> carregarReplay() {
        File file = new File(REPLAY_FILE);
        if (!file.exists()) {
            System.out.println("[REPLAY] Nenhum replay encontrado.");
            return new ArrayList<>(); // Retorna lista vazia
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {

            return (List<Partida>) ois.readObject();

        } catch (Exception e) {
            System.err.println("[REPLAY] Erro ao carregar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * (Método de exemplo do PDF)
     * Reproduz o replay no console (versão simplificada).
     */
    public static void reproduzir(List<Partida> historico, int delayMs) {
        for (int i = 0; i < historico.size(); i++) {
            Partida frame = historico.get(i);
            System.out.println("Frame " + i + ": Pontos = " +
                    frame.getSistemaPontuacao().getPontos());

            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException ignored) {}
        }
        System.out.println("[REPLAY] Reprodução concluída.");
    }
}