package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {
    
    /**
     * Adds the entity to the world based on the given game data.
     * The entity has to not currently be in the game and the game has to have been initialised. 
     * After the method has been called the entity has been added to the world.
     * @param gameData The current state of the game.
     * @param world The entities in the world currently.
     */
    void start(GameData gameData, World world);

    /**
     * Removes the entity from the game. The entity has to exist in the world to call the method.
     * After the method has been called, the entity has been removed from the world.
     * @param gameData Current game state.
     * @param world The entities in the world currently.
     */
    void stop(GameData gameData, World world);
}
