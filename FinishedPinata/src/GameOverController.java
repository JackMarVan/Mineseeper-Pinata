import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameOverController {
    //Returns the player back to the home page of the game after they lose
	public void retryScreenButtonPushed(ActionEvent event) throws IOException {
		Parent retryGame = FXMLLoader.load(getClass().getResource("PinataSweeper.fxml"));
		Scene retryGameScene = new Scene(retryGame);
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(retryGameScene);
    	window.show(); 
    }
	
	//Completely exits the program
	public void exitScreenButtonPushed(ActionEvent event) throws IOException {
		Parent retryGame = FXMLLoader.load(getClass().getResource("PinataSweeper.fxml"));
		Scene retryGameScene = new Scene(retryGame);
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(retryGameScene);
    	window.close();
	}
}
