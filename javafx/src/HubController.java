import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.text.html.HTML;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Pair;

public class HubController {
    private List<double[]> points = new ArrayList<>();
    private Stack<Pair<Integer, Integer>> strokeStack = new Stack<>();
    private List<Circle> circles = new ArrayList<>();
    private List<HtmlRect> rectangles = new ArrayList<>();
    private Stack<ObservableList<Node>> paneHistory = new Stack<>();
    private double stroke = 1;
    private int start = 0;
    private double font = 3;
    private CanvasProcessor cp;
    private int bodyLocation = 0;
    public static String htmlSource;

    // =============== Initialization ===============
    @FXML
    private WebView webview;

    @FXML
    private void initialize() {
        loadHtml("src/test.html");

        html_code.textProperty().addListener((obs, oldText, newText) -> {
            webview.getEngine().loadContent(html_code.getText());
        });

        cp = new CanvasProcessor();

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
        // if (!strokeStack.isEmpty()) {
        // Pair<Integer, Integer> temp = strokeStack.pop();
        // int beg = temp.getKey();
        // int end = temp.getValue();
        // stroke--;
        // canvas.getChildren().remove(beg, end);
        // points = points.subList(0, beg);
        // start = beg;
        // } else {
        // status.setText("Cannot Undo");

        // }
        // if (!paneHistory.isEmpty()) {
        // Rectangle rect = new Rectangle();

        // }
    }

    // =============== Canvas Area ===============
    private enum CanvasState {
        DRAW,
        SELECT,
        MOVE
    }

    private CanvasState canvasState = CanvasState.DRAW;
    private HtmlRect lastClicked;

    @FXML
    private void removeRect(KeyEvent e) {
        if (e.getCode() == KeyCode.DELETE) {
            canvas.getChildren().remove(lastClicked);
            renderHTMLText();
        }
    }

    @FXML
    private void pressCanvas(MouseEvent e) {
        if (canvasState == CanvasState.DRAW) {
            if (e.getButton() == MouseButton.PRIMARY) {
                canvas.requestFocus();
            } else if (e.getButton() == MouseButton.SECONDARY) {
                HtmlRect rect = cp.addShape(canvas, this.points, html_code);
                // addMouseDraggedEvent(rect);
                canvas.getChildren().add(rect);
                rectangles.add(rect);
                points = new ArrayList<>();
                strokeStack = new Stack<>();
                canvas.getChildren().removeAll(circles);
                circles = new ArrayList<>();
                paneHistory.push(canvas.getChildren());
                status.setText("Detected: " + rect.getTag());
                canvasState = CanvasState.MOVE;
                rect.requestFocus();
                renderHTMLText();
            }
        } else if (canvasState == CanvasState.MOVE) {
            if (e.getTarget().getClass() == canvas.getClass()) {
                canvasState = CanvasState.DRAW;
            } else {
                if (e.getTarget().getClass() == Rectangle.class || e.getTarget().getClass() == Text.class) {
                    lastClicked = (HtmlRect) ((Shape) e.getTarget()).getParent();
                } else {
                    lastClicked = (HtmlRect) e.getTarget();

                }

            }

        }

    }

    private void renderHTMLText() {
        String combined = "";
        for (Node node : canvas.getChildren()) {
            if (node.getClass() == HtmlRect.class) {
                HtmlRect rect = (HtmlRect) node;
                combined += "    " + rect.getHTMLString() + "\n";
            }
        }
        combined += "</body>\n</html>";
        html_code.replaceText(bodyLocation, html_code.getLength(), combined);
    }

    @FXML
    private void dragCanvas(MouseEvent e) {
        if (canvasState == CanvasState.DRAW) {
            if (e.getButton() == MouseButton.PRIMARY) {

                double x = e.getX();
                double y = e.getY();
                points.add(new double[] { x, y, stroke });
                Circle circle = new Circle(x, y, font / 2);
                canvas.getChildren().add(circle);
                circles.add(circle);
            }
        } else if (canvasState == CanvasState.MOVE) {
            if (e.getButton() == MouseButton.PRIMARY) {
                renderHTMLText();
            }
        }

    }

    @FXML
    private void liftCanvas(MouseEvent e) {
        if (canvasState == CanvasState.DRAW) {
            if (e.getButton() == MouseButton.PRIMARY) {
                stroke++;
                strokeStack.push(new Pair<Integer, Integer>(start, points.size()));
                start = points.size();
            }
        }

    }

    // =============== HTML Code Area ===============

    @FXML
    private TextArea html_code;

    private void loadHtml(String path) {

        List<String> htmlList = HtmlProcessor.html2List(path);
        String htmlSource = "";
        for (String line : htmlList) {
            htmlSource += line + "\n";
            if (line.endsWith("<body>")) {
                bodyLocation = htmlSource.length();
            }

        }

        html_code.setText(htmlSource);

        webview.getEngine().loadContent(htmlSource);
    }

    @FXML
    private void changeTab(KeyEvent e) {
        if (e.getCode() == KeyCode.TAB) {
            int location = html_code.getCaretPosition();
            html_code.replaceText(location - 1, location, "    ");
        }
    }

}