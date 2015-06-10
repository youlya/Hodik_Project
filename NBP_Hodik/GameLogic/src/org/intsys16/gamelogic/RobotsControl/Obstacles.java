/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.w3c.dom.*;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
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
    
    @Override
    public void interact(good_robot gr)
    {
        gr.score.BumbedInto+=1;
        GraphicMapAPI.getGraphicMap().setBumbedInto(gr.score.getObs_sc());
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
    
    private int getChildType()  //для toXML
    {
        String type = this.getClass().getSimpleName();
        switch (type)
        {
            case "Liquid": return 1;
            case "Pit": return 2;
            case "Stone": return 3;
            //case "healBonus": return 4;
            default: return -1;
        }
    }
    
    @Override
    public XMLobject toXML(XMLobject obj)
    {
        //тип
        Element obstacle = obj.doc.createElement("obstacle");
        Attr attr = obj.doc.createAttribute("type");
        attr.setValue(this.getChildType()+"");              //как определять тип препятствия??
        obstacle.setAttributeNode(attr);
        //координаты
        obj = this.c.toXML(obj);
        Element coords = obj.getcurrCoord();
        obstacle.appendChild(coords);
        //заглушка для однородности файла XML
        Element hp = obj.doc.createElement("hp");
        Attr at = obj.doc.createAttribute("life");
        at.setValue(100+"");
        hp.setAttributeNode(at);
        obstacle.appendChild(hp);
        
        //добавить препятствие в список
        obj.addObst(obstacle);
        
        return obj;
    }
}





