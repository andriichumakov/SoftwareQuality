package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;

import javax.swing.*;
import java.awt.*;

public class GotoSlideCommand extends FrameCommand
{
    public GotoSlideCommand(Frame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
        int pageNumber = Integer.parseInt(pageNumberStr);
        presentation.setSlideNumber(pageNumber - 1);
    }
}
