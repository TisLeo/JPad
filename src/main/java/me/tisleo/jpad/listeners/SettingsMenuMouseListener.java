package me.tisleo.jpad.listeners;

import me.tisleo.jpad.windows.SettingsWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * A listener for when a user presses the Settings menu (which acts as a button) in the File menu.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class SettingsMenuMouseListener implements MouseListener {

    /**
     * Opens the Settings window when the Settings menu is clicked.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            new SettingsWindow().setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
