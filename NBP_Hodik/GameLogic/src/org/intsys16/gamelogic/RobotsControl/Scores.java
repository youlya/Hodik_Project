/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.w3c.dom.*;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
/**
 *
 * @author r545-2 Syzko Anastasia
 */

public class Scores {

   public int StepScore;
  public int BumbedInto; //счётчик препятствий, на которые попал робот(кстати
   public int Eaten;
  
  public Scores()
  {
      StepScore = 0;
      BumbedInto = 0;
      Eaten = 0;
  }
  
   
 public int getEat_sc ()
  {
  return Eaten;
  }
 
    public int getObs_sc ()
  {
return BumbedInto;
  }
     public int get_Stepsc ()
  {
  return StepScore;
  }
    public XMLobject toXML(XMLobject obj)
    {
        Element Score = obj.doc.createElement("score");
        Attr attr1 = obj.doc.createAttribute("s1");
        attr1.setValue(StepScore+"");
        Score.setAttributeNode(attr1);
        Attr attr2 = obj.doc.createAttribute("s2");
        attr2.setValue(BumbedInto+"");
        Score.setAttributeNode(attr2);
        Attr attr3 = obj.doc.createAttribute("s3");
        attr3.setValue(Eaten+"");
        Score.setAttributeNode(attr3);
        //добавить очки
        obj.setcurrScore(Score);
        
        return obj;
    }
}