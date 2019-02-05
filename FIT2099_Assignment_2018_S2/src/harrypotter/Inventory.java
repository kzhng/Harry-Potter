package harrypotter;

import java.util.ArrayList;

public class Inventory {

	/** An ArrayList of <code>HPEntityInterface</code> carried by the <code>HPctor</code>**/
	private ArrayList<HPEntityInterface> Inventory;
	
	/** An <code>integer</code> that specifies inventory size, it is 1 if the actor doesn't have an INventory capability **/
	private int InventorySize;

	/**
	 * 
	 */
	public Inventory(int InventorySize) {
		super();
		this.Inventory = new ArrayList<HPEntityInterface>();
		this.InventorySize = InventorySize;
	}
	
	/**
	 * Returns the items carried by this <code>HPActor</code>. 
	 * <p>
	 * This method only returns the reference of the items carried 
	 * and does not remove items held from this <code>HPActor</code>.
	 * <p>
	 * If this <code>HPActor</code> is not carrying any item this method will return null.
	 * 
	 * @return 	the items carried by this <code>HPActor</code> or null if no item is held by this <code>HPActor</code>
	 * @see 	#Inventory
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<HPEntityInterface> getItemsCarried() {
		return (ArrayList<HPEntityInterface>) this.Inventory.clone();		//cloned items, due to privacy risk
	}
	/**
	 * 
	 * @return true if the actor carries at least one item
	 */
	public boolean containsItems() {
		return this.Inventory.size()>=1;
	}
	
	/**
	 * 
	 * @return true if the actor's inventory is not full
	 */
	public boolean notFull() {
		return (this.Inventory.size()<InventorySize);		//mh //magic number 
	}

	/**
	 * returns an item for the requested capability which has the highest Hitpoints or null if no such item exist
	 * <p>
	 * this method does not remove the item requested
	 * </p>
	 * @param capability
	 * @return returns an item for the requested capability which has the highest Hitpoints or null if no such item exist
	 */
	public HPEntityInterface getHighestItemWithCapability(Capability capability) {
		ArrayList<HPEntityInterface> items = this.getItemsWithCapability(capability);
		if ( items==null )
				return null;
		int highestCap = 0;
		for (int i = 0; i < items.size(); i++) {			
			if(items.get(i).getHitpoints()>items.get(highestCap).getHitpoints())
				highestCap = i;			
		}		
		return items.get(highestCap);
	}
	
	/**
	 * returns items for the requested capability or null if no such items exist
	 * <p>
	 * this method does not remove the items requested
	 * </p>
	 * @param capability
	 * @return returns items for the requested capability or null if no such items exist
	 */
	public ArrayList<HPEntityInterface> getItemsWithCapability(Capability capability) {
		ArrayList<HPEntityInterface> items = new ArrayList<HPEntityInterface>();
		for (HPEntityInterface item : this.Inventory) {
			if(item.hasCapability(capability))
				items.add(item);			
		}
		if (items.size() == 0)
				return null;				
		return items;
	}
	
	/**
	 * Adds an <code>item</code> to this <code>HPActor</code>'s
	 * <code>Inventory</code>
	 * <p>
	 * This method will add an item to this <code>HPActor</code>'s
	 * <code>Inventory</code>, it accepts regardless if the same item already exist in the inventory.
	 * furthermore if this an item is added when the inventory is full, this method will do nothing
	 * </p>
	 * @param 	item to be added from the inventory
	 * @see 	#Inventory
	 */
	
	public void add(HPEntityInterface item) {	//mh what if null is added, need to fix this
		if(this.notFull() && item!=null) {		
			this.Inventory.add(item);
		}
		return;
	}
	
	/**
	 * Remove the requested item form this <code>HPActor</code>'s <code>inventory</code>  
	 * <p>
	 * This method will remove the requested item this <code>HPActor</code>'s <code>inventory</code> only if the <code>HPActor</code>
	 * is carrying the requested item, otherwise it does noting 
	 * 
	 * @param 	item to be removed from the inventory
	 * @see 	#Inventory
	 */
	
	public void remove(HPEntityInterface item) {
		if(this.Inventory.contains(item)) {
			this.Inventory.remove(item);
		}
		return;
	}
//	/**
//	 * sets this <code>HPActor</code>'s <code>InventorySize</code>
//	 */
//	private void setInventorySize() {
//		this.InventorySize = (this.hasCapability(Capability.INVENTORY))? 3 : 1;		//inventory size is 3 for actors with INVENTORY capability
//	}
}
