package me.tisleo.jpad.windows;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.listeners.*;
import me.tisleo.jpad.submenus.*;
import me.tisleo.jpad.utils.FileHandler;
import me.tisleo.jpad.utils.LiveAppStore;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represents the root frame of the application. It contains the menu bar, the text area and the bottom toolbar.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class RootFrame extends JFrame {
    /**
     * The singleton instance of this class.
     */
    private static volatile RootFrame INSTANCE;

    private final SyntaxTextArea syntaxTextArea = SyntaxTextArea.getInstance();
    private final JLabel syntaxStyleLabel = new JLabel();
    private final JLabel themeLabel = new JLabel();
    private final JLabel fontLabel = new JLabel();
    private final JLabel saveStateLabel = new JLabel("Unsaved");

    /**
     * Initialises all the properties of the frame.
     * @throws IOException if an I/O error occurs.
     */
    private RootFrame() throws IOException {
        super("JPad " + LiveAppStore.APP_VERSION_NAME + " • " + "Untitled");
        INSTANCE = this;

        setSize(640, 720);
        setMinimumSize(getSize());
        setLayout(new BorderLayout());

        SwingUtilities.invokeLater(() -> {
            setJMenuBar(createMenuBar());
            add(syntaxTextArea, BorderLayout.CENTER);
            add(new RTextScrollPane(syntaxTextArea), BorderLayout.CENTER);
            add(createBottomToolbar(), BorderLayout.SOUTH);
        });

        initShortcuts();

        setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/JPad_logo.png"))));
        addWindowListener(new AppExitListener());
        setLocationRelativeTo(null);
    }

    /**
     * @return the double-checked singleton instance of this class.
     */
    public static RootFrame getInstance() {
        if (INSTANCE == null) {
            synchronized (RootFrame.class) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = new RootFrame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Initialises the control-based (or command-based if on Mac) shortcuts. They are as follows:
     * <ul>
     *     <li>Save File: Ctrl + S</li>
     *     <li>Open File: Ctrl + O</li>
     *     <li>New File: Ctrl + N</li>
     * </ul>
     */
    private void initShortcuts() {
        String prefix = LiveAppStore.getOsShortcutPrefix();

        createShortcut(prefix + " S", "save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileHandler.attemptSaveFile();
            }
        });

        createShortcut(prefix + " O", "open", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OpenFileListener().actionPerformed(null);
            }
        });

        createShortcut(prefix + " N", "new", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewFileListener().actionPerformed(null);
            }
        });
    }

    /**
     * Creates a new shortcut which is valid when the root frame is focused.
     * @param shortcut the shortcut to be used.
     * @param key the shortcut key/name to be used.
     * @param action the action to be performed when the shortcut is pressed.
     */
    private void createShortcut(String shortcut, String key, AbstractAction action) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(shortcut);
        syntaxTextArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
        syntaxTextArea.getActionMap().put(key, action);
    }

    /**
     * Creates the toolbar at the bottom of the window. Includes the syntax language, editor theme, font and save state.
     * @return a JToolBar
     */
    private JToolBar createBottomToolbar() {
        JToolBar toolBar = new JToolBar();

        toolBar.add(Box.createHorizontalStrut(8));
        syntaxStyleLabel.setText("Syntax Language: " + syntaxTextArea.getSyntaxEditingStyle().replaceAll("text/", ""));
        toolBar.add(syntaxStyleLabel);

        addSpaceToToolbar(toolBar, 4);

        themeLabel.setText("Editor Theme: " + LiveAppStore.getCurrentEditorTheme());
        toolBar.add(themeLabel);

        addSpaceToToolbar(toolBar, 4);

        fontLabel.setText("Font: " + LiveAppStore.getCurrentFontName() + " ");
        toolBar.add(fontLabel);

        saveStateLabel.setText("Unsaved");
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(saveStateLabel);
        toolBar.add(Box.createHorizontalStrut(8));

        return toolBar;
    }

    /**
     * Adds some spacing to a given JToolBar.
     * @param toolBar the JToolBar to add spacing to.
     * @param width the width of the spacing.
     */
    private void addSpaceToToolbar(JToolBar toolBar, int width) {
        toolBar.add(Box.createHorizontalStrut(width));
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalStrut(width));
    }

    /**
     * Creates the menu bar at the top of the window. Includes File, View and Settings menus, and a text wrapping checkbox.
     * @return a JMenuBar
     */
    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();
        bar.add(createFileMenu());
        bar.add(createViewMenu());

        if (LiveAppStore.OS_NAME != LiveAppStore.OS.MAC) {
            JMenu settingsButton = new JMenu("Settings");
            settingsButton.addMouseListener(new SettingsMenuMouseListener());
            bar.add(settingsButton);
        }

        return bar;
    }

    /**
     * Creates the File menu for the menu bar. Includes opening, saving, save as, and new operations.
     * @return a JMenu
     */
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        JMenuItem item;

        item = new JMenuItem("New");
        fileMenu.add(item);
        item.addActionListener(new NewFileListener());
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        item = new JMenuItem("Open");
        fileMenu.add(item);
        item.addActionListener(new OpenFileListener());
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        item = new JMenuItem("Save");
        fileMenu.add(item);
        item.addActionListener(new SaveFileListener());
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        item = new JMenuItem("Save as");
        fileMenu.add(item);
        item.addActionListener(new SaveAsFileListener());
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | KeyEvent.ALT_DOWN_MASK));

        return fileMenu;
    }

    /**
     * Create the View menu for the menu bar. Includes the editor and app theme, syntax language, and font submenus.
     * @return a JMenu
     */
    private JMenu createViewMenu() {
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');

        viewMenu.add(new EditorThemeMenu());
        viewMenu.add(new AppThemeMenu());
        viewMenu.add(new SyntaxLanguageMenu());
        viewMenu.add(new FontMenu());
        viewMenu.add(new FontStyleMenu());

        JCheckBoxMenuItem wrapping = new JCheckBoxMenuItem("Text Wrapping");
        wrapping.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        wrapping.addItemListener(this::textWrapping);
        viewMenu.add(wrapping);
        return viewMenu;
    }

    /**
     * Toggles (on or off) text wrapping on the text area.
     * @param e the event
     */
    private void textWrapping(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            SwingUtilities.invokeLater(() -> SyntaxTextArea.getInstance().setLineWrap(true));
        } else {
            SwingUtilities.invokeLater(() -> SyntaxTextArea.getInstance().setLineWrap(false));
        }
    }

    public JLabel getSyntaxStyleLabel() {
        return syntaxStyleLabel;
    }

    public JLabel getThemeLabel() {
        return themeLabel;
    }

    public JLabel getFontLabel() {
        return fontLabel;
    }

    public JLabel getSaveStateLabel() {
        return saveStateLabel;
    }

}
