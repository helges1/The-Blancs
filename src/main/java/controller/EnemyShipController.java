package controller;
import com.badlogic.gdx.math.Vector2;

import model.Ship;
public class EnemyShipController {
    private Ship enemyShip;
    private Ship playerShip;
    private Vector2 playerShipPos;

    public EnemyShipController(Ship enemyShip, Ship playerShip) {
        this.enemyShip = enemyShip;
        this.playerShip = playerShip;
        playerShipPos = new Vector2();
    }

    public void update(float deltaTime) {
        System.out.println("EnemyShipController update");
        playerShipPos.set(playerShip.getX(), playerShip.getY());
        enemyShip.rotateShip(playerShipPos);
    }
}

