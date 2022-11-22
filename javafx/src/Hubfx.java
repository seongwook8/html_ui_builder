import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Hubfx extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL hubFxml = getClass().getResource("hubfx.fxml");
        FXMLLoader loader = new FXMLLoader(hubFxml);
        Scene scene = new Scene(loader.load(), 1200, 800);

        URL css = getClass().getResource("css/style.css");
        scene.getStylesheets().add(css.toExternalForm());

        stage.setTitle("HTML_UI_Builder");
        stage.setMinWidth(1200);
        stage.setMinHeight(810);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();

    }
}