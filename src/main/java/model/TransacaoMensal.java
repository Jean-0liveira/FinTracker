/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ojean
 */
public class TransacaoMensal extends Transacao {
    private int mes;
    
public TransacaoMensal(String descricao, double valor, String tipo, int mes) {
    super(descricao, valor, tipo);
            this.mes = mes;
}

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        return super.toString() + "| Mês: " + mes;
    }
}
