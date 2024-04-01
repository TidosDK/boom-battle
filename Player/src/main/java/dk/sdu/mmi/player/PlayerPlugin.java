package dk.sdu.mmi.player;

import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.Entity;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;
    public Player createPlayer() {
        return new Player();
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
