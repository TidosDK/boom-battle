package dk.sdu.mmi.player;

public class PlayerPlugin {  // Implements IGamePluginService
    // private Entity player;
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
