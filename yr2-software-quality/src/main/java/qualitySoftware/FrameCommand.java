package qualitySoftware;

import java.awt.Frame;

public abstract class FrameCommand implements Command
{
    protected Frame frame;
    protected Presentation presentation;
    
    public FrameCommand(Frame frame, Presentation presentation) {
        this.frame = frame;
        this.presentation = presentation;
    }
    public abstract void execute();
}
