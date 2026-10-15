package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransacaoTest {

    private LocalDate data;
    private Transacao transacao;

    @BeforeEach
    void preparar() {
        data = LocalDate.of(2026, 10, 14);
        transacao = new Transacao("Salário", 3000, Tipo.RECEITA, data);
    }

    @Test
    void construtorSemIdDeixaIdNulo() {
        assertNull(transacao.getId());
    }

    @Test
    void construtorGuardaOsValores() {
        assertEquals("Salário", transacao.getDescricao());
        assertEquals(3000, transacao.getValor(), 0.001);
        assertEquals(Tipo.RECEITA, transacao.getTipo());
        assertEquals(data, transacao.getData());
    }

    @Test
    void construtorComIdGuardaOId() {
        Transacao comId = new Transacao(7, "Aluguel", 1200, Tipo.DESPESA, data);
        assertEquals(7, comId.getId());
    }

    @Test
    void settersAtualizamOsValores() {
        transacao.setId(5);
        transacao.setDescricao("Freelance");
        transacao.setValor(500.5);
        transacao.setTipo(Tipo.DESPESA);
        transacao.setData(LocalDate.of(2026, 1, 2));

        assertEquals(5, transacao.getId());
        assertEquals("Freelance", transacao.getDescricao());
        assertEquals(500.5, transacao.getValor(), 0.001);
        assertEquals(Tipo.DESPESA, transacao.getTipo());
        assertEquals(LocalDate.of(2026, 1, 2), transacao.getData());
    }

    @Test
    void toStringContemOsDadosPrincipais() {
        String texto = transacao.toString();
        assertTrue(texto.contains("RECEITA"));
        assertTrue(texto.contains("14/10/2026"));
        assertTrue(texto.contains("Salário"));
        assertTrue(texto.contains("3.000,00"));
    }
}
