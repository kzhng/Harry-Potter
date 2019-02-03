package harrypotter.spells;

import harrypotter.HPEntityInterface;
import harrypotter.Spell;
import harrypotter.actions.Leave;

import java.util.ArrayList;

import harrypotter.HPActor;

/**
 * Class that represents Expelliarmus, a type of <code>Spell</code> that can be casted in the HP world.
 * When casted upon a target actor, <code>Expelliarmus</code> disarms it and makes the entity leave its item.
 *
 * @author Shakeel Rafi
 */
public class Expelliarmus extends Spell {

	/**
	 * Constructor for <code>Expelliarmus</code> class. Will initialize the name, description and generic result (afterEffect) of <code>Expelliarmus</code>.
	 * 
	 */
	public Expelliarmus() {
		super("Expelliarmus", "Make target drop its item", "has dropped its item!");
	}
	
	/**
	 * Method that carries out the effect of <code>Expelliarmus</code>, which explicitly casts a HPEntityInterface to a HPActor and use the <code>Leave</code> action to make it drop its item.
	 * 
	 * @param theTarget the target <code>HPEntityInterface</code> for the Spell to be casted on
	 */
	@Override
	public void spellEffect(HPEntityInterface theTarget) {
		
		if (theTarget instanceof HPActor){
			ArrayList<HPEntityInterface> items = ((HPActor)theTarget).getItemsCarried();
			for (HPEntityInterface item : items) {
				Leave leaveEffect = new Leave(item, null);
				leaveEffect.act((HPActor)theTarget);
			}
		}
	}
}