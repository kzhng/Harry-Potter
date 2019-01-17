package harrypotter.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPEntity;
import harrypotter.actions.Take;

/**
 * A sword
 * 
 *  @author David Squire (adapted from Sword by Robert)
 *  @see {@link harrypotter.actions.Attack}
 */

public class Sword extends HPEntity {

	/**
	 * Constructor for the <code>Sword</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Sword</code></li>
	 * 	<li>Set the short description of this <code>Sword</code>>
	 * 	<li>Set the long description of this <code>Sword</code> 
	 * 	<li>Add a <code>Take</code> affordance to this <code>Sword</code> so it can be taken</li> 
	 * 	<li>Add a <code>WEAPON</code> capability to this <code>Sword</code></li> 
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link harrypotter.actions.Take}
	 * @see {@link harrypotter.Capability}
	 */
	public Sword(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "A sword";
		this.longDescription = "A long, sharp sword.";
		this.hitpoints = 100; // start with a nice powerful, sharp sword
		
		this.addAffordance(new Take(this, m));//add the take affordance so that the Sword can be taken by HPActors
		this.capabilities.add(Capability.WEAPON);// it's a weapon.  
	}
	
	/**
	 * A symbol that is used to represent the Sword on a text based user interface
	 * 
	 * @return 	A String containing a single character.
	 * @see 	{@link harrypotter.HPEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "†";
	}
	
	

}
