package me.tisleo.jpad.listeners;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.utils.Dialogs;
import me.tisleo.jpad.utils.FileHandler;
import me.tisleo.jpad.utils.LiveAppStore;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class represents the listener for the application exit event. If required, user is prompted to save their file
 * before exiting the application.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class AppExitListener extends WindowAdapter {

    /**
     * Allows user to save their file before exiting the application.
     * @param e the event to be processed
     * @see #saveLoop()
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if (!LiveAppStore.shouldSave() || SyntaxTextArea.getInstance().getText().isEmpty()) {
            System.exit(0);
        }

        int option = Dialogs.saveBeforeExit();
        switch (option) {
            case JOptionPane.NO_OPTION -> System.exit(0);
            case JOptionPane.YES_OPTION -> {
                if (FileHandler.attemptSaveFile()) {
                    System.exit(0);
                } else {
                    saveLoop();
                }
            }
        }
    }

    /**
     * Runs a loop if there's an error with saving the file  which a user can stop by clicking the negative option.
     */
    private void saveLoop() {
        while (true) {
            int saveOption = Dialogs.saveErrorExit();
            if (saveOption == JOptionPane.YES_OPTION) {
                if (FileHandler.attemptSaveFile()) {
                    System.exit(0);
                    break;
                }
            } else {
                System.exit(0);
                break;
            }
        }
    }
}
