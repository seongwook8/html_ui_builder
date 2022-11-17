import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class HtmlRect extends StackPane {
    public double prevX, prevY, prevW, prevH;
    private String tag;
    public Rectangle rect;
    private Text text;
    private String defaultText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    private String content;
    private TextArea html_code;

    public HtmlRect(double x, double y, double width, double height, String name, TextArea html_code) {
        super();
        this.html_code = html_code;
        setLayoutX(x);
        setLayoutY(y);
        this.tag = name;
        rect = new Rectangle(x, y, width, height);
        rect.setFill(Color.WHITESMOKE);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);
        rect.getStrokeDashArray().addAll(5.0, 5.0, 5.0, 5.0, 5.0);
        text = new Text(this.tag);
        content = defaultText;
        getChildren().add(rect);
        getChildren().add(text);

        setOnMouseMoved(e -> {
            double mouseX = e.getX();
            double mouseY = e.getY();
            double resizeWidth = 8;
            StackPane s = (StackPane) (e.getSource());
            double curW = s.getWidth();
            double curH = s.getHeight();

            if (mouseX < resizeWidth && mouseY < resizeWidth) {
                setCursor(Cursor.NW_RESIZE);
            } else if (mouseY < resizeWidth && mouseX > curW - resizeWidth) {
                setCursor(Cursor.NE_RESIZE);
            } else if (mouseX < resizeWidth && mouseY > curH - resizeWidth) {
                setCursor(Cursor.SW_RESIZE);
            } else if (mouseX > -resizeWidth + curW && mouseY > curH - resizeWidth) {
                setCursor(Cursor.SE_RESIZE);
            } else if (mouseX < resizeWidth) {
                setCursor(Cursor.W_RESIZE);
            } else if (mouseY < resizeWidth) {
                setCursor(Cursor.N_RESIZE);
            } else if (mouseX > -resizeWidth + curW) {
                setCursor(Cursor.E_RESIZE);
            } else if (mouseY > -resizeWidth + curH) {
                setCursor(Cursor.S_RESIZE);
            } else {
                setCursor(Cursor.HAND);
            }
        });

        focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                // rect.setStroke(Color.TRANSPARENT);
                // rect.setStrokeWidth(1);
                // rect.setFill(Color.TRANSPARENT);
                rect.setVisible(false);
                text.setVisible(false);
            } else {
                rect.setVisible(true);
                text.setVisible(true);
                rect.setStroke(Color.BLUE);
                rect.setFill(Color.WHITESMOKE);
                rect.setStrokeWidth(1.5);

            }
        });

        setOnMousePressed((e) -> {
            requestFocus();
            prevX = e.getSceneX();
            prevY = e.getSceneY();

        });

        setOnMouseDragged((e) -> {

            double offsetX = e.getSceneX() - prevX;
            double offsetY = e.getSceneY() - prevY;

            StackPane s = (StackPane) (e.getSource());
            Cursor curCursor = getCursor();

            double newX = s.getLayoutX() + offsetX;
            double newY = s.getLayoutY() + offsetY;
            Pair<Double, Double> parentWH = getParentWH();
            double paneW = parentWH.getKey();
            double paneH = parentWH.getValue();
            boolean leftBound = newX >= 0;
            boolean topBound = newY >= 0;
            boolean rightBound = newX + rect.getWidth() < paneW;
            boolean bottomBound = newY + rect.getHeight() < paneH;

            if (curCursor == Cursor.HAND) {
                if (leftBound && rightBound && topBound && bottomBound) {
                    s.setLayoutX(newX);
                    s.setLayoutY(newY);
                }

            }
            if (curCursor == Cursor.N_RESIZE || curCursor == Cursor.NE_RESIZE ||
                    curCursor == Cursor.NW_RESIZE) {
                if (topBound && rect.getHeight() - offsetY > 20) {
                    s.setLayoutY(s.getLayoutY() + offsetY);
                    rect.setHeight(rect.getHeight() - offsetY);
                }

            }
            if (curCursor == Cursor.E_RESIZE || curCursor == Cursor.NE_RESIZE ||
                    curCursor == Cursor.SE_RESIZE) {
                if (rightBound && rect.getWidth() + offsetX > 20) {
                    rect.setWidth(rect.getWidth() + offsetX);
                }

            }
            if (curCursor == Cursor.S_RESIZE || curCursor == Cursor.SE_RESIZE ||
                    curCursor == Cursor.SW_RESIZE) {
                if (bottomBound && rect.getHeight() + offsetY > 20) {

                    rect.setHeight(rect.getHeight() + offsetY);
                }

            }
            if (curCursor == Cursor.W_RESIZE || curCursor == Cursor.NW_RESIZE ||
                    curCursor == Cursor.SW_RESIZE) {
                if (leftBound && rect.getWidth() - offsetX > 20) {

                    s.setLayoutX(s.getLayoutX() + offsetX);
                    rect.setWidth(rect.getWidth() - offsetX);
                }

            }

            prevX = e.getSceneX();
            prevY = e.getSceneY();

        });

    }

    public String getTag() {
        return this.tag;
    }

    public Pair<Double, Double> getParentWH() {
        Pane pane = getParentPane();
        return new Pair<Double, Double>(pane.getWidth(), pane.getHeight());
    }

    public Pane getParentPane() {
        Pane pane = (Pane) getParent();

        return pane;
    }

    public String getHTMLString() {
        int num = ((Pane) getParent()).getChildren().size();
        // String output = "<" + this.tag + " ";
        String output = "<h1 ";
        output += "id = \"" + num + "\" style=\"position:absolute; width:" + rect.getWidth() + "px; height:"
                + rect.getHeight();
        output += "px; top:" + getLayoutY() + "px; left:" + getLayoutX() + "px;\">";
        // output += this.content + "</" + this.tag + ">";
        output += this.content + "</" + "h1" + ">";
        return output;
    }

}
