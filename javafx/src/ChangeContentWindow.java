
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeContentWindow {

    public static void display(HtmlRect rect) {
        List<String> tempList = new ArrayList<>(rect.getList());

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Content");
        window.setResizable(false);

        Label tagLabel = new Label("Element Type:");
        tagLabel.setPrefWidth(90);
        String[] tags = { "heading", "paragraph", "button", "image", "radio button", "checkbox", "unordered list",
                "ordered list" };
        ComboBox<String> tagCombo = new ComboBox<>(FXCollections.observableArrayList(tags));
        setBaseCombo(rect, tagCombo);

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setPrefWidth(400);
        row1.getChildren().addAll(tagLabel, tagCombo);

        Label headingSize = new Label("Heading Size:");
        headingSize.setPrefWidth(90);
        ComboBox<Integer> hSizeCombo = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6));
        hSizeCombo.setValue(rect.getHSize());
        hSizeCombo.setOnAction(e -> {
            rect.setHSize(hSizeCombo.getValue());
        });
        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.getChildren().addAll(headingSize, hSizeCombo);

        TextArea contentText = new TextArea(rect.getContent());
        contentText.setPrefWidth(400);
        contentText.setWrapText(true);
        contentText.setPrefHeight(200);
        HBox row3 = new HBox();
        row3.setAlignment(Pos.CENTER);
        row3.getChildren().addAll(contentText);

        Label srcLabel = new Label("Image Source:");
        srcLabel.setPrefWidth(90);
        TextField src = new TextField(rect.getSrc());
        HBox row4 = new HBox();
        row4.setAlignment(Pos.CENTER);
        row4.getChildren().addAll(srcLabel, src);

        Label legendLabel = new Label("Legend:");
        legendLabel.setPrefWidth(90);
        TextField legend = new TextField(rect.getLegend());
        HBox row5 = new HBox();
        row5.setAlignment(Pos.CENTER);
        row5.getChildren().addAll(legendLabel, legend);

        Label listNum = new Label();
        listNum.setPrefWidth(100);
        HBox row6 = new HBox();
        row6.setAlignment(Pos.CENTER);
        row6.getChildren().addAll(listNum);

        ObservableList<String> listElements = FXCollections.observableArrayList(tempList);
        ListView<String> listview = new ListView<>(listElements);
        listview.setPrefHeight(300);
        listview.setEditable(true);
        listview.setCellFactory(TextFieldListCell.forListView());
        listNum.setText(listview.getItems().size() + " items in the list");

        HBox row7 = new HBox();
        row7.setAlignment(Pos.CENTER);
        row7.getChildren().addAll(listview);

        Button addElement = new Button("Add");
        addElement.setPrefWidth(60);
        addElement.setOnAction(e -> {
            listElements.add("default text");
            listview.getSelectionModel().selectLast();
            listview.edit(listview.getItems().size() - 1);
            listNum.setText(listview.getItems().size() + " items in the list");

        });
        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            listElements.remove(listview.getSelectionModel().getSelectedIndex());
            listNum.setText(listview.getItems().size() + " items in the list");
        });
        delete.setPrefWidth(60);
        Button moveUp = new Button("Up");
        moveUp.setPrefWidth(50);
        moveUp.setOnAction(e -> {
            int index = listview.getSelectionModel().getSelectedIndex();
            String element = listElements.get(index);
            if (index > 0) {
                listElements.remove(index);
                listElements.add(index - 1, element);
                listview.getSelectionModel().select(index - 1);
            }
        });
        Button moveDown = new Button("Down");
        moveDown.setOnAction(e -> {
            int index = listview.getSelectionModel().getSelectedIndex();
            String element = listElements.get(index);
            if (index < listElements.size() - 1) {
                listElements.remove(index);
                listElements.add(index + 1, element);
                listview.getSelectionModel().select(index + 1);
            }
        });
        moveDown.setPrefWidth(50);

        HBox row8 = new HBox();
        row8.setAlignment(Pos.CENTER);
        row8.setSpacing(10);
        row8.getChildren().addAll(addElement, delete, moveUp, moveDown);

        Button apply = new Button("Apply");
        apply.setPrefWidth(60);
        apply.setOnAction(e -> {
            rect.setContent(contentText.getText());
            rect.setLegend(legend.getText());
            rect.setSrc(src.getText());
            rect.setList(listElements);
            rect.renderHTMLText();
        });
        Button close = new Button("Close");
        close.setOnAction(e -> {
            window.close();
            listElements.clear();
            listElements.addAll(rect.getList());
        });
        close.setPrefWidth(60);
        HBox rowLast = new HBox();
        rowLast.setAlignment(Pos.CENTER);
        rowLast.getChildren().addAll(apply, close);
        rowLast.setSpacing(10);

        List<HBox> rows = new ArrayList<>(
                Arrays.asList(row1, row2, row3, row4, row5, row6, row7, row8, rowLast));

        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setSpacing(10);
        makeVBox(layout, rows, rect, window);
        layout.setId("changeContPane");

        tagCombo.setOnAction(e -> {
            rect.setTag(combo2Tag(tagCombo.getValue()));
            makeVBox(layout, rows, rect, window);
            rect.setList(listElements);
        });

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("css/style.css");
        window.setScene(scene);
        window.show();

    }

    private static void makeVBox(VBox layout, List<HBox> rows, HtmlRect rect, Stage window) {
        layout.getChildren().clear();
        layout.getChildren().add(new Region());
        layout.getChildren().add(rows.get(0));
        if (rect.getTag().equals("button") || rect.getTag().equals("p") || rect.getTag().equals("h")) {
            layout.resize(400, 270);

            if (rect.getTag().equals("h")) {
                layout.getChildren().add(rows.get(1));
                layout.resize(400, 300);
            }
            layout.getChildren().add(rows.get(2));

        } else if (rect.getTag().equals("img")) {
            layout.getChildren().add(rows.get(3));
            layout.resize(400, 150);
        } else {
            if (rect.getTag().equals("radio") || rect.getTag().equals("checkbox")) {
                layout.getChildren().add(rows.get(4));
            }
            layout.getChildren().add(rows.get(5));
            layout.getChildren().add(rows.get(6));
            layout.getChildren().add(rows.get(7));
            layout.resize(400, 500);
            layout.getChildren().add(new Region());
        }

        layout.getChildren().add(rows.get(8));
        layout.getChildren().add(new Region());

        window.sizeToScene();
    }

    private static void setBaseCombo(HtmlRect rect, ComboBox<String> tagCombo) {
        String tag = rect.getTag();
        String output;
        if (tag.equals("h")) {
            output = "heading";
        } else if (tag.equals("p")) {
            output = "paragraph";
        } else if (tag.equals("button")) {
            output = "button";
        } else if (tag.equals("img")) {
            output = "image";
        } else if (tag.equals("radio")) {
            output = "radio button";
        } else if (tag.equals("checkbox")) {
            output = "checkbox";
        } else if (tag.equals("ul")) {
            output = "unordered list";
        } else {
            output = "ordered list";
        }
        tagCombo.setValue(output);
    }

    private static String combo2Tag(String comboTag) {
        if (comboTag.equals("heading")) {
            return "h";
        } else if (comboTag.equals("paragraph")) {
            return "p";
        } else if (comboTag.equals("image")) {
            return "img";
        } else if ((comboTag.equals("radio button"))) {
            return "radio";
        } else if (comboTag.equals("unordered list")) {
            return "ul";
        } else if (comboTag.equals("ordered list")) {
            return "ol";
        } else
            return comboTag;

    }

}
