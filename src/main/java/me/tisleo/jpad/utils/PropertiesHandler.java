package me.tisleo.jpad.utils;

import java.io.*;
import java.util.Properties;

/**
 * This class handles all property-related operations.
 * The properties are as follows:
 * <ul>
 *     <li><code>default.editor_theme</code> -- default is "Dark"</li>
 *     <li><code>default.app_theme</code> -- default is "Dark"</li>
 *     <li><code>default.font_family</code> -- default is "Monospaced"</li>
 *     <li><code>default.font_style</code> -- default is 0 (plain)</li>
 *     <li><code>default.check_updates</code> -- default is "true"</li>
 *
 * </ul>
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public abstract class PropertiesHandler {

    private static final String propertiesPath = LiveAppStore.APP_DATA_DIR + File.separator + "jpad.properties";

    /**
     * Gets a property from the properties file.
     * @param key the key of the property
     * @return the value of the property
     * @throws IOException if an I/O error occurs
     */
    public static String getProperty(String key) throws IOException {
        final Properties properties = new Properties();
        try(FileInputStream in = new FileInputStream(propertiesPath)) {
            properties.load(in);
            return properties.getProperty(key);
        }
    }

    /**
     * Sets a property in the properties file.
     * @param key the key of the property
     * @param value the value of the property
     * @throws IOException if an I/O error occurs
     */
    public static void setProperty(String key, String value) throws IOException {
        final Properties properties = new Properties();

        try(FileInputStream in = new FileInputStream(propertiesPath)) {
            properties.load(in);
        }

        try(FileOutputStream out = new FileOutputStream(propertiesPath)) {
            properties.setProperty(key, value);
            properties.store(out, null);
        }
    }

    /**
     * Initialises the properties file if it does not exist.
     */
    public static void initProperties() {
        File f = new File(LiveAppStore.APP_DATA_DIR);
        if (!f.exists()) {
            if (!f.mkdir()) { throw new RuntimeException("Could not create properties directory!"); }
        }

        f = new File(LiveAppStore.APP_DATA_DIR + File.separator + "jpad.properties");
        if (f.exists()) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f.getAbsolutePath()))) {
            writer.write("default.app_theme=Dark");
            writer.newLine();
            writer.write("default.editor_them=Dark");
            writer.newLine();
            writer.write("default.font_family=Monospaced");
            writer.newLine();
            writer.write("default.font_style=0");
            writer.newLine();
            writer.write("default.font_size=14");
            writer.newLine();
            writer.write("default.check_updates=true");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
