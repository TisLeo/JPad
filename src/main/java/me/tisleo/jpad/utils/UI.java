package me.tisleo.jpad.utils;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.windows.RootFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class handles some common UI-related operations.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public final class UI {

    private UI() {
    }

    /**
     * Changes the title of the main window on the EDT.
     * @param fileName The file name to set.
     */
    public static void changeTitle(String fileName) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                Window window = SwingUtilities.getWindowAncestor(SyntaxTextArea.getInstance());
                ((JFrame) window).setTitle("JPad " + LiveAppStore.APP_VERSION_NAME + " • " + fileName);
                return null;
            }
        }.execute();
    }

    /**
     * Initialises theme on initial start-up.
     * @throws IOException if an I/O exception occurs.
     * @throws UnsupportedLookAndFeelException - If an UnsupportedLookAndFeelException occurs.
     */
    public static void initTheme() throws IOException, UnsupportedLookAndFeelException {
        if (LiveAppStore.OS_NAME.contains("mac")) {
            if (LiveAppStore.getDefaultAppTheme().equalsIgnoreCase("dark")) {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
            }
        } else {
            if (LiveAppStore.getDefaultAppTheme().equalsIgnoreCase("dark")) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
        }
    }

    /**
     * Changes the app theme to light if it's not already light.
     */
    public static void changeAppThemeToLight() {
        if (UIManager.getLookAndFeel() instanceof FlatLightLaf || UIManager.getLookAndFeel() instanceof FlatMacLightLaf) return;
        SwingUtilities.invokeLater(() -> {
            try {
                if (LiveAppStore.OS_NAME.contains("mac")) {
                    UIManager.setLookAndFeel(new FlatMacLightLaf());
                } else {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                }
                SwingUtilities.updateComponentTreeUI(RootFrame.getInstance().getRootPane());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Changes the app theme to dark if it's not already dark.
     */
    public static void changeAppThemeToDark() {
        if (UIManager.getLookAndFeel() instanceof FlatDarkLaf || UIManager.getLookAndFeel() instanceof FlatMacDarkLaf) return;
        SwingUtilities.invokeLater(() -> {
            try {
                if (LiveAppStore.OS_NAME.contains("mac")) {
                    UIManager.setLookAndFeel(new FlatMacDarkLaf());
                } else {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                }
                SwingUtilities.updateComponentTreeUI(RootFrame.getInstance().getRootPane());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        });
    }
}
