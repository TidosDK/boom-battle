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
    private ITextureAnimator explosionAnimator;

    private ITextureAnimator explosionFireAnimator;



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
    public String getCurrentFireExplosionAnimatorPath(Coordinates coords) {
        Coordinates originCoords = this.getCoordinates();
        if (explosionFireAnimator == null) {
            return getTexturePath();
        } else {
            if (coords.equals(originCoords)) {
                return explosionFireAnimator.getCurrentImagePath(); // Center of explosion
            } else {
                return explosionFireAnimator.getCurrentImagePath(); // Extend this to support different images for different directions
            }
        }
    }

    public Collection<Coordinates> calculateBlastArea(World world) {
        IMap map = (IMap) world.getMap();
        GridPosition position = this.getCoordinates().getGridPosition();
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
