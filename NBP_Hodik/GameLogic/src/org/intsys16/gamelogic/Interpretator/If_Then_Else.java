/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;

/**
 *
 * @author r545-12
 */
public class If_Then_Else implements CMD {

    Boolean _if;
    CMD _then;
    CMD _else;
    
    If_Then_Else(Boolean condition,CMD a, CMD b){
        _if= condition;
        _then=a;
        _else=b;      
    }
    
    @Override
    public String Run() {
        return If_Then_Else();
    }
    public String If_Then_Else(){
        if(_if){
            String res1=_then.Run();
            return res1;
        }
        else {
            String res2=_else.Run();
            return res2;
        }
    }
}
