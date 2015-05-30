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
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.w3c.dom.*;

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

    @Override
    public XMLobject toXML(XMLobject obj)
    {
        //act_type
        Element mob = obj.doc.createElement("mob");
        Attr attr1 = obj.doc.createAttribute("type");
        attr1.setValue(1+"");                           //как определять тип моба???
        mob.setAttributeNode(attr1);
        Attr attr2 = obj.doc.createAttribute("name");
        attr2.setValue(act_type);
        mob.setAttributeNode(attr2);
        //координаты
        obj = this.c.toXML(obj);
        Element coords = obj.getcurrCoord();
        mob.appendChild(coords);
        //заглушка для однородности файла XML
        Element hp = obj.doc.createElement("hp");
        Attr at = obj.doc.createAttribute("life");
        at.setValue(100+"");
        hp.setAttributeNode(at);
        mob.appendChild(hp);
        
        //добавить моба в список
        obj.addMob(mob);
        
        return obj;
    }
}
