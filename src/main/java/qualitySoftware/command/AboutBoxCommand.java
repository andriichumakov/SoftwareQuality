package qualitySoftware.command;

import qualitySoftware.presentation.AboutBox;
import qualitySoftware.presentation.Presentation;

import java.awt.Frame;

public class AboutBoxCommand extends FrameCommand
{
    public AboutBoxCommand(Frame frame, Presentation presentation) {
        super(frame, presentation);
    }

    public void execute() {
        AboutBox.show(frame);
    }
}
