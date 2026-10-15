package controller;

import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Transacao;
import utils.Formatador;

public class MainController {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML private TableView<Transacao> tabela;
    @FXML private TableColumn<Transacao, String> colData;
    @FXML private TableColumn<Transacao, String> colDescricao;
    @FXML private TableColumn<Transacao, String> colTipo;
    @FXML private TableColumn<Transacao, String> colValor;

    private final ObservableList<Transacao> dados = FXCollections.observableArrayList();
    private FinTracker finTracker;

    @FXML
    private void initialize() {
        colData.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getData().format(FORMATO_DATA)));
        colDescricao.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescricao()));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo().name()));
        colValor.setCellValueFactory(c -> new SimpleStringProperty(Formatador.formatarMoeda(c.getValue().getValor())));
        tabela.setItems(dados);
    }

    public void setFinTracker(FinTracker finTracker) {
        this.finTracker = finTracker;
        atualizarTabela();
    }

    private void atualizarTabela() {
        dados.setAll(finTracker.getTransacoes());
    }

    @FXML
    private void remover() {
        Transacao selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null && finTracker.removerTransacao(selecionada)) {
            atualizarTabela();
        }
    }
}
