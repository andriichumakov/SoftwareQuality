<<<<<<<< HEAD:src/main/java/qualitySoftware/command/PreviousSlideCommand.java
package main.java.qualitySoftware.command;

import main.java.qualitySoftware.controller.Presentation;
========
package qualitySoftware;
>>>>>>>> refactor:yr2-software-quality/src/main/java/qualitySoftware/PreviousSlideCommand.java

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
