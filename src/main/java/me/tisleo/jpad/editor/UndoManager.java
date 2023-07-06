package me.tisleo.jpad.editor;

import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RUndoManager;

import javax.swing.event.UndoableEditEvent;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoableEdit;

/**
 * This class allows new lines to be undoable in an {@link RTextArea}.
 * See <a href="https://github.com/bobbylight/RSyntaxTextArea/issues/372">here</a> for the GitHub issue and a
 * more in-depth overview. Previously, undoing (Ctrl + Z or Command + Z) a new line would undo all previous text.
 * @author <a href="https://github.com/Sothatsit">Sothatsit</a>
 */
public class UndoManager extends RUndoManager {

    private final RTextArea textArea;

    public UndoManager(RTextArea textArea) {
        super(textArea);
        this.textArea = textArea;
    }

    /**
     * Allows new lines to be separately undoable.
     * @param e the UndoableEditEvent
     */
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        UndoableEdit edit = e.getEdit();

        if ("addition".equals(edit.getPresentationName())) {
            try {
                String inserted = textArea.getText(textArea.getCaretPosition() - 1, 1);
                if ("\n".equals(inserted)) {
                    beginInternalAtomicEdit();
                    super.undoableEditHappened(e);
                    endInternalAtomicEdit();
                    return;
                }
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
        super.undoableEditHappened(e);
    }

}
