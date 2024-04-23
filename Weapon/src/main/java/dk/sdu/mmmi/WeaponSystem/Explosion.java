package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.Properties.GameData;

public class Explosion extends Entity {
    private float elapsedTime;
    private float animTime; // Duration of the explosion animation

    public Explosion(String texturePath, float x, float y, float width, float height, float animTime) {
        super(texturePath, width, height);
        this.setX(x); // Set the X coordinate
        this.setY(y); // Set the Y coordinate
        this.animTime = animTime; // Set the duration of the animation
    }

    public void setElapsedTime(float time) {
        elapsedTime = time;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public float getAnimTime() {
        return animTime;
    }




}


