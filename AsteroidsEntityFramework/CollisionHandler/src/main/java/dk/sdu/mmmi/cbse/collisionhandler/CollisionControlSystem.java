/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionhandler;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.EntityHitEvent;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author Peterzxcvbnm
 */
public class CollisionControlSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (HasCollided(entity1, entity2)) {
                    if (entity1.type != entity2.type) {
                        System.out.println(entity1.type + " has collided with " + entity2.type);
                        gameData.addEvent(new EntityHitEvent(entity1));
                        gameData.addEvent(new EntityHitEvent(entity2));
                    }
                }
            }
        }
    }

    private boolean HasCollided(Entity entity1, Entity entity2) {
        PositionPart entity1Pos = entity1.getPart(PositionPart.class);
        PositionPart entity2Pos = entity2.getPart(PositionPart.class);
        
        float dx = entity1Pos.getX() - entity2Pos.getX();
        float dy = entity1Pos.getY() - entity2Pos.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < entity1.getRadius() + entity2.getRadius();
    }

}
