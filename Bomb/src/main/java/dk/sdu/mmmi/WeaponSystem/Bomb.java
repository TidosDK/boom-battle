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
    private Path explosionRightPath;
    private Path explosionLeftPath;
    private Path explosionUpPath;
    private Path explosionDownPath;
    private Path explosionMidHorizontalPath;
    private Path explosionMidVerticalPath;
    private Path explosionCenterPath;

    public Bomb(Path texturePath, float width, float height) {
        super(texturePath, width, height);
        animators = new HashMap<>();

        explosionRightPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/right/right-explosion-2.png");
        explosionLeftPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/left/left-explosion-2.png");
        explosionUpPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/up/up-explosion-2.png");
        explosionDownPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/down/down-explosion-2.png");
        explosionMidHorizontalPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/mid-horizontal/mid-hori-explosion-2.png");
        explosionMidVerticalPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/mid-vertical/mid-vert-explosion-2.png");
        explosionCenterPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/center/center-explosion-2.png");
    }

    public float calculateTimeTillExplosion(GameData gameData) {
        timeSincePlacement += gameData.getDeltaTime();
        return timeTillExplosionInSeconds - timeSincePlacement;
    }


    public Path getFireExplosionTexturePath(Coordinates coord) {
        Coordinates originCoords = this.getCoordinates();
        GridPosition position = originCoords.getGridPosition();

        int dx = coord.getGridPosition().getX() - position.getX();
        int dy = coord.getGridPosition().getY() - position.getY();

        // Determine if the explosion is at the end or the middle of the blast radius
        boolean isEndOfBlastX = Math.abs(dx) == blastLength;
        boolean isEndOfBlastY = Math.abs(dy) == blastLength;

        // Center of the explosion
        if (dx == 0 && dy == 0) {
            return explosionCenterPath;
        }

        // Horizontal explosion
        if (dy == 0) {
            if (dx > 0) { // Right
                return isEndOfBlastX ? explosionRightPath : explosionMidHorizontalPath;
            } else if (dx < 0) { // Left
                return isEndOfBlastX ? explosionLeftPath : explosionMidHorizontalPath;
            }
        }

        // Vertical explosion
        if (dx == 0) {
            if (dy > 0) { // Up
                return isEndOfBlastY ? explosionUpPath : explosionMidVerticalPath;
            } else if (dy < 0) { // Down
                return isEndOfBlastY ? explosionDownPath : explosionMidVerticalPath;
            }
        }

        return explosionCenterPath;
    }

    public Collection<Coordinates> calculateBlastArea(World world) {
        IMap map = (IMap) world.getMap();
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
                Coordinates blastPos = new Coordinates(new GridPosition(x, y));
                blastArea.add(blastPos);
            }
        }

        return blastArea;
    }

    @Override
    public Path getActiveTexturePath(Integer key) {
        if (animators.get(key) == null) {
            return getTexturePath();
        }
        return animators.get(key).getCurrentTexturePath();
    }

    @Override
    public void addAnimator(Integer key, ITextureAnimator animator) {
        animators.put(key, animator);
    }

    @Override
    public HashMap<Integer, ITextureAnimator> getAnimators() {
        return animators;
    }

    @Override
    public void setAnimators(HashMap<Integer, ITextureAnimator> animators) {
        this.animators = animators;
    }

    public float getTimeSincePlacement() {
        return timeSincePlacement;
    }

    public void setTimeSincePlacement(float timeOfPlacement) {
        this.timeSincePlacement = timeOfPlacement;
    }

    public float getTimeTillExplosionInSeconds() {
        return timeTillExplosionInSeconds;
    }

    public void setTimeTillExplosionInSeconds(float timeTillExplosionInSeconds) {
        this.timeTillExplosionInSeconds = timeTillExplosionInSeconds;
    }

    public int getBlastLength() {
        return blastLength;
    }

    public void setBlastLength(int blastLength) {
        this.blastLength = blastLength;
    }

    @Override
    public int getDamagePoints() {
        return damagePoints;
    }

    @Override
    public void setDamagePoints(int damagePoints) {
        this.damagePoints = damagePoints;
    }
}
