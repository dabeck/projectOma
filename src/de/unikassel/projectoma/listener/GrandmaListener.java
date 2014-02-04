package de.unikassel.projectoma.listener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GrandmaListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent e) {
	if (e.getPropertyName().equals("name")) {
            // grandma.name has changed
        } else if (e.getPropertyName().equals("level")) {
            // grandma.level has changed
        } else if (e.getPropertyName().equals("currentAction")) {
            // grandma.currentAction has changed
        } else if (e.getPropertyName().equals("wishes")) {
            // grandma.wishes has changed
        }
    }
    
}
