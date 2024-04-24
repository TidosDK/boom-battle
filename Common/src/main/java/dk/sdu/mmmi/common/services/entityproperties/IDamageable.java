package dk.sdu.mmmi.common.services.entityproperties;

public interface IDamageable {
    int getLifepoints();
    void setLifepoints(int lifepoints);
    void removeLifepoints(int amount);
}
