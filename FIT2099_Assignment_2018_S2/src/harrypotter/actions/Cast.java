package harrypotter.actions;

import harrypotter.HPAffordance;
import harrypotter.HPEntity;
import harrypotter.HPEntityInterface;
import harrypotter.Spell;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPActionInterface;
import harrypotter.HPActor;

/**
 * Class that represents a <code>Cast</code> action that can be carried out by a <code>HPActor</code>.
 * Each Cast action has a targetSpell (the <code>Spell</code> to be casted), its target and a <code>MessageRenderer</code>.
 * 
 * @author Shakeel Rafi
 */
public class Cast extends HPAffordance implements HPActionInterface {
	private Spell targetSpell;
	private boolean actionAffectsActor; // represents if the Cast action affects actor
	
	/**
	 * Constructor for <code>Cast</code> class. Will initialize the cast target, desired spell and assign a <code>MessageRenderer</code>.
	 * 
	 * @param theTarget <code>HPEntityInterface</code> that represents the target
	 * @param targetSpell <code>Spell</code> that is to be casted on the target
	 * @param m a <code>MessageRenderer</code> to display messages
	 */
	public Cast(HPEntityInterface theTarget, Spell targetSpell, MessageRenderer m, boolean actionAffectsActor) {
		super(theTarget, m);	
		this.targetSpell = targetSpell;
		this.actionAffectsActor = actionAffectsActor;
		priority = 1;
	}
	
	/**
	 * Method to assure system that this is a <code>Cast</code> command. Useful for deciding when to print the Cast sub-menu when human-controlled player decides to cast.
	 * 
	 * @return true
	 */
	@Override
	public boolean isCastCommand() {
		return true;
	}
	
	/**
	 * Returns the time is takes to perform this <code>Cast</code> action.
	 * 
	 * @return The duration of the Cast action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}
	
	/**
	 * A String describing what this <code>Cast</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "cast on " and the target's description
	 */
	@Override
	public String getDescription() {
		if (target != null){
			return "cast on " + this.target.getShortDescription();
		} else {
			return "cast a spell";
		}
	}
	
	/**
	 * Determine whether a particular <code>HPActor a</code> can cast spells on the target.
	 * 
	 * @param 	a the <code>HPActor</code> being queried
	 * @return 	true only if <code>HPActor</code> has a casting item,
	 * 			false if this <code>HPActor a</code> does not.
	 */
	@Override
	public boolean canDo(HPActor a) {
		if (a.isFrozen()) {
			a.unFreeze();
			return false;
		}
		
		if (a.Inventory.getHighestItemWithCapability(Capability.CASTING) != null && !a.isDead()){
				return true;
		} else {
			return false;
		}
	}

	/**
	 * Perform the <code>Cast</code> command on an entity.
	 * 
	 * If the result of the spell kills the entity, the <code>Attack</code> Affordance will be removed from the target.
	 * This method will perform the casting even if an entity is dead.
	 * Actors from the same <code>Team</code> can cast upon each other.
	 * 
	 * @param 	a the <code>HPActor</code> who is casting the spell
	 */
	@Override
	public void act(HPActor a) {
		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		
		// ensures that the target is an actor
		if (targetIsActor) {
			HPActor targetActor = (HPActor) target;
			targetSpell.spellEffect(targetActor);	// casts spell on the target
			targetActor.say(targetActor.getShortDescription() + " " + targetSpell.getAfterEffect());	// prints the effect of the spell
			
			if(targetActor.isDead()){			// remove the Attack affordance from the target if its dead
				
				Affordance[] targetAffordances = targetActor.getAffordances();
				
				for(int i = 0; i < targetAffordances.length; i ++){
					if(targetAffordances[i] instanceof Attack){
						targetActor.removeAffordance(targetAffordances[i]);		
					}
				}
			}
		} else if (!targetIsActor) {
			if (!targetSpell.affectActor()){ // affects an Entity instead of an Actor
				HPEntity targetEntity = (HPEntity)target;
				
				targetSpell.spellEffect(targetEntity);
			}
		}
	}
	
	/**
	 * Sets the <code>Spell</code> to be associated with this <code>Cast</code> action, useful when user selects a Spell.
	 * 
	 * @param 	targetSpell the <code>Spell</code> to set for this <code>Cast</code>
	 */
	public void setSpell(Spell targetSpell){
		this.targetSpell = targetSpell;
	}
	
	/**
	 * Returns the <code>Spell</code> that is associated with this <code>Cast</code> action, useful when user selects a Spell.
	 * 
	 * @return 	targetSpell the <code>Spell</code> that was set for this <code>Cast</code>
	 */
	public Spell getSpell(){
		return targetSpell;
	}
	
	/**
	 * Returns a boolean if this <code>Cast</code> action will affect a <code>HPActor</code>.
	 * Useful to determine which Cast option is available is to the User in the current turn,
	 * as there are spells that do not affect actors and only entities, and can be casted any time.
	 * 
	 * @return 	true if Cast action does, false if it does not
	 */
	public boolean doesCastAffectActor(){
		return actionAffectsActor;
	}
	
}
