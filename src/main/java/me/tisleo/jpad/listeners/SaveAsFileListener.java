package me.tisleo.jpad.listeners;

import me.tisleo.jpad.utils.FileHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Called when user clicks the "Save As" button in the File menu.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class SaveAsFileListener implements ActionListener {

    /**
     * Attempts to save the file as a new file.
     * @param e the event to be processed
     * @see FileHandler#attemptSaveFileAs()
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileHandler.attemptSaveFileAs();
    }
}
