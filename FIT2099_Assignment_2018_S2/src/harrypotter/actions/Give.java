package harrypotter.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPAction;
import harrypotter.HPActor;
import harrypotter.HPAffordance;
import harrypotter.HPEntityInterface;
import harrypotter.HPGrid;
import harrypotter.interfaces.HPGridTextInterface;

/**
 * <code>HPAction</code> that lets a <code>HPActor</code> pick up an object.
 * 
 * @author ram
 */
/*
 * Changelog 2017/01/26 - candDo method changed. An actor can only give if it's
 * holding any items already. - act method modified. Give affordance removed
 * from the item picked up, since an item picked up cannot be given. This is
 * just a safe guard. - canDo method changed to return true only if the actor is
 * not carrying an item (asel)
 */
public class Give extends HPAffordance {

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
	 * This method returns true if and only if <code>a</code> is carrying any item
	 * already and there is at least 1 actor from the same team.
	 * 
	 * @author ram
	 * @author Asel (26/01/2017)
	 * @param a the <code>HPActor</code> being queried
	 * @return true if the <code>HPActor</code> is can give this item, false
	 *         otherwise
	 * @see {@link harrypotter.HPActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(HPActor a) {
		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		HPActor targetActor = null;

		if (targetIsActor)
			targetActor = (HPActor) target;

		return a.getItemCarried() != null && targetIsActor && (a.getTeam() == targetActor.getTeam());
	}

	/**
	 * Perform the <code>Give</code> action by giving the item carried by the
	 * <code>HPActor</code> to the another HP actor in the same location ( the
	 * <code>HPActor a</code>'s item carried would be the target of this
	 * <code>Give</code>).
	 * <p>
	 * This method should only be called if the <code>HPActor a</code> is alive.
	 * 
	 * @author ram
	 * @author Asel (26/01/2017)
	 * @param a the <code>HPActor</code> that is taking the target
	 * @see {@link #theTarget}
	 * @see {@link harrypotter.HPActor#isDead()}
	 */
	@Override
	public void act(HPActor a) {
		/*
		 * if (target instanceof HPEntityInterface) { HPEntityInterface theItem =
		 * (HPEntityInterface) target; a.setItemCarried(null);
		 * b.setItemCarried(theItem);
		 * 
		 * 
		 * //remove the give affordance //target.removeAffordance(this); }
		 */

		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		HPActor targetActor = null;
		
		
		
		if (targetIsActor) {
			targetActor = (HPActor) target;

			if ( !(a.isHumanControlled()) && (a.getTeam() == targetActor.getTeam()) && a.getItemCarried() != null
					&& targetActor.getItemCarried() == null) {
				HPEntityInterface theItem = a.getItemCarried();
				
				if (Math.random() > 0.25) {
					a.setItemCarried(null);
					targetActor.setItemCarried(theItem);
					a.say(a.getShortDescription() + " gave " + theItem.getShortDescription() + " to "
							+ targetActor.getShortDescription());

				} else {
					a.say(targetActor.getShortDescription() + " refused to take " + theItem.getShortDescription() + " from "
							+ a.getShortDescription());
					return;
				}
			}
			
			
			if (a.isHumanControlled() && (a.getTeam() == targetActor.getTeam()) && a.getItemCarried() != null
					&& targetActor.getItemCarried() == null) {
				HPEntityInterface theItem = a.getItemCarried();
				
				if (Math.random() > 0.25) {
					a.setItemCarried(null);
					targetActor.setItemCarried(theItem);
					a.say(a.getShortDescription() + " gave " + theItem.getShortDescription() + " to "
							+ targetActor.getShortDescription());

				} else {
					a.say(targetActor.getShortDescription() + " refused to take " + theItem.getShortDescription() + " from "
							+ a.getShortDescription());
					return;
				}
			}
		}

		/*
		 * if (targetIsActor && (a.getTeam() == targetActor.getTeam()) &&
		 * a.getItemCarried()!= null && targetActor.getItemCarried() == null) {
		 * HPEntityInterface theItem = a.getItemCarried(); a.setItemCarried(null);
		 * targetActor.setItemCarried(theItem); a.say(a.getShortDescription() + " gave "
		 * + theItem.getShortDescription() + " to " +
		 * targetActor.getShortDescription()); } else if ( (a.isHumanControlled() &&
		 * targetIsActor )// for a human-controlled player, the receiver has a chance of
		 * rejecting the item || (targetIsActor && (a.getTeam() ==
		 * targetActor.getTeam()))) { // the actors must be on the same team
		 * 
		 * HPEntityInterface theItem = a.getItemCarried(); a.setItemCarried(null); //
		 * removing the item from the actor targetActor.setItemCarried(theItem); //
		 * setting the item to a new actor
		 * 
		 * a.say(a.getShortDescription() + " gave " + theItem.getShortDescription() +
		 * " to" + targetActor.getShortDescription()); }
		 */
	}

	/**
	 * A String describing what this action will do, suitable for display in a user
	 * interface
	 * 
	 * @author ram
	 * @return String comprising "give " and the short description of the target of
	 *         this <code>Give</code>
	 */
	@Override
	public String getDescription() {
		return  "give to " + target.getShortDescription();
	}

}




