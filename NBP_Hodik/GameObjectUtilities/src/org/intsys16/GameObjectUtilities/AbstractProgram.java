/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.GameObjectUtilities;

import javafx.beans.property.StringProperty;
import org.openide.windows.TopComponent;

/**
 *
 * @author Julia
 */
public abstract class AbstractProgram {
    public abstract String getProgramName();
    public abstract String getProgramPath();
    public abstract void setProgramPath(String progPath);
    public abstract void setEditorTC(TopComponent editor);
    public abstract TopComponent getEditorTC();
    public abstract String getProgramText();
    public abstract void setProgramName(String progName);
    public abstract void setProgramText(String progText);
    public abstract void addLineToProgram(String line);
    public abstract StringProperty programTextProperty();
}
