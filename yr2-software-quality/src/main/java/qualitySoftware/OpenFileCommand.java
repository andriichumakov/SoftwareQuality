<<<<<<<< HEAD:src/main/java/qualitySoftware/command/OpenFileCommand.java
package main.java.qualitySoftware.command;
import main.java.qualitySoftware.controller.Presentation;
import main.java.qualitySoftware.accessor.XMLAccessor;
========
package qualitySoftware;
>>>>>>>> refactor:yr2-software-quality/src/main/java/qualitySoftware/OpenFileCommand.java

import javax.swing.*;
import java.awt.Frame;
import java.io.IOException;

public class OpenFileCommand extends FrameCommand
{
    public OpenFileCommand(Frame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        // Show a dialog, allow to specify the file
        presentation.clear();
        XMLAccessor xmlAccessor = new XMLAccessor();
        String targetFile = JOptionPane.showInputDialog((Object) FILENAME);
        if (targetFile.isEmpty()) { // if no file is specified, open the test file (test.xml)
            targetFile = TESTFILE;
        }
        try {
            xmlAccessor.loadFile(presentation, TESTFILE);
            presentation.setSlideNumber(0);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(frame, IOEX + exc,
                    LOADERR, JOptionPane.ERROR_MESSAGE);
        }
        frame.repaint();
        System.out.println("Opened File: " + targetFile);
    }
}
