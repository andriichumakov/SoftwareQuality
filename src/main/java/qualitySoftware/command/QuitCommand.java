package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;

public class QuitCommand extends PresentationCommand
{
    public QuitCommand(Presentation presentation) {
        super(presentation);
    }

    public void execute() {
        presentation.exit(0);
    }
}
