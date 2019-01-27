/**
 * Abstract class that represents a <code>Spell</code> that can be casted in the HP world.
 * Each spell has a name, description, and spell afterEffect strings.
 * Each spell also has a spellEffect method, which actually carries out the actual effects of the <code>Spell.</code>
 * 
 * @author Shakeel Rafi
 * 
 */

package harrypotter;

public abstract class Spell {
	private String name;
	private String description;
	private String afterEffect;
	
	/**
	 * Constructor for <code>Spell</code> class. Will initialize the name, description and generic result (afterEffect) of <code>Spell</code>.
	 * 
	 * @param newName the name of the Spell
	 * @param newDescription a description of the Spell
	 * @param newAfterEffect a String representing the after-effect of the Spell (i.e. " has dropped his item!")
	 */
	public Spell(String newName, String newDescription, String newAfterEffect){
		name = newName;
		description = newDescription;
		afterEffect = newAfterEffect;
	}
	
	/**
	 * Abstract method that contains the effect of the <code>Spell</code>. All Spells need to have this method to allow Cast action to cast it.
	 * 
	 * @param theTarget the target <code>HPEntityInterface</code> for the Spell to be casted on
	 */
	public abstract void spellEffect(HPEntityInterface theTarget);

	/**
	 * Returns the name and description of the Spell.
	 * 
	 * @return a String of a short description of the <code>Spell</code>, suitable for display to the user in the interface
	 */
	public String getDescription(){
		return name + ": " + description;
	}
	
	/**
	 * Returns the generic result of the Spell.
	 * 
	 * @return a String of the result of a <code>Spell</code>, suitable for display to the user in the interface
	 */
	public String getAfterEffect(){
		return afterEffect;
	}
	
	/**
	 * Returns a boolean if the Spell affects a <code>HPActor</code>
	 * 
	 * @return true by the default. Needs to be overwritten by Spells that affect <code>HPActor</code>s.
	 */
	public boolean affectActor(){
		return true;
	}
}
