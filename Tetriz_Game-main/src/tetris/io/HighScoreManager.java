package tetris.io;

import java.io.*;


/**
 * Gerencia a persistência do High Score em um arquivo local.
 */
public class HighScoreManager {

    private static final String HIGH_SCORE_FILE = "highscore.dat";

    /**
     * Carrega o high score salvo.
     * Retorna 0 se nenhum score for encontrado.
     */
    public static int carregar() {
        File file = new File(HIGH_SCORE_FILE);
        if (!file.exists()) {
            return 0; // Nenhum high score salvo ainda
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (int) ois.readObject();
        } catch (Exception e) {
            System.err.println("Erro ao carregar high score: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Salva o novo high score.
     */
    public static void salvar(int score) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            oos.writeObject(score);
        } catch (IOException e) {
            System.err.println("Erro ao salvar high score: " + e.getMessage());
        }
    }
}