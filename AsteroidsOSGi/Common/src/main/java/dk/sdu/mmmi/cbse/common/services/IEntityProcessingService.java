package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     * Process the game and move it forward. The game has to have been initialised and started.
     * @param gameData The current state of the game.
     * @param world The current entities in the world.
     */
    void process(GameData gameData, World world);
}
