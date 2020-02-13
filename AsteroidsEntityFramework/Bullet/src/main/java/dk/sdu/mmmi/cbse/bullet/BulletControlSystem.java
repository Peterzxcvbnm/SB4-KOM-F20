/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.BulletExpiredEvent;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.ShootBulletEvent;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author Peterzxcvbnm
 */
public class BulletControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        ProcessExistingBullets(world, gameData);
        SpawnNewBullets(gameData, world);
        CleanUpExpiredBullets(gameData, world);
    }

    private void CleanUpExpiredBullets(GameData gameData, World world) {
        for(Event event: gameData.getEvents(BulletExpiredEvent.class)){
            world.removeEntity(event.getSource());
            gameData.removeEvent(event);
        }
    }

    private void SpawnNewBullets(GameData gameData, World world) {
        for(Event event: gameData.getEvents(ShootBulletEvent.class)){
            Entity asteroid = BulletPlugin.createBullet(gameData, (ShootBulletEvent) event);
            world.addEntity(asteroid);
            gameData.removeEvent(event);
        }
    }

    private void ProcessExistingBullets(World world, GameData gameData) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            LifePart lifePart = bullet.getPart(LifePart.class);

            movingPart.setUp(true);

            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
            lifePart.process(gameData, bullet);
            
            if(lifePart.getExpiration() < 0){
                gameData.addEvent(new BulletExpiredEvent(bullet));
            }

            updateShape(bullet);
            
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        int baseSize = 1;

        shapex[0] = (float) (x);
        shapey[0] = (float) (y - (baseSize));

        shapex[1] = (float) (x + Math.cos(3.1415f / 4) * (baseSize));
        shapey[1] = (float) (y - Math.sin(3.1415f / 4) * (baseSize));

        shapex[2] = (float) (x + (baseSize));
        shapey[2] = (float) (y);

        shapex[3] = (float) (x + Math.cos(3.1415f / 4) * (baseSize));
        shapey[3] = (float) (y + Math.sin(3.1415f / 4) * (baseSize));

        shapex[4] = (float) (x);
        shapey[4] = (float) (y + (baseSize));

        shapex[5] = (float) (x - Math.cos(3.1415f / 4) * (baseSize));
        shapey[5] = (float) (y + Math.sin(3.1415f / 4) * (baseSize));

        shapex[6] = (float) (x - (baseSize));
        shapey[6] = (float) (y);

        shapex[7] = (float) (x - Math.cos(3.1415f / 4) * (baseSize));
        shapey[7] = (float) (y - Math.sin(3.1415f / 4) * (baseSize));

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
