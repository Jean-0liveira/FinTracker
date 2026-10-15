package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exceptions.EntradaInvalidaException;
import org.junit.jupiter.api.Test;

class TipoTest {

    @Test
    void fromTextoIgnoraMaiusculasEEspacos() throws EntradaInvalidaException {
        assertEquals(Tipo.RECEITA, Tipo.fromTexto("receita"));
        assertEquals(Tipo.DESPESA, Tipo.fromTexto("  DESPESA "));
    }

    @Test
    void fromTextoInvalidoLancaExcecao() {
        assertThrows(EntradaInvalidaException.class, () -> Tipo.fromTexto("outro"));
        assertThrows(EntradaInvalidaException.class, () -> Tipo.fromTexto(""));
        assertThrows(EntradaInvalidaException.class, () -> Tipo.fromTexto(null));
    }
}
