package harrypotter;

import java.util.HashSet;

import edu.monash.fit2099.simulator.matter.Entity;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * Class that represents inanimate objects in the Harry Potter world. Objects that cannot move for example trees.
 * 
 * @author 	ram
 * @see 	edu.monash.fit2099.simulator.matter.Entity
 * @see 	HPEntityInterface
 */

public class HPEntity extends Entity implements HPEntityInterface {
	
	/**A string symbol that represents this <code>HPEntity</code>, suitable for display*/
	private String symbol;
	
	/**A set of <code>Capabilities</code> of this <code>HPEntity</code>*/
	protected HashSet<Capability> capabilities;
	
	/**The amount of <code>hitpoints</code> of this <code>HPEntity</code>.*/
	protected int hitpoints = 0; // Not all non-actor entities will make use of this

	/**
	 * Constructor for this <code>HPEntity</code>. Will initialize this <code>HPEntity</code>'s
	 * <code>messageRenderer</code> and set of capabilities.
	 * 
	 * @param m the <code>messageRenderer</code> to display messages
	 */
	protected HPEntity(MessageRenderer m) {
		super(m);
		capabilities = new HashSet<Capability>();
	}


	/**
	 * Returns a String symbol representing this <code>HPEntity</code>.
	 * 
	 * @return 	symbol a String that represents this <code>HPEntity</code>
	 * @see 	#symbol
	 */
	@Override
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Sets the symbol of this <code>HPEntity</code> with a new string <code>s</code>.
	 * 
	 * @param 	s the new string symbol for this <code>HPEntity</code>
	 * @see 	#symbol 
	 */
	@Override
	public void setSymbol(String s) {
		symbol = s;
	}

	@Override
	public boolean hasCapability(Capability c) {
		return capabilities.contains(c);
	}

	@Override
	public int getHitpoints() {
		return hitpoints;
	}
	
	/**
	 * Sets the <code>hitpoints</code> of this <code>HPEntity</code>
	 * to a new number of hit points <code>p</code>.
	 * 
	 * @param p the new number of <code>hitpoints</code>
	 */
	public void setHitpoints(int p) {
		hitpoints = p;
	}
	
	@Override
	public void takeDamage(int damage) {
		//Precondition 1: Ensure that the damage is not negative
		if (damage < 0) {
			throw new IllegalArgumentException("damage on HPEntity must not be negative");
		}
		this.hitpoints -= damage;
	}
	
}
