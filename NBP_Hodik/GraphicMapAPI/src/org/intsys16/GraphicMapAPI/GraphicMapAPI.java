
package org.intsys16.GraphicMapAPI;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.lookup.Lookups;

public interface GraphicMapAPI {
    public static GraphicMapAPI getGraphicMap() {
        GraphicMapAPI GraphicMap = Lookups.forPath("GraphicMapImpl")
                .lookup(GraphicMapAPI.class);
        if (GraphicMap == null ) {
            Logger.getLogger(GraphicMapAPI.class.getName())
                    .log(Level.SEVERE, "Cannot get graphic map object");
        }
        return GraphicMap;         
    }
    public void setParameters(double w, int r, Object gr, Object f);
    public void move(String cmd);
}
