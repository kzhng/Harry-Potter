package harrypotter.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActor;
import harrypotter.HPEntityInterface;
import harrypotter.HPLocation;
import harrypotter.HPWorld;
import harrypotter.Team;

/**
 * A very minimal <code>HPActor</code> that just describes the scene. It just stands still.
 */

public class TestActor extends HPActor {

	/**
	 * Constructor for the <code>TestActor</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>TestActor</code></li>
	 * 	<li>Initialize the world for this <code>TestActor</code></li>
	 *  <li>Set the <code>Team</code> for this <code>TestActor</code> as <code>Good</code></li>
	 * 	<li>Set the hit points for this <code>TestActor</code> as 50</li>
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>HPWorld</code> world to which this <code>TestActor</code> belongs to
	 */
	public TestActor(MessageRenderer m, HPWorld world) {
		super(Team.GOOD, 50, m, world);
	}
	
	@Override
	/**
	 * Stands still and waits for other actors to try its affordances. Introduced so that <code>Attack</code> could be tested
	 * <p>
	 * This method will only be called if this <code>Test Actor</code> is alive and is not waiting
	 * 
	 * @author dsquire
	 */
	public void act() {
		say(this.getShortDescription() + " is standing still at " + this.world.getEntityManager().whereIs(this).getShortDescription());
		describeScene();
	}
	
	/**
	 * This method will describe, 
	 * <ul>
	 * 	<li>the this <code>Player</code>'s location</li>
	 * 	<li>items carried (if this <code>Player</code> is carrying any)</li>
	 * 	<li>the contents of this <code>Player</code> location (what this <code>Player</code> can see) other than itself</li>
	 * <ul>
	 * <p>
	 * The output from this method would be through the <code>MessageRenderer</code>.
	 * 
	 *  @see {@link edu.monash.fit2099.simulator.userInterface.MessageRenderer}
	 */
	public void describeScene() {
		// get the location of the player and describe it
		HPLocation location = this.world.getEntityManager().whereIs(this);
		say(this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription());
		
		String entityDescription = new String();
		describeItems(this);
		
		// get the contents of the location
		List<HPEntityInterface> contents = this.world.getEntityManager().contents(location);

		// and describe the contents
		if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is
									// nothing to report
			say(this.getShortDescription() + " can see:");
			for (HPEntityInterface entity : contents) {
				if (entity != this) { // don't include self in scene description
					entityDescription = "\t " + entity.getSymbol() + " - " + entity.getLongDescription() + " ["
							+ entity.getHitpoints() + "]";
					if (entity instanceof HPActor) {
						HPActor actor = (HPActor) entity;
						describeItems(actor);
					}
					say(entityDescription);
				}
			}
		}

	}
	
	/**
	 * This method will describe items carried (if this <code>Player</code> is carrying any)
	 * The output from this method would be through the
	 * <code>MessageRenderer</code>.
	 * 
	 * @see {@link edu.monash.fit2099.simulator.userInterface.MessageRenderer}
	 */
	private void describeItems(HPActor a) {
		String entityDescription = new String();
		if(a.Inventory.containsItems()) {		
			ArrayList<HPEntityInterface> items = a.Inventory.getItemsCarried();
			// get the items carried by the player
			for (int i = 0; i < items.size(); i++) {
				if(i!=0 && i == items.size()-1) {
					entityDescription += "and ";
				}
				entityDescription += items.get(i).getShortDescription() + " ["
						+ items.get(i).getHitpoints() + "] ";
			} 
			// and describe the items carried if the player is actually carrying an item
			say(a.getShortDescription() + " is holding " + entityDescription);
		}
	}
	
	public boolean isTeacher() {
		return false;
	}
}
