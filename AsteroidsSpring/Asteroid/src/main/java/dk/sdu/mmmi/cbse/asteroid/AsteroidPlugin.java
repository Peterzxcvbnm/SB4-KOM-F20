/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTypes;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.SpawnAsteroidEvent;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peterzxcvbnm
 */
@Component
public class AsteroidPlugin implements IGamePluginService {

    private ArrayList<Entity> asteroids = new ArrayList<>();
    private static Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        asteroids.add(asteroid);
        world.addEntity(asteroid);
    }

    protected static Entity createAsteroid(GameData gameData) {
        float deacceleration = 10;
        float acceleration = 1000;
        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = 0;
        float y = 0;
        switch (random.nextInt(4)) {
            case 0:
                x = 0;
                y = gameData.getDisplayHeight() * random.nextFloat();
                break;
            case 1:
                x = gameData.getDisplayWidth();
                y = gameData.getDisplayHeight() * random.nextFloat();
                break;
            case 2:
                x = gameData.getDisplayWidth() * random.nextFloat();
                y = 0;
                break;
            case 3:
                x = gameData.getDisplayWidth() * random.nextFloat();
                y = gameData.getDisplayHeight() * random.nextFloat();
                break;
        }

        float radians = random.nextFloat() * (float) Math.PI * 2;

        Asteroid asteroid = new Asteroid();
        asteroid.type = EntityTypes.ASTEROID;
        asteroid.size = 8;
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.setColour(0.8f, 0.8f, 0.8f, 1);
        asteroid.setRadius(10);

        return asteroid;
    }

    protected static Entity createAsteroid(GameData gameData, SpawnAsteroidEvent event) {
        Asteroid source = (Asteroid) event.getSource();
        PositionPart sourcePositionPart = null;
        sourcePositionPart = source.getPart(PositionPart.class);

        float deacceleration = 10;
        float acceleration = 1000;
        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = sourcePositionPart.getX();
        float y = sourcePositionPart.getY();
        float radians = sourcePositionPart.getRadians() + random.nextFloat() * 2 * (float) Math.PI;

        Asteroid asteroid = new Asteroid();
        asteroid.type = EntityTypes.ASTEROID;
        asteroid.size = source.size / 2;
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.setColour(0.8f, 0.8f, 0.8f, 1);
        asteroid.setRadius(2 + asteroid.size);

        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : asteroids) {
            world.removeEntity(asteroid);
        }
    }

}
