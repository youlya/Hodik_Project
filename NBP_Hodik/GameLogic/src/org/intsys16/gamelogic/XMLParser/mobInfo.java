/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.XMLParser;

/**
 *
 * @author yunna_u
 */
public class mobInfo 
{
    String name;            
    String type;            //type: bad robot or obstacle
    int x;
    int y;
    int hp;

    public mobInfo() 
    {      
        this.name = new String();
        this.type = new String();
    }
}
