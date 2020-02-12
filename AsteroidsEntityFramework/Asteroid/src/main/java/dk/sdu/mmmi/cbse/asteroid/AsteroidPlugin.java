/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.SpawnAsteroidEvent;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peterzxcvbnm
 */
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
        float x = gameData.getDisplayWidth() / 4;
        float y = gameData.getDisplayHeight() / 4;
        float radians = 3.1415f / 2;

        Entity asteroid = new Asteroid();
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.setColour(200, 200, 200, 1);

        return asteroid;
    }
    
    protected static Entity createAsteroid(GameData gameData, SpawnAsteroidEvent event) {
        Asteroid source = (Asteroid) event.getSource();
        PositionPart sourcePositionPart = null;
        try {
            sourcePositionPart = source.getPart(Class.forName("PositionPart"));
        } catch (ClassNotFoundException ex) {
            System.out.println("Could not find class: PositionPart");
            ex.printStackTrace();
        }
        float deacceleration = 10;
        float acceleration = 1000;
        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = sourcePositionPart.getX();
        float y = sourcePositionPart.getY();
        float radians = sourcePositionPart.getRadians() + random.nextFloat() * 2 * (float) Math.PI;

        Entity asteroid = new Asteroid();
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.setColour(200, 200, 200, 1);

        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : asteroids) {
            world.removeEntity(asteroid);
        }
    }

}
