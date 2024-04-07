package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;

public abstract class PresentationCommand implements Command
{
    protected Presentation presentation;
    public PresentationCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }
    public abstract void execute();
}
