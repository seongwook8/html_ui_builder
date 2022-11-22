import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ChangeDimWindow {

    public static void display(HtmlRect rect, HubController controller) {
        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Dimension");

        Label wLabel = new Label("width:");
        wLabel.setPrefWidth(50);
        Label hLabel = new Label("height:");
        hLabel.setPrefWidth(50);

        Pair<Double, Double> wh = rect.getParentWH();
        double maxW = wh.getKey() - rect.getLayoutX();
        double maxH = wh.getValue() - rect.getLayoutY();

        Spinner<Double> wSpinner = new Spinner<Double>();
        wSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, maxW, rect.getRectangle().getWidth()));
        wSpinner.setPrefWidth(100);
        wSpinner.setEditable(true);
        wSpinner.getEditor().textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*")) {
                wSpinner.getEditor().setText(oldV);
            }
        });

        Spinner<Double> hSpinner = new Spinner<>();
        hSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, maxH, rect.getRectangle().getHeight()));
        hSpinner.setPrefWidth(100);
        hSpinner.setEditable(true);
        hSpinner.getEditor().textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*")) {
                hSpinner.getEditor().setText(oldV);
            }
        });

        Button apply = new Button("Apply");
        apply.setPrefWidth(60);
        apply.setOnAction(e -> {
            rect.setRect(wSpinner.getValue(), hSpinner.getValue());
            rect.setTextLocation();
            controller.renderHTMLText();
        });
        Button close = new Button("Close");
        close.setOnAction(e -> window.close());
        close.setPrefWidth(60);

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                rect.setRect(wSpinner.getValue(), hSpinner.getValue());
                controller.renderHTMLText();
            }
        });
        layout.setId("changeDimPane");
        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setPrefWidth(300);
        row1.setPrefHeight(50);
        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.setPrefHeight(50);
        HBox row3 = new HBox();
        row3.setAlignment(Pos.CENTER);
        row3.setSpacing(20);
        row3.setPrefHeight(50);

        row1.getChildren().addAll(wLabel, wSpinner);
        row2.getChildren().addAll(hLabel, hSpinner);
        row3.getChildren().addAll(apply, close);
        layout.getChildren().addAll(row1, row2, row3);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("css/style.css");
        window.setScene(scene);
        window.showAndWait();
    }

}
