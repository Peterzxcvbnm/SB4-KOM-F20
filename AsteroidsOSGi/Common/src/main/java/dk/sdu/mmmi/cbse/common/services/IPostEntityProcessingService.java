package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {
    /**
     * Process after entities have been processed. Happens after IEntityProcessingService.
     * Results in the game having been processed and moved forward.
     * @param gameData Current game state.
     * @param world CUrrent entities in the world.
     */
        void process(GameData gameData, World world);
}
