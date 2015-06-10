/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.FieldControl;
import java.util.HashMap;
import org.intsys16.gamelogic.XMLParser.XMLobject;
/**
 *
 * @author NS
 */
public class Field
{
    HashMap<Coordinate, Field_object> hex = new HashMap<>();
    public int width;
    public int height;
    public Field_object isFilled(Coordinate coord)
    {
        return hex.getOrDefault(coord, null);
    }
    public boolean endofField(Coordinate c)
    {  
      if(c.getX()==width || c.getY()==height) 
      {
        return false;  
      }
      else
      {
       return true;    
      }
    }
    public HashMap<Coordinate, Field_object> getHex()
    {
        return hex;
    }
    
    public Field()
    {

    }
    public void deleteFieldObject(Coordinate coord){
        hex.remove(coord);
    }
//    public Field(int width, int height) 
//    {
//		this.width = width;
//		this.height = height;
//    }
//    public Field(int l, int width, int height) 
//    {
//        level=l;
//		this.width = width;
//		this.height = height;
//    }   
    public int getWidth()
    {
        return this.width;
    }
    public int getHeight()
    {
        return this.height;
    }
    
    public XMLobject toXML(XMLobject obj)
    {
        for (Field_object o : hex.values()) 
        {
            if(o.getClass().getSimpleName().equalsIgnoreCase("good_robot") == false)
            {
                obj = o.toXML(obj);
            }
        }
        
        return obj;
    }
}
