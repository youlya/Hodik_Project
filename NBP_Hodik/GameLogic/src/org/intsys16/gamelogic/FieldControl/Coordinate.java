/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.intsys16.gamelogic.FieldControl;

import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.w3c.dom.*;
/**
 *
 * @author NS
 */
public class Coordinate
{
    public int x;
    public int y;
    public Coordinate(){
        x=0;
        y=0;
    }
    public Coordinate (int cordx, int cordy)
    {
        x = cordx;
        y = cordy;
    }
    public int getX()
    {return x;}
    public int getY()
    {return y;}
    
    public void setX(int newx)
    {
        this.x = newx;
    }
    public void setY(int newy)
    {
        this.y = newy;
    }
    @Override
    public String toString()
    {
        return "("+x+"; "+y+")";
    }
    
    //координаты
    public XMLobject toXML(XMLobject obj)
    {
        Element coord = obj.doc.createElement("coordinates");
        Attr attr1 = obj.doc.createAttribute("x");
        attr1.setValue(x+"");
        coord.setAttributeNode(attr1);
        Attr attr2 = obj.doc.createAttribute("y");
        attr2.setValue(y+"");
        coord.setAttributeNode(attr2);
        
        obj.setcurrCoord(coord);
        return obj;
    }

@Override
public int hashCode() 
{
     return x + y;
}        
@Override
public boolean equals(Object obj)
{
    boolean result = false;
	if (obj instanceof Coordinate)
        {
            Coordinate another = (Coordinate)obj;
            if (this.getX() == another.getX() && this.getY() == another.getY())
            {
                result = true;
            }
	}
        return result;
}

}
