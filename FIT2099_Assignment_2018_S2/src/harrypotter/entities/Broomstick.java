package harrypotter.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPEntity;
import harrypotter.actions.Take;

/**
 * A Broomstick.
 * <p>
 * In possession of a Broomstick an actor can travel at double speed it's usual speed
 * </p>
 * @author Matti (adapted from Sword by Robert)
 */

public class Broomstick extends HPEntity {
				
	/**
	 * Constructor for the <code>Broomstick</code> class. This constructor will,
	 * <ul>
	 * <li>Initialize the message renderer for the <code>Broomstick</code></li>
	 * <li>Set the short description of this <code>Broomstick</code>>
	 * <li>Set the long description of this <code>Broomstick</code>
	 * <li>Add a <code>Take</code> affordance to this <code>Broomstick</code> so it can
	 * be taken</li>
	 * <li>Add a <code>HEALTH</code> capability to this <code>Broomstick</code></li>
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link harrypotter.actions.Take}
	 * @see {@link harrypotter.Capability}
	 */
	public Broomstick(MessageRenderer m) {
		super(m);
		this.shortDescription = "A broomstick";
		this.longDescription = "A broomstick";							
		this.addAffordance(new Take(this, m)); // add the take affordance so that the Broomstick can be taken by HPActors
		this.capabilities.add(Capability.DOUBLESPEED); // it's a DOUBLESPEED Item.
		this.hitpoints = 30;
	}

	
	/**
	 * A symbol that is used to represent the health Broomstick on a text based user
	 * interface
	 * 
	 * @return A String containing the symbol of the Broomstick.
	 * @see {@link harrypotter.HPEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "bs ";
	}

}
