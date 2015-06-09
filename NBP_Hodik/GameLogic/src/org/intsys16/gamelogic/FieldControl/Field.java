/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.FieldControl;
import java.util.HashMap;
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
        return hex.getOrDefault(hex, null);
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
}
