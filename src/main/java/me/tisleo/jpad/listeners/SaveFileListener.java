package me.tisleo.jpad.listeners;

import me.tisleo.jpad.utils.FileHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Called when the user clicks the "Save" button in the File menu.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class SaveFileListener implements ActionListener {

    /**
     * Attempts to save the current file.
     * @param e the event to be processed
     * @see FileHandler#attemptSaveFile()
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileHandler.attemptSaveFile();
    }
}
