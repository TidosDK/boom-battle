package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.Coordinates;
import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.services.IWeapon;

import java.util.Collection;

public class Weapon extends Entity implements IWeapon {
    private float timeSincePlacement;
    private float timeTillExplosionInSeconds;
    private int damagePoints;
    private int blastLength;

    public Weapon(String texturePath, float width, float height) {
        super(texturePath, width, height);
    }

    public float calculateTimeTillExplosion(GameData gameData) {
        timeSincePlacement += gameData.getDeltaTime();
        return timeTillExplosionInSeconds - timeSincePlacement;
    }

    public Collection<Coordinates> calculateBlastArea() {
        throw new UnsupportedOperationException();
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
