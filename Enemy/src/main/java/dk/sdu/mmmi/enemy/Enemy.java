package dk.sdu.mmmi.enemy;
import dk.sdu.mmmi.common.data.entity.Entity;

import java.nio.file.Path;

public class Enemy extends Entity {
    public Enemy(Path texturePath, float width, float height) {
        super(texturePath, width, height);
    }
}
