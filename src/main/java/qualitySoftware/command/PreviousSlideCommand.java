package qualitySoftware.command;

import qualitySoftware.controller.Presentation;

public class PreviousSlideCommand extends PresentationCommand
{

    public PreviousSlideCommand(Presentation presentation)
    {
        super(presentation);
    }

    public void execute()
    {
        this.presentation.prevSlide();
    }
}
