package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import static org.junit.jupiter.api.Assertions.*;

class MovingPartTest {

    private MovingPart movePart;
    private Entity entity;
    private PositionPart posPart;
    private GameData gameData;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        posPart = new PositionPart(100, 100, 0);
        entity = new Entity();
        entity.add(posPart);
        gameData = new GameData();
        gameData.setDelta(1);
        gameData.setDisplayHeight(1000);
        gameData.setDisplayWidth(1000);
        movePart = new MovingPart(1, 10, 100, 1);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void moveForwardTest() {
        movePart.setUp(true);
        movePart.process(gameData, entity);
        assertEquals(109, posPart.getX());
    }

    @org.junit.jupiter.api.Test
    void noMoveTest() {
        movePart.process(gameData, entity);
        assertEquals(100, posPart.getX());
        assertEquals(100, posPart.getY());
    }

    @org.junit.jupiter.api.Test
    void rotateTest(){
        movePart.setLeft(true);
        movePart.process(gameData, entity);
        assertEquals(1, posPart.getRadians());
    }

    @org.junit.jupiter.api.Test
    void moveOutOfScreenTest(){
        gameData.setDisplayWidth(150);
        movePart.setUp(true);
        for(int i = 0; i< 8; i++){
            movePart.process(gameData, entity);
        }
        assertEquals(72, posPart.getX());
    }
}