package tetris.domain;

import java.io.Serializable; // Importa o Serializable
import java.util.UUID;

// Adiciona "implements Serializable" para corrigir o erro de replay
public class Jogador implements Serializable {

    private final UUID id;
    private String nome;

    /**
     * Construtor para um novo jogador.
     */
    public Jogador(String nome) {
        this.id = UUID.randomUUID();
        this.nome = nome;
    }

    /**
     * Construtor para carregar um jogador existente do banco de dados.
     */
    public Jogador(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return "Jogador [id=" + id + ", nome=" + nome + "]";
    }
}