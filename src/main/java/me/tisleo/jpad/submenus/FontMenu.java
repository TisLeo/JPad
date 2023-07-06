package me.tisleo.jpad.submenus;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.utils.LiveAppStore;
import me.tisleo.jpad.windows.RootFrame;

import javax.swing.*;
import java.awt.*;

/**
 * This class allows the user to change the text area font.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class FontMenu extends JMenu {

    /**
     * Loads the system's available fonts in the background, setting the font for each menu item to
     * its corresponding font. When a menu item is clicked, the font is applied to the text area.
     * @see Font
     */
    public FontMenu() {
        super("Font");

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground()  {
                JMenuItem item;
                for (String s : LiveAppStore.SYSTEM_AVAILABLE_FONTS) {
                    item = new JMenuItem(s);
                    item.setFont(new Font(s, LiveAppStore.getCurrentFontStyle(), 14));
                    add(item);

                    item.addActionListener(e -> {
                        String selectedFont = ((JMenuItem) e.getSource()).getText();
                        SyntaxTextArea.getInstance().setFont(new Font(selectedFont, Font.PLAIN, LiveAppStore.getCurrentFontSize()));
                        RootFrame.getInstance().getFontLabel().setText("Font: " + ((JMenuItem) e.getSource()).getText() + " ");
                        LiveAppStore.setCurrentFontName(((JMenuItem) e.getSource()).getText());
                    });
                }
                return null;
            }
        }.execute();
    }

}
