package main.java.qualitySoftware.ControllerManagement;
import javax.swing.*;
import java.awt.Frame;
import java.io.IOException;

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