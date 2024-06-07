package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.ui.SlideViewerFrame;

public class SaveFileCommand extends FrameCommand
{
    public SaveFileCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        this.presentation.saveFile(frame);
    }
}
