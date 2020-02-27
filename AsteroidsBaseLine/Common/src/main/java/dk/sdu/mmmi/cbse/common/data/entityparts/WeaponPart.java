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
public class WeaponPart implements EntityPart{

    private float Cooldown = 0.1f;
    
    @Override
    public void process(GameData gameData, Entity entity) {
        Cooldown -= gameData.getDelta();
        if(Cooldown < 0){
            Cooldown = -1;
        }
    }
    
    public boolean isOffCooldown(){
        return Cooldown < 0;
    }
    
    public void Shoot(){
        Cooldown = 0.1f;
    }
}
