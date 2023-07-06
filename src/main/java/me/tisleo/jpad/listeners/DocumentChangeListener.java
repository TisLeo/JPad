package me.tisleo.jpad.listeners;

import me.tisleo.jpad.utils.LiveAppStore;
import me.tisleo.jpad.windows.RootFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

/**
 * This class is intended to be used as a document listener for the text area which notifies the program if the user
 * does something that should provoke a save.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class DocumentChangeListener implements javax.swing.event.DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        LiveAppStore.setShouldSave(true);
        updateSaveLabel();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        LiveAppStore.setShouldSave(true);
        updateSaveLabel();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        LiveAppStore.setShouldSave(true);
        updateSaveLabel();
    }

    /**
     * Updates the save state label in the bottom right of the bottom toolbar to 'Unsaved'.
     */
    private void updateSaveLabel() {
        JLabel saveLabel = RootFrame.getInstance().getSaveStateLabel();
        if (!saveLabel.getText().equals("Unsaved")) {
            SwingUtilities.invokeLater(() -> saveLabel.setText("Unsaved"));
        }
    }
}
