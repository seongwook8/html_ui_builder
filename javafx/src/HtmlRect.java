import java.util.ArrayList;
import java.util.List;

import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class HtmlRect extends StackPane {
    private HubController controller;
    public Rectangle rect;
    private Text text;
    private ContextMenu contextMenu;

    public double prevX, prevY, prevW, prevH;

    private String tag;
    private String defaultText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    private String defaultSrc = "https://picsum.photos/id/24/500/500";
    private String content;
    private String src = "";
    private String alt = "";
    private int hSize = 1;

    private List<String> listElements = new ArrayList<>();
    private String legend = "";

    public HtmlRect(double x, double y, double width, double height, String name, HubController controller) {
        super();
        setLayoutX(x);
        setLayoutY(y);
        this.tag = name;
        this.controller = controller;
        this.src = defaultSrc;

        content = defaultText;

        setRectText(x, y, width, height);

        setMouseEvents();

        setContextMenu();

        focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                if (!this.tag.equals("radio") && !this.tag.equals("checkbox")) {
                    rect.setStroke(Color.BLACK);
                    rect.setStrokeWidth(1);
                    // rect.setVisible(false);
                } else {
                    rect.setStroke(Color.TRANSPARENT);
                }
                text.setVisible(false);
                rect.setFill(Color.TRANSPARENT);
            } else {
                rect.setVisible(true);
                text.setVisible(true);
                rect.setStroke(Color.BLUE);
                rect.setFill(Color.WHITESMOKE);
                rect.setStrokeWidth(1.5);

            }
        });

    }

    public void setRectText(double x, double y, double width, double height) {
        rect = new Rectangle(x, y, width, height);
        rect.setFill(Color.WHITESMOKE);
        rect.getStrokeDashArray().addAll(5.0, 5.0, 5.0, 5.0, 5.0);
        if (tag.equals("h")) {
            text = new Text(this.tag + hSize);
        } else {
            text = new Text(this.tag);
        }
        getChildren().add(rect);
        getChildren().add(text);
    }

    public void setMouseEvents() {
        mouseMoved();
        mousePressed();
        mouseDragged();
    }

    public void mouseMoved() {
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
    }

    public void mousePressed() {
        setOnMousePressed((e) -> {
            requestFocus();
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(rect, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
                prevX = e.getSceneX();
                prevY = e.getSceneY();
            }

        });
    }

    public void mouseDragged() {
        setOnMousePressed((e) -> {
            requestFocus();
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(rect, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
                prevX = e.getSceneX();
                prevY = e.getSceneY();
            }

        });

        setOnMouseDragged((e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {

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
                    if (leftBound && topBound) {
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
            }

        });
    }

    public void setContextMenu() {
        this.contextMenu = new ContextMenu();
        MenuItem changeDimension = new MenuItem("Change Dimension");
        changeDimension.setOnAction(e -> ChangeDimWindow.display(this, controller));

        MenuItem changeContent = new MenuItem("Change Content");
        changeContent.setOnAction(e -> ChangeContentWindow.display(this));

        MenuItem bringFront = new MenuItem("Bring to Front");
        bringFront.setOnAction(e -> controller.bringToFront(this));

        MenuItem sendBack = new MenuItem("Send to Back");
        sendBack.setOnAction(e -> controller.sendToBack(this));

        MenuItem removeElement = new MenuItem("Remove");
        removeElement.setOnAction(e -> {
            controller.removeElement(this);
        });

        contextMenu.getItems().addAll(changeDimension, bringFront, sendBack, changeContent,
                removeElement);
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getHSize() {
        return this.hSize;
    }

    public void setHSize(int hSize) {
        this.hSize = hSize;
        this.text.setText(this.tag + this.hSize);
    }

    public Rectangle getRectangle() {
        return this.rect;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getLegend() {
        return this.legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;

    }

    public List<String> getList() {
        return this.listElements;
    }

    public void renderHTMLText() {
        controller.renderHTMLText();
    }

    public void setList(List<String> list) {
        this.listElements = new ArrayList<>(list);
    }

    public void setRect(double width, double height) {
        rect.setWidth(width);
        rect.setHeight(height);
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

        if (this.tag.equals("img")) {
            return getHTMLImg();
        } else if (this.tag.equals("ul") || this.tag.equals("ol")) {
            return getHTMLlist();
        } else if (this.tag.equals("radio") || this.tag.equals("checkbox")) {
            return getHTMLCheck();
        } else if (this.tag.equals("h")) {
            return getHTag();
        } else {
            return getHTMLReg();
        }
    }

    public String getInLineStyle() {
        String output = "style=\"width:" + rect.getWidth() + "px; height:" + rect.getHeight();
        output += "px; top:" + getLayoutY() + "px; left:" + getLayoutX() + "px;\"";
        return output;
    }

    public String getHTMLImg() {
        String output = "<img src=\"" + this.src + "\"";
        output += " alt=\"" + this.alt + "\" ";
        output += getInLineStyle() + ">";
        return output;
    }

    public String getHTag() {
        String output = "<" + this.tag + this.hSize + " " + getInLineStyle() + ">";
        output += this.content;
        output += "</" + this.tag + this.hSize + ">";
        return output;
    }

    public String getHTMLReg() {
        String output = "<" + this.tag + " " + getInLineStyle() + ">";
        output += this.content;
        output += "</" + this.tag + ">";
        return output;
    }

    public String getHTMLlist() {
        String output = "<" + this.tag + " " + getInLineStyle() + ">\n";
        for (int i = 0; i < this.listElements.size(); i++) {
            output += "        <li>";
            output += listElements.get(i);
            output += "</li>\n";
        }
        output += "    </" + this.tag + ">";
        return output;
    }

    public String getHTMLCheck() {
        String output = "<fieldset " + getInLineStyle() + ">\n        <legend>" + this.legend + "</legend>\n";
        for (int i = 0; i < this.listElements.size(); i++) {
            output += "            <div>\n";
            output += "                <input type=\"" + this.tag + "\" name=\"" + this.hashCode() + "\" ";
            if (i == 0) {
                output += "checked";
            }
            output += ">\n";
            output += "                <label>" + listElements.get(i) + "</label>\n";
            output += "        </div>\n";
        }
        output += "    </fieldset>";
        return output;
    }

    public void setNumElements(int num) {
        for (int i = 0; i < num; i++) {
            this.listElements.add("default text");
        }
    }

}
