package me.tisleo.jpad;

import me.tisleo.jpad.utils.LiveAppStore;
import me.tisleo.jpad.utils.PropertiesHandler;
import me.tisleo.jpad.utils.ReleasesManager;
import me.tisleo.jpad.utils.UI;
import me.tisleo.jpad.windows.RootFrame;
import me.tisleo.jpad.windows.SettingsWindow;
import me.tisleo.jpad.windows.UpdateWindow;

import javax.swing.*;
import java.awt.*;
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
     */
    public static void main(String[] args) {
        PropertiesHandler.initProperties();
        if (LiveAppStore.OS_NAME.contains("mac"))
            initHandlers();

        SwingUtilities.invokeLater(() -> {
            try {
                UI.initTheme();
            } catch (IOException | UnsupportedLookAndFeelException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
            RootFrame.getInstance().setVisible(true);
        });

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                checkForUpdate();
                return null;
            }
        }.execute();
    }

    /**
     * Initialises the handlers for the Settings (Preferences) and About menus. Used for macOS.
     */
    private static void initHandlers() {
        Desktop desktop = Desktop.getDesktop();
        desktop.setPreferencesHandler(e -> {
            try {
                new SettingsWindow().setVisible(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        desktop.setAboutHandler(h -> JOptionPane.showMessageDialog(
                null,
                "JPad"
                        + "\nVersion: " + LiveAppStore.APP_VERSION_NAME
                        + "\nCreated by TisLeo"
                        + "\n\nBeta version: app may be unpolished and have bugs.",
                "About JPad",
                JOptionPane.INFORMATION_MESSAGE
        ));
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
            System.err.println("There was an error checking for updates: " + e);
            e.printStackTrace();
        }
    }

}