package dk.sdu.mmmi.common.services.Entity;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;

public interface IEntityProcessingService {
        /**
         * This method is used to process the entities in the game.
         * @param world The world that the entities are in.
         * @param gameData The data that is used in the game.
         */
        void process(World world, GameData gameData);
}
