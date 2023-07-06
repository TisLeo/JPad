package me.tisleo.jpad.listeners;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

/**
 * This class is used to open the link to the original source code of JPad.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class SourceLinkMouseListener extends MouseAdapter {

    /**
     * Opens the browser to open the link to the original source code of JPad when the label (in the settings window)
     * is clicked.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            Desktop.getDesktop().browse(URI.create("https://github.com/TisLeo/JPad"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
