package main.java.qualitySoftware.command;

import main.java.qualitySoftware.controller.Presentation;

public class NextSlideCommand extends PresentationCommand
{

    public NextSlideCommand(Presentation presentation)
    {
        super(presentation);
    }

    public void execute()
    {
        this.presentation.nextSlide();
    }
}
