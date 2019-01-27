package harrypotter.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActor;
import harrypotter.HPLocation;
import harrypotter.HPWorld;
import harrypotter.Team;
import harrypotter.actions.Move;
import harrypotter.entities.actors.behaviors.AttackInformation;
import harrypotter.entities.actors.behaviors.AttackNeighbours;

public class Dementor extends HPActor {

	/**
	 * Create a Dementor. • All Dementors are on team EVIL. • A Dementor has a home
	 * base, which is its initial location. • At the start of its turn (a turn is
	 * when its act() method is called), if a Dementor is in a location with one or
	 * more actors on a different team, it sucks energy out of the actors, reducing
	 * each actor’s hitpoints by 40. It then does the movement behaviour described
	 * below. • When a Dementor is at its home base, it waits for a random number of
	 * turns between 1 and 5 inclusive. It then randomly chooses a direction in
	 * which to travel, and randomly chooses how many steps to travel in that
	 * direction, up to a maximum of three steps. After that, on each turn it moves
	 * one step in its chosen direction until it cannot move further or it has moved
	 * the chosen number of steps. It then retraces its steps back to its base one
	 * step per turn.
	 * 
	 * @param hitpoints the number of hit points of this Dementor. If this decreases
	 *                  to below zero, the Death Eater will die.
	 * @param m         <code>MessageRenderer</code> to display messages.
	 * @param world     the <code>HPWorld</code> world to which this
	 *                  <code>Dementor</code> belongs to
	 * 
	 */
	private int minWait = 1;
	private int maxWait = 5;
	private int turnWait;
	private ArrayList<Direction> definedDirections;

	public Dementor(int hitpoints, MessageRenderer m, HPWorld world) {
		super(Team.EVIL, hitpoints, m, world);
		this.turnWait = (int) Math.floor((Math.random() * maxWait) + minWait); // waits for a random number of turns
																				// between 1 and 5 inclusive
		definedDirections = new ArrayList<Direction>();
	}

	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());

		ArrayList<AttackInformation> attackable = AttackNeighbours.attackAllLocals(this, this.world, false, false);
		if (attackable != null) {
			for(AttackInformation attack : attackable) {
				say(getShortDescription() + " sucked energy out of " + attack.entity.getShortDescription());
				scheduler.schedule(attack.affordance, this, 1);
			}
			
		} else {
			// build a list of available directions
			if (definedDirections.size() == 0) {

				ArrayList<Direction> possibledirections = new ArrayList<Direction>();
				ArrayList<Direction> oppositeDirections = new ArrayList<Direction>();
				this.turnWait = (int) Math.floor((Math.random() * maxWait) + minWait); // generate new turnWait

				for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
					if (HPWorld.getEntitymanager().seesExit(this, d)) {
						possibledirections.add(d);
						oppositeDirections.add(CompassBearing.opposite(d)); // opposite direction
					}
				}
				Direction heading = possibledirections
						.get((int) (Math.floor(Math.random() * possibledirections.size())));
				Direction retracing = oppositeDirections.get(possibledirections.indexOf(heading));
				int minSteps = 1;
				int maxSteps = 3;
				int steps = (int) Math.floor((Math.random() * maxSteps) + minSteps);
				for (int i = 0; i < steps; i++) {
					definedDirections.add(0, heading);
					definedDirections.add(retracing);
				}

			} 

			else if (this.turnWait == 0) {
				Direction heading = definedDirections.get(0);
				say(getShortDescription() + " is heading " + heading + " next.");
				Move myMove = new Move(heading, messageRenderer, world);

				scheduler.schedule(myMove, this, 1);
				definedDirections.remove(0);
			}
			else {
				turnWait--;
			}
			
		}
	}

	@Override
	public String getShortDescription() {
		return "a Dementor"; // they may as well all look the same
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

//previous implementation of act method
/*
 * public void act() { if (isDead()) { return; } say(describeLocation());
 * 
 * AttackInformation attack = AttackNeighbours.attackLocals(this, this.world,
 * false, false); if (attack != null) { say(getShortDescription() +
 * " sucks energy out of " + attack.entity.getShortDescription());
 * scheduler.schedule(attack.affordance, this, 1); } else { int minWait = 1; int
 * maxWait = 5; int turnWait = (int) Math.floor((Math.random() * maxWait) +
 * minWait); // waits for a random number of turns // between 1 and 5 inclusive
 * ArrayList<Direction> possibledirections = new ArrayList<Direction>();
 * ArrayList<Direction> oppositeDirections = new ArrayList<Direction>();
 * ArrayList<Direction> definedDirections = new ArrayList<Direction>();
 * 
 * // build a list of available directions if (definedDirections.size() == 0) {
 * for (Grid.CompassBearing d : Grid.CompassBearing.values()) { if
 * (HPWorld.getEntitymanager().seesExit(this, d)) { possibledirections.add(d);
 * oppositeDirections.add(CompassBearing.opposite(d)); // opposite direction } }
 * Direction heading = possibledirections .get((int) (Math.floor(Math.random() *
 * possibledirections.size()))); Direction retracing =
 * oppositeDirections.get(possibledirections.indexOf(heading)); int minSteps =
 * 1; int maxSteps = 3; int steps = (int) Math.floor((Math.random() * maxSteps)
 * + minSteps); steps = 3; for (int i = 0; i < steps; i++) {
 * definedDirections.add(0, heading); definedDirections.add(retracing); } }
 * 
 * Direction heading = definedDirections.get(0); say(getShortDescription() +
 * "is heading " + heading + " next."); Move myMove = new Move(heading,
 * messageRenderer, world);
 * 
 * scheduler.schedule(myMove, this, turnWait); definedDirections.remove(0);
 * 
 * } }
 * 
 * 
 */
