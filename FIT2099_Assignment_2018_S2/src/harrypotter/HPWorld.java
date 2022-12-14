package harrypotter;
import java.util.ArrayList;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.actions.*;
import harrypotter.entities.*;
import harrypotter.entities.actors.*;
import harrypotter.spells.*;

/**
 * Class representing a world in the Harry Potter universe. 
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 * 2018-09-01   Converted from Star Wars to Harry Potter universe
 */
public class HPWorld extends World {
	
	/**
	 * <code>HPGrid</code> of this <code>HPWorld</code>
	 */
	private HPGrid myGrid;
	public ArrayList<HPEntity> itemsExist = new ArrayList<HPEntity>(); //list of all items in the grid
	
	public ArrayList<HPEntity> getItems(){
		return itemsExist;
	}
	
	/**The entity manager of the world which keeps track of <code>HPEntities</code> and their <code>HPLocation</code>s*/
	private static final EntityManager<HPEntityInterface, HPLocation> entityManager = new EntityManager<HPEntityInterface, HPLocation>();
	
	/**
	 * Constructor of <code>HPWorld</code>. This will initialize the <code>HPLocationMaker</code>
	 * and the grid.
	 */
	public HPWorld() {
		HPLocation.HPLocationMaker factory = HPLocation.getMaker();
		myGrid = new HPGrid(10,10,factory);
		space = myGrid;
		
	}

