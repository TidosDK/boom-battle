package dk.sdu.mmi.enemy;

public class EnemyPlugin {  // Implements IGamePluginService
    // private Entity player;
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
