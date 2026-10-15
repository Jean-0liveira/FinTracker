package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TransacaoMensalTest {

    @Test
    void guardaOMesEHerdaDeTransacao() {
        TransacaoMensal mensal = new TransacaoMensal("Internet", 100, Tipo.DESPESA, 3);

        assertEquals(3, mensal.getMes());
        assertEquals("Internet", mensal.getDescricao());
        assertInstanceOf(Transacao.class, mensal);
    }

    @Test
    void setMesAtualizaOMes() {
        TransacaoMensal mensal = new TransacaoMensal("Internet", 100, Tipo.DESPESA, 3);
        mensal.setMes(4);

        assertEquals(4, mensal.getMes());
    }

    @Test
    void toStringIncluiOMes() {
        TransacaoMensal mensal = new TransacaoMensal("Internet", 100, Tipo.DESPESA, 3);

        assertTrue(mensal.toString().contains("Mês: 3"));
    }
}
