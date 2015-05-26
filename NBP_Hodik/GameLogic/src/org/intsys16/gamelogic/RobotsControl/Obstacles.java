/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Coordinate;
/**
 *
 * @author Lenus1k
 */
public class Obstacles extends Field_object {
    
    int damage;
    public Obstacles(Field a, Coordinate coord, int dmg)
    {
        super (a,/*in*/ null,coord);
        this.damage=dmg;
    }
    
    public void damageRobot(good_robot gr)
    {//gr.sc.Bump+=1;
        gr.HP-=damage;
    }
    
    @Override
    public void show_info()
    {
        super.show_info();
    }

    @Override
    public String getType() {
        return "obstacle";
    }

    @Override
    public String getActtype() {
        return "none";
    }

    @Override
    public int getDamage() {
        return damage;
    }
}

//extensions for Obstacle class







