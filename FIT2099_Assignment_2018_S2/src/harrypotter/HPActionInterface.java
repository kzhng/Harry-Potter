package harrypotter;

import edu.monash.fit2099.simulator.matter.ActionInterface;

/**
 * Interface that allows <code>HPAction</code> and <code>HPAffordance</code> to have a common ancestor (and thus
 * be stored in the same structures) despite one extending <code>Action</code> and the other extending <code>Affordance</code>.
 * 
 * @author ram
 */
public interface HPActionInterface extends ActionInterface {
	
	/**Returns if or not the action is a move command. Returns true if so, false otherwise*/
	public boolean isMoveCommand();
	
	/**
	 * Returns if the given <code>HPActor a</code> can perform this action
	 * 
	 * @param 	a the <code>HPActor</code> being queried
	 * @return	true if <code>a</code> can perform this action, false otherwise
	 */	
	public boolean canDo(HPActor a);
	
	/**
	 * The method that defines what needs to be performed when this action is performed.
	 * 
	 * @param a the <code>HPActor</code> who performs this action.
	 */
	public void act(HPActor a);	
}
