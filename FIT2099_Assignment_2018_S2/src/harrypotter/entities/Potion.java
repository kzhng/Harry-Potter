package harrypotter.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPEntity;
import harrypotter.actions.Take;

/**
 * A health Potion
 * 
 * @author Matti (adapted from Sword by Robert)
 */

public class Potion extends HPEntity {

	/**
	 * Constructor for the <code>Potion</code> class. This constructor will,
	 * <ul>
	 * <li>Initialize the message renderer for the <code>Potion</code></li>
	 * <li>Set the short description of this <code>Potion</code>>
	 * <li>Set the long description of this <code>Potion</code>
	 * <li>Add a <code>Take</code> affordance to this <code>Potion</code> so it can
	 * be taken</li>
	 * <li>Add a <code>HEALTH</code> capability to this <code>Potion</code></li>
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link harrypotter.actions.Take}
	 * @see {@link harrypotter.Capability}
	 */
	public Potion(MessageRenderer m) {
		super(m);
		this.shortDescription = "A health potion";
		this.longDescription = "A health potion";
		this.hitpoints = (int) Math.round((Math.random() * 40) + 1); // randomized hitpoints with a maximum of 30 hitpoints
																		
		this.addAffordance(new Take(this, m)); // add the take affordance so that the Potion can be taken by HPActors
		this.capabilities.add(Capability.HEALTH); // it's a health Item.
	}

	/**
	 * A symbol that is used to represent the health Potion on a text based user
	 * interface
	 * 
	 * @return A String containing a single character.
	 * @see {@link harrypotter.HPEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "hp ";
	}

}
