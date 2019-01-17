package harrypotter.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import harrypotter.HPActor;
import harrypotter.HPEntityInterface;
import harrypotter.HPLocation;
import harrypotter.HPWorld;
import harrypotter.actions.Attack;

public class AttackNeighbours {

	public static AttackInformation attackLocals(HPActor actor, HPWorld world, boolean avoidFriendlies, boolean avoidNonActors) {
		HPLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<HPEntityInterface, HPLocation> em = world.getEntityManager();
		List<HPEntityInterface> entities = em.contents(location);

		// select the attackable things that are here

		ArrayList<AttackInformation> attackables = new ArrayList<AttackInformation>();
		for (HPEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != actor && 
					(e instanceof HPActor && 
							(avoidFriendlies==false || ((HPActor)e).getTeam() != actor.getTeam()) 
							|| (avoidNonActors == false && !(e instanceof HPActor)))) {
				for (Affordance a : e.getAffordances()) {
					if (a instanceof Attack) {

						attackables.add(new AttackInformation(e, a));
						break;
					}
				}
			}
		}

		// if there's at least one thing we can attack, randomly choose
		// something to attack
		if (attackables.size() > 0) {
			return attackables.get((int) (Math.floor(Math.random() * attackables.size())));
		} else {
			return null;
		}
	}
}
