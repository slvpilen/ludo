package onc;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;



import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;



///  JavaFX App  ///

public class App extends Application {

    private static Scene scene;
        private MediaPlayer mediaPlayer;
        private Media media;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("startScreen"));
        stage.setTitle("LUDO cNo");
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(950);
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


    public void playMusic() throws FileNotFoundException {
        
        String songName = "BackgroundSongSuperMarioBros.mp3";
        URL songURL = getClass().getResource(songName);
        
        if (songURL != null) {
            System.out.println("hello");
            media = new Media(songURL.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }

        else {
            throw new FileNotFoundException("Sangen SUPERMARIOBROS ble ikke funnet!");
        }
    } 

    public static void main(String[] args) {
        launch();
    }

}