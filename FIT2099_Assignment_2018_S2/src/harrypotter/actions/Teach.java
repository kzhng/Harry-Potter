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
	//private Spell teachSpell;
	/**
	 * Constructor for the <code>Take</code> Class. Will initialize the message
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
						if (teacherSpells.get(i).getClass() == targetSpells.get(j).getClass()) {
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

	@Override
	public void act(HPActor a) {
		HPEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof HPActor;
		HPActor targetActor = null;

		if (targetIsActor) {
			targetActor = (HPActor) target;
			if (a.getTeam() == targetActor.getTeam()) {
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
					a.say(a.getShortDescription() + " taught " + selectedSpell.getDescription() + " to "
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
	
	public ArrayList<Spell> getSpellsToTeach(HPActor teacher, HPActor student) {
		ArrayList<Spell> spellsToTeach = new ArrayList<Spell>();
		ArrayList<Spell> targetSpells = student.getSpells();
		ArrayList<Spell> teacherSpells = teacher.getSpells();
		for (int i = 0; i < teacherSpells.size(); i++ ) {
			boolean learnableSpell = true;
			for (int j = 0; j < targetSpells.size(); j++) {
				if (teacherSpells.get(i).getClass() == targetSpells.get(j).getClass()) {
					learnableSpell = false;
				}
			if (learnableSpell && !spellsToTeach.contains(teacherSpells.get(i))) {
				spellsToTeach.add(teacherSpells.get(i));
			}
			}
		}
		return spellsToTeach;
	}

	@Override
	public String getDescription() {
		if (target != null) {
			return "teach " + this.target.getShortDescription();
		}
		return "teach a spell";
	}
}
