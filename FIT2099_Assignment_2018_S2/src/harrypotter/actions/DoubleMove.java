package harrypotter.actions;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
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
