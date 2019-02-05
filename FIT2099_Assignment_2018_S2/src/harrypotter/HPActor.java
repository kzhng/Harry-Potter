/**
 * Class that represents an Actor (i.e. something that can perform actions) in the harrypotter world.
 * 
 * @author ram
 * 
 * @modified 20130414 dsquire
 * 	- changed constructor so that affordances that all HPActors must have can be added
 * 	- changed team to be an enum rather than a string
 */
/*
 * Change log
 * 2017-01-20: Added missing Javadocs and improved comments (asel)
 * 2017-02-08: Removed the removeEventsMethod as it's no longer required.
 * 			   Removed the tick and act methods for HPActor as they are never called
 */
package harrypotter;

import java.util.ArrayList;
import java.util.HashSet;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.Actor;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.actions.Attack;
import harrypotter.actions.Cast;
import harrypotter.actions.DoubleMove;
import harrypotter.actions.Move;
import harrypotter.actions.Give;

public abstract class HPActor extends Actor<HPActionInterface> implements HPEntityInterface {
	
	/**the <code>Team</code> to which this <code>HPActor</code> belongs to**/
	private Team team;
	
	/**The amount of <code>hitpoints</code> of this actor. If the hitpoints are zero or less this <code>Actor</code> is dead*/
	private int hitpoints;
	
	/**The <code>maxHitpoints</code> of this actor. Health cannot increase beyond this maximum, when initialized its the same as hitpoints*/
	private int maxHitpoints;
	
	/**The <code>frozenTime</code> of this actor. The time frozen cannot be less than zero, and when initialized it is zero. */
	private int frozenTime;

	/**The world this <code>HPActor</code> belongs to.*/
	protected HPWorld world;
	
	/**Scheduler to schedule this <code>HPActor</code>'s events*/
	protected static Scheduler scheduler;
	
	/**If or not this <code>HPActor</code> is human controlled. <code>HPActor</code>s are not human controlled by default*/
	protected boolean humanControlled = false;
	
	/**A string symbol that represents this <code>HPActor</code>, suitable for display*/
	private String symbol;
	
	/**A set of <code>Capabilities</code> of this <code>HPActor</code>*/
	protected HashSet<Capability> capabilities;
	
	/**An ArrayList of <code>Spells</code> known to this <code>HPActor</code>, has to be learned when populating the HPWorld*/
	private ArrayList<Spell> knownSpells;
	
	/** An ArrayList of <code>HPEntity</code> seen by the <code>HPctor</code>**/
	private ArrayList<HPEntity> seenItems;
	
	/** An ArrayList of <code>HPEntityInterface</code> carried by the <code>HPctor</code>**/
	public harrypotter.Inventory Inventory;
	
	/** An <code>integer</code> that specifies inventory size, it is 1 if the actor doesn't have an INventory capability **/
	protected int InventorySize = 1;
	
	/**
	 * Constructor for the <code>HPActor</code>.
	 * <p>
	 * The constructor initializes the <code>actions</code> list of this <code>HPActor</code>.
	 * <p>
	 * By default,
	 * <ul>
	 * 	<li>All <code>HPActor</code>s can be attacked.</li>
	 * 	<li>Have their symbol set to '@'</li>
	 * </ul>
	 * 
	 * @param 	team to which this <code>HPActor</code> belongs to
	 * @param 	hitpoints initial hitpoints of this <code>HPActor</code> to start with
	 * @param 	m	message renderer for this <code>HPActor</code> to display messages
	 * @param 	world the <code>World</code> to which <code>HPActor</code> belongs to
	 * 
	 * @see 	#team
	 * @see 	#hitpoints
	 * @see 	#world
	 * @see 	harrypotter.actions.Attack
	 */
	public HPActor(Team team, int hitpoints, MessageRenderer m, HPWorld world) {
		super(m);
		actions = new HashSet<HPActionInterface>();
		this.team = team;
		this.hitpoints = hitpoints;
		this.maxHitpoints = hitpoints;
		this.world = world;
		this.symbol = "@";
		this.knownSpells = new ArrayList<Spell>();
		this.seenItems = new ArrayList<HPEntity>();		
		this.Inventory = new Inventory(InventorySize);
		this.capabilities = new HashSet<Capability>();
				
		//HPActors are given the Attack affordance hence they can be attacked
		HPAffordance attack = new Attack(this, m);
		this.addAffordance(attack);
		
		//HPActors are given the Cast affordance hence they can be casted on
		HPAffordance cast = new Cast(this, null, m, true);	
		this.addAffordance(cast);
		
		//HPActors are given the give affordance hence they can give to aother actors
		HPAffordance give = new Give(this, m);
		this.addAffordance(give);

	}
	
