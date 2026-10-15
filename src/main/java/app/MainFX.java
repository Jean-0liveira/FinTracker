package app;

import controller.FinTracker;
import controller.MainController;
import dao.TransacaoDAO;
import database.Conexao;
import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    private Connection conexao;

    @Override
    public void start(Stage stage) throws Exception {
        conexao = Conexao.abrir();
        Conexao.criarTabela(conexao);
        FinTracker finTracker = new FinTracker(new TransacaoDAO(conexao));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main-view.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setFinTracker(finTracker);

        stage.setTitle("FinTrack");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        if (conexao != null) {
            conexao.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
