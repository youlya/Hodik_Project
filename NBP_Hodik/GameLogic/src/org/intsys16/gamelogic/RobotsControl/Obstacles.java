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
/**
 *
 * @author Lenus1k
 */
public class Obstacles extends Field_object {
    
    int damage;
    public Obstacles(Field a, Interpretator in, Coordinate coord, int dmg)
    {
       /* super (a,in,coord);*/
        this.damage=dmg;
    }
    
    public void damageRobot(good_robot gr)
    {//gr.sc.Bump+=1;
        gr.xp-=damage;
    }
    
    @Override
    public void show_info()
    {
        super.show_info();
    }

   /* @Override
    public String getType() {
        return "obstacle";
    }

    @Override
    public String getActtype() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getDamage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}

//extensions for Obstacle class

class Stone extends Obstacles{
    public Stone (Field a, Interpretator in, Coordinate coord){
        super (a,in,coord,10);
    }
}

class Pit extends Obstacles{
    public Pit (Field a, Interpretator in, Coordinate coord){
        super (a,in,coord,25);
    }
}


class Liquid extends Obstacles{
    public Liquid (Field a, Interpretator in, Coordinate coord){
        //actually this thing should kill a robot instantly,
        //but it should appear less
        super (a,in,coord,100);   
    }
}
