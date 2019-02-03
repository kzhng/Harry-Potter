package harrypotter;
/**
 * Capabilities that various entities may have.  This is useful in <code>canDo()</code> methods of 
 * <code>HPActionInterface</code> implementations.
 *  
 * @author 	ram
 * @see 	HPActionInterface
 */

public enum Capability {
	CHOPPER,//CHOPPER capability allows an entity to Chop another entity which has the Chop Affordance
	WEAPON,//WEAPON capability allows an entity to Attack another entity which has the Attack Affordance
	CASTING, //CASTING capability allows an entity to Cast Spells on another entity which has the Cast Affordance
	HEALTH,	//hEALTH capability allow an entity to increase its health
	DOUBLESPEED,	// allow an actor to move at Double speed
	INVENTORY	// allow an actor to carry up to 3 items
}
