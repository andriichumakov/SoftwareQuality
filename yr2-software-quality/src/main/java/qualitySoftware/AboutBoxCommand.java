package qualitySoftware;

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
