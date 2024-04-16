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

    public Weapon(GameData gameData, String texturePath, float width, float height) {
        super(texturePath, width, height);

        if (!getITextureAnimatorController().isEmpty()) {
            ITextureAnimatorController animatorController = getITextureAnimatorController().stream().findFirst().get();

            if (animatorController != null) {
                explosionAnimator = animatorController.createTextureAnimator(gameData, "Weapon/src/main/resources/planted", 0, 5, 20f);
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
        } else {
            return explosionAnimator.getCurrentImagePath();
        }
    }

    public Collection<Coordinates> calculateBlastArea(World world) {
        IMap map = (IMap) world.getMap();
        GridPosition position = this.getCoordinates().getGridPosition();
        Collection<Coordinates> blastArea = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < this.blastLength; j++) {
                switch (i) {
                    case 0:
                        if (!map.isTileObstacle(position.getX() + j, position.getY())) {
                            blastArea.add(new Coordinates(position.getX() + j, position.getY()));
                        } else {
                            blastArea.add(new Coordinates(position.getX() + j, position.getY()));
                            break;
                        }
                    case 1:
                        if (!map.isTileObstacle(position.getX() - j, position.getY())) {
                            blastArea.add(new Coordinates(position.getX() - j, position.getY()));
                        } else {
                            blastArea.add(new Coordinates(position.getX() - j, position.getY()));
                            break;
                        }
                    case 2:
                        if (!map.isTileObstacle(position.getX(), position.getY() + j)) {
                            blastArea.add(new Coordinates(position.getX(), position.getY() + j));
                        } else {
                            blastArea.add(new Coordinates(position.getX(), position.getY() + j));
                            break;
                        }
                        break;
                    case 3:
                        if (!map.isTileObstacle(position.getX(), position.getY() - j)) {
                            blastArea.add(new Coordinates(position.getX(), position.getY() - j));
                        } else {
                            blastArea.add(new Coordinates(position.getX(), position.getY() - j));
                            break;
                        }
                }
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
