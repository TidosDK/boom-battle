package dk.sdu.mmmi.main.CustomStages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.common.data.entity.Entity;

import java.util.Collection;
import java.util.List;

public interface ICustomStage {

    void addEntity(Entity entity);

    void removeEntity(Entity entity);

    void clearEntities();

    List<Entity> getEntities();

    void setEntities(Collection<Entity> entities);

    float getXOffset();

    float getYOffset();

    void drawStage(SpriteBatch batch);
}
