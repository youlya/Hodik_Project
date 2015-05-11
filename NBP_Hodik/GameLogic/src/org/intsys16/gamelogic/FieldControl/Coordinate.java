/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.intsys16.gamelogic.FieldControl;

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
