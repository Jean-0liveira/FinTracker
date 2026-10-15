package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.Formatador;

public class Transacao {
    private Integer id;
    private String descricao;
    private double valor;
    private Tipo tipo;
    private LocalDate data;

    public Transacao(String descricao, double valor, Tipo tipo, LocalDate data) {
        this(null, descricao, valor, tipo, data);
    }

    public Transacao(Integer id, String descricao, double valor, Tipo tipo, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    @Override
    public String toString() {
        return "Tipo: " + tipo
                + " | Data: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " | Descrição: " + descricao
                + " | Valor: " + Formatador.formatarMoeda(valor);
    }
}