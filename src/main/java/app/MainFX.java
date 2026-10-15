package app;

import controller.FinTracker;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FinTracker finTracker = new FinTracker();

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
