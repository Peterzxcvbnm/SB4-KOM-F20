/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.util.Random;

/**
 *
 * @author Peterzxcvbnm
 */
public class Asteroid extends Entity {
    
    public int size = 1;
    public float[] shapeMultipliers = new float[8];
    private Random random = new Random();
    
    public Asteroid(){
        super.setShapeX(new float[8]);
        super.setShapeY(new float[8]);
        for(float no: shapeMultipliers){
            no = random.nextFloat() * 2;
        }
    }
    
}
