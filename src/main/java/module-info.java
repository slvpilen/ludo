module onc {
    requires javafx.controls;
    requires javafx.fxml;

    opens onc to javafx.fxml;
    exports onc;
}
