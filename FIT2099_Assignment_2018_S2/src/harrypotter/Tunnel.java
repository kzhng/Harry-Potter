package harrypotter;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * This class represents a Tunnel, which a subclass of HPWorld. It features its own HPGrid to represent its layout 
 * and EntityManager to manage the entities in the Tunnel.
 * 
 * @author Shakeel 
 */

public class Tunnel extends HPWorld {
	
	private HPGrid tunnelGrid;		// represents Tunnel's HPGrid - does not use HPWorld's myGrid
	private static final EntityManager<HPEntityInterface, HPLocation> tunnelEntityManager = new EntityManager<HPEntityInterface, HPLocation>();
	// Tunnel's personal EntityManager to manage the entities in its location
	// It is static to allow other classes to get the tunnel's entity manager

	/**
	 * Constructor for <code>Tunnel</code> class. Will initialize the HPGrid (8 columns by 1 row), and its LocationContainer to be the HPGrid.
	 * 
	 */
	public Tunnel() {
		HPLocation.HPLocationMaker factory = HPLocation.getMaker();
		tunnelGrid = new HPGrid(8,1,factory);
		this.space = tunnelGrid;
	}
	

	/**
	 * Overriden method to set up the Tunnel, setting descriptions for locations and placing items and actors
	 * on its grid.
	 * 
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	
	@Override
	public void initializeWorld(MessageRenderer iface) {
		HPLocation loc;
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = ((Grid<HPLocation>) space).getLocationByCoordinates(col, row);
				loc.setLongDescription("Tunnel (" + col + ", " + row + ")");
				loc.setShortDescription("Tunnel (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
	}
	
	/**
	 * Overriden accessor for Tunnel's HPGrid.
	 * 
	 * @return HPGrid of Tunnel
	 */
	@Override
	public HPGrid getGrid(){
		return tunnelGrid;
	}
	
	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>HPEntities</code> and
	 * <code>HPLocations</code> in <code>Tunnel</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>Tunnel</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<HPEntityInterface, HPLocation> getTunnelEntitymanager() {
		return tunnelEntityManager;
	}

}
