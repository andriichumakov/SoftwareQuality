package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;

import javax.swing.*;
import java.awt.*;

public class GotoSlideCommand extends FrameCommand {
    public GotoSlideCommand(Frame frame, Presentation presentation) {
        super(frame, presentation);
    }

    @Override
    public void execute() {
        String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
        execute(pageNumberStr);
    }

    // Overloaded method for testing purposes
    public void execute(String pageNumberStr) {
        try {
            int pageNumber = Integer.parseInt(pageNumberStr);
            presentation.setSlideNumber(pageNumber - 1);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid slide number format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
