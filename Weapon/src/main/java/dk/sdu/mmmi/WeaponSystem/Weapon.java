package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.Entity.Coordinates;
import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.GridPosition;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeapon;
import dk.sdu.mmmi.common.services.Map.IMap;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;

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


    public Weapon(GameData gameData, String texturePath, float width, float height) {
        super(texturePath, width, height);

        if (!getITextureAnimatorController().isEmpty()) {
            ITextureAnimatorController animatorController = getITextureAnimatorController().stream().findFirst().get();

            if (animatorController != null) {
                explosionAnimator = animatorController.createTextureAnimator(gameData, "Weapon/src/main/resources/planted", 0, 5, 20f);
                explosionFireAnimator = animatorController.createTextureAnimator(gameData, "Weapon/src/main/resources/explosion/center", 0, 4, 20f);
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

    public String getCurrentExplosionAnimatorPath() {
        if (explosionAnimator == null) {
            return getTexturePath();
        } else  {
            return explosionAnimator.getCurrentImagePath();
        }
    }


    public void createFireExplosionAnimators(GameData gameData) {

        if (!getITextureAnimatorController().isEmpty()) {
            ITextureAnimatorController animatorController = getITextureAnimatorController().stream().findFirst().get();

            if (animatorController != null) {
                // Create animation controller instance for each direction as according to directories
                explosionRightAnimator = animatorController.createTextureAnimator(gameData, explosionBasePath + "right", 0, 4, getAnimTime() /* other parameters */);
                explosionLeftAnimator = animatorController.createTextureAnimator(gameData, explosionBasePath + "left", 0, 4, getAnimTime() /* other parameters */);
                explosionUpAnimator = animatorController.createTextureAnimator(gameData, explosionBasePath + "up", 0, 4, getAnimTime()/* other parameters */);
                explosionDownAnimator = animatorController.createTextureAnimator(gameData, explosionBasePath + "down", 0, 4, getAnimTime()/* other parameters */);
                explosionMidHorizontalAnimator = animatorController.createTextureAnimator(gameData, explosionBasePath + "mid-horizontal", 0, 4, getAnimTime()/* other parameters */);
                explosionMidVerticalAnimator = animatorController.createTextureAnimator(gameData, explosionBasePath + "mid-vertical", 0, 4, getAnimTime()/* other parameters */);
            }
        }
    }

    public String getFireExplosionTexturePath(Coordinates coord, World world) {
        Coordinates originCoords = this.getCoordinates();
        GridPosition position = originCoords.getGridPosition();

        int dx = coord.getGridPosition().getX() - position.getX();
        int dy = coord.getGridPosition().getY() - position.getY();

        // Determine if the explosion is at the end or the middle of the blast radius
        boolean isEndOfBlastX = Math.abs(dx) == blastLength;
        boolean isEndOfBlastY = Math.abs(dy) == blastLength;

        // Center of the explosion
        if (dx == 0 && dy == 0) {
            return explosionBasePath + "center/center-explosion-1.png"; // example for the first frame
        }

        // Horizontal explosion
        if (dy == 0) {
            if (dx > 0) { // Right
                return isEndOfBlastX ? explosionRightAnimator.getCurrentImagePath() : explosionMidHorizontalAnimator.getCurrentImagePath();
            } else if (dx < 0) { // Left
                return isEndOfBlastX ? explosionLeftAnimator.getCurrentImagePath() : explosionMidHorizontalAnimator.getCurrentImagePath();
            }
        }

        // Vertical explosion
        if (dx == 0) {
            if (dy > 0) { // Up
                return isEndOfBlastY ? explosionUpAnimator.getCurrentImagePath() : explosionMidVerticalAnimator.getCurrentImagePath();
            } else if (dy < 0) { // Down
                return isEndOfBlastY ? explosionDownAnimator.getCurrentImagePath() : explosionMidVerticalAnimator.getCurrentImagePath();
            }
        }
        return explosionFireAnimator.getCurrentImagePath();
    }

    public Collection<Coordinates> calculateBlastArea(World world) {
        IMap map = (IMap) world.getMap();
        GridPosition position = this.getCoordinates().getGridPosition();
        System.out.println("Start position var inside calculateBlastArea: "+position.getX()+","+ position.getY());
        Collection<Coordinates> blastArea = new ArrayList<>();

        // Add the origin of the explosion
        blastArea.add(new Coordinates(position.getX(), position.getY()));

        // Loop to add coordinates in four directions from the bomb's position
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // right, left, up, down

        for (int[] direction : directions) {
            for (int j = 1; j <= this.blastLength; j++) {
                int x = position.getX() + j * direction[0];
                int y = position.getY() + j * direction[1];

                // If the next tile in the direction is an obstacle, stop adding to that direction
                if (map.isTileObstacle(x, y)) {
                    break;
                }
                blastArea.add(new Coordinates(x, y));
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
