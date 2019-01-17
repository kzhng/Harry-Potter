package harrypotter;

import edu.monash.fit2099.simulator.matter.Action;
import edu.monash.fit2099.simulator.matter.Actor;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * Base class for all intransitive commands/<code>Actions</code> in the harrypotter world. 
 * This class is an implementation of the <code>HPActionInterface</code>
 * 
 * @author 	ram
 * @see 	edu.monash.fit2099.simulator.matter.Action
 * @see 	HPActionInterface
 */
/*
 * Change log
 * 2017-02-08: The execute will only call the act of the HPActor is alive (asel) 
 * 2017-02-20: Removed the redundant compareTo method. The compareTo method is already implemented in the 
 * 			   ActionInterface. (asel)
 */
public abstract class HPAction extends Action implements HPActionInterface {
	
	/**The entity manager of the world which keeps track of <code>HPEntities</code> and their <code>HPLocation</code>s*/
	private static final EntityManager<HPEntityInterface, HPLocation> entityManager = HPWorld.getEntitymanager();

	/**
	 * Constructor for the <code>HPAction</code>. 
	 * Will initialize the message renderer for this <code>HPAction</code>
	 * 
	 * @param m the <code>MessageRenderer</code> to display messages.
	 */
	public HPAction(MessageRenderer m) {
		super(m);
	}

	/**
	 * Returns if or not this <code>HPAction</code> is a move command.
	 * By default, <code>HPAction</code>s are not move commands hence this
	 * method will always return false.  
	 * <p>
	 * This method needs to be overridden in any <code>HPAction</code> is a move command 
	 * (i.e. must be overridden to return true if the action can potentially change the location
	 * of a <code>HPEntity</code> or <code>HPActor</code>).
	 * 
	 * @return false
	 */
	@Override
	public boolean isMoveCommand() {
		return false;
	}
	
	/**
	 * Execute this <code>HPAction</code> by calling the <code>act()</code> method with an <code>Actor a</code>
	 * to perform the <code>HPAction</code>.
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
	public void execute(Actor<?> a) {
		if (a instanceof HPActor && !((HPActor)a).isDead()) 
			act((HPActor) a);

	}

	@Override
	public abstract void act(HPActor a);
	

	/**
	 * Returns the <code>EntityManager</code> which keeps track of <code>HPEntities</code> and their <code>HPLocation</code>s
	 * 
	 * @return 	entityManager of the <code>World</code> 
	 * @see 	#entityManager
	 */
	public static EntityManager<HPEntityInterface, HPLocation> getEntitymanager() {
		return entityManager;
	}


}
