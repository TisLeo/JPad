package me.tisleo.jpad.submenus;

import me.tisleo.jpad.utils.UI;
import javax.swing.*;

/**
 * This class is used to allow the user to change between Light and Dark mode. This is different to the editor theme
 * ({@link me.tisleo.jpad.submenus.EditorThemeMenu}) as this changes the entire application theme.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class AppThemeMenu extends JMenu {

    /**
     * Adds the menu items - light and dark - to the app theme menu.
     */
    public AppThemeMenu() {
        super("App Theme");
        JMenuItem item;

        item = new JMenuItem("Light");
        add(item);
        item.addActionListener(l -> UI.changeAppThemeToLight());

        item = new JMenuItem("Dark");
        add(item);
        item.addActionListener(l -> UI.changeAppThemeToDark());
    }

}
