package model.ships;

import model.GameLevel;

public interface ShipFactory {
	
	
	/**Generate a playerShip.
	 * 
	 * @return a <code>Ship</code>
	 */
	Ship getPlayerShip();
	
	/** Generate an enemy ship based on the given gameLevel.
	 * 
	 * @param gameLevel the current level of the game.
	 * @return a <code>Ship</code> 
	 */
	Ship getEnemyShip(GameLevel gameLevel);

}
