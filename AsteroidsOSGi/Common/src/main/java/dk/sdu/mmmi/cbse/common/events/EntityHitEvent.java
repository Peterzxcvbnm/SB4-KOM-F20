/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.events;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Peterzxcvbnm
 */
public class EntityHitEvent extends Event{
    
    public EntityHitEvent(Entity source) {
        super(source);
    }
    
}
