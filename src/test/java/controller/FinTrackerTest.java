package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dao.TransacaoDAO;
import database.Conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import model.Tipo;
import model.Transacao;
import model.TransacaoMensal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FinTrackerTest {

    private static final LocalDate DATA = LocalDate.of(2026, 10, 14);

    private FinTracker finTracker;

    @BeforeEach
    void preparar() {
        finTracker = new FinTracker();
    }

    @Test
    void saldoSemTransacoesEhZero() {
        assertEquals(0, finTracker.calcularSaldoTotal(), 0.001);
    }

    @Test
    void saldoSomaReceitasESubtraiDespesas() {
        finTracker.adicionarTransacao(new Transacao("Salário", 1000, Tipo.RECEITA, DATA));
        finTracker.adicionarTransacao(new Transacao("Mercado", 250.50, Tipo.DESPESA, DATA));

        assertEquals(749.50, finTracker.calcularSaldoTotal(), 0.001);
    }

    @Test
    void saldoPodeFicarNegativo() {
        finTracker.adicionarTransacao(new Transacao("Freelance", 100, Tipo.RECEITA, DATA));
        finTracker.adicionarTransacao(new Transacao("Aluguel", 1200, Tipo.DESPESA, DATA));

        assertEquals(-1100, finTracker.calcularSaldoTotal(), 0.001);
    }

    @Test
    void totalPorTipoSomaApenasOTipoPedido() {
        finTracker.adicionarTransacao(new Transacao("Salário", 3000, Tipo.RECEITA, DATA));
        finTracker.adicionarTransacao(new Transacao("Aluguel", 1200, Tipo.DESPESA, DATA));
        finTracker.adicionarTransacao(new Transacao("Mercado", 300, Tipo.DESPESA, DATA));

        assertEquals(3000, finTracker.calcularTotalPorTipo(Tipo.RECEITA), 0.001);
        assertEquals(1500, finTracker.calcularTotalPorTipo(Tipo.DESPESA), 0.001);
    }

    @Test
    void calcularSaldoAceitaListaDeTransacaoMensal() {
        List<TransacaoMensal> mensais = List.of(
                new TransacaoMensal("Internet", 100, Tipo.DESPESA, 3),
                new TransacaoMensal("Bônus", 400, Tipo.RECEITA, 3));

        assertEquals(300, FinTracker.calcularSaldo(mensais), 0.001);
    }

    @Test
    void removerPorIndiceValidoRetiraATransacao() {
        finTracker.adicionarTransacao(new Transacao("Salário", 3000, Tipo.RECEITA, DATA));

        assertTrue(finTracker.removerTransacao(0));
        assertTrue(finTracker.getTransacoes().isEmpty());
    }

    @Test
    void removerPorIndiceInvalidoRetornaFalso() {
        finTracker.adicionarTransacao(new Transacao("Salário", 3000, Tipo.RECEITA, DATA));

        assertFalse(finTracker.removerTransacao(5));
        assertFalse(finTracker.removerTransacao(-1));
        assertEquals(1, finTracker.getTransacoes().size());
    }

    @Test
    void transacoesPersistemEntreInstanciasComBanco() throws SQLException {
        try (Connection conexao = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            Conexao.criarTabela(conexao);
            TransacaoDAO dao = new TransacaoDAO(conexao);

            FinTracker primeiro = new FinTracker(dao);
            primeiro.adicionarTransacao(new Transacao("Salário", 3000, Tipo.RECEITA, DATA));
            primeiro.adicionarTransacao(new Transacao("Aluguel", 1200, Tipo.DESPESA, DATA));

            FinTracker segundo = new FinTracker(dao);
            assertEquals(2, segundo.getTransacoes().size());
            assertEquals(1800, segundo.calcularSaldoTotal(), 0.001);

            segundo.removerTransacao(0);

            FinTracker terceiro = new FinTracker(dao);
            assertEquals(1, terceiro.getTransacoes().size());
        }
    }
}
