package model;

import exceptions.EntradaInvalidaException;

public enum Tipo {
    RECEITA, DESPESA;

    public static Tipo fromTexto(String texto) throws EntradaInvalidaException {
        if (texto != null) {
            for (Tipo t : values()) {
                if (t.name().equalsIgnoreCase(texto.trim())) {
                    return t;
                }
            }
        }
        throw new EntradaInvalidaException("Tipo inválido. Digite 'receita' ou 'despesa'.");
    }
}