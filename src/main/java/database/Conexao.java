package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL = "jdbc:sqlite:fintrack.db";

    private Conexao() {
    }

    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabela(Connection conexao) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS transacoes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao VARCHAR(100), "
                + "valor DECIMAL(10,2), "
                + "tipo VARCHAR(10), "
                + "data DATE)";
        try (Statement statement = conexao.createStatement()) {
            statement.execute(sql);
        }
    }
}
