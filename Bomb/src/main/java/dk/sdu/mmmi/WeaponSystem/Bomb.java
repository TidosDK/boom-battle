package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.textureanimator.IAnimatable;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.weapon.IWeapon;
import dk.sdu.mmmi.common.services.map.IMap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Bomb extends Entity implements IWeapon, IAnimatable {
    private float timeSincePlacement;
    private float timeTillExplosionInSeconds;
    private int damagePoints;
    private int blastLength;

    private HashMap<Integer, ITextureAnimator> animators;
    private final Path explosionRightPath;
    private final Path explosionLeftPath;
    private final Path explosionUpPath;
    private final Path explosionDownPath;
    private final Path explosionMidHorizontalPath;
    private final Path explosionMidVerticalPath;
    private final Path explosionCenterPath;

    /**
     * Constructor for the Bomb class.
     *
     * @param texturePath Path to the default texture of the bomb
     * @param width       Width of the bomb texture
     * @param height      Height of the bomb texture
     */
    public Bomb(Path texturePath, float width, float height) {
        super(texturePath, width, height);
        animators = new HashMap<>();

        // Set path for explosion textures
        explosionRightPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/right/right-explosion-2.png");
        explosionLeftPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/left/left-explosion-2.png");
        explosionUpPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/up/up-explosion-2.png");
        explosionDownPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/down/down-explosion-2.png");
        explosionMidHorizontalPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/mid-horizontal/mid-hori-explosion-2.png");
        explosionMidVerticalPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/mid-vertical/mid-vert-explosion-2.png");
        explosionCenterPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/center/center-explosion-2.png");
    }

    /**
     * Calculates the time until the bomb explodes.
     *
     * @param gameData The game data
     * @return The time until the bomb explodes
     */
    public float calculateTimeTillExplosion(GameData gameData) {
        this.timeSincePlacement += gameData.getDeltaTime();
        return this.timeTillExplosionInSeconds - this.timeSincePlacement;
    }


    /**
     * Chooses the correct texture for the explosion based on the coordinates of the explosion.
     *
     * @param coordinates The coordinates of the explosion
     * @return The path to the explosion texture
     */
    public Path getFireExplosionTexturePath(Coordinates coordinates) {
        Coordinates initialCoordinates = this.getCoordinates();
        GridPosition position = initialCoordinates.getGridPosition();

        int dx = coordinates.getGridPosition().getX() - position.getX();
        int dy = coordinates.getGridPosition().getY() - position.getY();

        // Determine if the explosion is at the end or the middle of the blast radius
        boolean isEndOfBlastX = Math.abs(dx) == this.blastLength;
        boolean isEndOfBlastY = Math.abs(dy) == this.blastLength;

        // Center of the explosion
        if (dx == 0 && dy == 0) {
            return this.explosionCenterPath;
        }

        // Horizontal explosion
        if (dy == 0) {
            if (dx > 0) { // Right
                return isEndOfBlastX ? this.explosionRightPath : this.explosionMidHorizontalPath;
            } else if (dx < 0) { // Left
                return isEndOfBlastX ? this.explosionLeftPath : this.explosionMidHorizontalPath;
            }
        }

        // Vertical explosion
        if (dx == 0) {
            if (dy > 0) { // Up
                return isEndOfBlastY ? this.explosionUpPath : this.explosionMidVerticalPath;
            } else if (dy < 0) { // Down
                return isEndOfBlastY ? this.explosionDownPath : this.explosionMidVerticalPath;
            }
        }

        return this.explosionCenterPath;
    }

    /**
     * Calculates the area of the bomb's blast radius.
     *
     * @param world The game world
     * @return A collection of coordinates in the blast area
     */
    public Collection<Coordinates> calculateBlastArea(World world) {
        IMap map;
        if (world.getMap() instanceof IMap iMap) {
             map = iMap;
        } else {
            throw new IllegalArgumentException("The world's map must be an instance of IMap");
        }

        Coordinates position = this.getCoordinates();
        Collection<Coordinates> blastArea = new ArrayList<>();

        // Add the origin of the explosion
        blastArea.add(new Coordinates(new GridPosition(position.getGridX(), position.getGridY())));

        // Loop to add coordinates in four directions from the bomb's position
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // right, left, up, down

        for (int[] direction : directions) {
            for (int j = 1; j <= this.blastLength; j++) {
                int x = position.getGridX() + j * direction[0];
                int y = position.getGridY() + j * direction[1];

                // If the next tile in the direction is an obstacle, stop adding to that direction
                if (map.isTileObstacle(x, y)) {
                    break;
                }
                Coordinates blastPosition = new Coordinates(new GridPosition(x, y));
                blastArea.add(blastPosition);
            }
        }

        return blastArea;
    }

    @Override
    public Path getActiveTexturePath(Integer key) {
        if (this.animators.get(key) == null) {
            return getTexturePath();
        }
        return this.animators.get(key).getCurrentTexturePath();
    }

    @Override
    public void addAnimator(Integer key, ITextureAnimator animator) {
        this.animators.put(key, animator);
    }

    @Override
    public HashMap<Integer, ITextureAnimator> getAnimators() {
        return this.animators;
    }

    @Override
    public void setAnimators(HashMap<Integer, ITextureAnimator> animators) {
        this.animators = animators;
    }

    public float getTimeSincePlacement() {
        return this.timeSincePlacement;
    }

    public void setTimeSincePlacement(float timeOfPlacement) {
        this.timeSincePlacement = timeOfPlacement;
    }

    public float getTimeTillExplosionInSeconds() {
        return this.timeTillExplosionInSeconds;
    }

    public void setTimeTillExplosionInSeconds(float timeTillExplosionInSeconds) {
        this.timeTillExplosionInSeconds = timeTillExplosionInSeconds;
    }

    public int getBlastLength() {
        return this.blastLength;
    }

    public void setBlastLength(int blastLength) {
        this.blastLength = blastLength;
    }

    @Override
    public int getDamagePoints() {
        return this.damagePoints;
    }

    @Override
    public void setDamagePoints(int damagePoints) {
        this.damagePoints = damagePoints;
    }
}
