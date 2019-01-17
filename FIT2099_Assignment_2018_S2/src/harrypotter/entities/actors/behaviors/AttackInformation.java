package harrypotter.entities.actors.behaviors;

import edu.monash.fit2099.simulator.matter.Affordance;
import harrypotter.HPEntityInterface;

public class AttackInformation {

	public HPEntityInterface entity;
	public Affordance affordance;
	public AttackInformation(HPEntityInterface e, Affordance a) {
		entity = e;
		affordance = a;
	}
}
