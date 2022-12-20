module schoi330 {
    requires javafx.controls;
    requires javafx.fxml;

    opens schoi330 to javafx.fxml;
    exports schoi330;
}
