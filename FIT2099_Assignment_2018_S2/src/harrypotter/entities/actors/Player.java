package harrypotter.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPActionInterface;
import harrypotter.HPActor;
import harrypotter.HPEntityInterface;
import harrypotter.HPLocation;
import harrypotter.HPWorld;
import harrypotter.Inventory;
import harrypotter.Team;
import harrypotter.interfaces.HPGridController;

/**
 * A very minimal <code>HPActor</code> that the user can control. Its
 * <code>act()</code> method prompts the user to select a command.
 * 
 * @author ram
 */
/*
 * Change log 2017/02/22 Schedule actions in the act method instead of tick. A
 * controller used to get user input rather than the UI directly (Asel)
 */
public class Player extends HPActor {

	/**
	 * Constructor for the <code>Player</code> class. This constructor will,
	 * <ul>
	 * <li>Initialize the message renderer for the <code>Player</code></li>
	 * <li>Initialize the world for this <code>Player</code></li>
	 * <li>Initialize the <code>Team</code> for this <code>Player</code></li>
	 * <li>Initialize the hit points for this <code>Player</code></li>
	 * <li>Set this <code>Player</code> as a human controlled
	 * <code>HPActor</code></li>
	 * </ul>
	 * 
	 * @param team      the <code>Team</code> to which the this <code>Player</code>
	 *                  belongs to
	 * @param hitpoints the hit points of this <code>Player</code> to get started
	 *                  with
	 * @param m         <code>MessageRenderer</code> to display messages.
	 * @param world     the <code>HPWorld</code> world to which this
	 *                  <code>Player</code> belongs to
	 * 
	 */
	public Player(Team team, int hitpoints, MessageRenderer m, HPWorld world) {
		super(team, hitpoints, m, world);
		humanControlled = true; // this feels like a hack. Surely this should be dynamic
		this.capabilities.add(Capability.INVENTORY);
		this.InventorySize = (this.hasCapability(Capability.INVENTORY))? 3 : 1;		//inventory size is 3 for actors with INVENTORY capability
		this.Inventory = new Inventory(InventorySize);
	}

	/**
	 * This method will describe this <code>Player</code>'s scene and prompt for
	 * user input through the controller to schedule the command.
	 * <p>
	 * This method will only be called if this <code>Player</code> is alive and is
	 * not waiting.
	 * 
	 * @see {@link #describeScene()}
	 * @see {@link harrypotter.interfaces.HPGridController}
	 */
	@Override
	public void act() {
		describeScene();
		HPActionInterface userDecision = HPGridController.getUserDecision(this);
		boolean isActionValid = true;

		if (userDecision.isCastCommand()) {
			if (!this.getSpells().isEmpty()) {
				userDecision = HPGridController.getTargetSpell(userDecision, this);
			} else {
				this.say(this.getShortDescription() + " does not know any spells! No action done.");
				isActionValid = false;
			}
		}

		if (isActionValid) {
			scheduler.schedule(userDecision, this, 1);
		}
	}

	/**
	 * This method will describe,
	 * <ul>
	 * <li>the this <code>Player</code>'s location</li>
	 * <li>calls <code>describeItems()</code> items carried (if this <code>Player</code> is carrying any)</li>
	 * <li>the contents of this <code>Player</code> location (what this
	 * <code>Player</code> can see) other than itself</li>
	 * <ul>
	 * <p>
	 * The output from this method would be through the
	 * <code>MessageRenderer</code>.
	 * 
	 * @see {@link edu.monash.fit2099.simulator.userInterface.MessageRenderer}
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
		return true;
	}
}
