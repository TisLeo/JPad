package me.tisleo.jpad.listeners;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

/**
 * This class is used to open the update link to get the latest JPad release on GitHub.
 */
public class UpdateLinkMouseListener extends MouseAdapter {

    /**
     * Opens browser to get the latest JPad release on GitHub when the label (in the update window) is clicked.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            Desktop.getDesktop().browse(URI.create("https://github.com/TisLeo/JPad/releases"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
