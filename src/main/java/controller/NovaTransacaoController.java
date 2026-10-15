package controller;

import exceptions.EntradaInvalidaException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Tipo;
import model.Transacao;

public class NovaTransacaoController {

    @FXML private TextField campoDescricao;
    @FXML private TextField campoValor;
    @FXML private ComboBox<Tipo> comboTipo;
    @FXML private DatePicker campoData;

    private Transacao resultado;

    @FXML
    private void initialize() {
        comboTipo.getItems().setAll(Tipo.values());
        comboTipo.getSelectionModel().selectFirst();
        campoData.setValue(LocalDate.now());
    }

    public Transacao getResultado() {
        return resultado;
    }

    @FXML
    private void salvar() {
        try {
            resultado = montarTransacao();
            fechar();
        } catch (EntradaInvalidaException e) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }

    @FXML
    private void cancelar() {
        resultado = null;
        fechar();
    }

    private Transacao montarTransacao() throws EntradaInvalidaException {
        String descricao = campoDescricao.getText().trim();
        if (descricao.isEmpty()) {
            throw new EntradaInvalidaException("Informe a descrição.");
        }

        double valor;
        try {
            valor = Double.parseDouble(campoValor.getText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Digite um valor numérico válido.");
        }
        if (valor <= 0) {
            throw new EntradaInvalidaException("O valor deve ser maior que zero.");
        }

        Tipo tipo = comboTipo.getValue();
        if (tipo == null) {
            throw new EntradaInvalidaException("Selecione o tipo.");
        }

        LocalDate data = campoData.getValue();
        if (data == null) {
            throw new EntradaInvalidaException("Informe a data.");
        }

        return new Transacao(descricao, valor, tipo, data);
    }

    private void fechar() {
        ((Stage) campoDescricao.getScene().getWindow()).close();
    }
}
