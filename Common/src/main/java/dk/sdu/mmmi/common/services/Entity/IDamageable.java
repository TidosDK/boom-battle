package dk.sdu.mmmi.common.services.Entity;

public interface IDamageable {
    int getHitpoints();
    void takeDamage(int amount);
}
