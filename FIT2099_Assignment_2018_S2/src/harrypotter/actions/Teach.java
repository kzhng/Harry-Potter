package harrypotter.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.HPActionInterface;
import harrypotter.HPActor;
import harrypotter.HPAffordance;
import harrypotter.HPEntityInterface;
import harrypotter.Spell;
import harrypotter.interfaces.HPGridController;
import java.util.ArrayList;


public class Teach extends HPAffordance implements HPActionInterface {
	// private Spell teachSpell;
	/**
	 * Constructor for the <code>Teach</code> Class. Will initialize the message
	 * renderer, the target and set the priority of this <code>Action</code> to 1
	 * (lowest priority is 0).
	 * 
	 * @param theTarget a <code>HPEntity</code> that is being taken
	 * @param m         the message renderer to display messages
	 */
	public Teach(HPEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1; 
	}

	/**
	 * Returns if or not this <code>Teach</code> can be performed by the
	 * <code>HPActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code>
	 * 1- the target is an actor, 2-both actors on the same team, 3-the teacher knows at least one spell the target doesn't
	 * 4- both actors is not dead, 5- the HPActor is a teacher.
	 *</p>
	 * @param a the <code>HPActor</code> being queried
	 * @return true if the <code>HPActor</code> can teach the target a spell that don't already know, false otherwise
	 * @see {@link harrypotter.HPActor#isTeacher()}
	 * * @see {@link harrypotter.HPActor#knowSpell()}
	 */
	@Override
	public boolean canDo(HPActor a) {
		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		HPActor targetActor = null;

		if (targetIsActor) {
			targetActor = (HPActor) target;
			
			if (a.isTeacher() && a.getTeam() == targetActor.getTeam()) {
				ArrayList<Spell> targetSpells = targetActor.getSpells();
				ArrayList<Spell> teacherSpells = a.getSpells();
				for (int i = 0; i < teacherSpells.size(); i++ ) {
					boolean learnableSpell = true;
					for (int j = 0; j < targetSpells.size(); j++) {
						if (teacherSpells.get(i) == targetSpells.get(j)) {
							learnableSpell = false;
						}
					}
					if (learnableSpell) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * Perform the <code>Teach</code> action by having the
	 * <code>HPActor</code> teach a spell to another HP actor in the same location ( the
	 * <code>HPActor a</code>'s student(the one learning the spell) would be the target of this
	 * <code>Teach</code>).
	 * <p>
	 * This method should only be called if the <code>HPActor a</code> is alive.
	 * 
	 * @param a the <code>HPActor</code> that is teaching the spell 
	 * @see {@link #theTarget}
	 * @see {@link harrypotter.HPActor#isDead()}
	 */
	@Override
	public void act(HPActor a) {
		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		HPActor targetActor = null;

		if (targetIsActor) {
			targetActor = (HPActor) target;
			if (canDo(a)) {
				if (a.isHumanControlled()) {
					a.say("Choose a spell to teach " + targetActor.getShortDescription());
				}
				
				ArrayList<Spell> spellsToTeach = getSpellsToTeach(a, targetActor);
				Spell selectedSpell = HPGridController.getTeachingSpell(a, spellsToTeach);

				if (!(a.isHumanControlled())) {
					a.say(a.getShortDescription() + " wants to teach you the spell " + selectedSpell.getDescription());
				}

				// getting AI decision using machine learning
				boolean decision = HPGridController.getAcceptOrDecline(targetActor);
				if (decision) {
					targetActor.learnSpell(selectedSpell);
					a.say(a.getShortDescription() + " taught " + selectedSpell.getSpellName() + " to "
							+ targetActor.getShortDescription());

				} else {
					a.say(targetActor.getShortDescription() + " refused to learn " + selectedSpell.getDescription()
							+ " from " + a.getShortDescription());
					return;
				}

			}
		}

	}
	
	//public void setSpell(Spell teachSpell){
		//this.teachSpell = teachSpell;
	//}
	
	//public Spell getSpell() {
		//return teachSpell;
	//}
	
	/**
	 * Determine what spells the <code>HPActor a</code> can teach to <code>student</code>/
	 * 
	 * @author 	Kerry Zheng
	 * @param 	a the <code>HPActor</code> teaching the spell, student the <code>HPActor</code> learning the spell
	 * @return 	a list of <code>Spell</code> that the teacher knows that the student does not
	 */
	
	public ArrayList<Spell> getSpellsToTeach(HPActor teacher, HPActor student) {
		ArrayList<Spell> spellsToTeach = new ArrayList<Spell>();
		ArrayList<Spell> targetSpells = student.getSpells();
		ArrayList<Spell> teacherSpells = teacher.getSpells();
		if (targetSpells.size() == 0) {
			return teacherSpells;
		}
		for (int i = 0; i < teacherSpells.size(); i++ ) {
			boolean learnableSpell = true;
			for (int j = 0; j < targetSpells.size(); j++) {
				if (teacherSpells.get(i) == targetSpells.get(j)) {
					learnableSpell = false;
				}
			if (learnableSpell && !spellsToTeach.contains(teacherSpells.get(i))) {
				spellsToTeach.add(teacherSpells.get(i));
			}
			}
		}
		return spellsToTeach;
	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user
	 * interface
	 * 
	 * @author Kerry Zheng
	 * @return String comprising "teach " and the short description of the target of
	 *         this <code>Teach</code>
	 */
	
	@Override
	public String getDescription() {
		if (target != null) {
			return "teach " + this.target.getShortDescription();
		}
		return "teach a spell";
	}
}
