package dk.sdu.mmi.enemy;

import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.Entity;

public class EnemyPlugin implements IGamePluginService {
    private Entity player;
    public Enemy createPlayer() {
        return new Enemy();
    }

    // @Override
    public void start() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
