package me.tisleo.jpad.windows;

import me.tisleo.jpad.listeners.SourceLinkMouseListener;
import me.tisleo.jpad.utils.LiveAppStore;
import me.tisleo.jpad.utils.PropertiesHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represents the settings window of the application.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class SettingsWindow extends JDialog {
    private final JComboBox<String> fontComboBox = new JComboBox<>();
    private final JComboBox<String> fontStyleComboBox = new JComboBox<>();
    private final JComboBox<String> editorThemeComboBox = new JComboBox<>();
    private final JComboBox<String> appThemeComboBox = new JComboBox<>();
    private final JSpinner fontSizeSpinner = new JSpinner(new SpinnerNumberModel(LiveAppStore.getDefaultFontSize(), 1, 100, 1));
    private final JCheckBox checkForUpdates = new JCheckBox("Automatically check for updates");

    /**
     * Initialises all the properties of the frame.
     * @throws IOException if an I/O error occurs.
     */
    public SettingsWindow() throws IOException {
        setTitle("JPad Settings");
        setModalityType(ModalityType.APPLICATION_MODAL);

        setSize(626, 536);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(createDefaultsPanel());
        add(createShortcutPanel());
        add(createAboutPanel());
        add(createSavePanel());

        setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/JPad_logo.png"))));
        setLocationRelativeTo(null);
    }

    /**
     * Creates the Defaults panel, including font, font size, font style, and editor and themes.
     * @return a JPanel containing the default setting options
     */
    private JPanel createDefaultsPanel() throws IOException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 5, 10), BorderFactory.createTitledBorder("Startup Defaults")));

        panel.add(createFontInnerPanel());
        panel.add(createFontSizeInnerPanel());
        panel.add(createFontStyleInnerPanel());
        panel.add(createEditorThemeInnerPanel());
        panel.add(createAppThemeInnerPanel());
        return panel;
    }

    /**
     * Creates the font panel to be used in the defaults panel. Users can select the default font family they want to use.
     * @return a JPanel for the font family option
     */
    private JPanel createFontInnerPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel fontLabel = new JLabel("Font: ");
        panel.add(fontLabel);

        String font = LiveAppStore.getDefaultFont();
        fontComboBox.addItem(font);
        for (String s : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            if (!s.equals(font)) { fontComboBox.addItem(s); }
        }

        panel.add(fontComboBox);
        return panel;
    }

    /**
     * Creates the font style panel to be used in the defaults panel. Users can select the default font style they want to use
     * (bold, italic, plain).
     * @return a JPanel for the font style option
     */
    private JPanel createFontStyleInnerPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel fontStyleLabel = new JLabel("Font Style: ");
        panel.add(fontStyleLabel);

        String font = LiveAppStore.parseFontStyle(LiveAppStore.getDefaultFontStyle());
        fontStyleComboBox.addItem("Plain");
        fontStyleComboBox.addItem("Bold");
        fontStyleComboBox.addItem("Italic");
        fontStyleComboBox.setSelectedItem(font);

        panel.add(fontStyleComboBox);
        return panel;
    }

    /**
     * Creates the font size panel to be used in the defaults panel. Users can select the default font size they want to use.
     * @return a JPanel for the font size option
     */
    private JPanel createFontSizeInnerPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel fontSizeLabel = new JLabel("Font Size: ");
        panel.add(fontSizeLabel);

        fontSizeSpinner.setValue(LiveAppStore.getDefaultFontSize());
        JFormattedTextField txt = ((JSpinner.NumberEditor) fontSizeSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        panel.add(fontSizeSpinner);

        return panel;
    }

    /**
     * Creates the editor theme panel to be used in the defaults panel. Users can select the default text area
     * theme they want to use.
     * @return a JPanel for the editor theme option
     */
    private JPanel createEditorThemeInnerPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel themeLabel = new JLabel("Editor Theme: ");
        panel.add(themeLabel);

        String defaultTheme = LiveAppStore.getDefaultEditorTheme();
        editorThemeComboBox.addItem(defaultTheme);
        for (String s : LiveAppStore.EDITOR_THEMES.keySet()) {
            if(!s.equals(defaultTheme)) { editorThemeComboBox.addItem(s); }
        }

        panel.add(editorThemeComboBox);
        return panel;
    }

    /**
     * Creates the shortcut panel, including the shortcut keys for the main functions of the application.
     * @return a JPanel for the app theme option
     * @throws IOException if an I/O error occurs.
     */
    private JPanel createAppThemeInnerPanel() throws IOException {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel themeLabel = new JLabel("App Theme: ");
        panel.add(themeLabel);

        appThemeComboBox.addItem("Light");
        appThemeComboBox.addItem("Dark");
        appThemeComboBox.setSelectedItem(LiveAppStore.getDefaultAppTheme());

        panel.add(appThemeComboBox);
        return panel;
    }

    /**
     * Creates the About panel which contains some information about the application and a link to the GitHub repository.
     * @return a JPanel containing the About information and a clickable link to the GitHub repository.
     */
    private JPanel createAboutPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new CompoundBorder(new EmptyBorder(5, 10, 5, 10), BorderFactory.createTitledBorder("About")));

        JTextArea aboutTextArea = new JTextArea("JPad is a Java desktop app made with the Swing framework." +
                " It's completely open-source and available on GitHub. JPad was created with the main aim of introducing" +
                " built-in syntax highlighting to a simple, lightweight text editor.");
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        aboutTextArea.setEditable(false);

        JLabel sourceLinkLabel = new JLabel("<html><a href=\"\">View source on GitHub</a></html>");
        sourceLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sourceLinkLabel.addMouseListener(new SourceLinkMouseListener());
        sourceLinkLabel.setBorder(new EmptyBorder(0,8,0,panel.getWidth() - sourceLinkLabel.getWidth()));

        panel.add(aboutTextArea, BorderLayout.NORTH);
        panel.add(sourceLinkLabel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates the shortcuts panel which contains the shortcut keys for the main functions of the application.
     * @return a JPanel containing the shortcut keys.
     */
    private JPanel createShortcutPanel() {
        JPanel panel = new JPanel();
        switch(LiveAppStore.OS_NAME) {
            case MAC -> panel.setLayout(new GridLayout(2, 3));
            case WINDOWS, LINUX -> panel.setLayout(new GridLayout(4, 3));
        }
        panel.setBorder(new CompoundBorder(new EmptyBorder(5, 10, 5, 10), BorderFactory.createTitledBorder("Shortcuts")));

        String controlMask = LiveAppStore.getOsShortcutPrefixSymbol();
        String altMask = LiveAppStore.getOsMnemonicPrefix();
        switch(LiveAppStore.OS_NAME) {
            case MAC -> macShortcuts(panel, controlMask, altMask);
            case WINDOWS, LINUX -> windowsLinuxShortcuts(panel, controlMask, altMask);
        }

        return panel;
    }

    /**
     * Sets appropriate shortcuts for Windows and Linux.
     * @param panel the JPanel to add the shortcuts to
     * @param controlMask the control mask symbol for the shortcut key
     * @param altMask the alt mask for the shortcut key
     */
    private void windowsLinuxShortcuts(JPanel panel, String controlMask, String altMask) {
        JLabel filler = new JLabel();
        panel.add(createShortcutLabel("Save", controlMask, "S"));
        panel.add(createShortcutLabel("File Menu", altMask, "F"));
        panel.add(filler);
        panel.add(createShortcutLabel("Save as", altMask + "+" + controlMask, "S"));
        panel.add(createShortcutLabel("View Menu", altMask, "V"));
        panel.add(filler);
        panel.add(createShortcutLabel("New", controlMask, "N"));
        panel.add(filler);
        panel.add(createShortcutLabel("Open", controlMask, "O"));
    }

    /**
     * Sets appropriate shortcuts for macOS.
     * @param panel the JPanel to add the shortcuts to
     * @param controlMask the control mask symbol for the shortcut key
     * @param altMask the alt mask for the shortcut key
     */
    private void macShortcuts(JPanel panel, String controlMask, String altMask) {
        JLabel filler = new JLabel();
        panel.add(createShortcutLabel("Save", controlMask, "S"));
        panel.add(createShortcutLabel("Save as", altMask + "+" + controlMask, "S"));
        panel.add(filler);
        panel.add(createShortcutLabel("New", controlMask, "N"));
        panel.add(createShortcutLabel("Open", controlMask, "O"));
    }

    /**
     * Creates a JLabel containing the shortcut key for a specific function of the application.
     * @param name the name of the function
     * @param prefix the prefix symbol(s) for the shortcut key
     * @param key the shortcut key
     * @return a JLabel containing the shortcut key for a specific function of the application.
     */
    private JLabel createShortcutLabel(String name, String prefix, String key) {
        JLabel label = new JLabel("<html>" + name + ": <span style=\"font-family:monospaced;font-size:10px;\">" + prefix + "+" + key + "</span></html>");
        label.setBorder(new EmptyBorder(0, 4, 0, 0));
        return label;
    }

    /**
     * Creates the save panel which contains the save button. Allows for save functionality by writing to the properties file
     * based on the user's selections.
     * @return a JPanel containing the save button.
     */
    private JPanel createSavePanel() throws IOException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 10, 10, 10));

        checkForUpdates.setSelected(Boolean.parseBoolean(PropertiesHandler.getProperty("default.check_updates")));
        panel.add(checkForUpdates, BorderLayout.NORTH);

        JButton save = new JButton("Save");
        save.addActionListener(l -> {
            try {
                PropertiesHandler.setProperty("default.font_family", String.valueOf(fontComboBox.getSelectedItem()));
                PropertiesHandler.setProperty("default.font_size", String.valueOf(String.valueOf(fontSizeSpinner.getValue())));
                PropertiesHandler.setProperty("default.font_style", String.valueOf(LiveAppStore.parseFontStyle((String) Objects.requireNonNull(fontStyleComboBox.getSelectedItem()))));
                PropertiesHandler.setProperty("default.editor_theme", String.valueOf(editorThemeComboBox.getSelectedItem()));
                PropertiesHandler.setProperty("default.app_theme", String.valueOf(appThemeComboBox.getSelectedItem()));
                PropertiesHandler.setProperty("default.check_updates", String.valueOf(checkForUpdates.isSelected()));
                dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        panel.add(save, BorderLayout.SOUTH);
        return panel;
    }
}
