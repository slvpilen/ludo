package onc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import onc.backend.GameEngine;
import onc.backend.GameNameInfo;
import onc.backend.InterfaceGameEngineListener;
import onc.backend.InterfacePopupListener;
import onc.backend.Player;
import onc.backend.RobotPlayer;
import onc.backend.SaveAndReadToFile;
import onc.backend.Settings;


public class GameFaceController implements InterfaceGameEngineListener {

    private Scene scene;
    private GameEngine gameEngine;
    private GameNameInfo gameNameInfo;
    private boolean startMessageHidden;
    private SaveAndReadToFile fileSaver;
    private List<InterfacePopupListener> listeners = new ArrayList<>();
    
    @FXML private Text gameName, player1Name, player2Name, player3Name, player4Name, playerTurn;

    @FXML private GridPane gameGrid;

    @FXML private ImageView diceView;

    @FXML private VBox startMessage;

    @FXML private Label exceptionLabel;

    @FXML private AnchorPane diceBackground;


    /**
     * This method makes the start-info-message disappear at the very first diceroll.
     * In all other cases the only thing this method does, is to perform the roll-dice method in the gameEngine.
     */
    @FXML
    public void rollDice() throws IOException {

        if (!startMessageHidden) {
            startMessage.setVisible(false);
            startMessageHidden = true;
        }

        gameEngine.rollDice();
        blackDice();
    }

    /**
     * This method takes you from the gameFaceScreen to the startScreen.
     * It is run if you click the "main menu" - button.
     */
    @FXML
    private void goToStartScreen() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/startScreen.fxml"));
        scene = new Scene(loader.load());
        Stage stage = (Stage) gameGrid.getScene().getWindow();
        stage.setScene(scene);
   