	/**
	 * Sets the <code>scheduler</code> of this <code>HPActor</code> to a new <code>Scheduler s</code>
	 * 
	 * @param	s the new <code>Scheduler</code> of this <code>HPActor</code> 
	 * @see 	#scheduler
	 */
	public static void setScheduler(Scheduler s) {
		scheduler = s;
	}
	
	/**
	 * Returns the team to which this <code>HPActor</code> belongs to.
	 * <p>
	 * Useful in comparing the teams different <code>HPActor</code> belong to.
	 * 
	 * @return 	the team of this <code>HPActor</code>
	 * @see 	#team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Returns the hit points of this <code>HPActor</code>.
	 * 
	 * @return 	the hit points of this <code>HPActor</code> 
	 * @see 	#hitpoints
	 * @see 	#isDead()
	 */
	@Override
	public int getHitpoints() {
		return hitpoints;
	}

	/**
	 * @param sets hitpoints to this <code>HPActor</code> 
	 */
	private void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}
	
	/**
	 * adds hitpoints to this <code>HPActor</code> 
	 * @param the hit points from the health potion
	 */	
	public void addHitpoints(int potionHitpoints) {
		//Precondition 1: Ensure the added hitpoints is not negative. Negative hitpoints could decrease the HPActor's hitpoints
		assert (potionHitpoints >= 0)	:"damage on HPActor must not be negative";
		
		this.setHitpoints(this.hitpoints + potionHitpoints);
		
		if(this.hitpoints > this.maxHitpoints) {
			this.setHitpoints(maxHitpoints);
		}
	}

	
	/**
	 * Returns an ArrayList containing this Actor's available Actions, including the Affordances of items
	 * that the Actor is holding.
	 * 
	 * @author ram
	 */
	public ArrayList<HPActionInterface> getActions() {
		ArrayList<HPActionInterface> actionList = super.getActions();
		
		//If the HobbitActor is carrying anything, look for its affordances and add them to the list
		for (HPEntityInterface item : this.Inventory.getItemsCarried()) {
			for (Affordance aff : item.getAffordances())
				if (aff instanceof HPAffordance)
				actionList.add((HPAffordance)aff);
		}
		return actionList;
	}
	
