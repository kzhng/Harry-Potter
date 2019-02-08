package harrypotter.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import harrypotter.HPActor;
import harrypotter.HPEntityInterface;
import harrypotter.Spell;
import harrypotter.interfaces.HPGridController;
import harrypotter.HPWorld;
import harrypotter.HPGrid;
import harrypotter.HPLocation;

/**
 * Class that represents Apparate, a type of <code>Spell</code> that can be casted in the HP world.
 * When casted upon onself, <code>Apparate</code> teleports oneself to a chosen location.
 * 
 * @author KErry Zheng
 */
public class Apparate extends Spell {

	/**
	 * Constructor for <code>Apparate</code> class. Will initialize the name, description and generic result (afterEffect) of <code>Apparate</code>.
	 * 
	 */
	public Apparate() {
		super("Apparate", "teleport to a location", "has apparated");
	}
	
	/**
	 * <p>
	 * Method that carries out the effect of <code>Apparate</code>, which moves the <code>HPActor</code> to their chosen location.
	 * The movement via this spell to the chosen location is successful if the chosen location does not contain any other <code>HPActor</code>.
	 * If the chosen location is not empty, the actor takes splinching damage between 10 to 100 hitpoints of damage, and are moved
	 * in a random direction until such location is considered empty.
	 * @param theTarget the target <code>HPEntityInterface</code> for the Spell to be casted on
	 */
	@Override
	public void spellEffect(HPEntityInterface theTarget) {
		HPActor targetActor = null;
		
		if (theTarget instanceof HPActor){
			targetActor = (HPActor)theTarget;
			int height = HPGridController.getChosenCoordinate(targetActor);
			int width = HPGridController.getChosenCoordinate(targetActor);
			HPWorld currentWorld = new HPWorld();
			HPGrid myGrid = currentWorld.getGrid();
			EntityManager<HPEntityInterface, HPLocation> entityManager = currentWorld.getEntityManager();
			HPLocation loc = myGrid.getLocationByCoordinates(width , height);
			boolean locEmpty = isLocEmpty(loc, currentWorld);
			if (locEmpty) {
				entityManager.setLocation(targetActor, loc);
			}
			else {
				//the actor sustains between (1 - 10) * 10 hit-points of damage due to splinching
				int randomNumber = new Random().nextInt((10 - 1) + 1) + 1;
				int splinchDamage = randomNumber * 10;
				targetActor.takeDamage(splinchDamage);
				
				//After the splinch
				
				if (targetActor.isDead()) {//the actor who apparated is dead due to splinching
								
					targetActor.setLongDescription(targetActor.getLongDescription() + ", that died of splinching from apparating!");
					
				}
				
				else {
					ArrayList<Direction> directions = new ArrayList<Direction>();
					for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
						if (entityManager.seesExit(targetActor, d)) {
							directions.add(d);
						}
					}
					while (!locEmpty) {
						Direction heading = directions.get((int) (Math.floor(Math.random() * directions.size())));
						currentWorld.moveEntity(targetActor, heading);
						if (!entityManager.seesExit(targetActor, heading)) { // if this direction is not possible
							directions.remove(0);
							directions.remove(CompassBearing.opposite((CompassBearing) heading));
							heading = directions.get(0);
						}
					}
					
				}

			}

		}

	}
	

	/**
	 * Returns if or not this <code>HPLocation</code> is empty or not.
	 * This method returns true if and only if <code>loc</code> has no entities that are <code>HPActor</code>.
	 * @author Kerry Zheng
	 * @param location the <code>HPLocation</code> being queried
	 * @param world the <code>HPWorld</code> the location is at
	 * @return true if the <code>HPLocation</code> contains no entities that are <code>HPActor</code>, false
	 *         otherwise
	 */
	private static boolean isLocEmpty(HPLocation location, HPWorld world) {
		EntityManager<HPEntityInterface, HPLocation> em = world.getEntityManager();
		List<HPEntityInterface> entities = em.contents(location);
		for (HPEntityInterface e : entities) {
			if(e instanceof HPActor) {
				return false;
			}
		}
		return true;

	}

}
