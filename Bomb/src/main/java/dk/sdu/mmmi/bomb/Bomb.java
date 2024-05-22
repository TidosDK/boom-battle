package dk.sdu.mmmi.bomb;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.textureanimator.IAnimatable;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.weapon.IWeapon;
import dk.sdu.mmmi.common.services.map.IMap;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Bomb extends Entity implements IWeapon, IAnimatable {
    private float timeSincePlacement;
    private float timeTillExplosionInSeconds;
    private int damagePoints;
    private int blastLength;

    public enum State {
        PLACED, EXPLODING, FINISHED
    }

    private State state;

    private HashMap<Integer, ITextureAnimator> animators;
    private static Path explosionRightPath;
    private static Path explosionLeftPath;
    private static Path explosionUpPath;
    private static Path explosionDownPath;
    private static Path explosionMidHorizontalPath;
    private static Path explosionMidVerticalPath;
    private static Path explosionCenterPath;
    private Collection<Coordinates> cachedBlastArea; // New field to store the blast area


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
        this.state = State.PLACED; // Default state when a bomb is created
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
            return Bomb.explosionCenterPath;
        }

        // Horizontal explosion
        if (dy == 0) {
            if (dx > 0) { // Right
                return isEndOfBlastX ? Bomb.explosionRightPath : Bomb.explosionMidHorizontalPath;
            } else if (dx < 0) { // Left
                return isEndOfBlastX ? Bomb.explosionLeftPath : Bomb.explosionMidHorizontalPath;
            }
        }

        // Vertical explosion
        if (dx == 0) {
            if (dy > 0) { // Up
                return isEndOfBlastY ? Bomb.explosionUpPath : Bomb.explosionMidVerticalPath;
            } else if (dy < 0) { // Down
                return isEndOfBlastY ? Bomb.explosionDownPath : Bomb.explosionMidVerticalPath;
            }
        }

        return Bomb.explosionCenterPath;
    }

    /**
     * Calculates the area of the bomb's blast radius.
     *
     * @param world The game world
     * @return A collection of coordinates in the blast area
     */
    public Collection<Coordinates> calculateBlastArea(World world) {
        if (this.cachedBlastArea != null) {
            return this.cachedBlastArea; // Return cached area if available
        }

        Coordinates position = this.getCoordinates();
        Collection<Coordinates> blastArea = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Right, left, up, down

        if (world.getMap() instanceof IMap) {
            IMap map = (IMap) world.getMap();

            blastArea.add(new Coordinates(new GridPosition(position.getGridX(), position.getGridY()))); // Origin
            for (int[] direction : directions) {
                for (int j = 1; j <= this.blastLength; j++) {
                    int x = position.getGridX() + j * direction[0];
                    int y = position.getGridY() + j * direction[1];

                    if (map.isTileObstacle(x, y)) {
                        Coordinates blastPos = new Coordinates(new GridPosition(x, y));
                        blastArea.add(blastPos);
                        break;
                    }

                    Coordinates blastPos = new Coordinates(new GridPosition(x, y));
                    blastArea.add(blastPos);
                }
            }

            blastArea = blastArea.stream().filter(c -> !map.outOfBounds(c.getGridX(), c.getGridY())).toList();
        } else {
            // Handle case where world is not imap
            blastArea.add(new Coordinates(new GridPosition(position.getGridX(), position.getGridY())));
            for (int[] direction : directions) {
                for (int j = 1; j <= this.blastLength; j++) {
                    int x = position.getGridX() + j * direction[0];
                    int y = position.getGridY() + j * direction[1];
                    // If the next tile in the direction is an obstacle, stop adding to that direction
                    Coordinates blastPos = new Coordinates(new GridPosition(x, y));
                    blastArea.add(blastPos);
                }
            }
        }

        this.cachedBlastArea = blastArea; // Cache the area
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static Path getExplosionRightPath() {
        return explosionRightPath;
    }

    public static void setExplosionRightPath(Path explosionRightPath) {
        Bomb.explosionRightPath = explosionRightPath;
    }

    public static Path getExplosionCenterPath() {
        return explosionCenterPath;
    }

    public static void setExplosionCenterPath(Path explosionCenterPath) {
        Bomb.explosionCenterPath = explosionCenterPath;
    }

    public static Path getExplosionMidVerticalPath() {
        return explosionMidVerticalPath;
    }

    public static void setExplosionMidVerticalPath(Path explosionMidVerticalPath) {
        Bomb.explosionMidVerticalPath = explosionMidVerticalPath;
    }

    public static Path getExplosionMidHorizontalPath() {
        return explosionMidHorizontalPath;
    }

    public static void setExplosionMidHorizontalPath(Path explosionMidHorizontalPath) {
        Bomb.explosionMidHorizontalPath = explosionMidHorizontalPath;
    }

    public static Path getExplosionDownPath() {
        return explosionDownPath;
    }

    public static void setExplosionDownPath(Path explosionDownPath) {
        Bomb.explosionDownPath = explosionDownPath;
    }

    public static Path getExplosionUpPath() {
        return explosionUpPath;
    }

    public static void setExplosionUpPath(Path explosionUpPath) {
        Bomb.explosionUpPath = explosionUpPath;
    }

    public static Path getExplosionLeftPath() {
        return explosionLeftPath;
    }

    public static void setExplosionLeftPath(Path explosionLeftPath) {
        Bomb.explosionLeftPath = explosionLeftPath;
    }
}
