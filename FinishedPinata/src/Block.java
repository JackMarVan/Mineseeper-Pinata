
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Almost everything here was added just to help with testing or
// help me visualize the project. Please change things if they don't
// make sense.

public class Block extends ImageView {
	// declaring all instance variables
	private int xcoord;
	private int ycoord;
	private int proximity;
	boolean revealed;
	boolean flagged;
	private String blockType;

	public Block(int xcoord, int ycoord, String blockType) {
		// initializing variables
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		proximity = 0;
		this.blockType = blockType;
		revealed = false;
		flagged = false;
	}

	// Switches the t/f of this block's reveal state
	public void reveal() {
		if (!revealed) {
			revealed = true;
		}
		else {
			revealed = false;
		}
	}

	// Switches the t/f of this block's flagged state
	public void flag() {
		if (!flagged) {
			flagged = true;
		}
		else {
			flagged = false;
		}
	}
	
	public boolean getFlagged() {
		return flagged;
	}
	
	public boolean getRevealed() {
		return revealed;
	}

	public int getXCoord() {
		return xcoord;
	}

	public int getYCoord() {
		return ycoord;
	}
	
	public int getProximity() {
		return proximity;
	}

	// Gets the block type 
	public String getBlockType() {
		return blockType;
	}

	// Sets the block type
	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	
	// Sets the revealed state
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}
	
	// Sets the flagged state
	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
	
	// Sets the block type based on the passed amount of surrounding
	// blocks.
	public void setProximity(int prox) {
		if (prox == 0) {
			return;
		}
		else if (prox == 1) {
			setBlockType("one");
			proximity++;
		}
		else if (prox == 2) {
			setBlockType("two");
			proximity++;
		}
		else if (prox == 3) {
			setBlockType("three");
			proximity++;
		}
		else if (prox == 4) {
			setBlockType("four");
			proximity++;
		}
		else if (prox == 5) {
			setBlockType("five");
			proximity++;
		}
		else if (prox == 6) {
			setBlockType("six");
			proximity++;
		}
		else if (prox == 7) {
			setBlockType("seven");
			proximity++;
		}
		else if (prox == 8) {
			setBlockType("eight");
			proximity++;
		}
	}
	
	// Sets the proximity only for blocks that are around a pinata
	public void setProximityPNG(int prox) {
		if (prox == 0) {
			return;
		}
		else if (prox == 1) {
			setBlockType("onePNG");
			proximity++;
		}
		else if (prox == 2) {
			setBlockType("twoPNG");
			proximity++;
		}
		else if (prox == 3) {
			setBlockType("threePNG");
			proximity++;
		}
		else if (prox == 4) {
			setBlockType("fourPNG");
			proximity++;
		}
		else if (prox == 5) {
			setBlockType("fivePNG");
			proximity++;
		}
		else if (prox == 6) {
			setBlockType("sixPNG");
			proximity++;
		}
		else if (prox == 7) {
			setBlockType("sevenPNG");
			proximity++;
		}
		else if (prox == 8) {
			setBlockType("eightPNG");
			proximity++;
		}
	}
}