	/** 
	 * Returns the height of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int height() {
		return space.getHeight();
	}
	
	/** 
	 * Returns the width of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int width() {
		return space.getWidth();
	}
	
	/**
	/**
	 * Set up the world, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeWorld(MessageRenderer iface) {
		HPLocation loc;
		HPLocation loc1;
		
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("HPWorld (" + col + ", " + row + ")");
				loc.setShortDescription("HPWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		Tunnel tunnel = new Tunnel();
		tunnel.initializeWorld(iface);
		
		loc = tunnel.getGrid().getLocationByCoordinates(0,0);
		loc1 = myGrid.getLocationByCoordinates(7, 4);
		Door door = new Door(loc, loc1,iface);
		door.setShortDescription("a door");
		door.setLongDescription("new world awaits you!");
		door.setSymbol("U");
		Tunnel.getTunnelEntitymanager().setLocation(door, loc);
		entityManager.setLocation(door, loc1);
		door.addAffordance(new Enter(loc,loc1,door, iface));
		
		loc = tunnel.getGrid().getLocationByCoordinates(7,0);
		loc1 = myGrid.getLocationByCoordinates(0, 1);
		Door door2 = new Door(loc1,loc,iface);
		door2.setShortDescription("a door");
		door2.setLongDescription("new world awaits you!");
		door2.setSymbol("U");
		Tunnel.getTunnelEntitymanager().setLocation(door2, loc);
		entityManager.setLocation(door2, loc1);
		door2.addAffordance(new Enter(loc,loc1,door2, iface));

		
		// Hogsmeade
		loc = myGrid.getLocationByCoordinates(0, 0);
		loc.setShortDescription("Hogsmeade 0, 0");
		loc.setSymbol('h');

		loc = myGrid.getLocationByCoordinates(1, 0);
		loc.setShortDescription("Honeyduke's Sweetshop");
		loc.setSymbol('h');

		loc = myGrid.getLocationByCoordinates(2, 0);
		loc.setShortDescription("Hogsmeade 2, 0");
		loc.setSymbol('h');

		loc = myGrid.getLocationByCoordinates(0, 1);
		loc.setShortDescription("The Shrieking Shack");
		loc.setSymbol('S');

		loc = myGrid.getLocationByCoordinates(1, 1);
		loc.setShortDescription("Hogsmeade 1, 1");
		loc.setSymbol('h');

		loc = myGrid.getLocationByCoordinates(2, 1);
		loc.setShortDescription("The Hog's Head");
		loc.setSymbol('P');

		// Quidditch Pitch
		loc = myGrid.getLocationByCoordinates(3, 4);
		loc.setShortDescription("Quidditch Pitch");
		loc.setSymbol('Q');

		// Hogwarts
		loc = myGrid.getLocationByCoordinates(4, 5);
		loc.setShortDescription("Hogwarts 4, 5");
		loc.setSymbol('H');

		loc = myGrid.getLocationByCoordinates(5, 5);
		loc.setShortDescription("Dumbledore's Office");
		loc.setSymbol('D');

		loc = myGrid.getLocationByCoordinates(6, 5);
		loc.setShortDescription("Hogwarts 6, 5");
		loc.setSymbol('H');

		loc = myGrid.getLocationByCoordinates(4, 6);
		loc.setShortDescription("Hogwarts 4, 6");
		loc.setSymbol('H');

		loc = myGrid.getLocationByCoordinates(5, 6);
		loc.setShortDescription("Great Hall");
		loc.setSymbol('G');

		loc = myGrid.getLocationByCoordinates(6, 6);
		loc.setShortDescription("Hogwarts 6, 6");
		loc.setSymbol('H');

		loc = myGrid.getLocationByCoordinates(4, 7);
		loc.setShortDescription("Professor McGonagall's Office");
		loc.setSymbol('M');

		loc = myGrid.getLocationByCoordinates(5, 7);
		loc.setShortDescription("Hogwarts 5, 7");
		loc.setSymbol('H');

		loc = myGrid.getLocationByCoordinates(6, 7);
		loc.setShortDescription("Snape's Office");
		loc.setSymbol('S');

		// Lake
		for (int row = 8; row < 10; row++) {
		for (int col = 3; col < 8; col++) {
			loc = myGrid.getLocationByCoordinates(col, row);
			loc.setLongDescription("Lake (" + col + ", " + row + ")");
			loc.setShortDescription("Lake (" + col + ", " + row + ")");
			loc.setSymbol('L');
		}
	}

		// Hagrid's Hut
		loc = myGrid.getLocationByCoordinates(7, 2);
		loc.setShortDescription("Hagrid's Hut");
		loc.setSymbol('A');

		// Forbidden Forest
		for (int x = 8; x < 10; x++) {
		for (int y = 0; y < 10; y++) {
			loc = myGrid.getLocationByCoordinates(x, y);
			loc.setShortDescription("Forbidden Forest (" + y + ", " + x + ")");
			loc.setSymbol('F');
			for (int tree_count = 1; tree_count < 5; tree_count++) {
				HPEntity tree = new HPEntity(iface);
				tree.setSymbol("T");
				tree.setShortDescription("a tree");
				tree.setLongDescription("an spooky, old tree");
				tree.setHitpoints(40);
				entityManager.setLocation(tree, loc);
			}
		}
		}
		
		// Dumbledore
		loc = myGrid.getLocationByCoordinates(4,  5);
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.WEST, 
                /*CompassBearing.SOUTH,
                CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.SOUTH,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.NORTHWEST, CompassBearing.NORTHWEST*/};
		Dumbledore dumbledore = Dumbledore.getDumbledore(iface, this, patrolmoves);
		dumbledore.learnSpell(new Expelliarmus());
		dumbledore.learnSpell(new Immobulus());
		dumbledore.learnSpell(new AvadaKedavra());
		Sword sword = new Sword(iface); //kz made changes to how dumbledore receives his sword and updated it so that it interacts correctly with expelliarmus
		entityManager.setLocation(dumbledore, loc);
		dumbledore.Inventory.add(sword);
		for (Affordance a : sword.getAffordances()){
			if (a instanceof Take){
				sword.removeAffordance(a);
			}	
		}
		sword.addAffordance(new Leave(sword, iface));
		
		loc = myGrid.getLocationByCoordinates(4,5);	
		
		// Harry
		Player harry = new Player(Team.GOOD, 10000, iface, this, tunnel);
		harry.setShortDescription("Harry");
		harry.setLongDescription("Harry Potter, the boy who lived");
		entityManager.setLocation(harry, loc);
		Wand wand = new Wand(iface);
		harry.Inventory.add(wand);
		for (Affordance a : wand.getAffordances()){
			if (a instanceof Take){
				wand.removeAffordance(a);
			}	
		}
		
		wand.addAffordance(new Leave(wand, iface));	
		wand.capabilities.add(Capability.CASTING);
		//harry.resetMoveCommands(loc);
				
		// A broomstick
		loc = myGrid.getLocationByCoordinates(4,5);
