/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Transacao;

/**
 *
 * @author ojean
 */
public class FinTracker {
    private ArrayList<Transacao> transacoes = new ArrayList<>();
    
    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }
    
    public void listarTransacoes() {
        for (Transacao transacao : transacoes) {
            System.out.println(transacao);
        }
    }
    
    public double calcularSaldoTotal() {
        
        double saldo = 0;
        
        for (Transacao transacao : transacoes) {
            
            if (transacao.getTipo().equalsIgnoreCase("receita")) {
                saldo += transacao.getValor();
            } else {
                saldo -= transacao.getValor();
            }
        }
        
        return saldo;
    }
    
    public void removerTransacao(int indice) {
        
        if (indice >= 0 && indice < transacoes.size()) {
            transacoes.remove(indice);
        }
    }
}
