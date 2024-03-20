package controller;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import model.GameModel;
import model.Ship;
public class EnemyShipController {
    private LinkedList<Ship> enemyShips;
    private Ship playerShip;
    private float timeSinceLastShot = 0;
    private GameModel gameModel;

    public EnemyShipController(GameModel gameModel) {
        this.enemyShips = gameModel.getEnemyShips();
        this.playerShip = gameModel.getShip();
        this.gameModel = gameModel;
    }

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


        }
    }
}

