/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.XMLParser;

import org.intsys16.gamelogic.FieldControl.Coordinate;

/**
 *
 * @author yunna_u
 */
public class mobInfo 
{
    String act_type;            //act-type
    String type;            //type: bad robot "1" or obstacle "2"
    int x;
    int y;
    int hp;

    public mobInfo(String name, String type, int x, int y, int hp) 
    {      
        this.act_type = name;
        this.type = type;
        this.x=x;
        this.y=y;
        this.hp=hp;
    }
    
    public mobInfo(){}

    public void setCoords(Coordinate c) 
    {      
        x=c.getX();
        y=c.getY();
    }
}