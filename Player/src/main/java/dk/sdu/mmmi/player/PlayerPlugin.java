package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.Entity.Entity;

public class PlayerPlugin implements IGamePluginService {
    private GameData gameData;
    private Entity player;

    public Player createPlayer() {
        return new Player(gameData, "Player/src/main/resources/up/up-7.png", 2.2f, 3.3f);
    }


    @Override
    public void start(World world, GameData gameData) {
        this.gameData = gameData;
        player = createPlayer();
        player.setY(0);
        player.setX(0);
        world.addEntity(player);
    }

    @Override
    public void stop(World world, GameData gameData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
