package controller;

import java.util.ArrayList;
import model.Tipo;
import model.Transacao;

public class FinTracker {
    private ArrayList<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public void listarTransacoes() {
        for (int i = 0; i < transacoes.size(); i++) {
            System.out.println(i + " - " + transacoes.get(i));
        }
    }

    public double calcularSaldoTotal() {
        double saldo = 0;
        for (Transacao transacao : transacoes) {
            if (transacao.getTipo() == Tipo.RECEITA) {
                saldo += transacao.getValor();
            } else {
                saldo -= transacao.getValor();
            }
        }
        return saldo;
    }

    public boolean removerTransacao(int indice) {
        if (indice >= 0 && indice < transacoes.size()) {
            transacoes.remove(indice);
            return true;
        }
        return false;
    }
}