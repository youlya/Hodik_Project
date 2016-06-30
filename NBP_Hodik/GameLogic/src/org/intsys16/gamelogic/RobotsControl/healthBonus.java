/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;
import java.util.ArrayList;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.RobotsControl.Scores;
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
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
    
    @Override
    public void interact (robot gr)
    { 
          gr.score.setEat_sc(gr.score.getEat_sc() + 1);
          GraphicMapAPI.getGraphicMap().setEaten(gr.score.getEat_sc());
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
    
    @Override
    public XMLobject toXML(XMLobject obj)
    {
        //доделать
        return obj;
    }

    @Override
    public ArrayList<Field_object> getObjects() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}





