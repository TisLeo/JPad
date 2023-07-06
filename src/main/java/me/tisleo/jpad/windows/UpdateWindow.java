package me.tisleo.jpad.windows;

import me.tisleo.jpad.listeners.UpdateLinkMouseListener;
import me.tisleo.jpad.utils.PropertiesHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UpdateWindow extends JDialog {

    /**
     * Tells user an update is available and gives them a link to download it, along with an option
     * to not show the update window again.
     */
    public UpdateWindow() {
        setTitle("Update available");
        setLayout(new BorderLayout());
        setSize(300, 160);
        setResizable(false);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel.add(new JLabel("An new JPad update is available."));
        panel.add(Box.createVerticalStrut(8));

        JLabel update = new JLabel("<html><a href=\"\">Download new update</a></html>");
        update.addMouseListener(new UpdateLinkMouseListener());
        update.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(update);
        panel.add(Box.createVerticalStrut(8));

        JCheckBox check = new JCheckBox("Don't show again.");
        panel.add(check);
        panel.add(Box.createVerticalStrut(4));

        JButton close = new JButton("Close");
        close.addActionListener(l -> {
            try {
                close(check);
            } catch (IOException e) {
                e.printStackTrace();
                dispose();
            }
        });
        panel.add(close);
        panel.add(Box.createVerticalStrut(4));

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Closes the window and sets the property to not show the update window again if the checkbox is selected.
     * @param check the checkbox to check if it is selected
     * @throws IOException if an I/O error occurs
     */
    private void close(JCheckBox check) throws IOException {
        if (check.isSelected()) {
            PropertiesHandler.setProperty("default.check_updates", "false");
        }
        dispose();
    }

}
