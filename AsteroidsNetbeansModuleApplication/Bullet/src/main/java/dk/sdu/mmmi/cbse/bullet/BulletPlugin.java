/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTypes;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColourPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.ShootBulletEvent;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Peterzxcvbnm
 */
@ServiceProviders(value = { @ServiceProvider( service = IGamePluginService.class)})

public class BulletPlugin implements IGamePluginService{

    @Override
    public void start(GameData gameData, World world) {
     }
    
    protected static Entity createBullet(GameData gameData, ShootBulletEvent event) {
        Entity source = (Entity) event.getSource();
        PositionPart sourcePositionPart =  source.getPart(PositionPart.class);

        float deacceleration = 10;
        float acceleration = 1000000;
        float maxSpeed = 400;
        float rotationSpeed = 5;
        float radians = sourcePositionPart.getRadians();
        float x = sourcePositionPart.getX() + (float) Math.cos(radians) * (source.getRadius() + 4);
        float y = sourcePositionPart.getY() + (float) Math.sin(radians) * (source.getRadius() + 4);
        

        Entity bullet = new Bullet();
        bullet.type = EntityTypes.BULLET;
        bullet.setRadius(1);
        bullet.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new LifePart(1, 2));
        bullet.add(new ColourPart(1, 1, 1, 1));

        return bullet;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            world.removeEntity(bullet);
        }
    }
    
}
