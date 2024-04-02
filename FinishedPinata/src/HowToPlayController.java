import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class HowToPlayController {
	//Returns back to the Main screen to play the game
	public void returnScreenButtonPushed(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PinataSweeper.fxml"));
		Scene scene = new Scene(root);
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(scene);
    	window.show();
    }
}
	
	


