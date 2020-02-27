/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTypes;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Peterzxcvbnm
 */
@ServiceProviders(value = { @ServiceProvider( service = IGamePluginService.class)})
public class EnemyPlugin implements IGamePluginService{

    private Entity enemy;
    
    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }
    
      private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 4;
        float y = gameData.getDisplayHeight() / 4;
        float radians = 3.1415f / 2;
        
        Entity enemyShip = new Enemy();
        enemyShip.type = EntityTypes.ENEMY;
        enemyShip.setRadius(8);
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(5, 10000f));
        enemyShip.setColour(0.8f, 0, 0, 1);
        
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
    
}
