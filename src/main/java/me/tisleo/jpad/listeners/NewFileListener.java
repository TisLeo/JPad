package me.tisleo.jpad.listeners;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.utils.FileHandler;
import me.tisleo.jpad.utils.LiveAppStore;
import me.tisleo.jpad.utils.UI;
import me.tisleo.jpad.windows.RootFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used for when a user clicks "New" on the file menu.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class NewFileListener implements ActionListener {

    /**
     * After checking if the user should save their current file,
     * the text area is cleared and the current file is set to null to notify the program that the user is now working
     * with a completely new document.
     * @param e the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SyntaxTextArea syntaxTextArea = SyntaxTextArea.getInstance();
        FileHandler.checkIfShouldSave(false);
        LiveAppStore.setCurrentFile(null);
        UI.changeTitle("Untitled");

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                syntaxTextArea.setText("");
                return null;
            }

            @Override
            protected void done() {
                LiveAppStore.setShouldSave(false);
                RootFrame.getInstance().getSaveStateLabel().setText("Saved");
            }
        }.execute();
    }

}
