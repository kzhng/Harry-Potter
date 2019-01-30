
package harrypotter.actions;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActor;
import harrypotter.HPWorld;
import harrypotter.HPAffordance;
import harrypotter.HPEntityInterface;

public class Leave extends HPAffordance {
	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * 
	 * 
	 * @param theTarget a <code>HPEntity</code> that is being dropped
	 * @param m the message renderer to display messages
	 */
	public Leave(HPEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
		priority = 1;
	}
	/**
	 * @return boolean 
	 * 		 if the <code>HPActor</code> is allowed to Leave the item
	 */
	 public boolean canDo(HPActor a){
			if (a.isFrozen()) {
				a.unFreeze();
				return false;
			}
		 
		 return a.getItemCarried()!=null;
	 }
	 /**
	  * removes Leave affordance from the item
	  * sets take affordance to the item
	  * @param a HPActor 
	  * 		the actor who drops the item he is carrying
	  *
	  */
	 public void act(HPActor a){
		 if (target instanceof HPEntityInterface){
			 HPEntityInterface theItem = (HPEntityInterface) target;
			 a.setItemCarried(null);
			 HPWorld.myEntitymanager(a).setLocation(theItem, HPWorld.myEntitymanager(a).whereIs(a));
			 target.removeAffordance(this);
			 target.addAffordance(new Take(theItem, messageRenderer));
		 }
	 }
	 
	 /**
	  * @return String 
	  * 	outputs the action description
	  */
	 
	 public String getDescription(){
		 return "leave "+target.getShortDescription();
	 }

}
