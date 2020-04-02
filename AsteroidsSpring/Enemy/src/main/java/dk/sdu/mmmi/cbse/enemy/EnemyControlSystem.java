/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.DirectionEnum;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.EntityHitEvent;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.ShootBulletEvent;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.springframework.stereotype.Component;
/**
 *
 * @author Peterzxcvbnm
 */
@Component
public class EnemyControlSystem implements IEntityProcessingService {

    private DirectionEnum currentDirection;
    private int timeToChangeDirection = 0;
    private Random random = new Random();
    
    @Override
    public void process(GameData gameData, World world) {
        
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            if(timeToChangeDirection == 0){
                UpdateDirection();
            }else{
                timeToChangeDirection--;
            }
            
            switch(currentDirection){
                case up:
                    movingPart.setUp(true);
                    movingPart.setRight(false);
                    movingPart.setLeft(false);
                    break;
                case right:
                    movingPart.setUp(true);
                    movingPart.setRight(true);
                    movingPart.setLeft(false);
                    break;
                case left:
                    movingPart.setUp(true);
                    movingPart.setRight(false);
                    movingPart.setLeft(true);
                    break;
            }
            
            for(Event event: gameData.getEvents(EntityHitEvent.class, enemy.getID())){
               gameData.removeEvent(event);
               lifePart.setIsHit(true);
           }
            
            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);
            
            if(random.nextInt(100) < 5){
                gameData.addEvent(new ShootBulletEvent(enemy));
            }
            
            if(lifePart.getLife() <= 0){
                world.removeEntity(enemy);
            }

            updateShape(enemy);
        }
    }

    private void UpdateDirection() {
        timeToChangeDirection = random.nextInt(20) + 20;
        switch (random.nextInt(3)){
            case 0: currentDirection = DirectionEnum.up;
            break;
            case 1: currentDirection = DirectionEnum.left;
            timeToChangeDirection -= 20;
            break;
            case 2: currentDirection = DirectionEnum.right;
            timeToChangeDirection -= 20;
            break;
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
    
}
