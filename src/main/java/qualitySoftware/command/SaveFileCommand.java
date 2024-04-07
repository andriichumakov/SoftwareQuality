package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.accessor.Accessor;
import qualitySoftware.accessor.XMLAccessor;

import javax.swing.*;
import java.awt.Frame;
import java.io.IOException;

public class SaveFileCommand extends FrameCommand
{
    public SaveFileCommand(Frame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        // clear everything
        Accessor xmlAccessor = new XMLAccessor();
        String targetFile = JOptionPane.showInputDialog((Object) FILENAME);
        if (targetFile == null || targetFile.trim().isEmpty()) { // if no file is specified, open the test file (test.xml)
            targetFile = SAVEFILE;
        }
        try {
            xmlAccessor.saveFile(presentation, targetFile);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(frame, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Saved in: " + targetFile);
    }
}
