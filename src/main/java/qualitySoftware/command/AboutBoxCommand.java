package qualitySoftware.command;

import qualitySoftware.presentation.AboutBox;
import qualitySoftware.presentation.Presentation;
import qualitySoftware.ui.SlideViewerFrame;

import java.awt.Frame;

public class AboutBoxCommand extends FrameCommand
{
    public AboutBoxCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        AboutBox.show(frame);
    }
}
