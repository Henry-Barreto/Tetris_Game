package tetris.domain;

import tetris.io.HighScoreManager; // Importa o novo gerenciador
import java.io.Serializable;

/**
 * Gerencia a pontuação, o nível e o high score.
 * Implementa Serializable para ser salvo com a Partida.
 */
public class SistemaPontuacao implements Serializable {

    private int pontos = 0;
    private int nivel = 1;

    // Armazena o high score em memória
    // 'transient' impede que esta variável seja salva no 'partida_salva.dat',
    // pois ela é gerenciada globalmente pelo HighScoreManager.
    private transient int highScore;

    /**
     * Construtor do SistemaPontuacao.
     * Agora, ele também carrega o high score salvo em disco.
     */
    public SistemaPontuacao() {
        // Carrega o high score salvo no arquivo "highscore.dat"
        this.highScore = HighScoreManager.carregar();
    }

    /**
     * Adiciona pontos baseados nas linhas eliminadas e atualiza o nível.
     * Também verifica e atualiza o high score.
     */
    public void adicionarLinhas(int linhas) {
        int ganho = switch (linhas) {
            case 1 -> 40 * nivel;
            case 2 -> 100 * nivel;
            case 3 -> 300 * nivel;
            case 4 -> 1200 * nivel;
            default -> 0;
        };
        this.pontos += ganho;

        // Verifica se a pontuação atual ultrapassou o high score
        if (this.pontos > this.highScore) {
            this.highScore = this.pontos;
        }

        // Aumenta o nível a cada 1000 pontos
        if (pontos / 1000 > nivel - 1) {
            nivel++;
        }
    }

    // Getters padrão
    public int getPontos() { return pontos; }
    public int getNivel() { return nivel; }

    /**
     * Getter para o High Score.
     * Usado pelo ScorePanel para exibir a pontuação máxima.
     */
    public int getHighScore() { return highScore; }
}