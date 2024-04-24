package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.entity.Entity;

public class EnemyPlugin implements IGamePluginService {
    private Entity player;
    public Enemy createPlayer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void start(World world, GameData gameData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stop(World world, GameData gameData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
