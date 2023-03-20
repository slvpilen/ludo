package onc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
/* import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media; */
import javafx.stage.Stage;
import java.io.IOException;



///  JavaFX App  ///

public class App extends Application {

    private static Scene scene;
/*     private MediaPlayer mediaPlayer;
    private media Media; */

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("startScreen"));
        stage.setTitle("LUDO cNo");
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(950);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


/*     public void playMusic() {
        String songName = "BackgroundSongSuperMarioBros.mp3";
         media = new Media(songName.toURI())
        mediaPlayer = new MediaPlayer(media); 
    } */

    public static void main(String[] args) {
        launch();
    }

}