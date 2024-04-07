<<<<<<<< HEAD:src/main/java/qualitySoftware/command/NextSlideCommand.java
package main.java.qualitySoftware.command;

import main.java.qualitySoftware.controller.Presentation;
========
package qualitySoftware;
>>>>>>>> refactor:yr2-software-quality/src/main/java/qualitySoftware/NextSlideCommand.java

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
