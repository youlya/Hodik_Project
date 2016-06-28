/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import java.util.ArrayList;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.w3c.dom.*;

/**
 *
 * @author alyx
 */
public class bad_robot extends robot{
        
    String type;
    ArrayList<String> cmd;
    
    public bad_robot(Field a, Interpretator in, Coordinate coord, int hp, Direction d, Scores sc, Unit r)
    {
        super(a,in,coord,hp,d,r,sc);
        type = "bad_robot"; 
        cmd = this.getComm();
    }
    
    
    @Override
    public void show_info()
    {
        super.show_info();
        System.out.println("Type: "+ type);
    }
    
    @Override
    public String getType() {
        return type;
    }
    
    @Override
    public int getDamage() {
        return 0;
    }
    private void setDamage()
    {
        //
        //switch(act_type)...
    }
    //добавление и удаление команд нам может понадобиться только при изменении КОНКРЕТНОГО типа
    public void addComm(String comm)//добавление одной команды 
    {
        ArrayList l = new ArrayList();
            l = TC.get(type);
            l.add(comm);
            TC.put(type, l);
          
    };

    public void addComm(ArrayList<String> comm)//добавление списка команд
    {
        ArrayList l = new ArrayList();
 
            l = TC.get(type);
            l.addAll(comm);
            TC.put(type, l);
    
    };
    public void delComm(int n)//удаление команды по номеру
    {
        ArrayList l = TC.get(type);
        l.remove(n);
        System.out.println("Command '"+n+"' deleted");
    };
    public void delComm(String n)
    {
        ArrayList l = TC.get(type);
        l.remove(n);
        System.out.println("Command '"+n+"' deleted");
    }
    
    public ArrayList<String> getComm()
    {
        ArrayList l = TC.get(type); 
        
        return l;
    }
    
   @Override
    public XMLobject toXML(XMLobject obj)
    {
        //тип
        Element r = obj.doc.createElement("ROBOTS");
        Element t = obj.doc.createElement("ROB");
        Attr attr = obj.doc.createAttribute("type");
        attr.setValue(type);              
        t.setAttributeNode(attr);
        //команды
        Element cmd = obj.doc.createElement("CMD");
        
        for(String key : TC.keySet())
            for(int i=0; i<TC.get(key).size(); i++)
            {
                cmd.setTextContent(TC.get(key).get(i));
                t.appendChild(cmd);
            }
        r.appendChild(t);
 
        //сохранить робота 
        obj.setcurrRobot(r);
        return obj;
    }
}