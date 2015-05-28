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
   public int init; // исходные очки 0 или нек-ое число?
   public int StepScore;
  public int BumbedInto; //счётчик препятствий, на которые попал робот(кстати,
  //необязательно это число меньше или равно числу имеющихся препятствий :) )
  public int Eaten; // для бонусов
 // good_robot gr
  
public Scores()
{
    init=0;
    StepScore=0;
    BumbedInto=0;
    Eaten=0;
}


  
}
//добавить проверку на достижение коордитаны-цели
// добавить окно вывода результата?