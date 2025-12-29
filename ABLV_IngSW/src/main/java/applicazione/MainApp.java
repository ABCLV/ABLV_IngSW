package applicazione;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/client/Home.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("Software gare di pesca");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
