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
import org.intsys16.gamelogic.RobotsControl.Scores;
//import robots.good_robot;

/**
 *
 * @author Lenus1k
 */
public class healthBonus extends Field_object {
    int hp;
    
    public healthBonus(Field a, Interpretator in, Coordinate coord, int hp)
    {        
        super (a,in,coord);
        this.hp=hp;        
    };
    
    public void healRobot (good_robot gr)
    { 
       gr.score.Eaten+=1;
          gr.HP+=hp;
    }
    
    @Override
    public void show_info()
    {
        super.show_info();
        System.out.println("Health bonus: +" + this.hp +" hp");
    }

    @Override
    public String getType() {
        return "health";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getActtype() {
        return "none";
    }
    
    @Override
    public int getDamage() {
        //return -hp;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}





