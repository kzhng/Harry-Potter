package harrypotter.spells;

import harrypotter.HPEntityInterface;
import harrypotter.Spell;
import harrypotter.HPActor;
import harrypotter.entities.actors.*;
import harrypotter.HPLegend;


public class Immobulus extends Spell {

	public Immobulus() {
		super("Immobulus", "freezes the target", "has been frozen!");
	}
	
	@Override
	public void spellEffect(HPEntityInterface theTarget) {
		HPActor targetActor = null;
		
		if (theTarget instanceof HPActor) {
			targetActor = (HPActor)theTarget;
			targetActor.freeze(20);
		}
	}
}
