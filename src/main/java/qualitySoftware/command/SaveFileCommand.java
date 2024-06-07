package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.accessor.Accessor;
import qualitySoftware.accessor.XMLAccessor;

import javax.swing.*;
import java.awt.Frame;
import java.io.IOException;

public class SaveFileCommand extends FrameCommand
{
    public SaveFileCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        this.presentation.saveFile(frame);
    }
}
