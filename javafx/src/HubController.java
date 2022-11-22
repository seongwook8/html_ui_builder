import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class HubController {
    private List<double[]> points = new ArrayList<>();
    private List<Circle> circles = new ArrayList<>();
    private List<HtmlRect> rectangles = new ArrayList<>();
    private double stroke = 1;
    private double font = 3;
    private CanvasProcessor cp;
    private int bodyLocation = 0;

    // =============== Initialization ===============
    @FXML
    private WebView webview;

    @FXML
    private void initialize() {
        loadHtml("src/test.html");
        html_code.setEditable(false);

        html_code.textProperty().addListener((obs, oldText, newText) -> {
            webview.getEngine().loadContent(html_code.getText());
        });

        menubar.setStyle("-fx-background-color: #1f276f;");

        cp = new CanvasProcessor();

    }

    // =============== Menu Bar Controls ===============
    @FXML
    private AnchorPane app_main;

    @FXML
    private MenuBar menubar;

    @FXML
    private Label status;

    @FXML
    private void closeApp() {
        Stage stage = (Stage) app_main.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Pane canvas;

    // =============== Canvas Area ===============

    private enum CanvasState {
        DRAW,
        MOVE
    }

    private boolean stylusMode = false;
    private CanvasState canvasState = CanvasState.DRAW;
    private HtmlRect lastClicked;

    @FXML
    private void removeRect(KeyEvent e) {
        if (e.getCode() == KeyCode.DELETE && lastClicked != null) {
            removeElement(lastClicked);
        }
    }

    @FXML
    private void removeSelected() {
        if (lastClicked != null) {
            removeElement(lastClicked);
        }

    }

    public void removeElement(HtmlRect rect) {
        canvas.getChildren().remove(rect);
        renderHTMLText();
    }

    @FXML
    private void clearAll() {
        clearElements();
        clearStrokes();
    }

    @FXML
    private void clearElements() {
        canvas.getChildren().clear();
        renderHTMLText();
    }

    @FXML
    private void clearStrokes() {
        points = new ArrayList<>();
        canvas.getChildren().removeAll(circles);
        circles = new ArrayList<>();
        stroke = 1;
        renderHTMLText();

    }

    @FXML
    private void changeDimension() {
        if (lastClicked != null) {
            ChangeDimWindow.display(lastClicked, this);
        }

    }

    @FXML
    private void changeContent() {
        if (lastClicked != null) {
            ChangeContentWindow.display(lastClicked);
        }

    }

    @FXML
    private void pressCanvas(MouseEvent e) {
        if (canvasState == CanvasState.DRAW) {
            if (e.getButton() == MouseButton.PRIMARY) {
                canvas.requestFocus();

            } else if (e.getButton() == MouseButton.SECONDARY && !stylusMode) {
                detectGesture();
            }
        } else if (canvasState == CanvasState.MOVE) {
            if (e.getButton() == MouseButton.PRIMARY) {
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

                if (stylusMode) {

                }

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
            }

        }

    }

    @FXML
    private void selectedToFront() {
        if (lastClicked != null) {
            bringToFront(lastClicked);
        }

    }

    @FXML
    private void selectedToBack() {
        if (lastClicked != null) {
            sendToBack(lastClicked);
        }

    }

    public void bringToFront(HtmlRect element) {
        canvas.getChildren().remove(element);
        canvas.getChildren().add(element);
        renderHTMLText();
    }

    public void sendToBack(HtmlRect element) {
        canvas.getChildren().remove(element);
        canvas.getChildren().add(0, element);
        renderHTMLText();
    }

    @FXML
    private void detectGesture() {
        if (points.size() > 0) {
            HtmlRect rect = cp.addShape(canvas, this.points, this);
            rectangles.add(rect);
            clearStrokes();
            status.setText("Detected: " + rect.getTag());
            canvasState = CanvasState.MOVE;
            rect.requestFocus();
            renderHTMLText();
            lastClicked = rect;
        }

    }

    // =============== HTML Code Area ===============

    @FXML
    private void loadNew() {
        clearAll();
        loadHtml("src/test.html");
    }

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

    public void renderHTMLText() {
        String combined = "";
        for (Node node : canvas.getChildren()) {
            if (node.getClass() == HtmlRect.class) {
                HtmlRect rect = (HtmlRect) node;
                combined += "    " + rect.getHTMLString() + "\n";
            }
        }
        combined += "</body>\n\n</html>";
        html_code.replaceText(bodyLocation, html_code.getLength(), combined);
    }

}