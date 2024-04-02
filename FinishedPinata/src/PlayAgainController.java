import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayAgainController {
    @FXML
	//Completely exits the program when clicked
    void exitScreenButtonPushed(ActionEvent event) throws IOException{
    	Parent replayGame = FXMLLoader.load(getClass().getResource("PinataSweeper.fxml"));
		Scene replayGameScene = new Scene(replayGame);
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(replayGameScene);
    	window.close();
    }
    
    @FXML
    //Lets the player go back to the main screen to play again
    void playAgainScreenButtonPushed(ActionEvent event) throws IOException{
    	Parent replayGame = FXMLLoader.load(getClass().getResource("PinataSweeper.fxml"));
		Scene replayGameScene = new Scene(replayGame);
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(replayGameScene);
    	window.show(); 
    }

}
