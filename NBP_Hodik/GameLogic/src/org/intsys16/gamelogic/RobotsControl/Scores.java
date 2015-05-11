/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;
import java.io.File;
import java.util.ArrayList;
import robots.good_robot;
/**
 *
 * @author r545-2 Syzko Anastasia
 */
//Связь ПОЛЕ(Планета?), знать свойства поля, цели?
// загружать из данных о поле кол-во препятствий, "плюшек" врагов? Где они хранятся?
// Цель-координата хранится в параметрах каждого поля
// Как передать прогресс уровня для сохранения?
//  1) Надо хранить положение робота на поле
//  2) Хранить

// Если на поле несколько роботов?
public class Scores {
   public int init=0; // исходные очки 0 или нек-ое число?
   public int StepScore;
  public int BumbedInto; //счётчик препятствий, на которые попал робот(кстати,
  //необязательно это число меньше или равно числу имеющихся препятствий :) )
  public int Eaten; // для бонусов
 // good_robot gr
  
  // Stepscore берётся из действий робота
  public void   AddScore( int init){
  init=init+StepScore;
  }
}
//добавить проверку на достижение коордитаны-цели
// добавить окно вывода результата?