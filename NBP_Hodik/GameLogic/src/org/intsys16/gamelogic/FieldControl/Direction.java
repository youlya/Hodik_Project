/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.FieldControl;

/**
 *
 * @author r545-4
 */
public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;
    public int deltaX() {
        switch (this) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            default:
                return 0;
        }
    }
    public int deltaY() {
        switch (this) {
            case UP:
                return -1;
            case DOWN:
                return 1;
            default:
                return 0;
        }
    }
}
