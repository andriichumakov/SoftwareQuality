package qualitySoftware.command;

import qualitySoftware.controller.Presentation;

import java.awt.Frame;

public class NewFileCommand extends FrameCommand
{
    public NewFileCommand(Frame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        // clear everything
        presentation.clear();
        frame.repaint();
    }
}