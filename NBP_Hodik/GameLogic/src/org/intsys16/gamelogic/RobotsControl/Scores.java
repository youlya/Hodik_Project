/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;
import java.io.File;
import java.util.ArrayList;
/**
 *
 * @author r545-2 Syzko Anastasia
 */

public class Scores {

   public int StepScore=0;
  public int BumbedInto=0; //счётчик препятствий, на которые попал робот(кстати,
  //необязательно это число меньше или равно числу имеющихся препятствий :) )
  public int Eaten=0; // для бонусов
 // good_robot gr
  
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
}
 
  
