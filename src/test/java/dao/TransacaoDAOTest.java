package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import database.Conexao;
import exceptions.PersistenciaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import model.Tipo;
import model.Transacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransacaoDAOTest {

    private Connection conexao;
    private TransacaoDAO dao;

    @BeforeEach
    void abrirBanco() throws SQLException {
        conexao = DriverManager.getConnection("jdbc:sqlite::memory:");
        Conexao.criarTabela(conexao);
        dao = new TransacaoDAO(conexao);
    }

    @AfterEach
    void fecharBanco() throws SQLException {
        conexao.close();
    }

    @Test
    void inserirDefineOIdGerado() {
        Transacao transacao = new Transacao("Salário", 3000, Tipo.RECEITA, LocalDate.of(2026, 10, 1));

        dao.inserir(transacao);

        assertNotNull(transacao.getId());
        assertTrue(transacao.getId() > 0);
    }

    @Test
    void inserirGeraIdsDiferentes() {
        Transacao primeira = new Transacao("Salário", 3000, Tipo.RECEITA, LocalDate.of(2026, 10, 1));
        Transacao segunda = new Transacao("Aluguel", 1200, Tipo.DESPESA, LocalDate.of(2026, 10, 5));

        dao.inserir(primeira);
        dao.inserir(segunda);

        assertNotEquals(primeira.getId(), segunda.getId());
    }

    @Test
    void listarSemDadosRetornaListaVazia() {
        assertTrue(dao.listar().isEmpty());
    }

    @Test
    void listarRetornaOsDadosGravados() {
        dao.inserir(new Transacao("Mercado", 450.75, Tipo.DESPESA, LocalDate.of(2026, 10, 14)));

        List<Transacao> lista = dao.listar();

        assertEquals(1, lista.size());
        Transacao lida = lista.get(0);
        assertEquals("Mercado", lida.getDescricao());
        assertEquals(450.75, lida.getValor(), 0.001);
        assertEquals(Tipo.DESPESA, lida.getTipo());
        assertEquals(LocalDate.of(2026, 10, 14), lida.getData());
    }

    @Test
    void listarOrdenaPorData() {
        dao.inserir(new Transacao("Recente", 10, Tipo.DESPESA, LocalDate.of(2026, 10, 20)));
        dao.inserir(new Transacao("Antiga", 10, Tipo.DESPESA, LocalDate.of(2026, 10, 1)));

        List<Transacao> lista = dao.listar();

        assertEquals("Antiga", lista.get(0).getDescricao());
        assertEquals("Recente", lista.get(1).getDescricao());
    }

    @Test
    void buscarPorIdExistenteRetornaATransacao() {
        Transacao transacao = new Transacao("Salário", 3000, Tipo.RECEITA, LocalDate.of(2026, 10, 1));
        dao.inserir(transacao);

        assertTrue(dao.buscarPorId(transacao.getId()).isPresent());
        assertEquals("Salário", dao.buscarPorId(transacao.getId()).get().getDescricao());
    }

    @Test
    void buscarPorIdInexistenteRetornaVazio() {
        assertTrue(dao.buscarPorId(999).isEmpty());
    }

    @Test
    void removerExistenteApagaORegistro() {
        Transacao transacao = new Transacao("Salário", 3000, Tipo.RECEITA, LocalDate.of(2026, 10, 1));
        dao.inserir(transacao);

        assertTrue(dao.remover(transacao.getId()));
        assertTrue(dao.listar().isEmpty());
    }

    @Test
    void removerInexistenteRetornaFalso() {
        assertFalse(dao.remover(999));
    }

    @Test
    void erroDeSqlLancaPersistenciaException() throws SQLException {
        conexao.createStatement().execute("DROP TABLE transacoes");
        Transacao transacao = new Transacao("Salário", 3000, Tipo.RECEITA, LocalDate.of(2026, 10, 1));

        assertThrows(PersistenciaException.class, () -> dao.inserir(transacao));
        assertThrows(PersistenciaException.class, () -> dao.listar());
    }
}
