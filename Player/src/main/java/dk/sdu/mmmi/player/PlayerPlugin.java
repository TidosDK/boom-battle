package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.data.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.Entity;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;

    public Player createPlayer() {
//        return new Player("/Users/mj/Documents/[9] Schule/Semester 4/SP4/boom-battle/Player/src/main/resources/personLeft1.png", 2f, 2f);
        return new Player("Player/src/main/resources/personLeft1.png", 2f, 2f);
    }


    @Override
    public void start(World world, GameData gameData) {
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