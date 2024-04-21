package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.Entity.Entity;

public class Explosion extends Entity {
    public Explosion(String texturePath, float x, float y, float width, float height) {
        super(texturePath, width, height);
        this.setX(x); // Set the X coordinate
        this.setY(y); // Set the Y coordinate
    }
}




