package harrypotter.spells;

import harrypotter.HPEntityInterface;
import harrypotter.actions.Leave;
import harrypotter.Spell;

import java.util.ArrayList;

import harrypotter.HPActor;

/**
 * Class that represents Avada Kedavra, a type of <code>Spell</code> that can be casted in the HP world.
 * When casted upon a target actor, <code>AvadaKedavra</code> kills it instantly.
 * 
 * @author Shakeel Rafi
 */
public class AvadaKedavra extends Spell {

	/**
	 * Constructor for <code>AvadaKedavra</code> class. Will initialize the name, description and generic result (afterEffect) of <code>AvadaKedavra</code>.
	 * 
	 */
	public AvadaKedavra() {
		super("Avada Kedavra", "Kill target instantly", "has been killed!");
	}
	
	/**
	 * Method that carries out the effect of <code>AvadaKedavra</code>, which casts a HPEntityInterface to a HPActor and deal damage equivalent to the HPActor's hit points.
	 * 
	 * @param theTarget the target <code>HPEntityInterface</code> for the Spell to be casted on
	 */
	@Override
	public void spellEffect(HPEntityInterface theTarget) {
		
		HPActor targetActor = null;
		
		if (theTarget instanceof HPActor){
			targetActor = (HPActor)theTarget;
			
			int targetHitPoints = targetActor.getHitpoints();
			targetActor.takeDamage(targetHitPoints);
			
			//After the attack
			targetActor.setLongDescription(targetActor.getLongDescription() + ", was killed by the Killing Curse");

			ArrayList<HPEntityInterface> items = ((HPActor)theTarget).getItemsCarried();
			for (HPEntityInterface item : items) {
				Leave leaveEffect = new Leave(item, null);
				leaveEffect.act((HPActor)theTarget);
			}
			

		}		
	}
	
	
	
}
