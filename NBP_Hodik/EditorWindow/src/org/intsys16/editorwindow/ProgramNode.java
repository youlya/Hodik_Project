/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.editorwindow;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Julia
 */
public class ProgramNode extends AbstractProgram implements Serializable, Lookup.Provider {
    private String progName;
    private StringProperty progText = new SimpleStringProperty(this, "programText", "");
    private String path;
    private Lookup lookup;
    
    public ProgramNode(String progName, String progText, String path) {
        this.progName = progName;
        this.progText.set(progText);
        this.path = path;
        lookup = Lookups.singleton(this);
    }
    @Override
    public String getProgramName() {
        return progName;
    }
    @Override
    public String getProgramPath() {
        return path;
    }
    @Override
    public String getProgramText() {
        return progText.get();
    }
    @Override
    public void setProgramName(String progName) {
        this.progName = progName;
    }
    @Override
    public void setProgramText(String progText) {
        this.progText.set(progText);
    }
    @Override
    public Lookup getLookup() {
        return lookup;
    }
    @Override
    public StringProperty programTextProperty() { 
        return progText; 
    }
}
