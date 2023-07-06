package me.tisleo.jpad.listeners;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.utils.FileHandler;
import me.tisleo.jpad.utils.LiveAppStore;
import me.tisleo.jpad.windows.RootFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * This class is responsible for opening a file from the file system and reflecting it in the text area.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class OpenFileListener implements ActionListener {

    /**
     * Prompts the user to open a file from the file system. If the file is valid, the text area is cleared and
     * the file lines are appended to the text area.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SyntaxTextArea syntaxTextArea = SyntaxTextArea.getInstance();
        RootFrame rootFrame = RootFrame.getInstance();
        FileHandler.checkIfShouldSave(false);

        File f = FileHandler.showOpenFileChooser();
        if (f == null) return;
        LiveAppStore.setCurrentFile(f);

        // Read the file in a background thread and append the lines to the text area on the EDT.
        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<String> words = FileHandler.readFile(f.getAbsolutePath());
                for (String s : words) {
                    publish(s);
                }

                Window window = SwingUtilities.getWindowAncestor(SyntaxTextArea.getInstance());
                ((JFrame) window).setTitle("JPad " + LiveAppStore.APP_VERSION_NAME + " • " + f.getName());
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                syntaxTextArea.setText("");
                for (String s : chunks) {
                    syntaxTextArea.append(s + "\n");
                }
            }
        }.execute();
        LiveAppStore.setShouldSave(false);
        SwingUtilities.invokeLater(() -> rootFrame.getSaveStateLabel().setText("Saved"));
    }
}

