package backend;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.io.IOException;
import java.net.URL;
import javafx.stage.Stage;

public class ApplicationTest extends Application {
    
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationTest.class.getResource("gameFace.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setHeight(830); //830
        stage.setWidth(1100);
        stage.show();
    }


        
}



