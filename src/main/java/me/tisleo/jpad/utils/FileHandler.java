package me.tisleo.jpad.utils;

import me.tisleo.jpad.editor.SyntaxTextArea;
import me.tisleo.jpad.windows.RootFrame;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles all file-related operations.
 * @author <a href="https://github.com/TisLeo">TisLeo</a>
 */
public final class FileHandler {

    /**
     * The default file filter for the file chooser dialogs (.txt files).
     */
    private static final FileFilter defaultFileFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.getName().endsWith(".txt") || f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "Text files (*.txt)";
        }
    };


    /**
     * Writes the given lines to the given file.
     * @param path the path to the file
     * @param lines the lines to write to the file
     * @throws IOException if an I/O error occurs
     */
    private static void writeToFile(String path, List<String> lines) throws IOException {
        Files.write(Paths.get(path), lines);
    }

    /**
     * Reads all lines from a file and returns them as a {@link List<String>}.
     * @param path the path to the file
     * @return the lines of the file
     * @throws IOException if an I/O error occurs
     */
    public static List<String> readFile(String path) throws IOException {
        return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
    }

    /**
     * Saves the current file in a background thread.
     * @param lines the lines to write to the file
     * @throws IOException if an I/O error occurs
     * @see SwingWorker
     */
    private static void saveFile(List<String> lines) throws IOException {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws IOException {
                writeToFile(LiveAppStore.getCurrentFile().getAbsolutePath(), lines);
                return null;
            }
        }.execute();
    }

    /**
     * Saves a new file in a background thread.
     * @param path the path to the new file
     * @param lines the lines to write to the file
     * @throws IOException if an I/O error occurs
     * @see SwingWorker
     */
    private static void saveFileAs(String path, List<String> lines) throws IOException {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws IOException {
                writeToFile(path, lines);
                return null;
            }
        }.execute();
    }

    /**
     * Attempts to save the current file. If the current working file is null, the user will be prompted to create
     * a new one.
     * @return true if the file was saved successfully, false otherwise
     * @see #attemptSaveFileAs()
     */
    public static boolean attemptSaveFile() {
        if (LiveAppStore.getCurrentFile() == null) {
            return attemptSaveFileAs() != null;
        } else {
            try {
                FileHandler.saveFile(Arrays.asList(SyntaxTextArea.getInstance().getText().split("\\n")));
                updateIfSaveSuccessful();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Attempts to create a new file based on the user's input from the {@link JFileChooser}.
     * @return the created file or null if the file is invalid.
     * @see #showSaveFileChooser()
     */
    public static File attemptSaveFileAs() {
        File f = showSaveFileChooser();
        if (f == null) return null;
        if (f.exists()) {
            int option = Dialogs.fileExists();
            if (option != JOptionPane.YES_OPTION) {
                return null;
            }
        }

        try {
            FileHandler.saveFileAs(f.getAbsolutePath(), Arrays.asList(SyntaxTextArea.getInstance().getText().split("\\n")));
            updateIfSaveAsSuccessful(f);
            return f;
        } catch (IOException ex) {
            ex.printStackTrace();
            Dialogs.saveError();
        }

        return null;
    }

    /**
     * Updates the app's current file, save state label, and window title if a Save As operation was successful.
     * @param f the file that was saved
     */
    private static void updateIfSaveAsSuccessful(File f) {
        LiveAppStore.setCurrentFile(f);
        LiveAppStore.setShouldSave(false);
        SwingUtilities.invokeLater(() -> RootFrame.getInstance().getSaveStateLabel().setText("Saved"));
        UI.changeTitle(f.getName());
    }

    /**
     * Updates the app's save-state label if a Save operation was successful.
     */
    private static void updateIfSaveSuccessful() {
        LiveAppStore.setShouldSave(false);
        SwingUtilities.invokeLater(() -> RootFrame.getInstance().getSaveStateLabel().setText("Saved"));
    }

    /**
     * Checks whether the user should save the current file after provoking an action that would cause the loss of
     * unsaved data. If this is the case, the user will be prompted to save the file.
     * @param fromShortcut whether the action was provoked by a keyboard shortcut
     */
    public static void checkIfShouldSave(boolean fromShortcut) {
        if (!LiveAppStore.shouldSave()) return;
        if (SyntaxTextArea.getInstance().getText().isEmpty()) return;
        if (!fromShortcut) {
            int option = Dialogs.promptFileSave();
            if (option != JOptionPane.YES_OPTION) return;
        }

        if (LiveAppStore.getCurrentFile() != null) {
            FileHandler.attemptSaveFile();
        } else {
            FileHandler.attemptSaveFileAs();
        }
    }

    /**
     * Shows a file chooser dialog with the open option.
     * @return The selected file or null if no valid file was selected.
     */
    public static File showOpenFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(defaultFileFilter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int option = fileChooser.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION) return null;
        LiveAppStore.setCurrentDir(fileChooser.getSelectedFile().getAbsolutePath());
        return fileChooser.getSelectedFile();
    }

    /**
     * Shows a file chooser dialog with the save option. Automatically sets the file name to 'untitled.txt'.
     * @return The selected file or null if no valid file was selected.
     */
    private static File showSaveFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setSelectedFile(new File((LiveAppStore.getCurrentDir() + "/untitled.txt")));

        int option = fileChooser.showSaveDialog(null);
        if (option != JFileChooser.APPROVE_OPTION) return null;
        LiveAppStore.setCurrentDir(fileChooser.getSelectedFile().getAbsolutePath());
        return fileChooser.getSelectedFile();
    }


}
