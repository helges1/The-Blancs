package model;

import com.badlogic.gdx.graphics.Texture;

public class GameModel {
    private Ship ship;

    public GameModel(){
        ship = new Ship(new Texture("pictures/playerShip.png"));
    }

    public Ship getShip(){
        return ship;
    }
}
