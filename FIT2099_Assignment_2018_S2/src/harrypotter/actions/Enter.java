package harrypotter.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActionInterface;
import harrypotter.HPActor;
import harrypotter.HPAffordance;
import harrypotter.HPEntityInterface;
import harrypotter.HPLocation;
import harrypotter.HPWorld;
import harrypotter.Tunnel;
import harrypotter.entities.actors.Player;
/**
 * Enter affordance class
 *  <code>HPAffordance</code> that lets <code>HPActor</code>s enter a different grid.
 *  
 *  @author Shakeel
 */
public class Enter extends HPAffordance implements HPActionInterface {
	public HPLocation tl;
	public HPLocation wl;
/**
 * Constructor for <code>Enter</code> class 
 * will set the right grid depending on the <code>Player</code> location
 * @param tl 
 * 		 tunnel location
 * @param wl
 * 		 world location
 * @param theTarget
 * 		the door the enter applies on
 * @param m
 * 		Message Renderer
 */
	public Enter(HPLocation tl, HPLocation wl,HPEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		this.tl =tl;
		this.wl = wl;
		this.priority = 2;
		// TODO Auto-generated constructor stub
	}
	/**
	 * method checks if the actor can do the enter action
	 * @return boolean to check
	 */
	@Override
	public boolean canDo(HPActor a) {
		// TODO Auto-generated method stub
		return !a.isDead();
	}
	
	 /** Perform the <code>Enter</code> action.
	 * <p>
	 * If it is possible for <code>HPActor a</code> to enter the door, sap the Grids, reset <code>a</code>'s move commands 
	 *  for a particular grid into a possible new set of available <code>Moves</code>. 
	 * 
	 * This method will only be called if the <code>HPActor a</code> is alive and uses the <code>Door</door>
	 * 
	 * 
	 * @param a the HPActor who is entering
	 */
	@Override
	public void act(HPActor a) {
		Player player = (Player)a;	
		// TODO Auto-generated method stub
		if (Player.isPlayerInTunnel()){ //if Player is in the tunnel 
			Tunnel.getTunnelEntitymanager().remove(player); //remove player form the tunnel he is moving out 
			HPWorld.myEntitymanager().setLocation(player, wl); //set player in WORLD
			Player.setPlayerTunnel(false); //change flag
			player.setPlayerGrid();//set new grid
			player.resetMoveCommands(HPWorld.myEntitymanager().whereIs(player)); //reset possible moves
		} else {
			//if player is not in tunnel
			HPWorld.myEntitymanager().remove(player); //remove player from the world
			Tunnel.getTunnelEntitymanager().setLocation(player, tl); //set player in tunnel
			Player.setPlayerTunnel(true); //change flag
			player.setPlayerGrid();//swap grid
			player.resetMoveCommands(Tunnel.getTunnelEntitymanager().whereIs(player)); //reset possible moves
		}
	}
	/**
	 * getter for description
	 * @return String of the affordance description
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Enter " + this.target.getShortDescription();
	}

}
