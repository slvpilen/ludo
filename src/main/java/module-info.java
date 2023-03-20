module onc {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens onc to javafx.fxml;
    exports onc;
}
