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


public class App extends Application {
    
    private Image ludoIcon = new Image(getClass().getResourceAsStream("/onc/LudoIcon.jpg"));
    private static Scene scene;
    private static MediaPlayer mediaPlayer;
    private static Media media;

    /**
     * This method runs when the program gets launched.
     * The startScreen gets loaded, and the title of the screen is set to "LUDO cNo"
     * Additionally, a ludoIcon is applied to the app.
     * The startScreen is shown at the end, and superMarioBros music gets played.
     */
    @Override
    public void start(Stage stage) throws IOException {
        
        scene = new Scene(loadFXML("startScreen"));
        stage.setTitle("LUDO cNo");
        stage.getIcons().add(ludoIcon);

        stage.setScene(scene);
        stage.setHeight(830); //830
        stage.setWidth(1100);
        playMusic(); 
        stage.show();
    }

    /**
     * This is a utility-method which loads an fxml-file, which is specified with the String-argument.
     * @param fxml The name of the fxml-file which should be loaded (excluding the .fxml part).
     * @throws IOException If the argument is not a fxml-file in the project.
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * This methods loads the superMarioBros-music, and starts to play it.
     * @throws FileNotFoundException If the superMarioBros music file is not found.
     */
    public static void playMusic() throws FileNotFoundException {
        
        URL songURL = App.class.getResource("/onc/BackgroundSongSuperMarioBros.mp3");
        
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


    /**
     * This method resumes the superMarioBrosMusic.
     */
    public static void resumeMusic() throws FileNotFoundException {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }  

    /**
     * This method pauses the superMarioBrosMusic.
     */
    public static void pauseMusic() throws FileNotFoundException {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        
    } 
    
    /**
     * This method starts the entire program.
     * When the program starts, the launch-method extended from Application is executed.
     */
    public static void main(String[] args) {
        launch();
    }


}