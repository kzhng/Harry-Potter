package harrypotter.actions;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPActor;
import harrypotter.HPAffordance;
import harrypotter.HPEntityInterface;

/**
 * <code>HPAction</code> that lets a <code>HPActor</code> drink an object.
 * 
 * @author Matti
 */

public class Drink extends HPAffordance {

	/**
	 * Constructor for the <code>Drink</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>HPEntity</code> that is being taken
	 * @param m the message renderer to display messages
	 */
	public Drink(HPEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}


	/**
	 * Returns if or not this <code>Drink</code> can be performed by the <code>HPActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not carrying any item already.
	 *  
	 * @param 	a the <code>HPActor</code> being queried
	 * @return 	true if the <code>HPActor</code> is can drink this item, false otherwise
	 * @see		{@link harrypotter.HPActor#getItemsCarried()}
	 */
	@Override
	public boolean canDo(HPActor a) {
		ArrayList<HPEntityInterface> InventoryItems = a.Inventory.getItemsWithCapability(Capability.HEALTH);
		if (target instanceof HPEntityInterface && InventoryItems != null) {
			
			for (HPEntityInterface item : InventoryItems) {
				if(target == item)
					return true;
			}
		}
		return false;
	}

	/**
	 * Perform the <code>Drink</code> action by setting the item carried by the <code>HPActor</code> to the target (
	 * the <code>HPActor a</code>'s item carried would be the target of this <code>Drink</code>).
	 * <p>
	 * This method should only be called if the <code>HPActor a</code> is alive.
	 * 
	 * @param 	a the <code>HPActor</code> that does the drinking
	 * @see 	{@link #theTarget}
	 * @see		{@link harrypotter.HPActor#isDead()}
	 */
	@Override
	public void act(HPActor a) {
		if (target instanceof HPEntityInterface) {
			HPEntityInterface theItem = (HPEntityInterface) target;
			a.addHitpoints(theItem.getHitpoints());
			a.Inventory.remove(theItem);
			
			//remove the drink affordance
			theItem.removeAffordance(this);
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @return String comprising "drink " and the short description of the target of this <code>Drink</code>
	 */
	@Override
	public String getDescription() {
		return "drink " + target.getShortDescription();
	}

}
