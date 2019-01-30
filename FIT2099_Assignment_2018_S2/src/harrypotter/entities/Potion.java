package harrypotter.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPEntity;
import harrypotter.actions.Take;

/**
 * A Potion(Health)
 * 
 * @author Matti (adapted from Sword by Robert)
 */

public class Potion extends HPEntity {
	
	/**
	 * Stores the Symbol of Potion
	 */
	private String symbol; 
				
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
		this.hitpoints = (int) Math.round((Math.random() * 50) + 1); // randomized hitpoints with a maximum of 50 hitpoints
																		
		this.addAffordance(new Take(this, m)); // add the take affordance so that the Potion can be taken by HPActors
		this.capabilities.add(Capability.HEALTH); // it's a health Item.
		this.symbol = "hp ";
	}

	/**
	 * makes the symbol of potion into a empty string, in effect it makes it hidden
	 * it also changes the long description to inform the player that it's hidden
	 */
	public void makeHidden() {
		this.longDescription = "A health potion(hidden)";
		this.symbol = "";
	}
	
	/**
	 * A symbol that is used to represent the health Potion on a text based user
	 * interface
	 * 
	 * @return A String containing the symbol of the Potion.
	 * @see {@link harrypotter.HPEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return symbol;
	}

}
