package dk.sdu.mmmi.common.services.Entity;

public interface IDamageable {
    int getLifepoints();
    void setLifepoints(int lifepoints);
    void removeLifepoints(int amount);
}
