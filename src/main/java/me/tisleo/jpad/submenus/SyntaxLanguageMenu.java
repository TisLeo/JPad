package me.tisleo.jpad.submenus;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.windows.RootFrame;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import java.lang.reflect.Field;

/**
 * This class allows the user to change the text area's syntax highlighting language.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class SyntaxLanguageMenu extends JMenu {

    /**
     * Adds all the available syntax highlighting languages to the menu.
     * When one of the items is clicked, the language highlighting is applied to the {@link SyntaxTextArea}.
     * @see SyntaxConstants
     */
    public SyntaxLanguageMenu() {
        super("Syntax Language");
        JMenuItem item;

        for (Field f : SyntaxConstants.class.getDeclaredFields()) {
            if (f.getType() != String.class) continue;

            try {
                String newStyle = f.get(null).toString().replaceAll("text/", "");
                item = new JMenuItem(newStyle);
                add(item);

                item.addActionListener(e -> {
                    SyntaxTextArea.getInstance().setSyntaxEditingStyle("text/" + ((JMenuItem) e.getSource()).getText());
                    RootFrame.getInstance().getSyntaxStyleLabel().setText("Syntax Language: " + newStyle);
                });
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
