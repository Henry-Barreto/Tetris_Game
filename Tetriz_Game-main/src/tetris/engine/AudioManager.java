package tetris.engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Gerencia a reprodução da música de fundo.
 */
public class AudioManager {

    private Clip clip;

    public AudioManager(String musicFilePath) {
        try {
            File musicPath = new File(musicFilePath);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            } else {
                System.err.println("Arquivo de música não encontrado: " + musicFilePath);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar a música:");
            e.printStackTrace(); // A IDE prefere isso a System.err
        }
    }

    /**
     * Inicia a música em loop contínuo.
     */
    public void play() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Para a música.
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}