module onc {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    opens onc to javafx.fxml;
    exports onc;
}
