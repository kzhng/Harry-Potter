package harrypotter;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.LocationMaker;

/**
 * Grid of <code>HPLocation</code>s.
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-01-20: 	Bug fix where the location of width 8 used to display a location of width 7
 * 2017-02-02: 	Removed the render method and the location width attributes. The rendering of the map
 * 				and displaying it is now the job of the UI. The dependency with EntityManager package was hence removed
 * 				and this resulted in a simpler HPGrid class (asel) 
 */
public class HPGrid extends Grid<HPLocation> {

	/**
	 * The constructor of the <code>HPGrid</code>. 
	 * Will create a 10 by 10 grid with 100 <code>HPLocation</code>s
	 * 	 
	 * @param int number of rows in HPGrid
	 * @param int number of columns of HPGrid
	 * @param factory the maker of the <code>HPLocation</code>s
	 */
	public HPGrid(int row, int column, LocationMaker<HPLocation> factory) {
		super(row,column,factory);
	}
	

}
