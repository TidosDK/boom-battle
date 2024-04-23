package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.weapon.IWeapon;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class Weapon extends Entity implements IWeapon {
    private float timeSincePlacement;
    private float timeTillExplosionInSeconds;
    private int damagePoints;
    private int blastLength;

    private float animTime;
    private ITextureAnimator explosionAnimator;

    private ITextureAnimator explosionFireAnimator;

    private String explosionBasePath = "Weapon/src/main/resources/explosion/";

    private ITextureAnimator explosionRightAnimator;
    private ITextureAnimator explosionLeftAnimator;
    private ITextureAnimator explosionUpAnimator;
    private ITextureAnimator explosionDownAnimator;
    private ITextureAnimator explosionMidHorizontalAnimator;
    private ITextureAnimator explosionMidVerticalAnimator;


    public Weapon(GameData gameData, Path texturePath, float width, float height) {
        super(texturePath, width, height);

        if (!getITextureAnimatorController().isEmpty()) {
            ITextureAnimatorController animatorController = getITextureAnimatorController().stream().findFirst().get();

            if (animatorController != null) {
                explosionAnimator = animatorController.createTextureAnimator(gameData, Paths.get("Weapon/src/main/resources/planted"), 0, 5, 20f);
                explosionFireAnimator = animatorController.createTextureAnimator(gameData, Paths.get("Weapon/src/main/resources/explosion/center"), 0, 4, 20f);
            }
        }
    }

    public float calculateTimeTillExplosion(GameData gameData) {
        timeSincePlacement += gameData.getDeltaTime();
        return timeTillExplosionInSeconds - timeSincePlacement;
    }

    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public Path getCurrentExplosionAnimatorPath() {
        if (explosionAnimator == null) {
            return getTexturePath();
        } else {
            return explosionAnimator.getCurrentTexturePath();
        }
    }


    public void createFireExplosionAnimators(GameData gameData) {

        if (!getITextureAnimatorController().isEmpty()) {
            ITextureAnimatorController animatorController = getITextureAnimatorController().stream().findFirst().get();

            if (animatorController != null) {
                // Create animation controller instance for each direction as according to directories
                explosionRightAnimator = animatorController.createTextureAnimator(gameData, Paths.get(explosionBasePath + "right"), 0, 4, getAnimTime() /* other parameters */);
                explosionLeftAnimator = animatorController.createTextureAnimator(gameData, Paths.get(explosionBasePath + "left"), 0, 4, getAnimTime() /* other parameters */);
                explosionUpAnimator = animatorController.createTextureAnimator(gameData, Paths.get(explosionBasePath + "up"), 0, 4, getAnimTime()/* other parameters */);
                explosionDownAnimator = animatorController.createTextureAnimator(gameData, Paths.get(explosionBasePath + "down"), 0, 4, getAnimTime()/* other parameters */);
                explosionMidHorizontalAnimator = animatorController.createTextureAnimator(gameData, Paths.get(explosionBasePath + "mid-horizontal"), 0, 4, getAnimTime()/* other parameters */);
                explosionMidVerticalAnimator = animatorController.createTextureAnimator(gameData, Paths.get(explosionBasePath + "mid-vertical"), 0, 4, getAnimTime()/* other parameters */);
            }
        }
    }

    public Path getFireExplosionTexturePath(Coordinates coord, World world) {
        Coordinates originCoords = this.getCoordinates();
        GridPosition position = originCoords.getGridPosition();

        int dx = coord.getGridPosition().getX() - position.getX();
        int dy = coord.getGridPosition().getY() - position.getY();

        // Determine if the explosion is at the end or the middle of the blast radius
        boolean isEndOfBlastX = Math.abs(dx) == blastLength;
        boolean isEndOfBlastY = Math.abs(dy) == blastLength;

        // Center of the explosion
        if (dx == 0 && dy == 0) {
            return Paths.get(explosionBasePath + "center/center-explosion-1.png"); // example for the first frame
        }

        // Horizontal explosion
        if (dy == 0) {
            if (dx > 0) { // Right
                return isEndOfBlastX ? explosionRightAnimator.getCurrentTexturePath() : explosionMidHorizontalAnimator.getCurrentTexturePath();
            } else if (dx < 0) { // Left
                return isEndOfBlastX ? explosionLeftAnimator.getCurrentTexturePath() : explosionMidHorizontalAnimator.getCurrentTexturePath();
            }
        }

        // Vertical explosion
        if (dx == 0) {
            if (dy > 0) { // Up
                return isEndOfBlastY ? explosionUpAnimator.getCurrentTexturePath() : explosionMidVerticalAnimator.getCurrentTexturePath();
            } else if (dy < 0) { // Down
                return isEndOfBlastY ? explosionDownAnimator.getCurrentTexturePath() : explosionMidVerticalAnimator.getCurrentTexturePath();
            }
        }
        return explosionFireAnimator.getCurrentTexturePath();
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

    public void setAnimTime(float animTime) {
        this.animTime = animTime;
    }

    public float getAnimTime() {
        return animTime;
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
