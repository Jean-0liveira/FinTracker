package dao;

import exceptions.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Tipo;
import model.Transacao;

public class TransacaoDAO {

    private final Connection conexao;

    public TransacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Transacao transacao) {
        String sql = "INSERT INTO transacoes (descricao, valor, tipo, data) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, transacao.getDescricao());
            ps.setDouble(2, transacao.getValor());
            ps.setString(3, transacao.getTipo().name());
            ps.setString(4, transacao.getData().toString());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    transacao.setId(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir a transação.", e);
        }
    }

    public List<Transacao> listar() {
        String sql = "SELECT id, descricao, valor, tipo, data FROM transacoes ORDER BY data, id";
        List<Transacao> resultado = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar as transações.", e);
        }
        return resultado;
    }

    public Optional<Transacao> buscarPorId(int id) {
        String sql = "SELECT id, descricao, valor, tipo, data FROM transacoes WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar a transação.", e);
        }
        return Optional.empty();
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM transacoes WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao remover a transação.", e);
        }
    }

    private Transacao mapear(ResultSet rs) throws SQLException {
        return new Transacao(
                rs.getInt("id"),
                rs.getString("descricao"),
                rs.getDouble("valor"),
                Tipo.valueOf(rs.getString("tipo")),
                LocalDate.parse(rs.getString("data")));
    }
}
