package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.accessor.XMLAccessor;
import qualitySoftware.ui.SlideViewerFrame;

import javax.swing.*;
import java.io.IOException;

public class OpenFileCommand extends FrameCommand
{
    public OpenFileCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        this.presentation.openFile(frame);
    }
}
