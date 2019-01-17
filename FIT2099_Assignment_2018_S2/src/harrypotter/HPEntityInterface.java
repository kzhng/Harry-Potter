package harrypotter;

import edu.monash.fit2099.simulator.matter.EntityInterface;

/**
 * All <code>Entities</code> and <code>Actors</code> in the harrypotter client package should implement this interface.
 * 
 * It allows them to be managed by the <code>EntityManager</code>.
 * 
 * @author ram
 * @see	edu.monash.fit2099.simulator.matter.EntityInterface
 * @see edu.monash.fit2099.simulator.matter.EntityManager
 */
public interface HPEntityInterface extends EntityInterface {

	/**
	 * Returns a string symbol that represents this <code>HPEntity</code> or <code>HPActor</code>.
	 * <p>
	 * The Views use this method to obtain the symbols that are used to query for resources(images) and for display.
	 *  
	 * @return a String representing the <code>HPEntity</code> or <code>HPActor</code>.
	 * 
	 * @see 	harrypotter.interfaces.HPGridTextInterface
	 */
	public abstract String getSymbol();
	
	/**
	 * Sets the symbol of this <code>HPEntity</code> or <code>HPActor</code> with a new string <code>string</code>.
	 * <p>
	 * Although not a must the new symbol is preferably, 
	 * <ul>
	 * 	<li>single character</li>
	 * 	<li>unique for each <code>HPEntity</code> or <code>HPActor</code></li>
	 * </ul>
	 * <p>
	 * The Views use this method to obtain the symbols that are used to query for resources(images) and for display.
	 * 
	 * @param string a string to represent this <code>HPEntity</code> or <code>HPActor</code>
	 */
	public abstract void setSymbol(String string);
	
	/**
	 * Returns true if this <code>HPEntity</code> or <code>HPActor</code> has the given 
	 * capability <code>c</code>, false otherwise.
	 * 
	 * @param 	c the <code>Capability</code> to search for
	 * @return	true if this <code>Capability c</code> is manifested, false otherwise
	 * @see 	harrypotter.Capability
	 */
	public boolean hasCapability(Capability c);
	
	/**
	 * Returns the hitpoints of this <code>HPEntity</code> or <code>HPActor</code>.
	 * 
	 * @return the amount of hitpoints
	 */
	public int getHitpoints();
	
	/**
	 * Method that reduces the <code>hitpoints</code> to insist damage on of this 
	 * <code>HPEntity</code> or <code>HPActor</code>.
	 * 
	 * @param damage the amount of <code>hitpoints</code> to be reduced
	 * @pre <code>damage</code> should be greater than or equal to zero to avoid any increase in the number of <code>hitpoints</code>
	 */
	public void takeDamage(int damage);

}
