
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
		 
		 return a.carriesItems(); 
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
			 a.removeFromInventory(theItem);
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
		 return "leave "+ target.getShortDescription();
	 }

}

//public void act(HPActor a){
//	HPEntityInterface selectedItem = HPGridController.getChosenItem(a);
//	this.target = selectedItem;
//	 if (selectedItem instanceof HPEntityInterface){
//		 //HPEntityInterface theItem = (HPEntityInterface) target;
//		 a.removeFromInventory(selectedItem);
//		 HPWorld.myEntitymanager(a).setLocation(selectedItem, HPWorld.myEntitymanager(a).whereIs(a));
//		 selectedItem.removeAffordance(this);
//		 selectedItem.addAffordance(new Take(selectedItem, messageRenderer));
//	 }
//}
