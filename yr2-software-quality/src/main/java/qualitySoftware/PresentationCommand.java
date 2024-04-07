<<<<<<<< HEAD:src/main/java/qualitySoftware/command/PresentationCommand.java
package main.java.qualitySoftware.command;

import main.java.qualitySoftware.controller.Presentation;
========
package qualitySoftware;
>>>>>>>> refactor:yr2-software-quality/src/main/java/qualitySoftware/PresentationCommand.java

public abstract class PresentationCommand implements Command
{
    protected Presentation presentation;
    public PresentationCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }
    public abstract void execute();
}
