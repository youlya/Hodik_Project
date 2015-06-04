/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.editorwindow;

import java.io.Serializable;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Julia
 */
public class ProgramNode implements Lookup.Provider {
    private String progName;
    private String progText;
    private Lookup lookup;
    
    public ProgramNode(String progName, String progText) {
        this.progName = progName;
        this.progText = progText;  
        lookup = Lookups.singleton(this);
    }
    public String getProgramName() {
        return progName;
    }
    public String getProgramText() {
        return progText;
    }
    public void setProgramName(String progName) {
        this.progName = progName;
    }
    public void setProgramText(String progText) {
        this.progText = progText; 
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }
    
}
