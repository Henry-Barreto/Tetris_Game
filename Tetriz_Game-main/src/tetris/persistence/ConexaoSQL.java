package tetris.persistence; // Garanta que este pacote existe

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement; // (Adicionando para o próximo passo)

/**
 * Gerencia a conexão com o banco de dados SQL Server.
 * Utiliza o Padrão Singleton.
 */
public class ConexaoSQL {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=TetrisDB;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true";

    private static final String USUARIO = "sa"; // (Ajuste se necessário)
    private static final String SENHA = "senha123"; // (Ajuste se necessário)

    private static Connection conexao; // A instância única

    // Construtor privado para impedir novas instâncias
    private ConexaoSQL() {}

    /**
     * Método estático que retorna a única instância da conexão.
     */
    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                // Registra o driver (garante que a biblioteca foi carregada)
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            } catch (ClassNotFoundException e) {
                // Este erro acontece se o .jar do driver não foi encontrado
                throw new SQLException("ERRO: Driver JDBC do SQL Server não foi encontrado.", e);
            }
        }
        return conexao;
    }

    /**
     * Fecha a conexão singleton.
     */
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão com o banco de dados fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}