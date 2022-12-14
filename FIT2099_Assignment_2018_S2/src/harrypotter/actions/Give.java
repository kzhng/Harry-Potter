package harrypotter.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActionInterface;
import harrypotter.HPActor;
import harrypotter.HPAffordance;
import harrypotter.HPEntityInterface;
import harrypotter.interfaces.HPGridController;

/**
 * <code>HPAction</code> that lets a <code>HPActor</code> Give an object.
 * 
 * @author Willy Wonka (Mati)
 */

public class Give extends HPAffordance implements HPActionInterface {

	/**
	 * Constructor for the <code>Give</code> Class. Will initialize the message
	 * renderer, the target and set the priority of this <code>Action</code> to 1
	 * (lowest priority is 0).
	 * 
	 * @param theTarget a <code>HPEntity</code> that is being given
	 * @param m         the message renderer to display messages
	 */
	public Give(HPEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}

	/**
	 * Returns if or not this <code>Give</code> can be performed by the
	 * <code>HPActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code>
	 * 1- the target is an actor, 2-both actors on the same team, 3-inventory of the target actor is not full,
	 * 5- both actors is not dead, 6-the actor that is giving carrries at least one item .
	 *</p>
	 * @param a the <code>HPActor</code> being queried
	 * @return true if the <code>HPActor</code> can give this item, false otherwise
	 * @see {@link harrypotter.HPActor#getItemsCarried()}
	 */
	@Override
	public boolean canDo(HPActor a) {
		if (a.isFrozen()) {
			a.unFreeze();
			return false;
		}

		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		HPActor targetActor = null;

		if (targetIsActor)
			targetActor = (HPActor) target;
		
		//check javadoc for the logic
		return targetIsActor && a.Inventory.containsItems() && targetActor.Inventory.notFull()
				&& (a.getTeam() == targetActor.getTeam()) && !targetActor.isDead() && !a.isDead();
	}

	/**
	 * Perform the <code>Give</code> action by giving the item carried by the
	 * <code>HPActor</code> to the another HP actor in the same location ( the
	 * <code>HPActor a</code>'s item carried would be the target of this
	 * <code>Give</code>).
	 * <p>
	 * This method should only be called if the <code>HPActor a</code> is alive.
	 * 
	 * @param a the <code>HPActor</code> that is giving to the target
	 * @see {@link #theTarget}
	 * @see {@link harrypotter.HPActor#isDead()}
	 */
	@Override
	public void act(HPActor a) {

		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		HPActor targetActor = null;

		if (targetIsActor) {
			targetActor = (HPActor) target;

			if ((a.getTeam() == targetActor.getTeam()) && a.Inventory.containsItems() && targetActor.Inventory.notFull()) {

				if (a.isHumanControlled()) {
					a.say("Choose an item to give to " + targetActor.getShortDescription());
				}
				
				HPEntityInterface selectedItem = HPGridController.getChosenItem(a);

				if (!(a.isHumanControlled())) {
					a.say(a.getShortDescription() + " wants to give you this " + selectedItem.getShortDescription());
				}

				// getting AI decision using machine learning
				boolean decision = HPGridController.getAcceptOrDecline(targetActor);
				if (decision) {
					a.Inventory.remove(selectedItem);
					targetActor.Inventory.add(selectedItem);
					a.say(a.getShortDescription() + " gave " + selectedItem.getShortDescription() + " to "
							+ targetActor.getShortDescription());

				} else {
					a.say(targetActor.getShortDescription() + " refused to take " + selectedItem.getShortDescription()
							+ " from " + a.getShortDescription());
					return;
				}

			}
		}

	}

	/**
	 * A String describing what this action will do, suitable for display in a user
	 * interface
	 * 
	 * @return String comprising "give " and the short description of the target of
	 *         this <code>Give</code>
	 */
	@Override
	public String getDescription() {
		return "give an item to " + target.getShortDescription();
	}

}

