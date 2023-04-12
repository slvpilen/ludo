package backend;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import onc.App;
import onc.GameFaceController;
import onc.backend.GameEngine;
import onc.backend.Player;
import onc.backend.GameNameInfo;
import onc.backend.SaveAndReadToFile;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;


public class SaveAndReadToFileTest extends Application {

    GameFaceController gameFaceController;
    GameNameInfo gameNameInfo;
    GameEngine gameEngine;
    SaveAndReadToFile fileSaver;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gameFace.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
    }

    @BeforeAll
    public static void setupClass() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gameFace.fxml"));
        fxmlLoader.load();
    }

    @BeforeEach
    public void setup() throws IOException {
        List<String> gameNameInfoAsList = Arrays.asList("Paaske cup", "Kari", "Ola","Truls", "Fredd");
        GameNameInfo gameNameInfo = new GameNameInfo(gameNameInfoAsList, 4, true);

        FXMLLoader loader = new FXMLLoader(GameNameInfo.class.getResource("/onc/startScreen.fxml"));
        loader.load();
        GameFaceController gameFaceController = loader.getController();

        gameFaceController.gameSetup(gameNameInfo);
        SaveAndReadToFile fileSaver = new SaveAndReadToFile();
    }

    

    
    @Test
    public void testSaveAndLoad() throws IOException {
        fileSaver.saveLudoGame(gameFaceController.getGameEngine(), gameNameInfo);
        GameEngine loadedGameEngine = fileSaver.loadLudoGame();
        List<String> playerNames = loadedGameEngine.getPlayers().stream().map(Player::getUsername).collect(Collectors.toList());

        assertEquals(playerNames.get(0), "Kari");
        
    
    }





}
