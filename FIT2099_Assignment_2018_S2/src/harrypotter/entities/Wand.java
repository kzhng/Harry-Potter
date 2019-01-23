package harrypotter.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPEntity;
import harrypotter.actions.Take;

/**
 * A Wand
 * 
 */

public class Wand extends HPEntity {
	
	/**
	 * Constructor for the <code>Wand</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Wand</code></li>
	 * 	<li>Set the short description of this <code>Wand</code>>
	 * 	<li>Set the long description of this <code>Wand</code> 
	 * 	<li>Add a <code>Take</code> affordance to this <code>Wand</code> so it can be taken</li> 
	 * 	<li>Add a <code>CASTING</code> capability to this <code>Wand</code></li> 
	 * </ul>
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link harrypotter.actions.Take}
	 * @see {@link harrypotter.Capability}
	 */
	public Wand(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "wand";
		this.longDescription = "A wooden, carved wand.";
		this.hitpoints = 20; // a wand is fragile!
		
		this.addAffordance(new Take(this, m));//add the take affordance so that the Wand can be taken by HPActors
			}
	
	/**
	 * A symbol that is used to represent the Wand on a text based user interface
	 * 
	 * @return 	A String containing a single character.
	 * @see 	{@link harrypotter.HPEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "w";
	}
}
