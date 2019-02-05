package harrypotter.actions;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPActor;
import harrypotter.HPWorld;

public class DoubleMove extends Move{

	/**
	 * Constructor for <code>DoubleMove</code> class. Will initialize the direction and the world for the <code>DoubleMove</code>.
	 * 
	 * @param d the <code>Direction</code> in which the Entity is supposed to double move
	 * @param m <code>MessageRenderer</code> to display messages
	 * @param world the world in which the <code>DoubleMove</code> action needs to happen
	 */
	public DoubleMove(Direction d, MessageRenderer m, HPWorld world) {
		super(d, m, world);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Returns if or not a <code>HPActor a</code> can perform a <code>Move</code> command.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not dead and is not frozen and has an item with capability DOUBLESPEED.
	 * <p>
	 * We assume that actors don't get movement commands attached to them unless they can
	 * in fact move in the appropriate direction.  If this changes, then this method will
	 * need to be altered or overridden.
	 * 
	 * @author 	ram
	 * @param 	a the <code>HPActor</code> doing the moving
	 * @return 	true if and only if <code>a</code> is not dead, false otherwise.
	 * @see 	{@link harrypotter.HPActor#isDead()}
	 */
	@Override
	public boolean canDo(HPActor a) {
		return super.canDo(a) && a.Inventory.getHighestItemWithCapability(Capability.DOUBLESPEED) != null;
	}
	
	/**
	 * Perform the <code>DoubleMove</code> action.
	 * <p>
	 * If it is possible for <code>HPActor a</code> to double move in the given direction, tell the world to double move them
	 * and then reset <code>a</code>'s move commands to take into account a possible new set of available double <code>Moves</code>. 
	 * If it is not possible for <code>a</code> to double move in that direction, this method does nothing.
	 * <p>
	 * This method will only be called if the <code>HPActor a</code> is alive
	 * 
	 * @param 	a the <code>HPActor</code> who is moving
	 */	
	@Override
	public void act(HPActor a) {		
		if (world.canDoubleMove(a, whichDirection)) {
			world.moveEntity(a, whichDirection);
			world.moveEntity(a, whichDirection);
			a.resetMoveCommands(world.find(a));//reset the new possible set of moves based on the new location of the entity
			messageRenderer.render(a.getShortDescription() + " is double moving " + whichDirection);
		}				
	}
	
	/**
	 * Returns a String describing this <code>Move</code>, suitable for display to the user.
	 * 
	 * @return String comprising "move " and the direction.
	 */
	@Override
	public String getDescription() {
		return "double move " + whichDirection.toString();
	}
	
}
