/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;
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
}