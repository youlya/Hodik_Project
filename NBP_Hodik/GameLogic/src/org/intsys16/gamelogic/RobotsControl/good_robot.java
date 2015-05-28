/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.Interpretator.Interpretator;

/**
 *
 * @author jbenua
 */
public class good_robot extends Field_object{
    public int HP;
    Unit robot;
    public Direction dir;
    public Scores score;
    public good_robot(Field a, Interpretator in, Coordinate coord, int h, Direction d, Unit r,Scores sc) //Scores sc
    {
        super(a, in, coord);
        HP=h;
        dir=d;
        robot=r;
        score=sc;
    }
    
    public void setCoords(Coordinate newC){
        c=newC;
    }
    
    @Override
    public void show_info()
    {
        super.show_info();
        System.out.println("Name: "+ robot.name);
        System.out.println("HP: " + HP);
    }

    @Override
    public String getType() {
        return "robot";
    }

    @Override
    public String getActtype() {
        return robot.name;
    }

    @Override
    public int getDamage() {
        return 0;
    }
    
//    toXML(ArrayList<>)
//    {
//        хз вообще что, но для Кати:)
//        имя робота, HP, соординаты
//    }
}
