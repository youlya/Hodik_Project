/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.FieldControl;

import java.util.HashMap;

/**
 *
 * @author NS
 */
class Cell extends HashMap<Coordinate,Field_object>
{
        Field_object get(int x ,int y)
        {
            Coordinate pos = new Coordinate(x ,y);
            return this.get(pos);
        }
    public Cell newcell=new Cell();
    public Field_object[] getallCell()
    {
        return this.newcell.values().toArray(new Field_object[0]);
    }
}
