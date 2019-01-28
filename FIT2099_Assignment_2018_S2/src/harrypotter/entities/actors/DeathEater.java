package harrypotter.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActor;
import harrypotter.HPLocation;
import harrypotter.HPWorld;
import harrypotter.Team;
import harrypotter.actions.Move;
import harrypotter.entities.actors.behaviors.AttackInformation;
import harrypotter.entities.actors.behaviors.AttackNeighbours;

public class DeathEater extends HPActor {

	/**
	 * Create a Death Eater.  Death Eaters will randomly wander
	 * around the world (on any given turn, there is a 50% probability
	 * that they will move) and attack anything they can (if they can attack
	 * something, they will).  They 
	 * are all members of team EVIL, so their attempts to attack
	 * other evil characters won't have any effect.
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Death Eater. If this
	 *            decreases to below zero, the Death Eater will die.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>HPWorld</code> world to which this
	 *            <code>DeathEater</code> belongs to
	 * 
	 */
	public DeathEater(int hitpoints, MessageRenderer m, HPWorld world) {
		super(Team.EVIL, hitpoints, m, world);	//mh changed to Good
	}

	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());

		AttackInformation attack = AttackNeighbours.attackLocals(this, this.world, false, false);
		if (attack != null) {
			say(getShortDescription() + " has attacked" + attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, this, 1);
		}
		else if (Math.random() > 0.5) {
			
			ArrayList<Direction> possibledirections = new ArrayList<Direction>();

			// build a list of available directions
			for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
				if (HPWorld.getEntitymanager().seesExit(this, d)) {
					possibledirections.add(d);
				}
			}

			Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
			say(getShortDescription() + "is heading " + heading + " next.");
			Move myMove = new Move(heading, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

	@Override
	public String getShortDescription() {
		return "a Death Eater"; // they all look the same
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		HPLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
}
