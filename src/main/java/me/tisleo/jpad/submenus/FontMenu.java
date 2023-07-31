package me.tisleo.jpad.submenus;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.utils.LiveAppStore;
import me.tisleo.jpad.windows.RootFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * This class allows the user to change the text area font.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public class FontMenu extends JMenu {

    /**
     * Loads the system's available fonts in the background, setting the font for each menu item to
     * its corresponding font. When a menu item is clicked, the font is applied to the text area.
     *
     * @see Font
     */
    public FontMenu() {
        super("Font");

        new SwingWorker<Void, JMenuItem>() {
            @Override
            protected Void doInBackground()  {
                JMenuItem item;
                for (String s : LiveAppStore.SYSTEM_AVAILABLE_FONTS) {
                    item = new JMenuItem(s);
                    item.setFont(new Font(s, Font.PLAIN, 14));

                    item.addActionListener(e -> {
                        new Listener().actionPerformed(e);
                    });

                    publish(item);
                }
                return null;
            }

            @Override
            protected void process(List<JMenuItem> chunks) {
                for (JMenuItem item : chunks) {
                    add(item);
                }
            }
        }.execute();
    }

    private static class Listener implements ActionListener {

        /**
         * Applies the selected font to the text area.
         * @param e the (click) event that occurred (on the font menu item)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedFont = ((JMenuItem) e.getSource()).getText();
            SwingUtilities.invokeLater(() -> {
                SyntaxTextArea.getInstance().setFont(new Font(selectedFont, LiveAppStore.getCurrentFontStyle(), LiveAppStore.getCurrentFontSize()));
                RootFrame.getInstance().getFontLabel().setText("Font: " + ((JMenuItem) e.getSource()).getText() + " ");
            });
            LiveAppStore.setCurrentFontName(((JMenuItem) e.getSource()).getText());
        }
    }
}
