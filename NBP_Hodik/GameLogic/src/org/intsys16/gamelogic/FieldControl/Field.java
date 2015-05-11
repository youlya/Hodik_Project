/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.FieldControl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.HashMap;
/**
 *
 * @author NS
 */
public class Field
{
    int level=0;
    HashMap<Coordinate, Field_object> hex = new HashMap<>();
    public int width;
    public int height;

    public boolean isFilled(Coordinate coord)
    {
        return hex.get(coord)==null;
    }

    public Field(int width, int height) 
    {
		this.width = width;
		this.height = height;
    }
    public Field(int l, int width, int height) 
    {
        level=l;
		this.width = width;
		this.height = height;
    }   
   

 
    public int getWidth()
    {
        return this.width;
    }
    public int getHeight()
    {
        return this.height;
    }
}

//////saved my old one just in case ///////////////
    /*public Field(Coordinate F)
    {
        myField = F;
        fieldheight = myField.getX();
        fieldwidth = myField.getY();
       // createField(fieldheight,fieldwidth);
    
    }
    void createField(int x,int y)
    {
       Coordinate coord = new Coordinate(x,y);
    }
   
*/

 ////////////////////check it later/was writed by old group////////////////////////////
  /*
File fld_create_obst(File F)
    {
        System.out.println("Здесь создается поле из файла");
        for(int i=0;i<h;i++)
            for(int j=0;j<w;j++)
            {
                obstacles.add(new Coordinate(i,j,0));
            }
        for(int i=0;i<obstacles.size();i++) obstacles<i>.toString();
        return F;
    }
    
    void fld_move_frame(int horizontal, int vertical)
    {
        this.coordX += horizontal;
        this.coordY += vertical;
    }
}*/  
