package harrypotter.actions;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActor;
import harrypotter.HPEntityInterface;
import harrypotter.HPWorld;
import harrypotter.entities.Broomstick;
import harrypotter.entities.actors.Dumbledore;
import harrypotter.interfaces.HPGridController;

/**
 * <code>HPAction</code> that lets a <code>Teacher</code> Give a broomstick.
 * 
 * @author Mati
 */

public class GiveBroomStick extends Give {

	/**The world in which this <code>DoubleMove</code> action should occur, needed so resetMoveCommands can be used to take into account that the actor has a broomstick
	/* @see Broomstick
	 * 
	 */
	private HPWorld world;
	
	/**
	 * Constructor for the <code>Give</code> Class. Will initialize the message
	 * renderer, the target and set the priority of this <code>Action</code> to 1
	 * (lowest priority is 0).
	 * 
	 * @param theTarget a <code>HPEntity</code> that is being given
	 * @param m         the message renderer to display messages
	 * @param world the world in which the <code>DoubleMove</code> action needs to be triggered
	 */
	public GiveBroomStick(HPEntityInterface theTarget, MessageRenderer m, HPWorld world) {
		super(theTarget, m);
		priority = 1;
		this.world = world;		
	}
	
	/**
	 * Returns if or not this <code>GiveBroomStick</code> can be performed by the
	 * <code>HPActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> 
	 * <p>
	 * 1- the target is ab actor, 2-both actors on the same team, 3-inventory of the target actor is not full,
	 * 4-target actor can use broomstick, 5- targetactor is not dead, 6-the actor that is giving the broomstick is a teacher .
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
		return targetIsActor &&  !targetActor.ownsBroomstick() && targetActor.Inventory.notFull() && a instanceof Dumbledore && targetActor.canUseBroomstick()		//mh please, uncoment the teacher bit once teacher class is completed
				&& (a.getTeam() == targetActor.getTeam()) && !targetActor.isDead() && !a.isDead();
	}
	
	/**
	 * Perform the <code>GiveBroomstick</code> action by giving the item carried by the
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

			if ((a.getTeam() == targetActor.getTeam()) && targetActor.Inventory.notFull()) {

				if (!(a.isHumanControlled())) {
					a.say(a.getShortDescription() + " wants to give you this a Broomstick");
				}

				// getting AI decision using machine learning
				boolean decision = HPGridController.getAcceptOrDecline(targetActor);
				if (decision) {
					// create a new broomstick object
					Broomstick broomstick= new Broomstick(messageRenderer);
					for (Affordance affor : broomstick.getAffordances()){
						if (affor instanceof Take){
							broomstick.removeAffordance(affor);
						}	
					}
					broomstick.addAffordance(new Leave(broomstick, messageRenderer));
					targetActor.Inventory.add(broomstick);
					targetActor.setOwnsBroomstick(true);
					targetActor.resetMoveCommands(world.find(targetActor));
					
					a.say(a.getShortDescription() + " gave " + broomstick.getShortDescription() + " to "
							+ targetActor.getShortDescription());

				} else {
					a.say(targetActor.getShortDescription() + " refused to take the Broomstick" 
							+ " from " + a.getShortDescription());
					return;
				}

			}
		}

	}

}
