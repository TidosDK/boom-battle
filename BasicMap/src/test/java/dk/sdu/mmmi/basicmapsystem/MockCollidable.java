package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.services.entityproperties.ICollidable;

public class MockCollidable extends Entity implements ICollidable {

    public MockCollidable() {
        super(null, 0, 0);
    }

}
