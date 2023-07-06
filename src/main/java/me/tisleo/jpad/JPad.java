package me.tisleo.jpad;

import me.tisleo.jpad.utils.PropertiesHandler;
import me.tisleo.jpad.utils.ReleasesManager;
import me.tisleo.jpad.utils.UI;
import me.tisleo.jpad.windows.RootFrame;
import me.tisleo.jpad.windows.UpdateWindow;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * JPad's main class.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class JPad {

    /**
     * The main method of the application. Initialises a light or dark app theme based on the user's preference and
     * shows the root frame.
     * @param args the command line arguments.
     * @throws UnsupportedLookAndFeelException if the look and feel is not supported.
     * @throws IOException if an I/O error occurs.
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException {
        PropertiesHandler.initProperties();
        UI.initTheme();
        UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());

        SwingUtilities.invokeLater(() -> RootFrame.getInstance().setVisible(true));

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                checkForUpdate();
                return null;
            }
        }.execute();
    }

    /**
     * Shows the update window if an update is available and the user has not disabled update checking.
     */
    private static void checkForUpdate() {
        try {
            if (Boolean.parseBoolean(PropertiesHandler.getProperty("default.check_updates")) && ReleasesManager.checkUpdate()) {
                new UpdateWindow().setVisible(true);
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}