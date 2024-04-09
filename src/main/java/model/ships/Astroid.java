package model.ships;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
public class Astroid extends Sprite{

    public enum AstroidType {
        ASTROID1(new Texture("pictures/astroid1.png"), new float[]{0, 80, 170, 260, 350, 450, 535, 620, 710, 800, 880, 980, 1070});
        //ASTROID2(new Texture("pictures/astroid2.png"), new float[]{0, 80, 170, 260, 350, 450, 535, 620, 710, 800, 880, 980, 1070});
    
        private final Texture astroidTexture;
        private final float[] xPositions;
    
        AstroidType(Texture astroidTexture, float[] xPositions) {
            this.astroidTexture = astroidTexture;
            this.xPositions = xPositions;
        }
    
        public Texture getAstroidTexture() {
            return astroidTexture;
        }
    
    
        public float[] getxPositions() {
            return xPositions;
        }
    }

    private final float yPos = 950;
    AstroidType type;
    Vector2 velocity;
    private int rotationStage = 0;
    private Texture astroidTexture;
    private TextureRegion currentFrame;
    private TextureRegion[] astroidFrames;
    private float frameTime = 0;
    private float frameDuration = 0.3f;
    private Vector2 astroidSize;


    public Astroid(float xStartPos, Vector2 velocity, AstroidType astroidType, float astroidSize) {
        this.type = astroidType;
        this.velocity = velocity;
        this.astroidSize = new Vector2(astroidSize, astroidSize);
        // Set the texture for the sprite to the first frame
        this.astroidFrames = createTextureSheet(astroidType);
        this.currentFrame = astroidFrames[0]; // Initialize with the first frame

        // Set sprite's properties
        this.setRegion(currentFrame);
        this.setSize(astroidSize, astroidSize);
        this.setPosition(xStartPos, yPos);
    }

    private TextureRegion[] createTextureSheet(AstroidType astroidType) {
        Texture astroidTexture = astroidType.getAstroidTexture();
        float[] xPositions = astroidType.getxPositions();
        TextureRegion[] astroidFrames = new TextureRegion[xPositions.length - 1];

        for (int i = 0; i < xPositions.length - 1; i++) {
            float xPos = xPositions[i];
            float width = xPositions[i + 1] - xPos;
            astroidFrames[i] = new TextureRegion(astroidTexture, (int) xPos, 0, (int) width, astroidTexture.getHeight());
        }

        return astroidFrames;
    }


    public void update(float deltaTime) {
        moveAstroid(deltaTime);
        frameTime += deltaTime; // Update the frame time

        if (frameTime >= frameDuration) {
            frameTime = 0;
            updateRotationStage();
        }
    }

    private void moveAstroid(float deltaTime) {
        this.translate(velocity.x * deltaTime, velocity.y * deltaTime);
    }

    private void updateRotationStage() {
        rotationStage = (rotationStage + 1) % astroidFrames.length;
        this.setRegion(astroidFrames[rotationStage]);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }


    
}
