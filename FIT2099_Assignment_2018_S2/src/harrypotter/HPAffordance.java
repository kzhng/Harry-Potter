package harrypotter;

import edu.monash.fit2099.simulator.matter.Actor;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * Class that represents <code>Affordances</code> in the <code>harrypotter</code> world.
 * <p> 
 * This class implements such methods in <code>HPActionInterface</code> as can reasonably
 * be written at this stage, to minimize the amount of work that needs to be done to add new <code>Affordances</code>.
 * 
 * @author 	ram
 * @see 	edu.monash.fit2099.simulator.matter.Affordance
 * @see 	harrypotter.HPActionInterface
 */
/*
 * Change log
 * 2017-02-04 Added conditions in tick method to avoid dead actors from performing actions (asel)
 * 2017-02-20: Removed the redundant compareTo method. The compareTo method is already implemented in the 
 * 			   ActionInterface. (asel)
 */
public abstract class HPAffordance extends Affordance implements HPActionInterface {

	/**
	 * Constructor for the <code>HPAffordance</code>. 
	 * This would initialize the <code>MessageRenderer</code> and the target for this <code>HPAffordance</code>.
	 * 
	 * @param theTarget a sub class of <code>HPEntityInterface</code> on which the action needs to be performed on
	 * @param m the <code>messageRenderer</code> to display messages for this <code>HPAffordance</code>.
	 */
	public HPAffordance(HPEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}

	/**
	 * Returns if or not this <code>HPAffordance</code> is a move command.
	 * By default, <code>HPAffordances</code> are not move commands hence this
	 * method will always return false.  
	 * <p>
	 * This method needs to be overridden in any <code>HPAffordance</code> is a move command 
	 * (i.e. must be overridden to return true if the action can potentially change the location
	 * of a <code>HPEntity</code> or <code>HPActor</code>).
	 * 
	 * @return false
	 */
	@Override
	public boolean isMoveCommand() {
		return false;
	}


	@Override
	public int getDuration() {
		return 1;
	}
	
	
	/**
	 * Accessor for the target of this <code>HPAffordance</code>. The target is a subclass of 
	 * <code>HPEntityInterface</code> on which the action is performed on.
	 * <p>
	 * This method checks that the target conforms to the <code>HPEntityInterface</code> and
	 * downcasts it to a HPEntityInterface and returns it if so; otherwise returns null.
	 * 
	 * @return the current target downcasted to <code>HPEntityInterface</code>
	 * 		   or null if the current target isn't a <code>HPEntityInterface</code>
	 */
	protected HPEntityInterface getTarget() {
		if (target instanceof HPEntityInterface)
			//return the downcasted target
			return (HPEntityInterface) target;
		
		return null;//if the target is not a HPEntityInterface
	}

	/**
	 * Execute this <code>HPAffordance</code> by calling the <code>act()</code> method with an <code>Actor a</code>
	 * to perform the <code>HPAffordance</code>.
	 * <p>
	 * This method acts as a wrapper for the <code>act()</code> method that downcasts its parameter 
	 * to minimize dangerous downcasting in subclasses.
	 * <p>
	 * This method only calls the <code>act()</code> method if and only,
	 * <ul>
	 * 	<li>The sub class of <code>Actor a</code> is a <code>HPActor</code>, and</li>
	 * 	<li>The <code>HPActor</code> is not dead</li>
	 * </ul>
	 * else this method does nothing.
	 * 
	 * @param 	a a sub class of <code>Actor</code>
	 * @see 	#act(HPActor)
	 * @see 	HPActor#isDead()
	 */
	@Override
	public void execute(Actor<?> actor) {
		if (actor instanceof HPActor && !((HPActor)actor).isDead())
			act((HPActor) actor);

	}
	
}
