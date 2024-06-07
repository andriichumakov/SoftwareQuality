package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.ui.SlideViewerFrame;
import java.awt.Frame;

public abstract class FrameCommand implements Command {
    protected final static String FILENAME = "Filename?";
    protected static final String TESTFILE = "test.xml";
    protected static final String SAVEFILE = "dump.xml";
    protected static final String IOEX = "IO Exception: ";
    protected static final String LOADERR = "Load Error";
    protected static final String SAVEERR = "Save Error";
    public static final String PAGENR = "Page number?";
    protected SlideViewerFrame frame;
    protected Presentation presentation;

    public FrameCommand(Frame frame, Presentation presentation) {
        this.frame = frame;
        this.presentation = presentation;
        this.frame = frame;
    }

    public Frame getFrame() {
        return frame;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public abstract void execute();
}
