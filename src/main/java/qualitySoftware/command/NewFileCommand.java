package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.ui.SlideViewerFrame;

import java.awt.Frame;

public class NewFileCommand extends FrameCommand
{
    public NewFileCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        this.presentation.newFile(frame);
    }
}
