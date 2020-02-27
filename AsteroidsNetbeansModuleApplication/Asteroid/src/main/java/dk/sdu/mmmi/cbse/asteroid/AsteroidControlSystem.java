/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.DirectionEnum;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTypes;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.EntityHitEvent;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.SpawnAsteroidEvent;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Peterzxcvbnm
 */

@ServiceProviders(value = { @ServiceProvider( service = IEntityProcessingService.class)})
public class AsteroidControlSystem implements IEntityProcessingService {

    private Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        ProcessExistingAsteroids(world, gameData);

        for (Event event : gameData.getEvents(EntityHitEvent.class)) {
            Entity sourceAsteroid = event.getSource();
            if (sourceAsteroid.type == EntityTypes.ASTEROID) {
                world.removeEntity(sourceAsteroid);
                if (((Asteroid) sourceAsteroid).size > 2) {
                    gameData.addEvent(new SpawnAsteroidEvent(event.getSource()));
                }
                gameData.removeEvent(event);
            }
        }

        RandomlySpawnAsteroid(gameData, world);
        ProcessSpawnAsteroidEvent(gameData, world);
    }

    private void RandomlySpawnAsteroid(GameData gameData, World world) {
        if (random.nextInt(250) < 1) {
            Entity asteroid = AsteroidPlugin.createAsteroid(gameData);
            world.addEntity(asteroid);
        }
    }

    private void ProcessExistingAsteroids(World world, GameData gameData) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            updateShape((Asteroid) asteroid);
        }
    }

    private void ProcessSpawnAsteroidEvent(GameData gameData, World world) {
        for (Event event : gameData.getEvents(SpawnAsteroidEvent.class)) {
            Entity asteroid = AsteroidPlugin.createAsteroid(gameData, (SpawnAsteroidEvent) event);
            world.addEntity(asteroid);
            Entity asteroid1 = AsteroidPlugin.createAsteroid(gameData, (SpawnAsteroidEvent) event);
            world.addEntity(asteroid1);
            gameData.removeEvent(event);
        }
    }

    private void updateShape(Asteroid entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        int baseSize = 2;

        shapex[0] = (float) (x);
        shapey[0] = (float) (y - (baseSize + entity.shapeMultipliers[0]) * entity.size);

        shapex[1] = (float) (x + Math.cos(3.1415f / 4) * (baseSize + entity.shapeMultipliers[1]) * entity.size);
        shapey[1] = (float) (y - Math.sin(3.1415f / 4) * (baseSize + entity.shapeMultipliers[1]) * entity.size);

        shapex[2] = (float) (x + (baseSize + entity.shapeMultipliers[2]) * entity.size);
        shapey[2] = (float) (y);

        shapex[3] = (float) (x + Math.cos(3.1415f / 4) * (baseSize + entity.shapeMultipliers[3]) * entity.size);
        shapey[3] = (float) (y + Math.sin(3.1415f / 4) * (baseSize + entity.shapeMultipliers[3]) * entity.size);

        shapex[4] = (float) (x);
        shapey[4] = (float) (y + (baseSize + entity.shapeMultipliers[4]) * entity.size);

        shapex[5] = (float) (x - Math.cos(3.1415f / 4) * (baseSize + entity.shapeMultipliers[5]) * entity.size);
        shapey[5] = (float) (y + Math.sin(3.1415f / 4) * (baseSize + entity.shapeMultipliers[5]) * entity.size);

        shapex[6] = (float) (x - (baseSize + entity.shapeMultipliers[6]) * entity.size);
        shapey[6] = (float) (y);

        shapex[7] = (float) (x - Math.cos(3.1415f / 4) * (baseSize + entity.shapeMultipliers[7]) * entity.size);
        shapey[7] = (float) (y - Math.sin(3.1415f / 4) * (baseSize + entity.shapeMultipliers[7]) * entity.size);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
