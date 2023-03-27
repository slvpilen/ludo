package onc;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;



///  JavaFX App  ///

public class App extends Application {
    
    Image ludoIcon = new Image(getClass().getResourceAsStream("LudoIcon.jpg"));

    private static Scene scene;
        private static MediaPlayer mediaPlayer;
        private static Media media;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("startScreen"));
        stage.setTitle("LUDO cNo");
            
        stage.getIcons().add(ludoIcon);

        stage.setScene(scene);
        stage.setHeight(830);
        stage.setWidth(1100);
        playMusic(); 
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void playMusic() throws FileNotFoundException {
        
        URL songURL = App.class.getResource("BackgroundSongSuperMarioBros.mp3");
        
        if (songURL != null) {
            media = new Media(songURL.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }

        else {
            throw new FileNotFoundException("Sangen SUPERMARIOBROS ble ikke funnet!");
        }
    } 

    public static void pauseMusic() throws FileNotFoundException {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        
    } 

    public static void resumeMusic() throws FileNotFoundException {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
        
    } 

    public static void main(String[] args) {
        launch();
    }

}