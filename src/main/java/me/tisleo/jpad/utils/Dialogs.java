package me.tisleo.jpad.utils;

import javax.swing.*;

/**
 * This class is used to display some pre-made, common dialogs to the user.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public final class Dialogs {

    private Dialogs() {
    }

    /**
     * Displays a dialog stating an error occurred while saving the file.
     */
    public static void saveError() {
        JOptionPane.showMessageDialog(null,
                "An error occurred while saving the file. Please try again.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a dialog stating the current file is not saved.
     * @return the user's response
     * @see JOptionPane
     */
    public static int promptFileSave() {
        return JOptionPane.showConfirmDialog(null,
                "Current file is not saved. Would you like to save it?",
                "Save current file?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Displays a dialog stating the chosen file already exists.
     * @return the user's response
     * @see JOptionPane
     */
    public static int fileExists() {
        return JOptionPane.showConfirmDialog(null,
                "This file already exists. Do you want to replace it?",
                "Confirm", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Displays a dialog stating the current file is not saved and asks the user if they want to save it before exiting
     * the app.
     * @return the user's response
     * @see JOptionPane
     */
    public static int saveBeforeExit() {
        return JOptionPane.showConfirmDialog(null,
                "Current file is not saved. Would you like to save it before exiting?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Displays a dialog stating an error occurred while saving the file and asks the user if they want to try again.
     * @return the user's response
     * @see JOptionPane
     */
    public static int saveErrorExit() {
        return JOptionPane.showConfirmDialog(null,
                "An error occurred while saving the file. Would you like to try again?",
                "Error Saving File", JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE);
    }

}
