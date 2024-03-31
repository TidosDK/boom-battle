package dk.sdu.mmi.player;

import dk.sdu.mmmi.common.services.IActor;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;

public class PlayerControlSystem implements IActor, IEntityProcessingService { // implements IDamageable
    private IWeapon[] weapons;

    // @Override
    public void process() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void placeWeapon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void takeDamage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void move(Enum direction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
