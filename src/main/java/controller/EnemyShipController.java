package controller;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import model.Ship;
public class EnemyShipController {
    private LinkedList<Ship> enemyShips;
    private Ship playerShip;

    public EnemyShipController(LinkedList<Ship> enemyShips, Ship playerShip) {
        this.enemyShips = enemyShips;
        this.playerShip = playerShip;
    }

    public void update(float deltaTime) {
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

