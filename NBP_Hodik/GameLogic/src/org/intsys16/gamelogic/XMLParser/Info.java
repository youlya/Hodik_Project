package org.intsys16.gamelogic.XMLParser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Field_object;
//
/**
 *
 * @author yunna_u
 */
public class Info 
{
    String robotName;
    public int levelNumber;
    public int x;
    public int y;  
    public int HP;
    public int score1;
    public int score2;
    public int score3;
    List<mobInfo> mob = new ArrayList();
    public Info(){};
    public Info(String name, int ln, Coordinate c, int h, int s1, int s2, int s3)
    {
        robotName=name;
        levelNumber=ln;
        x=c.getX();
        y=c.getY();
        HP=h;
        score1=s1;
        score2=s2;
        score3=s3;
    }
    
    public int getLevel()
    {
        return levelNumber;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getHP()
    {
        return HP;
    }
    
    public int getScore1()
    {
        return score1;
    }
    
    public int getScore2()
    {
        return score2;
    }
    
    public int getScore3()
    {
        return score3;
    }
    
    public List<mobInfo> getMobs()
    {
        return mob;
    }
    
    public void loadMobs(Field a)
    {
        HashMap<Coordinate, Field_object> objects=a.getHex();
        for (Coordinate key : objects.keySet()) {
            Field_object item=objects.get(key);
            mobInfo info=new mobInfo();
            info.type=item.getType();
            if (!"obstacle".equals(info.type))
            {
                info.act_type=item.getActtype();
                info.hp=item.getDamage();
            }
            info.setCoords(item.getCoord());
            mob.add(info);
        }
    }
}