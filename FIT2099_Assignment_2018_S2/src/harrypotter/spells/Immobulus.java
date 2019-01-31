package harrypotter.spells;

import harrypotter.HPEntityInterface;
import harrypotter.Spell;
import harrypotter.HPActor;


/**
 * Class that represents Immobulus, a type of <code>Spell</code> that can be casted in the HP world.
 * When casted upon a target actor, <code>Immobulus</code> freezes the actor renders the target unable to move or act for 5 turns.
 *
 * @author Kerry Zheng
 */
public class Immobulus extends Spell {
	
	/**
	 * Constructor for <code>Immobulus</code> class. Will initialize the name, description and generic result (afterEffect) of <code>Immobulus</code>.
	 * 
	 */
	public Immobulus() {
		super("Immobulus", "freezes the target", "has been frozen!");
	}
	
	/**
	 * Method that carries out the effect of <code>Immobulus</code>, which adds the freeze time of the target by 5 turns.
	 * 
	 * @param theTarget the target <code>HPEntityInterface</code> for the Spell to be casted on
	 */
	@Override
	public void spellEffect(HPEntityInterface theTarget) {
		HPActor targetActor = null;
		
		if (theTarget instanceof HPActor) {
			targetActor = (HPActor)theTarget;
			targetActor.freeze(5);
		}
	}
}
