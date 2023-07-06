package me.tisleo.jpad.editor;

import me.tisleo.jpad.listeners.DocumentChangeListener;
import me.tisleo.jpad.utils.LiveAppStore;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RUndoManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The text area used for JPad.
 * @see RSyntaxTextArea
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class SyntaxTextArea extends RSyntaxTextArea {

    /**
     * The singleton instance of this class.
     */
    private static volatile SyntaxTextArea INSTANCE;

    /**
     * Initialises all the properties of the text area.
     */
    private SyntaxTextArea() throws IOException {
        super(20, 60);
        INSTANCE = this;

        setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        setCodeFoldingEnabled(true);
        initTheme();
        initFont();
        setZoomListener();
        getDocument().addDocumentListener(new DocumentChangeListener());
    }

    /**
     * @return the double-checked singleton instance of this class.
     */
    public static SyntaxTextArea getInstance() {
        if (INSTANCE == null) {
            synchronized (SyntaxTextArea.class) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = new SyntaxTextArea();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Sets the theme of the text area based on the default theme from the properties file.
     * @throws IOException if an I/O error occurs.
     */
    private void initTheme() throws IOException {
        String defaultTheme = LiveAppStore.getDefaultEditorTheme();
        Theme t = Theme.load(getClass().getResourceAsStream("/themes/" + defaultTheme.toLowerCase() + "_theme.xml"));
        t.apply(this);
        LiveAppStore.setCurrentEditorTheme(defaultTheme);
    }

    /**
     * Sets the font and font style of the text area based on the default values from the properties file.
     * @throws IOException if an I/O error occurs.
     */
    private void initFont() throws IOException {
        String defaultFont = LiveAppStore.getDefaultFont();
        int defaultSize = LiveAppStore.getDefaultFontSize();
        int defaultStyle = LiveAppStore.getDefaultFontStyle();

        setFont(new Font(defaultFont, defaultStyle, defaultSize));
        LiveAppStore.setCurrentFontName(defaultFont);
        LiveAppStore.setCurrentFontSize(defaultSize);
        LiveAppStore.setCurrentFontStyle(defaultStyle);
    }

    /**
     * Sets the zoom listener for the text area. Listens for the control key and mouse wheel rotation.
     * Increments or decrements the font size by 1 with each up or down rotation respectively.
     */
    private void setZoomListener() {
        addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                if (e.getWheelRotation() < 0) {
                    LiveAppStore.setCurrentFontSize(LiveAppStore.getCurrentFontSize() + 1);
                    SwingUtilities.invokeLater(() -> setFont(new Font(LiveAppStore.getCurrentFontName(), Font.PLAIN, LiveAppStore.getCurrentFontSize())));
                } else {
                    if (LiveAppStore.getCurrentFontSize() - 1 <= 0) return;
                    LiveAppStore.setCurrentFontSize(LiveAppStore.getCurrentFontSize() - 1);
                    SwingUtilities.invokeLater(() -> setFont(new Font(LiveAppStore.getCurrentFontName(), Font.PLAIN, LiveAppStore.getCurrentFontSize())));
                }
            }
        });
    }

    /**
     * @see UndoManager
     * @return the undo manager for this text area.
     */
    @Override
    protected RUndoManager createUndoManager() {
        return new UndoManager(this);
    }
}
