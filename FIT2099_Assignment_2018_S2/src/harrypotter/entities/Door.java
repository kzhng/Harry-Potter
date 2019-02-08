package harrypotter.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.*;
/**
 * The door
 * Class Door represents the instance of Entity, which stores the HPLocations of the tunnel and world it connects to
 *
 * @author Shakeel
 */

public class Door extends HPEntity {
	private HPLocation tunnLoc; //location of the tunnel
	private HPLocation worldLoc; //location of the world
	/**
	 * Constructor for Door
	 * @param tunnLoc - pass location of the tunnel;
	 * @param worldLoc - pass location of the world
	 * @param m - pass message
	 */
	public Door(HPLocation tunnLoc,HPLocation worldLoc,MessageRenderer m) {
		super(m);
		// TODO Auto-generated constructor stub
		this.tunnLoc = tunnLoc;
		this.worldLoc = worldLoc;
		this.shortDescription = "A door";
		this.longDescription = "new world awaits you!";
	}
		
	/**
	 *  getter of the door symbol
	 * @return String the symbol of the door
	 */
	@Override
	public String getSymbol() {
		return "U";
	}

}
