package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.ui.SlideViewerFrame;

import javax.swing.*;
import java.awt.*;

public class GotoSlideCommand extends FrameCommand
{
    public GotoSlideCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        this.presentation.gotoSlide(frame);
    }
}
