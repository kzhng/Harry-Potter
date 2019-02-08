package harrypotter.interfaces;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.gridworld.GridController;
import edu.monash.fit2099.gridworld.GridRenderer;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import harrypotter.Capability;
import harrypotter.HPActionInterface;
import harrypotter.HPActor;
import harrypotter.HPEntityInterface;
import harrypotter.HPGrid;
import harrypotter.HPWorld;
import harrypotter.Spell;
import harrypotter.actions.Cast;

/**
 * Concrete implementation of the <code>GridController</code>.
 * <p>
 * This controller calls the UI methods to render map, messages and obtain user input.
 * 
 * @author 	Asel
 * @see 	{@link edu.monash.fit2099.gridworld.GridController}
 *
 */
public class HPGridController implements GridController {

	/**The user interface to be used by the controller. All user interfaces should be concrete 
	 * implementations of the <code>GridRenderer</code> interface
	 * 
	 * @see {@link edu.monash.fit2099.gridworld.GridRenderer}*/
	private static GridRenderer ui; 
	
	/**HPgrid of the world*/
	private HPGrid grid;
	
	/**
	 * Constructor of this <code>HPGridController</code>
	 * <p>
	 * The constructor will initialize the <code>grid</code> and the user interface to be used by the controller.
	 * <p>
	 * If a different User Interface (also know as a View) is to be used it must be changed in this constructor.
	 * 
	 * @param 	world the world to be considered by the controller
	 * @pre 	the world should not be null
	 */
	public HPGridController(HPWorld world) {
		this.grid = world.getGrid();
		
		//change the user interface to be used here in the constructor
		HPGridController.ui = new HPGridTextInterface(this.grid); //use a Text Interface to interact
		//this.ui = new HPGridBasicGUI(this.grid); //Use a Basic GUI to interact
		//this.ui = new HPGridGUI(this.grid); //Use a GUI with better graphics to interact
	}

	@Override
	public void render() {
		//Call the UI to handle this
		ui.displayMap();		
	}

	@Override
	public void render(String message) {
		//call the UI to handle this too
		ui.displayMessage(message);
	}
	
	/**
	 * Will return a Action selected by the user.
	 * <p>
	 * This method will provide the user interface with a list of commands from which the user 
	 * needs to select one from and will return this selection.	
	 * 
	 * @param 	a the <code>HPActor</code> for whom an Action needs to be selected
	 * @return	the selected action for the <code>HPActor a</code>
	 */
	public static HPActionInterface getUserDecision(HPActor a) {
		
		//this list will store all the commands that HPActor a can perform
		ArrayList<ActionInterface> cmds = new ArrayList<ActionInterface>();

		//Get all the actions the HPActor a can perform
		for (HPActionInterface ac : HPWorld.getEntitymanager().getActionsFor(a)) {
			if (ac.canDo(a))
				cmds.add(ac);
		}
		
		//Get the UI to display the commands to the user and get a selection
		//TO DO: Ensure the cmd list is not empty to avoid an infinite wait
		assert (cmds.size()>0): "No commands for Harry Potter Actor";
		
		ActionInterface selectedAction = ui.getSelection(cmds);
		
		//cast and return selection
		return (HPActionInterface)selectedAction;
	}

	/**
	 * Will return a Action selected by the user.
	 * <p>
	 * This method will provide the user interface with a list of commands from which the user 
	 * needs to select one from and will return this selection.	
	 * 
	 * @param 	a the <code>HPActor</code> for whom an Action needs to be selected
	 * @return	the selected action for the <code>HPActor a</code>
	 */
	public static HPActionInterface getUserDecision(HPActor a, MessageRenderer m) {
		
		//this list will store all the commands that HPActor a can perform
		ArrayList<ActionInterface> cmds = new ArrayList<ActionInterface>();

		//Get all the actions the HPActor a can perform
		for (HPActionInterface ac : HPWorld.getEntitymanager().getActionsFor(a)) {
			if (ac.canDo(a))
				cmds.add(ac);
		}
		
		if (a.Inventory.getHighestItemWithCapability(Capability.CASTING) != null){
			for (Spell s : a.getSpells()){
				if (!s.affectActor()){
					cmds.add(new Cast(null, s, m, false));
				}				
			}
		}
		
		//Get the UI to display the commands to the user and get a selection
		//TO DO: Ensure the cmd list is not empty to avoid an infinite wait
		assert (cmds.size()>0): "No commands for Harry Potter Actor";
		
		ActionInterface selectedAction = ui.getSelection(cmds);
		
		//cast and return selection
		return (HPActionInterface)selectedAction;
	}
	
	/**
	 * Will return a Spell selected by the user.
	 * <p>
	 * This method will provide the user interface with a list of Spells from which the user 
	 * needs to select one from and will return this selection.	
	 * 
	 * @param 	a the <code>HPActor</code> for whom a Spell needs to be selected
	 * @return	the selected Spell for the <code>HPActor a</code>
	 */
	
	public static HPActionInterface getTargetSpell(HPActionInterface cast, HPActor a){
		
		ArrayList<Spell> tempList = new ArrayList<Spell>();
		
		if (!((Cast)cast).doesCastAffectActor()){
			for (Spell s : a.getSpells()){
				if (!s.affectActor()){
					tempList.add(s);
				}
			}	
		} else {
			for (Spell s : a.getSpells()){
				if (s.affectActor()){
					tempList.add(s);
				}
			}	
		}
		
		Spell selectedSpell = ((HPGridTextInterface) ui).getSpellSelection(tempList);	
		((Cast)cast).setSpell(selectedSpell);
		
		return (HPActionInterface)cast;
	}	
	
	/**
	 * Will return a boolean representing Accept and decline for HumanControled Actors or random decision for NonPlayers
	 * 
	 * @return a boolean represent Accept and decline
	 * @author Matti
	 * @param a			an HPActor 
	 */
	
	public static boolean getAcceptOrDecline(HPActor a){
		if(a.isHumanControlled()) {
			return ((HPGridTextInterface) ui).getRespond();	
		}
		else {
			return (Math.random() > 0.25);	//AI decision is random, but more likely to accept
		}
	}
	
	/**
	 * Will return a item selected by the user.
	 * <p>
	 * This method will provide the user interface with a list of items from which the user 
	 * needs to select one from and will return this selection.	
	 * 
	 * Nonplayers will return a random item,
	 * 
	 * @param 	a the <code>HPActor</code> for whom a item needs to be selected
	 * @return	the selected item for the <code>HPActor a</code>
	 */
	
	public static HPEntityInterface getChosenItem( HPActor a) {
			if(a.isHumanControlled()) {
				HPEntityInterface selectedItem = ((HPGridTextInterface) ui).getItemSelection(a.Inventory.getItemsCarried());
				return selectedItem;
			}
			else {
				int rnd = new Random().nextInt(a.Inventory.getItemsCarried().size());
				return a.Inventory.getItemsCarried().get(rnd);
			}
	}
	
	public static Spell getTeachingSpell(HPActor a, ArrayList<Spell> spellsToTeach) {
		if(a.isHumanControlled()) {
			Spell selectedSpell = ((HPGridTextInterface) ui).getSpellSelection(spellsToTeach);
			return selectedSpell;
		}
		else {
			
			Random randomNumberGenerator = new Random();
			int randomSpellIndex = randomNumberGenerator.nextInt(spellsToTeach.size());
			return spellsToTeach.get(randomSpellIndex);
		}
	}
}
