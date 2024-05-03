package model.powerUps;

/**
 * Implement to create a factory for PowerUps.
 */
public interface PowerUpsFactory {
	
	/**
	 * Create a power up.
	 * 
	 * @param powerUpDuration how long the PowerUp should last.
	 * @return a <code>PowerUps</code> object.
	 */
	PowerUps createPowerUp(float powerUpDuration);

	/**
	 * Get a random PowerUpType.
	 * 
	 * @return a random <code>PowerUpType</code>.
	 */
	PowerUps.PowerUpType getRandomPowerUpType();
	

}
