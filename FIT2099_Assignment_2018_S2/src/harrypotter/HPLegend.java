package harrypotter;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * This class represents "legends" - major characters - in the Harry Potter universe.  
 * They use a variation of the Singleton
 * pattern to ensure that only ONE of each legend can exist.
 * 
 * Subclasses are intended to contain a static instance which represents the one
 * and only instance of the subclass.  
 * 
 * Subclasses should implement their own "getLegendClass" method that returns 
 * the single instance. There is no abstract method for this to avoid an 
 * unnecessary downcast.
 * 
 * To prevent HPLegends acting until intended, this abstract class implements
 * an API for activating them when getInstance is called.
 * 
 * Rather than implement act() like regular HPActors, Legends should implement
 * legendAct().  
 * 
 * @author Robert Merkel
 *
 */
public abstract class HPLegend extends HPActor {

	private boolean isActivated;

	
	/** 
	 * Protected constructor to prevent random other code from creating 
	 * HPLegends or their descendents.
	 * @param team
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	
	protected HPLegend(Team team, int hitpoints, MessageRenderer m, HPWorld world) {
		super(team, hitpoints, m, world);
		isActivated = false;
	}

	
	protected boolean isActive() {
		return isActivated;
	}
	
	protected void activate() {
		isActivated = true;
	}
	
	@Override
	public void act() {
		if (isActive()) {
			this.legendAct();
		}
		return;
	}

	protected abstract void legendAct();
}
