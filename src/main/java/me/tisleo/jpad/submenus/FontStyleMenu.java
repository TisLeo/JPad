package me.tisleo.jpad.submenus;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.utils.LiveAppStore;

import javax.swing.*;
import java.awt.*;

/**
 * Similar to {@link me.tisleo.jpad.submenus.FontMenu}, this class allows the user to change the text area font style.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class FontStyleMenu extends JMenu {

    /**
     * Adds the menu items - plain, bold and italic - to the font style menu. Upon clicking a menu item, the font style
     * is applied to the text area.
     * @see Font
     */
    public FontStyleMenu() {
        super("Font Style");

        add(createMenuItem("Plain", Font.PLAIN));
        add(createMenuItem("Bold", Font.BOLD));
        add(createMenuItem("Italic", Font.ITALIC));
    }

    /**
     * Creates a menu item with the specified text and font style.
     * @param text the name/title of the menu item
     * @param fontStyle the font style of the menu item
     * @return the menu item
     */
    private JMenuItem createMenuItem(String text, int fontStyle) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            LiveAppStore.setCurrentFontStyle(fontStyle);
            SyntaxTextArea.getInstance().setFont(new Font(LiveAppStore.getCurrentFontName(), fontStyle, LiveAppStore.getCurrentFontSize()));
        }));
        return menuItem;
    }

}
