package controller;

import dao.TransacaoDAO;
import java.util.Collection;
import java.util.List;
import model.Tipo;
import model.Transacao;
import repository.RepositorioGenerico;

public class FinTracker {
    private final RepositorioGenerico<Transacao> repositorio = new RepositorioGenerico<>();
    private final TransacaoDAO dao;

    public FinTracker() {
        this.dao = null;
    }

    public FinTracker(TransacaoDAO dao) {
        this.dao = dao;
        repositorio.adicionarTodos(dao.listar());
    }

    public void adicionarTransacao(Transacao transacao) {
        if (dao != null) {
            dao.inserir(transacao);
        }
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
        List<Transacao> lista = repositorio.listar();
        if (indice < 0 || indice >= lista.size()) {
            return false;
        }
        return removerTransacao(lista.get(indice));
    }

    public boolean removerTransacao(Transacao transacao) {
        if (dao != null && transacao.getId() != null) {
            dao.remover(transacao.getId());
        }
        return repositorio.remover(transacao);
    }
}
