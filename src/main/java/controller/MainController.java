package controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Tipo;
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
        configurarCelulaColorida(colTipo);
        configurarCelulaColorida(colValor);
        tabela.setItems(dados);
    }

    private void configurarCelulaColorida(TableColumn<Transacao, String> coluna) {
        coluna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().removeAll("receita", "despesa");
                if (empty || item == null) {
                    setText(null);
                    return;
                }
                setText(item);
                Transacao transacao = getTableRow() == null ? null : getTableRow().getItem();
                if (transacao != null) {
                    getStyleClass().add(transacao.getTipo() == Tipo.RECEITA ? "receita" : "despesa");
                }
            }
        });
    }

    public void setFinTracker(FinTracker finTracker) {
        this.finTracker = finTracker;
        atualizarTabela();
    }

    private void atualizarTabela() {
        dados.setAll(finTracker.getTransacoes());
    }

    @FXML
    private void novaTransacao() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/nova-transacao-view.fxml"));
        Parent root = loader.load();
        NovaTransacaoController dialogo = loader.getController();

        Stage janela = criarJanelaModal("Nova transação", root);
        janela.showAndWait();

        Transacao nova = dialogo.getResultado();
        if (nova != null) {
            finTracker.adicionarTransacao(nova);
            atualizarTabela();
        }
    }

    @FXML
    private void abrirRelatorio() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/relatorio-view.fxml"));
        Parent root = loader.load();
        RelatorioController relatorio = loader.getController();
        relatorio.setFinTracker(finTracker);

        criarJanelaModal("Relatório", root).showAndWait();
    }

    @FXML
    private void remover() {
        Transacao selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null && finTracker.removerTransacao(selecionada)) {
            atualizarTabela();
        }
    }

    private Stage criarJanelaModal(String titulo, Parent root) {
        Stage janela = new Stage();
        janela.setTitle(titulo);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.initOwner(tabela.getScene().getWindow());
        janela.setScene(new Scene(root));
        janela.setResizable(false);
        return janela;
    }
}
