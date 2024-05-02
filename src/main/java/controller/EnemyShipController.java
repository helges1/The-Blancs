package controller;
import java.util.LinkedList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import model.Asteroid;
import model.GameModel;
import model.powerUps.PowerUps.PowerUpType;
import model.ships.Ship;

/**
 * This class is responsible for controlling the enemy ships in the game.
 */
public class EnemyShipController {
    private LinkedList<Ship> enemyShips;
    private LinkedList<Asteroid> asteroids;
    private Ship playerShip;
    private float timeSinceLastShot = 0;
    private GameModel gameModel;

    /**
     * Constructor for the EnemyShipController class.
     * @param gameModel The game model object.
     */
    public EnemyShipController(GameModel gameModel) {
        this.enemyShips = gameModel.getEnemyShips();
        this.playerShip = gameModel.getPlayerShip();
        this.asteroids = gameModel.getAsteroids();
        this.gameModel = gameModel;
    }

    /**
     * Updates the enemy ships in the game.
     * @param deltaTime The time passed since the last frame.
     */
    public void update(float deltaTime) {
        timeSinceLastShot += deltaTime;
        // Every 1 second, make a random enemy ship shoot
        if (timeSinceLastShot >= 1.0f && enemyShips.size() > 0) {
            // Reset the timer
            timeSinceLastShot = 0;

            // Select a random enemy ship to shoot
            int randomIndex = MathUtils.random(0, enemyShips.size() - 1);
            Ship shootingShip = enemyShips.get(randomIndex);
            
            // Make the selected enemy ship shoot
            gameModel.fireEnemyLaser(shootingShip);

        }

        
        for (Ship enemyShip : enemyShips) {
            // Rotate the enemy ship to face the player's ship
            Vector2 playerShipPos = new Vector2(playerShip.getX(), playerShip.getY());
            enemyShip.rotateShip(playerShipPos);

            Vector2 toPlayer = new Vector2(
                playerShip.getX() + playerShip.getWidth() / 2 - (enemyShip.getX() + enemyShip.getWidth() / 2), 
                playerShip.getY() + playerShip.getHeight() / 2 - (enemyShip.getY() + enemyShip.getHeight() / 2)
            );

            // Distance from enemy to player
            float distanceToPlayer = toPlayer.len();
            
            // Very basic mvoement to move towards the player's ship keeping 100 pixels distance
            if (playerShip.getX() > enemyShip.getX() + 100) {
                enemyShip.moveRight(deltaTime);
            } else if (playerShip.getX() < enemyShip.getX() - 100){
                enemyShip.moveLeft(deltaTime);
            }

            if (playerShip.getY() > enemyShip.getY() + 100) {
                enemyShip.moveUp(deltaTime);
            } else if (playerShip.getY() < enemyShip.getY() - 100){
                enemyShip.moveDown(deltaTime);
            }

            // If playerShip has Blast powerup
            if (gameModel.getPlayerShip().getActivePowerUp() == PowerUpType.BLAST) {
                // Apply blast effect to enemy ships
                applyBlastEffect(enemyShip, distanceToPlayer);
            }
        }
    }

    /**
     * Applies the blast effect to the enemy ship.
     * @param enemyShip The enemy ship to apply the blast effect to.
     * @param distanceToPlayer The distance from the enemy ship to the player ship.
     */
    private void applyBlastEffect(Ship enemyShip, float deltaTime) {

        // Calculate the vector from the enemy ship to the player ship
        Vector2 toPlayer = new Vector2(
            playerShip.getX() + playerShip.getWidth() / 2 - (enemyShip.getX() + enemyShip.getWidth() / 2), 
            playerShip.getY() + playerShip.getHeight() / 2 - (enemyShip.getY() + enemyShip.getHeight() / 2)
        );
        
        // Distance from enemy to player
        float distanceToPlayer = toPlayer.len();
        float blastRadius = 200; // radius in which the blast affects enemy ships
    
        if (distanceToPlayer < blastRadius) {
            // Make the ship spin
            float spinSpeed = 1080; // degrees per second
            enemyShip.rotate(spinSpeed * deltaTime);
    
            // Apply a force that pushes the ship away
            Vector2 blastDirection = toPlayer.nor().scl(-1); // Invert direction, so it moves away from the player
            float pushBackForce = 15; // Adjust this as needed
            enemyShip.applyBlastForce(blastDirection.scl(pushBackForce));
        }
    }
    
}

