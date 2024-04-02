

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sun.prism.paint.Color;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PinataController {
	
	//the UI representation of mines remaining according to flags placed
	@FXML
	Text minesLeft; 
	
	@FXML
    private Label timeLabel;
	
	@FXML
    GridPane grid;	
	
	Image tile = new Image(getClass().getResourceAsStream("images/greeentile.png"));
	Image mine = new Image(getClass().getResourceAsStream("images/goose.png"));
	Image green = new Image(getClass().getResourceAsStream("images/green.png"));
	Image one = new Image(getClass().getResourceAsStream("images/one.png"));
	Image two = new Image(getClass().getResourceAsStream("images/two.png"));
	Image three = new Image(getClass().getResourceAsStream("images/three.png"));
	Image four = new Image(getClass().getResourceAsStream("images/four.png"));
	Image five = new Image(getClass().getResourceAsStream("images/five.png"));
	Image six = new Image(getClass().getResourceAsStream("images/six.png"));
	Image seven = new Image(getClass().getResourceAsStream("images/seven.png"));
	Image eight = new Image(getClass().getResourceAsStream("images/eight.png"));
	Image onePNG = new Image(getClass().getResourceAsStream("images/onePNG.png")); 
	Image twoPNG = new Image(getClass().getResourceAsStream("images/twoPNG.png"));
	Image threePNG = new Image(getClass().getResourceAsStream("images/threePNG.png"));
	Image fourPNG = new Image(getClass().getResourceAsStream("images/fourPNG.png"));
	Image fivePNG = new Image(getClass().getResourceAsStream("images/fivePNG.png"));
	Image sixPNG = new Image(getClass().getResourceAsStream("images/sixPNG.png"));
	Image sevenPNG = new Image(getClass().getResourceAsStream("images/sevenPNG.png"));
	Image eightPNG = new Image(getClass().getResourceAsStream("images/eightPNG.png"));
	Image empty = new Image(getClass().getResourceAsStream("images/clicked-tile.png"));
	Image flag = new Image(getClass().getResourceAsStream("images/flag-tile.png"));
	Image pinata = new Image(getClass().getResourceAsStream("images/Pinata.png"));
	
	private static int x_size = 10;
	private static int y_size = 10;
	private static int mine_num = 15;
	private static int pinata_num = 2;
	private static int mines_left = 15;
	private static boolean generated = false;
	private int time = 0;
	private Timeline timeline = new Timeline();
	
	ArrayList<Block> blocks = new ArrayList<Block>(0);
	ArrayList<String> mineList = new ArrayList<String>(0);
	ArrayList<String> pinataList = new ArrayList<String>(0);
    
	//Changes the main screen to the howToPlay Screen 
    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
    	restart(); // restart is called here to avoid confusion when the scene swaps back
    	Parent howToPlayParent = FXMLLoader.load(getClass().getResource("howToPlay.fxml"));
    	Scene howToPlayScene = new Scene(howToPlayParent);	
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(howToPlayScene);
    	window.show();
    }

	// called once at the start
    public void initialize() throws IOException {
    	// Create click actions  	
    	// Removes the green block and set the images to what the block has been assigned
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
	        @Override 
	        public void handle(MouseEvent e) {
	    	   if (e.getSource() instanceof Block) {
		    	   Block block = (Block) e.getSource(); // retrieves the target and casts it to a block
		    	   if (e.getSource() == block) {
		    		   if (e.getButton().toString() == "PRIMARY") {
		    			   reveal(block, false); // reveals the block in a manner that can trigger a mine
		    		   }
		    		   else if (e.getButton().toString() == "SECONDARY") {
		    			   if (generated) {
		    				   // If the board is made already, a flag is placed on right click or taken away.
		    				   // This also decrements/increments the mine counter UI element
		    				   block.flag();
		    				   if (block.getImage() == tile) {
		    					   block.setImage(flag);
		    					   mines_left--;
		    					   minesLeft.setText(String.valueOf(mines_left));
		    				   }
		    				   else if (block.getImage() == flag) {
		    					   block.setImage(tile);
		    					   mines_left++;
		    					   minesLeft.setText(String.valueOf(mines_left));
		    				   }
		    			   }
		    		   }   
		    	   }
	    	   }
	        }
	    }; 
	   // Create grid blocks
       for (int i = 0; i < x_size; i++) {
    	   for (int j = 0; j < y_size; j++) {
    		   // Blocks are created with default values then introduced to a stack
    		   // so each block has two layers, the second only being visible if the
    		   // block element is currently an image with transparency. This means
    		   // blocks that surround a pinata can be identified. Then, the stack
    		   // is added to its respective spot on the grid. This also fills the
    		   // blocks array list.
    		   StackPane stack = new StackPane();
    		   Block block = new Block(i, j, "empty");
    		   ImageView greenImage = new ImageView();
    		   block.setImage(tile);
    		   greenImage.setImage(green);
    		   block.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    		   block.setFitHeight(grid.getPrefHeight() / y_size);
    		   block.setFitWidth(grid.getPrefWidth() / x_size);
    		   greenImage.setFitHeight(grid.getPrefHeight() / y_size);
    		   greenImage.setFitWidth(grid.getPrefWidth() / x_size);
    		   blocks.add(block);
    		   stack.getChildren().addAll(greenImage, block);
    		   grid.setRowIndex(stack, i);
    		   grid.setColumnIndex(stack, j);
    		   grid.getChildren().add(stack);
    	   }
       }
    }
    
    public void startTime() {
    	time = 0;
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {    
        	@Override
            public void handle(ActionEvent event) {	
         		time++;	
         		timeLabel.setText(String.format("Time: %d", time));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
      
    // Reveals the passed block on the grid. The cascading boolean determines
    // both whether mines can be revealed this way and whether empty spaces
    // cascade.
    public void reveal(Block block, boolean cascading) {
        // Checks if the blocks can even be clicked based on if a flag is present or if it is already revealed 
    	if (block.getFlagged() == false && block.getRevealed() == false) {
    		block.reveal();
    		// The grid is generated if it hasn't been already
        	if (generated == false) {
        		// START TIMER HERE
        		startTime();
        		generated = true;
        		generate(block);
        	}
        	if (block.getBlockType() == "empty") {
        		block.setImage(empty);
        		pulse(block); // empty blocks cascade when clicked
        	}
        	else if (block.getBlockType() == "pinata") {
        		// When a pinata is revealed, it reveals itself as well as
        		// another random block, preferring empty blocks. This only
        		// happens if the block that it finds is not revealed or
        		// flagged.
        		if (!cascading) {
        			Random r = new Random();
            		block.setImage(pinata);
            		for (int i = 0; i <= 60; i++) {
            			int x = r.nextInt(10);
            			int y = r.nextInt(10);
            			if (getBlock(x, y).getBlockType() == "empty"
            					&& getBlock(x, y).getRevealed() == false
            					&& getBlock(x, y).getFlagged() == false) {
            				reveal(getBlock(x, y), true);
            				break;
            			}
            			if (i == 60) {
            				for (int j = 0; j <= 100; j++) {
//            					System.out.println("Tried to find a spot");
            					int a = r.nextInt(10);
                    			int b = r.nextInt(10);
            					if (getBlock(a, b).getProximity() > 0
            							&& getBlock(a, b).getRevealed() == false
            							&& getBlock(a, b).getFlagged() == false) {
//            						System.out.println("Found a spot");
                    				reveal(getBlock(a, b), true);
                    				break;
                    			}
            				}
            			}
                	}
        		}
        	}
        	else if (block.getBlockType() == "mine") {
        		// When a mine is revealed, the game is over, the board restarts,
        		// and the scene is switched to the game over fxml.
        		block.setImage(mine);
        		
        		if (!cascading) {
        	        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {    
        	        	@Override
        	            public void handle(ActionEvent event) {	
        	        		try {
    						restart();
    						Parent gameOverParent;
    					    gameOverParent = FXMLLoader.load(getClass().getResource("gameOver.fxml"));
    					    Scene gameOverScene = new Scene(gameOverParent);
    		                Stage window = (Stage) block.getScene().getWindow();
    		   	            window.setScene(gameOverScene);
    	                    window.show();	
    					} catch (IOException e1) {
    						e1.printStackTrace();
    					}

        	            }
        	        }));					
        		}
        	}
        	else if (block.getBlockType() == "one") {
        		block.setImage(one);
        	}
        	else if (block.getBlockType() == "two") {
        		block.setImage(two);
        	}
        	else if (block.getBlockType() == "three") {
        		block.setImage(three);
        	}
        	else if (block.getBlockType() == "four") {
        		block.setImage(four);
        	}
        	else if (block.getBlockType() == "five") {
        		block.setImage(five);
        	}
        	else if (block.getBlockType() == "six") {
        		block.setImage(six);
        	}
        	else if (block.getBlockType() == "seven") {
        		block.setImage(seven);
        	}
        	else if (block.getBlockType() == "eight") {
        		block.setImage(eight);
        	}
        	else if (block.getBlockType() == "onePNG") {
        		block.setImage(onePNG);
        	}
        	else if (block.getBlockType() == "twoPNG") {
        		block.setImage(twoPNG);
        	}
        	else if (block.getBlockType() == "threePNG") {
        		block.setImage(threePNG);
        	}
        	else if (block.getBlockType() == "fourPNG") {
        		block.setImage(fourPNG);
        	}
        	else if (block.getBlockType() == "fivePNG") {
        		block.setImage(fivePNG);
        	}
        	else if (block.getBlockType() == "sixPNG") {
        		block.setImage(sixPNG);
        	}
        	else if (block.getBlockType() == "sevenPNG") {
        		block.setImage(sevenPNG);
        	}
        	else if (block.getBlockType() == "eightPNG") {
        		block.setImage(eightPNG);
        	}
        	//Calls win method to check win
        	checkWin();
    	}
    }
    
    // Uses a counter to determine if the number of revealed non-mine/pinata
    // blocks on the grid is equal to the amount of blocks on the grid minus
    // the amount of mines and pinatas. If it is, the game board restarts and
    // the scene is switched to the play again fxml.
    public void checkWin() {
    	int count = 0;
    	for (int i = 0; i < blocks.size(); i++) {
    		Block block = blocks.get(i);
    		if (block.getBlockType() != "mine" && block.getRevealed() == true
    				&& block.getBlockType() != "pinata") {
    			count++;
    		}
    	}
    	if (count == blocks.size() - mine_num - pinata_num) {

    		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {    
            	@Override
            	
                public void handle(ActionEvent event) {	
            		try {
    			// RECORD TIMER HERE
            			Path path = Paths.get("C:\\temp\\myBestTime.txt");
            			if (Files.exists(path) && !Files.isDirectory(path)) {
            				BufferedReader br = new BufferedReader(new FileReader("C:\\temp\\myBestTime.txt"));
            			    String bestTimeText = br.readLine();
            			    Integer bestTime = Integer.valueOf(bestTimeText);           			
            			    if (bestTime > time) {
            			    	BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\temp\\myBestTime.txt"));
            			    	bw.write(String.format("%d", time));
        			            bw.close();           				
            			    }  
            			} else {
            				BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\temp\\myBestTime.txt"));
                            bw.write(String.format("%d", time));
    			            bw.close();	
            			}
            			
            			             		        
                        restart();
				        Parent youWinParent;
		         		youWinParent = FXMLLoader.load(getClass().getResource("PlayAgain.fxml"));
			        	Scene youWinScene = new Scene(youWinParent);	
	         			Stage window = (Stage) blocks.get(0).getScene().getWindow();
             	        window.setScene(youWinScene);
                        window.show();
			        } catch (IOException e1) {
				        e1.printStackTrace();
		        	}
                }
            }));
    		
    	}
    }
    
    // Creates any mine, pinata, and detection blocks on the board, using the passed
    // block value as an exception, so a mine isn't placed on the first block clicked.
    public void generate(Block block) {
    	// The block that was clicked is added to the mine list so a mine can't be randomly
    	// placed there.
    	minesLeft.setText(String.valueOf(mines_left));
		mineList.add(String.valueOf(block.getXCoord()) + String.valueOf(block.getYCoord()));
		
		
		// Mine positions are created randomly, one for each counted in mine_num. The system
		// fetches random numbers representing coordinates and tries to create new mines in a
		// while loop based on those until eventually successful. Each mine is added to the list
		// as it goes to avoid putting them in the same place.
		Random r = new Random();
    	for (int i = 1; i <= mine_num; i++) {
    		boolean accepted = false;
    		while (!accepted) {
    			int x = r.nextInt(10);
    			int y = r.nextInt(10);
    			String spot = String.valueOf(x) + String.valueOf(y);
    			for (int j = 0; j < mineList.size(); j++) {
    				if (mineList.get(j).equals(spot)) {
    					break;
    				}
    				else if (j == mineList.size() - 1) {
    					mineList.add(spot);
    					getBlock(x, y).setBlockType("mine");
    					accepted = true;
    					break;
    				}
    			}
    		}
    	}
    	
    	// The pinatas are added in a way that is almost identical to mines, considering mines
    	// and themselves in their placement.
    	pinataList.add(String.valueOf(block.getXCoord()) + String.valueOf(block.getYCoord()));
    	for (int i = 1; i <= pinata_num; i++) {
    		boolean accepted = false;
    		while (!accepted) {
    			int x = r.nextInt(10);
    			int y = r.nextInt(10);
    			String spot = String.valueOf(x) + String.valueOf(y);
    			for (int j = 0; j < pinataList.size(); j++) {
    				if (pinataList.get(j).equals(spot) || block.getBlockType() == "mine") {
    					break;
    				}
    				else if (j == pinataList.size() - 1) {
    					pinataList.add(spot);
    					getBlock(x, y).setBlockType("pinata");
    					accepted = true;
    					break;
    				}
    			}
    		}
    	}
    	
    	// Once mines and pinatas are added, the block list is iterated through to assign proximity
    	// types to any block that is near mines or pinatas in a keypad grid, ignoring any block that 
    	//	is already a mine or pinata.
    	for (int i = 0; i < blocks.size(); i++) {
    		if (blocks.get(i).getBlockType().equals("mine") || blocks.get(i).getBlockType().equals("pinata")) {
    			continue;
    		}
    		else {
    			checkProximity(blocks.get(i));
    		}
    	}
    }
    
    // This method provides a way to access a block based on two integers which
    // represent its coordinates.
    public Block getBlock(int x, int y) {
    	for (int i = 0; i < blocks.size(); i++) {
    		if (blocks.get(i).getXCoord() == x) {
    			if (blocks.get(i).getYCoord() == y) {
    				return blocks.get(i);
    			}
    		}
    	}
    	// If a block is not found, a block that is effectively null is returned.
    	return new Block(13, 13, "tile");
    }
    
    // Checks the surrounding blocks to see if their types are a mine or pinata. If one
    // pinata is found, it uses an image with transparency to indicate pinata adjacency.
    public void checkProximity(Block block) {
    	int mineCount = 0;
    	boolean pinataPresent = false;
    	// Check for normal mines
    	if (getBlock(block.getXCoord() - 1, block.getYCoord()).getBlockType().equals("mine")) {
    		mineCount++;
    	}
    	if (getBlock(block.getXCoord() - 1, block.getYCoord() - 1).getBlockType().equals("mine")) {
    		mineCount++;
    	}
    	if (getBlock(block.getXCoord() - 1, block.getYCoord() + 1).getBlockType().equals("mine")) {
    		mineCount++;
    	}
    	if (getBlock(block.getXCoord(), block.getYCoord() - 1).getBlockType().equals("mine")) {
    		mineCount++;
    	}
    	if (getBlock(block.getXCoord(), block.getYCoord() + 1).getBlockType().equals("mine")) {
    		mineCount++;
    	}
    	if (getBlock(block.getXCoord() + 1, block.getYCoord()).getBlockType().equals("mine")) {
    		mineCount++;
    	}
    	if (getBlock(block.getXCoord() + 1, block.getYCoord() - 1).getBlockType().equals("mine")) {
    		mineCount++;
    	}
    	if (getBlock(block.getXCoord() + 1, block.getYCoord() + 1).getBlockType().equals("mine")) {
    		mineCount++;
    	}
    	// Check for pinatas
    	if (getBlock(block.getXCoord() - 1, block.getYCoord()).getBlockType().equals("pinata")) {
    		mineCount++;
    		pinataPresent = true;
    	}
    	if (getBlock(block.getXCoord() - 1, block.getYCoord() - 1).getBlockType().equals("pinata")) {
    		mineCount++;
    		pinataPresent = true;
    	}
    	if (getBlock(block.getXCoord() - 1, block.getYCoord() + 1).getBlockType().equals("pinata")) {
    		mineCount++;
    		pinataPresent = true;
    	}
    	if (getBlock(block.getXCoord(), block.getYCoord() - 1).getBlockType().equals("pinata")) {
    		mineCount++;
    		pinataPresent = true;
    	}
    	if (getBlock(block.getXCoord(), block.getYCoord() + 1).getBlockType().equals("pinata")) {
    		mineCount++;
    		pinataPresent = true;
    	}
    	if (getBlock(block.getXCoord() + 1, block.getYCoord()).getBlockType().equals("pinata")) {
    		mineCount++;
    		pinataPresent = true;
    	}
    	if (getBlock(block.getXCoord() + 1, block.getYCoord() - 1).getBlockType().equals("pinata")) {
    		mineCount++;
    		pinataPresent = true;
    	}
    	if (getBlock(block.getXCoord() + 1, block.getYCoord() + 1).getBlockType().equals("pinata")) {
    		mineCount++;
    		pinataPresent = true;
    	}
    	// Apply proximity depending on whether there is a pinata around
    	if (pinataPresent) {
    		block.setProximityPNG(mineCount);
    	}
    	else {
        	block.setProximity(mineCount);
    	}
    }
    
    // All values and lists are set to their defaults
    public void restart() {
    	// STOP TIMER HERE   	
    	time = 0;
    	timeLabel.setText(String.format("Time: %d", time)); 
    	timeline.stop();
    	timeline = new Timeline();
    	

    	generated = false;

    	mines_left = 15;
    	
    	mineList.clear();
    	pinataList.clear();
    	
    	for (int i = 0; i < blocks.size(); i++) {
    		blocks.get(i).setBlockType("empty");
    		blocks.get(i).setImage(tile);
    		blocks.get(i).setRevealed(false);
    		blocks.get(i).setFlagged(false);
    	}
    }
    
    public void restartScreenButtonPushed(ActionEvent event) throws IOException {
    	restart();
    }
    
    // This is a mechanism that allows indirect recursion. When an empty space is revealed,
    // this method is called and reveals surrounding blocks in keypad proximity, proceeding
    // until empty spaces are no longer revealed this way.
    public void pulse(Block block) {
    	reveal(getBlock(block.getXCoord() - 1, block.getYCoord()), true);
    	reveal(getBlock(block.getXCoord() - 1, block.getYCoord() - 1), true);
    	reveal(getBlock(block.getXCoord() - 1, block.getYCoord() + 1), true);
    	reveal(getBlock(block.getXCoord(), block.getYCoord() - 1), true);
    	reveal(getBlock(block.getXCoord(), block.getYCoord() + 1), true);
    	reveal(getBlock(block.getXCoord() + 1, block.getYCoord()), true);
    	reveal(getBlock(block.getXCoord() + 1, block.getYCoord() - 1), true);
    	reveal(getBlock(block.getXCoord() + 1, block.getYCoord() + 1), true);
    }
}