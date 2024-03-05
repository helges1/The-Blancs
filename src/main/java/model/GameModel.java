package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameModel {

    // Game Objectst
    private Ship playerShip;
    private LinkedList<Projectile> playerLasers; // Linked list so that elements can be removed from the middel with little cost (*)

    private LinkedList<Ship> enemyShips; // (*)
    private LinkedList<Projectile> enemyLasers; // (*)

    // World values
    private final static float WORLD_WIDTH = 1080; // For example
    private final static float WORLD_HEIGHT = 720;

    // Graphics
    /* If all the graphics stuff is in the model it may be more modular, as we only have to make changes to this class and not the view.
     * When we have all textures, we can create a texture atlas to import, instead of importing them all individually
     * The following Texture-variables should then be changed to TextureRegions
     * */
//    private final TextureAtlas textureAtlas;
    private Texture playerShipTextrue;
    private Texture playerLaserTexture;

    private Texture enemyShipTextrue;
    private Texture enemyLaserTextrue;

    // When we find a backgournd
//    private Texture backGroundTexture;

    public GameModel(){
        this.playerShip = new Ship(new Texture("pictures/playerShip.png"));
        // Initiate Textures and enemyShips and stuff...
    }

    /**
     * This method should update the model based on the <code>deltaTime</code>.
     *
     * @param deltaTime a float representing the amount of time passed since the last time the render-emthod
     * was called from the GameScreen.
     */
    public void updateModel(float deltaTime) {
        //TODO: Implement
    }

    public Ship getShip(){
        return playerShip;
    }

    public Iterator<Projectile> getPlayerLasers() {
        return playerLasers.listIterator();
    }

    public Iterator<Ship> getEnemyShips() {
        return enemyShips.listIterator();
    }

    public Iterator<Projectile> getEnemyLasers() {
        return enemyLasers.listIterator();
    }

}
