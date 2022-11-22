import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelpWindow {
    public void display() {
        Stage window = new Stage();
        window.setResizable(false);
        window.setTitle("List of Gestures");

        VBox layout = new VBox();

        HBox row1 = new HBox();
        VBox grid1 = new VBox();

        ImageView img = new ImageView("img/img.jpeg");
        img.setFitHeight(150);
        img.setFitWidth(150);
        Label imgLabel = new Label("Image");
        grid1.getChildren().addAll(img, imgLabel);

        VBox grid2 = new VBox();
        ImageView h = new ImageView("img/h.jpeg");
        h.setFitHeight(150);
        h.setFitWidth(150);
        Label hLabel = new Label("Heading");
        grid2.getChildren().addAll(h, hLabel);

        VBox grid3 = new VBox();
        ImageView p = new ImageView("img/p.jpeg");
        p.setFitHeight(150);
        p.setFitWidth(150);
        Label pLabel = new Label("Paragraph");
        grid3.getChildren().addAll(p, pLabel);
        row1.getChildren().addAll(grid1, grid2, grid3);
        row1.setSpacing(20);

        HBox row2 = new HBox();
        VBox grid4 = new VBox();
        ImageView button = new ImageView("img/button.jpeg");
        button.setFitHeight(150);
        button.setFitWidth(150);
        Label buttonLabel = new Label("Button");
        grid4.getChildren().addAll(button, buttonLabel);

        VBox grid5 = new VBox();
        ImageView radio = new ImageView("img/radio.jpeg");
        radio.setFitHeight(150);
        radio.setFitWidth(150);
        Label radioLabel = new Label("Radio");
        grid5.getChildren().addAll(radio, radioLabel);

        VBox grid6 = new VBox();
        ImageView checkbox = new ImageView("img/checkbox.jpeg");
        checkbox.setFitHeight(150);
        checkbox.setFitWidth(150);
        Label checkboxLabel = new Label("Checkbox");
        grid6.getChildren().addAll(checkbox, checkboxLabel);
        row2.getChildren().addAll(grid4, grid5, grid6);
        row2.setSpacing(20);

        HBox row3 = new HBox();
        VBox grid7 = new VBox();
        ImageView ul = new ImageView("img/ul.jpeg");
        ul.setFitHeight(150);
        ul.setFitWidth(150);
        Label ulLabel = new Label("Unordered List");
        grid7.getChildren().addAll(ul, ulLabel);

        VBox grid8 = new VBox();
        ImageView ol = new ImageView("img/ol.jpeg");
        ol.setFitHeight(150);
        ol.setFitWidth(150);
        Label olLabel = new Label("Ordered List");
        grid8.getChildren().addAll(ol, olLabel);
        row3.getChildren().addAll(grid7, grid8);
        row3.setSpacing(20);

        Button close = new Button("Close");
        close.setOnAction(e -> window.close());

        layout.getChildren().addAll(row1, row2, row3, close);
        layout.setSpacing(20);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("css/help.css");
        window.setScene(scene);
        window.show();
    }
}
