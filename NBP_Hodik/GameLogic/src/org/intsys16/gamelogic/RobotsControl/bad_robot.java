/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.Interpretator.Interpretator;

/**
 *
 * @author jbenua
 */
public class bad_robot extends Field_object{
    String act_type;
    int damage=0;
    public bad_robot(Field f, Interpretator in, Coordinate coord, String type)
    {
        super(f, in, coord);
        act_type=type;
        this.setDamage();
    }
    @Override
    public String getActtype(){
        return act_type;
    }
    @Override
    public int getDamage(){
        return damage;
    }
    private void setDamage()
    {
        //
        //switch(act_type)...
    }
    
    
    @Override
    public void show_info()
    {
        super.show_info();
        System.out.println("damage: " + damage);
    }

    @Override
    public String getType() {
        return "mob";
    }
//    toXML(ArrayList<>)
//    {
//        хз вообще что, но для Кати:)
//        act_type, damage, coord
//    }
}
