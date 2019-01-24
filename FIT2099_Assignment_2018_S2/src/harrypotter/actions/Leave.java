package harrypotter.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPAction;
import harrypotter.HPActor;
import harrypotter.HPAffordance;
import harrypotter.HPEntityInterface;

/**
 * <code>HPAction</code> that lets a <code>HPActor</code> pick up an object.
 * 
 * @author ram
 */
/*
 * Changelog
 * 2017/01/26	- candDo method changed. An actor can only leave if it's not holding any items already.
 * 				- act method modified. Leave affordance removed from the item picked up, since an item picked up
 * 				  cannot be taken. This is just a safe guard.
 * 				- canDo method changed to return true only if the actor is not carrying an item (asel)
 */
public class Leave extends HPAffordance {

	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>HPEntity</code> that is being taken
	 * @param m the message renderer to display messages
	 */
	public Leave(HPEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}


	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>HPActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not carrying any item already.
	 *  
	 * @author 	ram
	 * @author 	Asel (26/01/2017)
	 * @param 	a the <code>HPActor</code> being queried
	 * @return 	true if the <code>HPActor</code> is can leave this item, false otherwise
	 * @see		{@link harrypotter.HPActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(HPActor a) {
		return a.getItemCarried()==null;
	}

	/**
	 * Perform the <code>Leave</code> action by setting the item carried by the <code>HPActor</code> to the target (
	 * the <code>HPActor a</code>'s item carried would be the target of this <code>Leave</code>).
	 * <p>
	 * This method should only be called if the <code>HPActor a</code> is alive.
	 * 
	 * @author 	ram
	 * @author 	Asel (26/01/2017)
	 * @param 	a the <code>HPActor</code> that is taking the target
	 * @see 	{@link #theTarget}
	 * @see		{@link harrypotter.HPActor#isDead()}
	 */
	@Override
	public void act(HPActor a) {
		if (target instanceof HPEntityInterface) {
			HPEntityInterface theItem = (HPEntityInterface) target;			
			a.setItemCarried(null);
			//HPAction.getEntitymanager().setLocation(theItem,);;//remove the target from the entity manager since it's now held by the HPActor
			
			//remove the leave affordance
			target.removeAffordance(this);
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author ram
	 * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		return "leave " + target.getShortDescription();
	}

}
