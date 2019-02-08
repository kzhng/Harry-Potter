package harrypotter.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPActionInterface;
import harrypotter.HPAffordance;
import harrypotter.HPLegend;
import harrypotter.HPWorld;
import harrypotter.Inventory;
import harrypotter.Team;
import harrypotter.actions.Give;
import harrypotter.actions.GiveBroomStick;
import harrypotter.actions.Move;
import harrypotter.entities.actors.behaviors.AttackInformation;
import harrypotter.entities.actors.behaviors.AttackNeighbours;
import harrypotter.entities.actors.behaviors.Patrol;

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
		this.capabilities.add(Capability.INVENTORY);
		this.InventorySize = (this.hasCapability(Capability.INVENTORY))? 3 : 1;		//inventory size is 3 for actors with INVENTORY capability
		this.Inventory = new Inventory(InventorySize);

	}

	public static Dumbledore getDumbledore(MessageRenderer m, HPWorld world, Direction[] moves) {
		albus = new Dumbledore(m, world, moves);
		albus.activate();
		return albus;
	}

	@Override
	protected void legendAct() {

		HPAffordance albusGive = null;
		HPAffordance albusBroomstickGive = null;

		// Get all the actions the HPActor a can perform
		for (HPActionInterface ac : HPWorld.getEntitymanager().getActionsFor(this)) {
			if (ac instanceof Give) {
				if (ac.canDo(this)) {
					albusGive = (HPAffordance) ac;
				}
			}
			if (ac instanceof GiveBroomStick) {
				if (ac.canDo(this)) {
					albusBroomstickGive = (HPAffordance) ac;
				}
			}
		}

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
		
		else if(albusBroomstickGive != null) {
			albusBroomstickGive.execute(this);
			scheduler.schedule(null, this, 1);
		}
		
		else if (albusGive!=null) {  // His grace's generosity, quite smelly
			albusGive.execute(this);
			scheduler.schedule(null, this, 1);
		} 
		else {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

	public boolean isTeacher() {
		return true;
	}
	
}
