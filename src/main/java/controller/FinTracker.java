package controller;

import java.util.Collection;
import java.util.List;
import model.Tipo;
import model.Transacao;
import repository.RepositorioGenerico;

public class FinTracker {
    private final RepositorioGenerico<Transacao> repositorio = new RepositorioGenerico<>();

    public void adicionarTransacao(Transacao transacao) {
        repositorio.adicionar(transacao);
    }

    public List<Transacao> getTransacoes() {
        return repositorio.listar();
    }

    public void listarTransacoes() {
        int i = 0;
        for (Transacao transacao : repositorio.listar()) {
            System.out.println(i++ + " - " + transacao);
        }
    }

    public double calcularSaldoTotal() {
        return calcularSaldo(repositorio.listar());
    }

    public double calcularTotalPorTipo(Tipo tipo) {
        double total = 0;
        for (Transacao transacao : repositorio.filtrar(t -> t.getTipo() == tipo)) {
            total += transacao.getValor();
        }
        return total;
    }

    public static double calcularSaldo(Collection<? extends Transacao> transacoes) {
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
        return repositorio.removerPorIndice(indice);
    }

    public boolean removerTransacao(Transacao transacao) {
        return repositorio.remover(transacao);
    }
}
