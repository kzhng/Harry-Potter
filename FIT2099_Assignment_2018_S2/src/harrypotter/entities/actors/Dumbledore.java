package harrypotter.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActionInterface;
import harrypotter.HPActor;
import harrypotter.HPEntity;
import harrypotter.HPEntityInterface;
import harrypotter.HPLegend;
import harrypotter.HPWorld;
import harrypotter.Team;
import harrypotter.actions.Give;
import harrypotter.actions.Move;
import harrypotter.actions.Take;
import harrypotter.entities.Sword;
import harrypotter.entities.actors.behaviors.AttackInformation;
import harrypotter.entities.actors.behaviors.AttackNeighbours;
import harrypotter.entities.actors.behaviors.Patrol;
import harrypotter.interfaces.HPGridController;

/**
 * Dumbledore
 * 
 * At this stage, he's an extremely strong critter with a <code>Sword</code> who
 * wanders around in a fixed pattern and neatly slices any Actor not on his team
 * with his sword.
 * 
 * Note that you can only create ONE Dumbledore, like all SWLegends.
 * 
 * @author Matti
 *
 */
public class Dumbledore extends HPLegend {

	private static Dumbledore albus = null; // yes, it is OK to return the static instance!
	private Patrol path;

	private Dumbledore(MessageRenderer m, HPWorld world, Direction[] moves) {
		super(Team.GOOD, 1000, m, world);
		path = new Patrol(moves);
		this.setSymbol("D");
		this.setShortDescription("Albus Dumbledore");
		this.setLongDescription("Albus Dumbledore, a very powerful wizard");
	}

	public static Dumbledore getDumbledore(MessageRenderer m, HPWorld world, Direction[] moves) {
		albus = new Dumbledore(m, world, moves);
		albus.activate();
		return albus;
	}

	@Override
	protected void legendAct() {

		if (isDead()) {
			return;
		}

		if (isFrozen()) {
			unFreeze();
			return;
		}
		
		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(albus, albus.world, true, true);

		if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " + attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, albus, 1);
		}

		
		else if (this.albusGive()) {	// His grace's generosity, quite smelly 
			scheduler.schedule(null, this, 1);

		} else {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}
	
	
	/**
	 * @author Matti
	 * @return true if the player accept the item carried by albus, otherwise false
	 */
	// massive code smell
	private boolean albusGive() {
		
		HPEntityInterface theItem = this.getItemCarried();
		if(theItem == null) {
			return false;
		}
		
		ArrayList<AttackInformation> givable;
		givable = AttackNeighbours.attackAllLocals(albus, albus.world, false, true);
		if (givable != null) {
			for (AttackInformation giveTofreindly : givable) {
				HPActor target = (HPActor) giveTofreindly.entity;
				
				if (target instanceof Player && target.getItemCarried() == null) {	
					//
					
					this.say(this.getShortDescription() + " wants to give you this "
							+ theItem.getShortDescription());
					boolean userDecision = HPGridController.getAcceptOrDecline(target);

					if (userDecision) {
						this.setItemCarried(null);
						target.setItemCarried(theItem);
						this.say(this.getShortDescription() + " gave " + theItem.getShortDescription()
								+ " to " + giveTofreindly.entity.getShortDescription());
						return true;
					} else {
						return false;
					}
					//
//						for (Affordance thisAffordance : this.getAffordances()) {
//
//							if (thisAffordance instanceof Give) {
//								HPEntityInterface theItem = this.getItemCarried();
//								this.say(this.getShortDescription() + " wants to give you this "
//										+ theItem.getShortDescription());
//								boolean userDecision = HPGridController.getAcceptOrDecline(target);
//
//								if (userDecision) {
//									this.setItemCarried(null);
//									target.setItemCarried(theItem);
//									this.say(this.getShortDescription() + " gave " + theItem.getShortDescription()
//											+ " to " + giveTofreindly.entity.getShortDescription());
//
//									scheduler.schedule(thisAffordance, this, 1);
//								} else {
//									continue;
//								}
//							}
//
//						}
					
				}

			}
		}
		return false;
		
		
	}

}