        // Workaround to get the vBox in the startScreen to update its position.
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();
        stage.setWidth(currentWidth+0.001);
        stage.setHeight(currentHeight+0.001); 
    }   

        
    

    /**
     * This method is run after a player has rolled his die. The method updates the die-image
     * in the gameFaceScene, such that the image corresponds to the die-roll which the gameEngine produced.
     * 
     * @param latestDice The integer value of the die-roll which was just performed.
     * @throws IllegalArgumentException If the die-roll is not an integer between 1 and 6 inclusive.
     */
    @Override
    public void updateImageOfDice(int latestDice) {
        if (latestDice < 1 || latestDice > 6)
            throw new IllegalArgumentException("Dice must be between 1-6");

        Image sourceimage1 = new Image("file:src/main/resources/dicesImages/dice" + Integer.toString(latestDice) + ".png");
        diceView.setImage(sourceimage1);
    }

    /**
     * This method sets the background color of the dice to grey.
     */
    public void blackDice() {
        diceBackground.setStyle("-fx-background-color: #555555");
    }

    /**
     * This method sets the bakground color of the die to be the color of the house of the current player.
     */
    public void colorDice() {  
        int currentHouseNumber = gameEngine.getCurrentPlayer().getHouseNumber();
        switch (currentHouseNumber) {
            case 1:
                diceBackground.setStyle("-fx-background-color: #00ff00");
                break;
            case 2:
                diceBackground.setStyle("-fx-background-color: #ffd700");
                break;
            case 3:
                diceBackground.setStyle("-fx-background-color: #4968bc");
                break;
            case 4:
                diceBackground.setStyle("-fx-background-color: #EE4B2B");
                break;
        }
    }

    /**
     * This method is used to update the text to the left of the die.
     * In the current implementation of the game, there are three possible arguments that may be fired:
     * " must move!" -----> {playerName} must move!   |
     * " can't move!" -----> {playerName} can't move!  |
     * " got three 6's!" ----> {playerName} got three 6's!   |
     * The method also changes the color of the text and the background color of the die, to match the color of the current player. 
     * 
     * @param text The text which should be added after {playerName}
     */
    @Override
    public void updatePlayerText(String text) {   
        String playerName = gameEngine.getCurrentPlayer().getUsername();
        playerTurn.setText(playerName + text);
    
        int currentHouseNumber = gameEngine.getCurrentPlayer().getHouseNumber();
        switch (currentHouseNumber) {
            case 1:
                playerTurn.setFill(Color.valueOf("#00ff00"));
                break;
            case 2:
                playerTurn.setFill(Color.valueOf("#ffd700"));
                break;
            case 3:
                playerTurn.setFill(Color.valueOf("#4968bc"));
                break;
            case 4:
                playerTurn.setFill(Color.valueOf("#EE4B2B"));
                break;
        }
    }
    
    /**
     * This method changes the in-game-text and color to show which player's turn it is.
     */
    public void updatePlayerThrowText() {
        
        String playerName = gameEngine.getCurrentPlayer().getUsername();
        int currentHouseNumber = gameEngine.getCurrentPlayer().getHouseNumber();
        
        String text;
        Color color = new Color(0, 0, 0, 1);
    
        if (playerName.charAt(playerName.length() - 1) == 's' || playerName.charAt(playerName.length() - 1) == 'x' || playerName.charAt(playerName.length() - 1) == 'z') {
            text = playerName + "' throw";
        } 
        
        else {
            text = playerName + "'s throw";
        }
    
        switch (currentHouseNumber) {
            case 1:
                color = Color.valueOf("#00ff00");
                break;
            case 2:
                color = Color.valueOf("#ffd700");
                break;
            case 3:
                color = Color.valueOf("#4968bc");
                break;
            case 4:
                color = Color.valueOf("#EE4B2B");
                break;
        }
        
        playerTurn.setText(text);
        playerTurn.setFill(color);
    }

    /** 
     * This method is used by CreateGameController to initialize the game scene after clicking submit.
     * Specifically, the gameEngine gets initialized with a set of players and some settings.
     * At this point, custom settings have not yet been implemented, so default settings are applied.
     * 
     * The method creates the player objects, which represents the players who are playing the game.
     * It also updates the textBoxes in the gameFaceScene, such that they display the names of the players.
     * After that, it creates a gameEngine with the specified settings, and the player-objects.
     * 
     * At the end, the method makes the gameFaceController observe the gameEngine.
     * This serves the function of indicating to the gameFaceController when to update the visuals.
     * 
     * @param gameNameInfo Information about the number of human players, and the names of the players + the name of the game.
     */
    public void gameSetup(GameNameInfo gameNameInfo) throws IOException {
        
        this.gameNameInfo = gameNameInfo;
        List<String> gameNameInfoAsList = gameNameInfo.getGameNameInfoAsList();
        int numPlayers = gameNameInfo.getNumPlayers();

        //Setting the texts in the textboxes to be equal to the playerNames.
        Text[] texts = {gameName, player1Name, player2Name, player3Name, player4Name};
        IntStream.range(0, gameNameInfoAsList.size()).forEach(index -> texts[index].setText(texts[index].getText() + gameNameInfoAsList.get(index)));
        
        //At this time, settings doesn't actually do anything, but it is something that can be worked on in the future.
        Settings settings = new Settings();
        
        //Creating the player objects, and making a gameEngine with the players.
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(player1Name.getText(), 1, gameGrid));
        
        if (numPlayers == 1) {
            players.add(new RobotPlayer(player2Name.getText(), 2, gameGrid));
            players.add(new RobotPlayer(player3Name.getText(), 3, gameGrid));
            players.add(new RobotPlayer(player4Name.getText(), 4, gameGrid));
        }

        else {
            players.add(numPlayers == 2 ? new RobotPlayer(player2Name.getText(), 2, gameGrid) : new Player(player2Name.getText(), 2, gameGrid));
            players.add(new Player(player3Name.getText(), 3, gameGrid));
            players.add(numPlayers == 4 ? new Player(player4Name.getText(), 4, gameGrid) : new RobotPlayer(player4Name.getText(), 4, gameGrid));
        }

        gameEngine = new GameEngine(settings, players, players.get(0), 1, 0, false, gameNameInfo);
        gameEngine.addListener(this);
        listeners.add(gameEngine);
        fileSaver = new SaveAndReadToFile();
        updateImageOfDice(1);
        colorDice();
    }
    
    /**
     * This method is used when you load the saved game from the startScreen.
     * It displays the saved game in the gameFace-screen.
     * The method does exactly the same thing as gameSetup, the only difference 
     * is that this method loads a saved game, instead of loading a newly created game. 
     * 
     * @param gameEngine The gameEngine which contains all the information that is needed to load the game.  
     */
    public void loadGameSetup(GameEngine gameEngine) {
        
        this.gameEngine = gameEngine;
        this.gameNameInfo = gameEngine.getGameNameInfo();
        List<String> gameNameInfoAsList = gameNameInfo.getGameNameInfoAsList();

        //Setting the texts in the textboxes to be equal to the playerNames.
        Text[] texts = {gameName, player1Name, player2Name, player3Name, player4Name};
        IntStream.range(0, gameNameInfoAsList.size()).forEach(index -> texts[index].setText(texts[index].getText() + gameNameInfoAsList.get(index)));
    
        
        gameEngine.addListener(this);
        listeners.add(gameEngine);
        fileSaver = new SaveAndReadToFile(); 
        gameEngine.getPieces().forEach(piece -> piece.addPieceToGrid(gameGrid));

        if (gameEngine.getCanMakeMove()) {updateImageOfDice(gameEngine.getDice()); updatePlayerText(" must move!"); blackDice();}
        else {colorDice(); updatePlayerThrowText();}
        startMessage.setVisible(false);
        updateImageOfDice(gameEngine.getDice()); 
        
    }

    /**
     * This method loads the saved ludogame with the help of the filesaver.
     * Before the saved game is loaded, the gameFace cleans all info from the game you were currently playing.
     * The cleaning process includes removing all the pieces, resetting player names and so on (see cleanGameFace).
     * If you try to load a saved game while a robotPlayer is playing,
     * a popup is displayed saying that you must wait until it is a human player's turn to load the saved game.
     * @throws IOException If the saveFile is not found.
     */
    @FXML
    public void loadGameInGameFace() throws IOException{
        
        if (gameEngine.getCurrentPlayer() instanceof RobotPlayer) {
            firePopupDisplayed();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Illegal Action");
            alert.setHeaderText("You tried to load the saved game during a robotPlayer's turn!");
            alert.setContentText("Loading the saved game is not allowed during a robotPlayer's turn. Please wait until a human player is at turn to load the saved the game.");
            
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/onc/LudoIcon.jpg")));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alertStage.close();
                firePopupClosed();
            }
            return;
        }

        cleanGameFace();
        loadGameSetup(fileSaver.loadLudoGame());
    }

    /**
     * This method basically resets the gameFace, and the method is used in the loadGameInGameFace() method.
     * The method resets alle the player names, it removes all the pieces from the screen,
     * and it removes all gameEngines listening to gameFaceController.
     */
    private void cleanGameFace() {

        Text[] texts = {gameName, player1Name, player2Name, player3Name, player4Name};
        IntStream.range(0, gameNameInfo.getGameNameInfoAsList().size()).forEach(index -> texts[index].setText(""));
        gameEngine.getPieces().stream().forEach(piece -> gameGrid.getChildren().remove(piece.getCircle()));
        listeners.clear();
    }

    /**
     * This method is run when the current player changes, and also when a player has made a move, and must make another move (because he rolled a 6).
     * This method updates the text shown in the gameFace to the following: "{playerName}'s throw!"
     * The method also changes the color of the text to match the color of the current player. 
     * Updating bacground color of dice to current player
     */
    @Override
    public void currentPlayerChanged() {
        updatePlayerThrowText();
        colorDice();
    }

    /**
     * This method is run when the gameEngine announces that a player has won the game.
     * 
     * This method ensures that a pop-up window appears when a player has won the game.
     * Clicking the ok-button which appears will take you to the home screen.
     * 
     * @param winnerName The name of the player who won the game.
     */
    @Override 
    public void playerWon(String winnerName) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(winnerName + " has won the game!");
        alert.setContentText("Click OK to return to the home screen.");
        
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/onc/LudoIcon.jpg")));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                goToStartScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    

    /**
     * This method is used by the robotPlayers. It makes it possible for the robots to roll the dice without actually clicking it.
     */
    @Override
    public void robotRolledDice() {
        
        try {
            rollDice();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is run after a player has moved a piece, and also in some other cases.
     * The method makes the bakcground color of the die match the color of the current player's housenumber, indicating that the die needs to be rolled to continue the game.
     */
    @Override
    public void playerMadeMove() {
        colorDice();
    }
    
    /**
     * When this method is run, you will (be able/ not be able) to click the dice, depending on the argument arg.
     * @param arg If true, a human will player will be able to click the die. If false, then a human player will not be able to click the die.
     */
    @Override
    public void diceClickable(boolean arg) {
        diceView.setDisable(!arg);
    }

    /**
     * This method saves the current game state to the ludoSave.txt - file
     */
    @FXML
    private void saveLudoGame() throws IOException {
        
        if (gameEngine.getCurrentPlayer() instanceof RobotPlayer) {
            
            firePopupDisplayed();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Illegal Action");
            alert.setHeaderText("You tried to save the game during a robotPlayer's turn!");
            alert.setContentText("Saving is not allowed during a robotPlayer's turn. Please wait until a human player is at turn to save the game.");
            
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/onc/LudoIcon.jpg")));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alertStage.close();
                firePopupClosed();
            } 
            return;
        }

        fileSaver.saveLudoGame(gameEngine, gameNameInfo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("You successfully saved the game!");
        alert.setContentText("Click OK to return to the ludoBoard.");

        DialogPane popup = alert.getDialogPane();
        popup.setContent(null);

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/onc/LudoIcon.jpg")));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            alertStage.close();
        } 

    }

    /**
     * This method is used by the gameFaceController when creating/loading a game,
     * to make the gameEngine listen to changes in the gameFaceController.
     * The reason that the gameEngine must listen to the controller, is that the gameEngine
     * must know when there is a popup displayed in the gameFace.
     * When a popup is displayed, all robotPlayers must pause their actions, and not continue 
     * until the popup is gone. 
     * @param listener
     */
    public void addListener(InterfacePopupListener listener) {
        
        if (listeners.contains(listener)) {
            throw new IllegalArgumentException("This object already listens to GameFaceController!");
        }

        listeners.add(listener);
        
    }

   /**
    * This method is used by the gameFaceController when a popup is displayed.
    * It sends a signal to the gameEngine, and the gameEngine responds by pausing all robotPlayer actions.
    */ 
    private void firePopupDisplayed() {
        listeners.stream().forEach(InterfacePopupListener::popupDisplayed);
    }

    /**
     * This method is used by the gameFaceController when a popup is closed.
     * It sends a signal to the gameEngine, and the gameEngine responds by continuing all robotPlayer actions.
     */
    private void firePopupClosed() {
        listeners.stream().forEach(InterfacePopupListener::popupClosed);
    }

    /**
     * This method is used only for testing purposes, to acces gameEngine
     */
    public GameEngine getGameEngine(){
        return this.gameEngine;
    }
    
}
