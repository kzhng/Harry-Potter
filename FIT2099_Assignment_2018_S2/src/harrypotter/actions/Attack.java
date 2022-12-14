package harrypotter.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPActionInterface;
import harrypotter.HPActor;
import harrypotter.HPAffordance;
import harrypotter.HPEntityInterface;

/**
 * Command to attack entities.
 * 
 * This affordance is attached to all attackable entities
 * 
 * @author David.Squire@monash.edu (dsquire)
 */
/*
 * Change log
 * 2017/02/03	Fixed the bug where the an actor could attack another actor in the same team (asel)
 * 2017/02/08	Attack given a priority of 1 in constructor (asel)
 */
public class Attack extends HPAffordance implements HPActionInterface {

	
	/**
	 * Constructor for the <code>Attack</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Attack</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being attacked
	 * @param m message renderer to display messages
	 */
	public Attack(HPEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}


	/**
	 * Returns the time is takes to perform this <code>Attack</code> action.
	 * 
	 * @return The duration of the Attack action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	
	/**
	 * A String describing what this <code>Attack</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "attack " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "attack " + this.target.getShortDescription();
	}


	/**
	 * Determine whether a particular <code>HPActor a</code> can attack the target.
	 * 
	 * @author 	dsquire
	 * @param 	a the <code>HPActor</code> being queried
	 * @return 	true any <code>HPActor</code> can always try an attack, it just won't do much 
	 * 			good unless this <code>HPActor a</code> has a suitable weapon.
	 */
	@Override
	public boolean canDo(HPActor a) {
		if (a.isFrozen()) {
			a.unFreeze();
			return false;
		}
		return !a.isDead();
	}

	
	/**
	 * Perform the <code>Attack</code> command on an entity.
	 * <p>
	 * This method does not perform any damage (an attack) if,
	 * <ul>
	 * 	<li>The target of the <code>Attack</code> and the <code>HPActor a</code> are in the same <code>Team</code></li>
	 * 	<li>The <code>HPActor a</code> is holding an item without the <code>WEAPON Affordance</code></li>
	 * </ul>
	 * <p>
	 * else it would damage the entity attacked, tires the attacker, and blunts any weapon used for the attack.
	 * 
	 * TODO : check if the weapon has enough hitpoints and the attacker has enough energy before an attack.
	 * 
	 * @author 	dsquire -  adapted from the equivalent class in the old Eiffel version
	 * @author 	Asel - bug fixes.
	 * @param 	a the <code>HPActor</code> who is attacking
	 * @pre 	this method should only be called if the <code>HPActor a</code> is alive
	 * @pre		an <code>Attack</code> must not be performed on a dead <code>HPActor</code>
	 * @post	if a <code>HPActor</code>dies in an <code>Attack</code> their <code>Attack</code> affordance would be removed
	 * @see		harrypotter.HPActor#isDead()
	 * @see 	harrypotter.Team
	 */
	@Override
	public void act(HPActor a) {
		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		HPActor targetActor = null;
		int energyForAttackWithWeapon = 1;//the amount of energy required to attack with a weapon
		
		if (targetIsActor) {
			targetActor = (HPActor) target;
		}
					
		
		if (targetIsActor && (a.getTeam() == targetActor.getTeam())) { //don't attack HPActors in the same team
			a.say("\t" + a.getShortDescription() + " says: Silly me! We're on the same team, " + target.getShortDescription() + ". No harm done");
		}
		else if (a.isHumanControlled() // a human-controlled player can attack anyone
			|| (targetIsActor && (a.getTeam() != targetActor.getTeam()))) {  // others will only attack actors on different teams
				
			a.say(a.getShortDescription() + " is attacking " + target.getShortDescription() + "!");
			
			if (a.Inventory.containsItems()) {//if the actor is carrying an item 
				HPEntityInterface itemCarried = a.Inventory.getHighestItemWithCapability(Capability.WEAPON);
				if (itemCarried != null) {
					target.takeDamage(itemCarried.getHitpoints() + 1); // blunt weapon won't do much, but it will still do some damage
					itemCarried.takeDamage(1); // weapon gets blunt
					a.takeDamage(energyForAttackWithWeapon); // actor uses energy to attack
				}
				else {//an attack with a none weapon
					itemCarried = a.Inventory.getItemsCarried().get(0); //get the first item
					if (targetIsActor) {
						targetActor.say("\t" + targetActor.getShortDescription()
								+ " is amused by " + a.getShortDescription()
								+ "'s attempted attack with "
								+ itemCarried.getShortDescription());
					}
				} 
			}
			else { // attack with bare hands
				target.takeDamage((a.getHitpoints()/20) + 1); // a bare-handed attack doesn't do much damage.
				a.takeDamage(2*energyForAttackWithWeapon); // actor uses energy. It's twice as tiring as using a weapon
			}
			
			
			
			//After the attack
			
			if (a.isDead()) {//the actor who attacked is dead after the attack
							
				a.setLongDescription(a.getLongDescription() + ", that died of exhaustion while attacking someone");
				
				//remove the attack affordance of the dead actor so it can no longer be attacked
				a.removeAffordance(this);
				
				
			}
			if (this.getTarget().getHitpoints() <= 0) {  // can't use isDead(), as we don't know that the target is an actor
				target.setLongDescription(target.getLongDescription() + ", that was killed in a fight");
							
				//remove the attack affordance of the dead actor so it can no longer be attacked
				targetActor.removeAffordance(this);

				
			}
		} // not game player and different teams
		
	}
}
