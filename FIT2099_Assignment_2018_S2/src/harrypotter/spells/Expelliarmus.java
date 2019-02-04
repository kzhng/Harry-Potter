package harrypotter.spells;

import harrypotter.HPEntityInterface;
import harrypotter.Spell;
import harrypotter.actions.Leave;

import java.util.ArrayList;
import java.util.Random;

import harrypotter.Capability;
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
	 * <p>
	 * Method that carries out the effect of <code>Expelliarmus</code>, which explicitly casts a HPEntityInterface
	 *  to a HPActor and use the <code>Leave</code> action to make it drop its item. furthermore the 
	 *  actor will drop a random item that has either a {@link Capability} of CASTING or WEAPON. If the actor has items
	 *   of the same capability, then the actor will drop the one with the highest hit points.
	 * </p>
	 * @param theTarget the target <code>HPEntityInterface</code> for the Spell to be casted on
	 */
	@Override
	public void spellEffect(HPEntityInterface theTarget) {
		
		if (theTarget instanceof HPActor){
			ArrayList<HPEntityInterface> items = new ArrayList<HPEntityInterface>();			
			HPEntityInterface weaponItem = ((HPActor)theTarget).getHighestItemWithCapability(Capability.WEAPON);
			HPEntityInterface castItem = ((HPActor)theTarget).getHighestItemWithCapability(Capability.CASTING);
			
			if (weaponItem!=null)
					items.add(weaponItem);
			if (castItem!=null)
					items.add(castItem);
			if(!items.isEmpty()) {
				int random  = new Random().nextInt(items.size());
				HPEntityInterface itemToLeave = items.get(random);
				Leave leaveEffect = new Leave(itemToLeave, null);
				leaveEffect.act((HPActor)theTarget);
			}
		}
	}
}