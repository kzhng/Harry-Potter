package harrypotter.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
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

/**
 * Dumbledore
 * 
 * At this stage, he's an extremely strong critter with a <code>Sword</code> who
 * wanders around in a fixed pattern and neatly slices any Actor not on his team
 * with his sword.
 * 
 * Note that you can only create ONE Dumbledore, like all SWLegends.
 * 
 * @author rober_000, davids
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

		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(albus, albus.world, true, true);

		HPEntityInterface itemCarried = this.getItemCarried();

		if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " + attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, albus, 1);
		}

		else if (itemCarried != null) {
			ArrayList<AttackInformation> givable;
			givable = AttackNeighbours.attackAllLocals(albus, albus.world, false, true);
			if (givable != null) {
				for (AttackInformation giveTofreindly : givable) {
					if (giveTofreindly.entity instanceof Player) {

						Player target = (Player) giveTofreindly.entity;

						if (target.getItemCarried() == null) {
							for (Affordance thisAffordance : this.getAffordances()) {

								if (thisAffordance instanceof Give) {
									HPEntityInterface theItem = this.getItemCarried();
									this.setItemCarried(null);
									target.setItemCarried(theItem);
									this.say(this.getShortDescription() + " gave " + theItem.getShortDescription()
											+ " to " + giveTofreindly.entity.getShortDescription());

									scheduler.schedule(thisAffordance, this, 1);
								}
							}
						}

					}
				}

			}
		} else {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

}
