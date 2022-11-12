import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Pair;

public class HubController {
    private List<double[]> points = new ArrayList<>();
    private Stack<Pair<Integer, Integer>> strokeStack = new Stack<>();
    private double stroke = 1;
    private int start = 0;

    // =============== Initialization ===============
    @FXML
    private WebView webview;

    @FXML
    private void initialize() {
        String htmlText = "<html><head><link rel=\"stylesheet\" href=\"styles.css\"></head><body><h1>This is a heading</h1><p style=\"color:red; background-color: gray;\">This is a paragraph.</p><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1></body></html>";
        webview.getEngine().loadContent(htmlText);
    }

    // =============== Menu Bar Controls ===============
    @FXML
    private AnchorPane app_main;

    @FXML
    private Label status;

    @FXML
    private void closeApp() {
        Stage stage = (Stage) app_main.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Pane canvas;

    @FXML
    private void undo() {
        if (!strokeStack.isEmpty()) {
            Pair<Integer, Integer> temp = strokeStack.pop();
            int beg = temp.getKey();
            int end = temp.getValue();
            stroke--;
            canvas.getChildren().remove(beg, end);
            points = points.subList(0, beg);
            start = beg;
        } else {
            status.setText("Cannot Undo");

        }
    }

    @FXML
    private void draw(MouseEvent e) {
        double font = 3;
        if (e.getButton() == MouseButton.PRIMARY) {
            double x = e.getX();
            double y = e.getY();
            points.add(new double[] { x, y, stroke });
            canvas.getChildren().add(new Circle(x, y, font / 2));
            status.setText("Drawing Shape");
        }

    }

    @FXML
    private void liftMouse(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            stroke++;
            strokeStack.push(new Pair<Integer, Integer>(start, points.size()));
            start = points.size();
        }
    }

}