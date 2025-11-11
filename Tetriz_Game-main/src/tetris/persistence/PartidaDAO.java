package tetris.persistence;

import tetris.domain.Jogador;
import tetris.domain.Partida;

import java.io.*; // Importa todas as classes de I/O
import java.sql.*;
import java.util.UUID;

/**
 * DAO para a entidade Partida.
 * Salva e carrega o estado completo da partida usando serialização binária
 * em uma coluna VARBINARY(MAX).
 */
public class PartidaDAO {

    /**
     * Garante que a tabela 'Partida' exista no banco de dados.
     */
    public void criarTabelaSeNaoExistir() {
        // SQL para criar a tabela
        // (Inclui data_criacao para podermos carregar a última)
        String sql = """
            IF NOT EXISTS (
                SELECT * FROM sysobjects
                WHERE name='Partida' and xtype='U'
            )
            CREATE TABLE Partida (
                id INT IDENTITY(1,1) PRIMARY KEY,
                jogador_id UNIQUEIDENTIFIER NOT NULL,
                dados VARBINARY(MAX) NOT NULL,
                data_criacao DATETIME DEFAULT GETDATE(),
                FOREIGN KEY (jogador_id) REFERENCES Jogador(id)
            );
            """;

        try (Connection conn = ConexaoSQL.getConexao();
             Statement st = conn.createStatement()) {

            st.execute(sql);

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela Partida: " + e.getMessage());
        }
    }

    /**
     * Salva o estado completo da partida no banco de dados.
     * Serializa o objeto Partida para um array de bytes.
     */
    public void salvar(Partida partida) {
        String sql = "INSERT INTO Partida (jogador_id, dados) VALUES (?, ?)";

        try (Connection conn = ConexaoSQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 1. Serializa o objeto 'Partida' para um array de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(partida);
            oos.close();
            byte[] dadosSerializados = baos.toByteArray();

            // 2. Define os parâmetros do PreparedStatement
            ps.setObject(1, partida.getJogador().getId());
            ps.setBytes(2, dadosSerializados);

            ps.executeUpdate();

        } catch (SQLException | IOException e) {
            // Captura tanto erros de SQL quanto de Serialização (IOException)
            System.err.println("Erro ao salvar partida: " + e.getMessage());
        }
    }

    /**
     * Carrega a última partida salva de um jogador específico.
     * Desserializa os bytes do banco de volta para um objeto Partida.
     */
    public Partida carregarUltima(Jogador jogador) {
        // SQL para buscar a última partida (TOP 1) de um jogador
        String sql = """
            SELECT TOP 1 dados
            FROM Partida
            WHERE jogador_id = ?
            ORDER BY data_criacao DESC
            """;

        try (Connection conn = ConexaoSQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, jogador.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) { // Se encontrou uma partida
                // 1. Lê os bytes da coluna 'dados'
                byte[] blob = rs.getBytes("dados");

                // 2. Desserializa os bytes de volta para um objeto
                ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                ObjectInputStream ois = new ObjectInputStream(bais);

                Partida partidaCarregada = (Partida) ois.readObject();
                ois.close();

                System.out.println("Última partida carregada do banco.");
                return partidaCarregada;
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Captura erros de SQL, I/O, ou de classe (serialização)
            System.err.println("Erro ao carregar partida: " + e.getMessage());
        }

        System.out.println("Nenhuma partida encontrada no banco para este jogador.");
        return null; // Retorna nulo se nada for encontrado
    }
}