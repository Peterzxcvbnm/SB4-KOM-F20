/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Peterzxcvbnm
 */
public class ColourPart implements EntityPart{

    public float red;
    public float green;
    public float blue;
    public float alpha;
    
    public ColourPart(float r, float g, float b, float a){
        red =  r;
        green = g;
        blue = b;
        alpha = a;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
         }
    
}
