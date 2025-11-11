package tetris.persistence;

import tetris.domain.Jogador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DAO (Data Access Object) para a entidade Jogador.
 * Encapsula toda a lógica de persistência (CRUD) de jogadores.
 */
public class JogadorDAO {

    /**
     * Garante que a tabela 'Jogador' exista no banco de dados.
     * Este método é "idempotente" (pode ser executado várias vezes sem erro).
     */
    public void criarTabelaSeNaoExistir() {
        // SQL para criar a tabela se ela não existir
        String sql = """
            IF NOT EXISTS (
                SELECT * FROM sysobjects
                WHERE name='Jogador' and xtype='U'
            )
            CREATE TABLE Jogador (
                id UNIQUEIDENTIFIER PRIMARY KEY,
                nome NVARCHAR(100) NOT NULL
            );
            """;

        // 'try-with-resources' garante que a conexão (conn) e
        // o statement (st) sejam fechados automaticamente.
        try (Connection conn = ConexaoSQL.getConexao();
             Statement st = conn.createStatement()) {

            st.execute(sql); // Executa o comando

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela Jogador: " + e.getMessage());
        }
    }

    /**
     * Insere um novo jogador no banco de dados.
     * Usa PreparedStatement para prevenir SQL Injection.
     */
    public void inserir(Jogador jogador) {
        String sql = "INSERT INTO Jogador (id, nome) VALUES (?, ?)";

        try (Connection conn = ConexaoSQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Define os parâmetros (?) do PreparedStatement
            ps.setObject(1, jogador.getId()); // Mapeia UUID para UNIQUEIDENTIFIER
            ps.setString(2, jogador.getNome());

            ps.executeUpdate(); // Executa a inserção

        } catch (SQLException e) {
            System.err.println("Erro ao inserir jogador: " + e.getMessage());
        }
    }

    /**
     * Retorna uma lista com todos os jogadores do banco de dados.
     */
    public List<Jogador> listarTodos() {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT id, nome FROM Jogador";

        try (Connection conn = ConexaoSQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { // Executa a consulta

            // Itera sobre cada linha (registro) do resultado
            while (rs.next()) {
                // Mapeia os dados da linha para um objeto Java
                UUID id = (UUID) rs.getObject("id");
                String nome = rs.getString("nome");

                // NOTA: O PDF cria um 'JogadorComId'[cite: 383]. Para simplificar,
                // vamos precisar de um construtor no Jogador que aceite o ID.

                // **Vá até a classe Jogador.java e adicione este construtor:**
                // public Jogador(UUID id, String nome) {
                //    this.id = id;
                //    this.nome = nome;
                // }

                 jogadores.add(new Jogador(id, nome));
                System.out.println("Jogador encontrado: " + nome);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar jogadores: " + e.getMessage());
        }

        return jogadores;
    }
}