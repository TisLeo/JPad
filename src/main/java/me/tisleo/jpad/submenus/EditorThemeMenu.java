package me.tisleo.jpad.submenus;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.utils.LiveAppStore;
import me.tisleo.jpad.windows.RootFrame;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class allows the user to change the editor theme. This is different to the app theme ({@link AppThemeMenu}) as this only
 * changes the {@link SyntaxTextArea} theme.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class EditorThemeMenu extends JMenu {

    public EditorThemeMenu() {
        super("Editor Theme");
        initItems();
    }

    /**
     * Adds all the available themes to the menu. When one of the items is clicked, the theme is applied to the
     * {@link SyntaxTextArea}.
     */
    private void initItems() {
        SyntaxTextArea syntaxTextArea = SyntaxTextArea.getInstance();
        JMenuItem item;

        for (String key : LiveAppStore.EDITOR_THEMES.keySet()) {
            item = new JMenuItem(key);
            add(item);

            item.addActionListener(e -> {
                try {
                    String selectedTheme = ((JMenuItem) e.getSource()).getText();
                    Theme t = Theme.load(getClass().getResourceAsStream(LiveAppStore.EDITOR_THEMES.get(selectedTheme)));
                    t.apply(syntaxTextArea);

                    syntaxTextArea.setFont(new Font(LiveAppStore.getCurrentFontName(), Font.PLAIN, LiveAppStore.getCurrentFontSize()));
                    RootFrame.getInstance().getThemeLabel().setText("Editor Theme: " + selectedTheme);

                    LiveAppStore.setCurrentEditorTheme(selectedTheme);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
    }

}
