package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.ui.SlideViewerFrame;

import java.awt.Frame;

public abstract class FrameCommand implements Command
{
    public final static String FILENAME = "Filename?";
    public static final String TESTFILE = "test.xml";
    public static final String SAVEFILE = "dump.xml";
    public static final String IOEX = "IO Exception: ";
    public static final String LOADERR = "Load Error";
    public static final String SAVEERR = "Save Error";
    public static final String PAGENR = "Page number?";
    protected SlideViewerFrame frame;
    protected Presentation presentation;
    
    public FrameCommand(SlideViewerFrame frame, Presentation presentation) {
        this.presentation = presentation;
        this.frame = frame;
    }
    public abstract void execute();
}