//	/**
//	 * Returns the items carried by this <code>HPActor</code>. 
//	 * <p>
//	 * This method only returns the reference of the items carried 
//	 * and does not remove items held from this <code>HPActor</code>.
//	 * <p>
//	 * If this <code>HPActor</code> is not carrying any item this method will return null.
//	 * 
//	 * @return 	the items carried by this <code>HPActor</code> or null if no item is held by this <code>HPActor</code>
//	 * @see 	#Inventory
//	 */
//	@SuppressWarnings("unchecked")
//	public ArrayList<HPEntityInterface> getItemsCarried() {
//		return (ArrayList<HPEntityInterface>) this.Inventory.clone();		//cloned items, due to privacy risk
//	}
//	/**
//	 * 
//	 * @return true if the actor carries at least one item
//	 */
//	public boolean carriesItems() {
//		return this.Inventory.size()>=1;
//	}
//	
//	/**
//	 * @return true if the actor's inventory is not full
//	 */
//	public boolean inventoryNotFull() {
//		assert this.InventorySize > 0 : "Inventory size must be a positive integer";
//		return (this.Inventory.size()<InventorySize);		
//	}
//
//	/**
//	 * returns an item for the requested capability which has the highest Hitpoints or null if no such item exist
//	 * <p>
//	 * this method does not remove the item requested
//	 * </p>
//	 * @param capability
//	 * @return returns an item for the requested capability which has the highest Hitpoints or null if no such item exist
//	 */
//	public HPEntityInterface getHighestItemWithCapability(Capability capability) {
//		ArrayList<HPEntityInterface> items = this.getItemsWithCapability(capability);
//		if ( items==null )
//				return null;
//		int highestCap = 0;
//		for (int i = 0; i < items.size(); i++) {			
//			if(items.get(i).getHitpoints()>items.get(highestCap).getHitpoints())
//				highestCap = i;			
//		}		
//		return items.get(highestCap);
//	}
//	
//	/**
//	 * returns items for the requested capability or null if no such items exist
//	 * <p>
//	 * this method does not remove the items requested
//	 * </p>
//	 * @param capability
//	 * @return returns an arraylist of items for the requested capability or null if no such items exist
//	 */
//	public ArrayList<HPEntityInterface> getItemsWithCapability(Capability capability) {
//		ArrayList<HPEntityInterface> items = new ArrayList<HPEntityInterface>();
//		for (HPEntityInterface item : this.Inventory) {
//			if(item.hasCapability(capability))
//				items.add(item);			
//		}
//		if (items.size() == 0)
//				return null;				
//		return items;
//	}
//	
//	/**
//	 * Adds an <code>item</code> to this <code>HPActor</code>'s
//	 * <code>Inventory</code>
//	 * <p>
//	 * This method will add an item to this <code>HPActor</code>'s
//	 * <code>Inventory</code>, it accepts regardless if the same item already exist in the inventory.
//	 * furthermore if this an item is added when the inventory is full, this method will do nothing
//	 * </p>
//	 * @param 	item to be added from the inventory
//	 * @see 	#Inventory
//	 */
//	
//	public void addToInventory(HPEntityInterface item) {	//mh what if null is added, need to fix this
//		if(this.inventoryNotFull() && item!=null) {		
//			this.Inventory.add(item);
//		}
//		return;
//	}
//	
//	/**
//	 * Remove the requested item form this <code>HPActor</code>'s <code>inventory</code>  
//	 * <p>
//	 * This method will remove the requested item this <code>HPActor</code>'s <code>inventory</code> only if the <code>HPActor</code>
//	 * is carrying the requested item, otherwise it does noting 
//	 * 
//	 * @param 	item to be removed from the inventory
//	 * @see 	#Inventory
//	 */
//	
//	public void removeFromInventory(HPEntityInterface item) {
//		if(this.Inventory.contains(item)) {
//			this.Inventory.remove(item);
//		}
//		return;
//	}
	
	/**
	 * Sets the team of this <code>HPActor</code> to a new team <code>team</code>.
	 * <p>
	 * Useful when the <code>HPActor</code>'s team needs to change dynamically during the simulation.
	 * For example, a bite from an evil actor makes a good actor bad.
	 *
	 * @param 	team the new team of this <code>HPActor</code>
	 * @see 	#team
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Method insists damage on this <code>HPActor</code> by reducing a 
	 * certain amount of <code>damage</code> from this <code>HPActor</code>'s <code>hitpoints</code>
	 * 
	 * @param 	damage the amount of <code>hitpoints</code> to be reduced
	 * @pre 	<code>damage</code> should not be negative
	 */
	@Override
	public void takeDamage(int damage) {
		//Precondition 1: Ensure the damage is not negative. Negative damage could increase the HPActor's hitpoints
		assert (damage >= 0)	:"damage on HPActor must not be negative";
		this.hitpoints -= damage;
	}

	
	/**
	 * Reduces the <code>frozenTime</code> of this <code>HPActor</code>.
	 * 
	 * @see 	#frozenTime
	 * @see 	#isFrozen()
	 * @see 	#freeze(int)
	 */
	public void unFreeze() {
		//PRECONDITION: 
		assert (frozenTime >= 1) : "freeze time on HPActor must be positive to unfreeze";
		this.frozenTime -= 1;
	}
	
	
	/**
	 * Returns true if this <code>HPActor</code> is dead, false otherwise.
	 * <p>
	 * A <code>HPActor</code> is dead when it's <code>hitpoints</code> are less than or equal to zero (0)
	 *
	 * @author 	ram
	 * @return 	true if and only if this <code>HPActor</code> is dead, false otherwise
	 * @see 	#hitpoints
	 */
	public boolean isDead() {
		return hitpoints <= 0;
	}
	
	/**
	 * Returns true if this <code>HPActor</code> is frozen, false otherwise.
	 * <p>
	 * A <code>HPActor</code> is frozen when it's <code>frozenTime</code> is greater than zero.
	 *
	 * @author 	Kerry Zheng
	 * @return 	true if and only if this <code>HPActor</code> is frozen, false otherwise
	 * @see 	#frozenTime
	 */
	public boolean isFrozen() {
		return frozenTime > 0;
	}
	
	@Override
	public String getSymbol() {
		return symbol;
	}
	

	@Override
	public void setSymbol(String s) {
		symbol = s;
	}
	
	/**
	 * Returns if or not this <code>HPActor</code> is human controlled.
	 * <p>
	 * Human controlled <code>HPActors</code>' <code>HPActions</code> are selected by the user as commands from the Views.
	 * 
	 * @return 	true if the <code>HPActor</code> is controlled by a human, false otherwise
	 * @see 	#humanControlled
	 */
	public boolean isHumanControlled() {
		return humanControlled;
	}
	

	@Override
	public boolean hasCapability(Capability c) {
		return capabilities.contains(c);
	}
	
	/**
	 * This method will poll this <code>HPActor</code>'s current <code>Location loc</code>
	 * to find potential exits, and replaces all the instances of <code>Move</code>
	 * in this <code>HPActor</code>'s command set with <code>Moves</code> to the new exits.
	 * <p>
	 * This method doesn't affect other non-movement actions in this <code>HPActor</code>'s command set.
	 *  
	 * @author 	ram
	 * @param 	loc this <code>HPActor</code>'s location
	 * @pre		<code>loc</code> is the actual location of this <code>HPActor</code>
	 */
	public void resetMoveCommands(Location loc) {
		HashSet<HPActionInterface> newActions = new HashSet<HPActionInterface>();
		
		// Copy all the existing non-movement options to newActions
		for (HPActionInterface a: actions) {
			if (!a.isMoveCommand())
				newActions.add(a);
		}
		
		// add new movement possibilities
		for (CompassBearing d: CompassBearing.values()) { 														  
			if (loc.getNeighbour(d) != null) { //if there is an exit from the current location in direction d, add that as a Move command
				newActions.add(new Move(d,messageRenderer, world)); 
				
				
				
				if(this.Inventory.getHighestItemWithCapability(Capability.DOUBLESPEED)!=null && world.canDoubleMove(this, d))
					newActions.add(new DoubleMove(d, messageRenderer, world));
			}
		}
		
		// replace command list of this HPActor
		this.actions = newActions;		
		
		// TODO: This assumes that the only actions are the Move actions. This will clobber any others. Needs to be fixed.
		/* Actually, that's not the case: all non-movement actions are transferred to newActions before the movements are transferred. --ram */
	}
	
	/**
	 * This method returns the list of Spells learned by the <code>HPActor</code>.
	 *  
	 *  @return an ArrayList of <code>Spell</code>
	 */
	public ArrayList<Spell> getSpells(){
		return knownSpells;
	}
	
	/**
	 * This method allows a <code>HPActor</code> to learn a Spell.
	 *  
	 *  @param newSpell a <code>Spell</code> to be 'taught' to this <code>HPActor</code>
	 */
	public void learnSpell(Spell newSpell){
		knownSpells.add(newSpell);
	}
	
	/**
	 * This method adds to the <code>frozenTime</code> of this <code>HPActor</code> by a given integer parameter.
	 * @param freezeTime an integer value of how long this <code>HPActor</code> is to be frozen for
	 */
	public void freeze(int freezeTime) {
		assert freezeTime > 0: "HPActor can only be frozen for a positive integer value";
		frozenTime += freezeTime;
	}
	
	/**
	 * This method returns an ArrayList of HPEntity which represents the items seen by the HPActor
	 *  
	 *  @return ArrayList<HPEntity> of seen items
	 */
	public ArrayList<HPEntity> returnSeenItems(){
		ArrayList<HPEntity>  tempSeenItems= new ArrayList<HPEntity>(seenItems);
		return tempSeenItems;
	}	
}



///**The item carried by this <code>HPActor</code>. <code>itemCarried</code> is null if this <code>HPActor</code> is not carrying an item*/
//private HPEntityInterface itemCarried;
