package me.tisleo.jpad.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to store common live data across the application.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public final class LiveAppStore {

    private LiveAppStore() {
    }

    /**
     * The current app version name. If you are contributing to the original project, do not touch this.
     */
    public static final String APP_VERSION_NAME = "v1.0.0-beta";

    /**
     * The current app version. If you are contributing to the original project, do not touch this.
     * <br>
     * Structure as follows: <code>{major, minor, patch}</code>
     */
    public static final int[] APP_VERSION = {1, 0, 0};

    /**
     * A {@link HashMap} of all the available editor themes. Corresponds to the theme name and the relative resource path to the theme file.
     */
    public static final Map<String, String> EDITOR_THEMES = new HashMap<>();

    /**
     * The directory where the application data is stored.
     */
    public static final String APP_DATA_DIR;

    /**
     * The name of the operating system.
     */
    public static final String OS_NAME;

    /**
     * The available fonts on the system.
     */
    public static final String[] SYSTEM_AVAILABLE_FONTS = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    /**
     * The current directory that will automatically be opened when a {@link javax.swing.JFileChooser} with the save
     * option is opened.
     * Starts as the user's home directory.
     */
    private static String currentDir = System.getProperty("user.home");

    /**
     * The current file that is being edited. <code>null</code> if the user is editing a new file.
     */
    private static File currentFile;

    /**
     * Used to determine whether the document needs to be saved (true) or not (false).
     */
    private static boolean shouldSave;

    private static String currentEditorTheme;
    private static String currentFontName;
    private static int currentFontStyle;
    private static int currentFontSize;

    static {
        EDITOR_THEMES.put("Light", "/themes/light_theme.xml");
        EDITOR_THEMES.put("Dark", "/themes/dark_theme.xml");
        EDITOR_THEMES.put("Druid", "/themes/druid_theme.xml");
        EDITOR_THEMES.put("Eclipse", "/themes/eclipse_theme.xml");
        EDITOR_THEMES.put("Idea", "/themes/idea_theme.xml");
        EDITOR_THEMES.put("Monokai", "/themes/monokai_theme.xml");
        EDITOR_THEMES.put("VS", "/themes/vs_theme.xml");

        OS_NAME = System.getProperty("os.name").toLowerCase();
        if (OS_NAME.contains("mac")) {
            APP_DATA_DIR = System.getProperty("user.home") + "/Library/Preferences/JPad";
        } else {
            APP_DATA_DIR = System.getenv("LOCALAPPDATA") + File.separator + "JPad";
        }
    }

    public static synchronized String getDefaultFont() throws IOException {
        return PropertiesHandler.getProperty("default.font_family") != null ? PropertiesHandler.getProperty("default.font_family") : "Monospaced";
    }

    public static synchronized int getDefaultFontSize() throws IOException {
        return PropertiesHandler.getProperty("default.font_size") != null ? Integer.parseInt(PropertiesHandler.getProperty("default.font_size")) : 14;
    }

    public static synchronized String getDefaultEditorTheme() throws IOException {
        return PropertiesHandler.getProperty("default.editor_theme") != null ? PropertiesHandler.getProperty("default.editor_theme") : "Dark";
    }

    public static synchronized int getDefaultFontStyle() throws IOException {
        return PropertiesHandler.getProperty("default.font_style") != null ? Integer.parseInt(PropertiesHandler.getProperty("default.font_style")) : 0;
    }

    public static synchronized String getDefaultAppTheme() throws IOException {
        return PropertiesHandler.getProperty("default.app_theme") != null ? PropertiesHandler.getProperty("default.app_theme") : "Light";
    }

    /**
     * @return the prefix name for control-related shortcuts based on OS
     */
    public static String getOsShortcutPrefix() {
        if (OS_NAME.contains("mac")) {
            return "meta";
        } else {
            return "control";
        }
    }

    /**
     * @return the prefix symbol for control-related shortcuts based on OS
     */
    public static String getOsShortcutPrefixSymbol() {
        if (OS_NAME.contains("mac")) {
            return "⌘";
        } else {
            return "Ctrl";
        }
    }

    /**
     * @return the mnemonic prefix for alt-related shortcuts based on OS
     */
    public static String getOsMnemonicPrefix() {
        if (OS_NAME.contains("mac")) {
            return "Ctrl";
        } else {
            return "Alt";
        }
    }

    /**
     * Parses the font style from a string to an integer.
     * @param fontStyle the font style to parse
     * @return the parsed font style
     * @see Font
     */
    public static int parseFontStyle(String fontStyle) {
        return switch (fontStyle) {
            case "Plain" -> Font.PLAIN;
            case "Bold" -> Font.BOLD;
            case "Italic" -> Font.ITALIC;
            default -> throw new IllegalArgumentException("Unexpected value: '" + fontStyle + "'. Expected: 'Plain', 'Bold' or 'Italic'");
        };
    }

    /**
     * Parses the font style from an integer to a string.
     * @param fontStyle the font style to parse
     * @return the parsed font style
     * @see Font
     */
    public static String parseFontStyle(int fontStyle) {
        return switch (fontStyle) {
            case Font.BOLD -> "Bold";
            case Font.ITALIC -> "Italic";
            default -> "Plain";
        };
    }

    public static synchronized void setCurrentEditorTheme(String theme) {
        currentEditorTheme = theme;
    }

    public static synchronized String getCurrentEditorTheme() {
        return currentEditorTheme;
    }

    public static synchronized void setCurrentFontName(String font) {
        currentFontName = font;
    }

    public static synchronized String getCurrentFontName() {
        return currentFontName;
    }

    public static synchronized int getCurrentFontSize() {
        return currentFontSize;
    }

    public static synchronized void setCurrentFontSize(int size) {
        currentFontSize = size;
    }

    public static synchronized File getCurrentFile() {
        return currentFile;
    }

    public static synchronized void setCurrentFile(File file) {
        currentFile = file;
    }

    public static synchronized boolean shouldSave() {
        return shouldSave;
    }

    public static synchronized void setShouldSave(boolean state) {
        shouldSave = state;
    }

    public static synchronized int getCurrentFontStyle() {
        return currentFontStyle;
    }

    public static synchronized void setCurrentFontStyle(int currentFontStyle) {
        LiveAppStore.currentFontStyle = currentFontStyle;
    }

    public static synchronized String getCurrentDir() {
        return currentDir;
    }

    public static synchronized void setCurrentDir(String currentDir) {
        LiveAppStore.currentDir = currentDir;
    }
}