//		Broomstick broomstick= new Broomstick(iface);
//		harry.Inventory.add(broomstick);
//		for (Affordance a : broomstick.getAffordances()){
//			if (a instanceof Take){
//				broomstick.removeAffordance(a);
//			}	
//		}
//		broomstick.addAffordance(new Leave(broomstick, iface));
		harry.resetMoveCommands(loc);
		
		/*
		 * Scatter some other entities and actors around
		 */
		// a dagger
		loc = myGrid.getLocationByCoordinates(3,1);
		HPEntity dagger = new HPEntity(iface);
		dagger.setSymbol("+");
		dagger.setShortDescription("a dagger");
		dagger.setLongDescription("an old, blunt dagger");
		dagger.setHitpoints(10);
		dagger.addAffordance(new Take(dagger, iface));
		dagger.capabilities.add(Capability.WEAPON);
		entityManager.setLocation(dagger, loc);
		itemsExist.add(dagger);

		// a ring
		loc = myGrid.getLocationByCoordinates(1,5);
		HPEntity ring = new HPEntity(iface);
		ring.setShortDescription("a ring");
		ring.setLongDescription("a dull, gold ring, with a runish inscription. Is this supposed to be here?");
		ring.setSymbol("o");
		ring.setHitpoints(100);
		// add a Take affordance to the ring, so that an actor can take it
		ring.addAffordance(new Take(ring, iface));
		entityManager.setLocation(ring, loc);
		itemsExist.add(ring);//mmoh

		
		// an axe
		loc = myGrid.getLocationByCoordinates(2,8);
		HPEntity axe = new HPEntity(iface);
		axe.setShortDescription("an axe");
		axe.setLongDescription("a large, sturdy axe");
		axe.setSymbol("P");
		axe.setHitpoints(200);
		axe.capabilities.add(Capability.WEAPON);
		axe.capabilities.add(Capability.CHOPPER);
		// add a Take affordance to the oil axe, so that an actor can take it
		axe.addAffordance(new Take(axe, iface));
		entityManager.setLocation(axe, loc);
		itemsExist.add(axe);
				
		// Some health potion
		loc = myGrid.getLocationByCoordinates(4,7);
		Potion potion = new Potion(iface);
		entityManager.setLocation(potion, loc);
		potion.addAffordance(new Drink(potion, iface));
		
		loc = myGrid.getLocationByCoordinates(4,8);
		potion = new Potion(iface);
		entityManager.setLocation(potion, loc);
		potion.makeHidden();		// made this health potion hidden
		potion.addAffordance(new Drink(potion, iface));
		
		// Some Death Eaters
		DeathEater deathEater = new DeathEater(10, iface, this);
		deathEater.setSymbol("E");
		loc = myGrid.getLocationByCoordinates(4,3); 
		entityManager.setLocation(deathEater, loc);
		
		deathEater = new DeathEater(10, iface, this);
		deathEater.setSymbol("E");
		loc = myGrid.getLocationByCoordinates(5,2);
		entityManager.setLocation(deathEater, loc);

		// Some Dementor
		Dementor dementor = new Dementor(780, iface, this);
		dementor.setSymbol("Z");
		loc = myGrid.getLocationByCoordinates(3,3);
		entityManager.setLocation(dementor, loc);
		
		dementor = new Dementor(780, iface, this);
		dementor.setSymbol("Z");
		loc = myGrid.getLocationByCoordinates(7,2);
		entityManager.setLocation(dementor, loc);
				
		// Whomping Willow
		loc = myGrid.getLocationByCoordinates(7, 4);
		HPEntity whompingwillow = new HPEntity(iface);
		whompingwillow.setShortDescription("Whomping Willow");
		whompingwillow.setLongDescription("Whomping Willow, a huge, ominous tree");
		whompingwillow.setSymbol("W");
		whompingwillow.setHitpoints(40);
		entityManager.setLocation(whompingwillow, loc);

	}

	/*
	 * Render method was removed from here
	 */
	
	/**
	 * Determine whether a given <code>HPActor a</code> can move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	ram
	 * @param 	a the <code>HPActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canMove(HPActor a, Direction whichDirection) {
		HPLocation where = (HPLocation)entityManager.whereIs(a); // requires a cast for no reason I can discern
		return where.hasExit(whichDirection);
	}
	
	/**
	 * Determine whether a given <code>HPActor a</code> can double move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	Matti
	 * @param 	a the <code>HPActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to double move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canDoubleMove(HPActor a, Direction whichDirection) {
		
		if(this.canMove(a, whichDirection)) {
			this.moveEntity(a, whichDirection);	//move the actor to the new location temporary
			
			if(this.canMove(a, whichDirection)) {
				this.moveEntity(a, CompassBearing.opposite((CompassBearing) whichDirection));	//return the actor to the previous loc
				return true;
			}
			else{
				this.moveEntity(a, CompassBearing.opposite((CompassBearing) whichDirection));	//return the actor to the previous loc
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Accessor for the grid.
	 * 
	 * @author ram
	 * @return the grid
	 */
	public HPGrid getGrid() {
		return myGrid;
	}

	/**
	 * Move an actor in a direction.
	 * 
	 * @author ram
	 * @param a the actor to move
	 * @param whichDirection the direction in which to move the actor
	 */
	public void moveEntity(HPActor a, Direction whichDirection) {
		
		//get the neighboring location in whichDirection
		Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
		
		// Base class unavoidably stores superclass references, so do a checked downcast here
		if (loc instanceof HPLocation)
			//perform the move action by setting the new location to the the neighboring location
			entityManager.setLocation(a, (HPLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
	}

	/**
	 * Returns the <code>Location</code> of a <code>HPEntity</code> in this grid, null if not found.
	 * Wrapper for <code>entityManager.whereIs()</code>.
	 * 
	 * @author 	ram
	 * @param 	e the entity to find
	 * @return 	the <code>Location</code> of that entity, or null if it's not in this grid
	 */
	public Location find(HPEntityInterface e) {
		return entityManager.whereIs(e); //cast and return a HPLocation?
	}

	/**
	 * This is only here for compliance with the abstract base class's interface and is not supposed to be
	 * called.
	 */

	@SuppressWarnings("unchecked")
	public EntityManager<HPEntityInterface, HPLocation> getEntityManager() {
		return entityManager;
	}
	
	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>HPEntities</code> and
	 * <code>HPLocations</code> in <code>HPWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>HPWorld</code>
	 * @param 	e the entity to find
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<HPEntityInterface, HPLocation> myEntitymanager() {
		if(Player.isPlayerInTunnel()){
			return Tunnel.getTunnelEntitymanager();
		} else {
			return HPWorld.entityManager;
		}
	}

	/**
	 * Provides a method to return the appropriate <code>EntityManager</code>, 
	 * depending on the type of <code>HPEntityInterface</code>. 
	 * 
	 * @param <code>HPEntityInterface</code> e that represents the entity requesting the EntityManager
	 * 
	 * @return 	the <code>EntityManager</code> of where the <code>Player</code> or <code>HPEntityInterface</code> is
	 * @see 	{@link #entityManager}
	 */
	
	public static EntityManager<HPEntityInterface, HPLocation> myEntitymanager(HPEntityInterface e) {
		if(Player.isPlayerInTunnel() && (e instanceof Player)){
			return Tunnel.getTunnelEntitymanager();
		} else {
			return HPWorld.entityManager;
		}
	}
	
	
	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>HPEntities</code> and
	 * <code>HPLocations</code> in <code>HPWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>HPWorld</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<HPEntityInterface, HPLocation> getEntitymanager() {
		return entityManager;
	}
	
	/**
	 * Makes all entities in HPWorld and Tunnel tick, and carry out their actions 
	 * 
	 * @param <code>HPEntityInterface</code> e that represents the entity requesting the EntityManager
	 */
	@Override
	public void tick() {
		entityManager.tick();
		Tunnel.getTunnelEntitymanager().tick();
	}
}
