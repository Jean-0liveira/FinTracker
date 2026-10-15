package app;

import controller.FinTracker;
import controller.MainController;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Tipo;
import model.Transacao;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FinTracker finTracker = new FinTracker();
        finTracker.adicionarTransacao(new Transacao("Salário", 3000, Tipo.RECEITA, LocalDate.now()));
        finTracker.adicionarTransacao(new Transacao("Aluguel", 1200, Tipo.DESPESA, LocalDate.now()));
        finTracker.adicionarTransacao(new Transacao("Mercado", 450.75, Tipo.DESPESA, LocalDate.now()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main-view.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setFinTracker(finTracker);

        stage.setTitle("FinTrack");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
