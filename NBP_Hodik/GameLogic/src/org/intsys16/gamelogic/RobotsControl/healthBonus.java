/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Coordinate;

//import robots.good_robot;

/**
 *
 * @author Lenus1k
 */
public class healthBonus extends Field_object {
    int hp;
    
    public healthBonus(Field a, Interpretator in, Coordinate coord, int hp)
    {
        /** @debug 
        * super (a,in,coord); */
        this.hp=hp;
    };
    
    public void healRobot (good_robot gr)
    {
        gr.xp+=hp;
    }
    
    @Override
    public void show_info()
    {
        super.show_info();
        System.out.println("Health bonus: +" + this.hp +" hp");
    }
}

class smallHealth extends healthBonus{
    public smallHealth (Field a, Interpretator in, Coordinate coord){
        super (a,in,coord,5);
    }
}

class mediumHealth extends healthBonus{
    public mediumHealth (Field a, Interpretator in, Coordinate coord){
        super (a,in,coord,15);
    }
}

class largeHealth extends healthBonus{
    public largeHealth (Field a, Interpretator in, Coordinate coord){
        super (a,in,coord,25);
    }
}