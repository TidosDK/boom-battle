package dk.sdu.mmmi.common.services;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;

public interface IGamePluginService {
    void start(World world, GameData gameData);

    void stop(World world, GameData gameData);
}
