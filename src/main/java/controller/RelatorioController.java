package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Tipo;
import utils.Formatador;

public class RelatorioController {

    @FXML private Label lblReceitas;
    @FXML private Label lblDespesas;
    @FXML private Label lblSaldo;
    @FXML private Label lblQuantidade;

    public void setFinTracker(FinTracker finTracker) {
        double receitas = finTracker.calcularTotalPorTipo(Tipo.RECEITA);
        double despesas = finTracker.calcularTotalPorTipo(Tipo.DESPESA);
        double saldo = finTracker.calcularSaldoTotal();

        lblReceitas.setText(Formatador.formatarMoeda(receitas));
        lblDespesas.setText(Formatador.formatarMoeda(despesas));
        lblSaldo.setText(Formatador.formatarMoeda(saldo));
        lblQuantidade.setText(String.valueOf(finTracker.getTransacoes().size()));

        lblSaldo.getStyleClass().removeAll("positivo", "negativo");
        lblSaldo.getStyleClass().add(saldo >= 0 ? "positivo" : "negativo");
    }

    @FXML
    private void fechar() {
        ((Stage) lblSaldo.getScene().getWindow()).close();
    }
}
