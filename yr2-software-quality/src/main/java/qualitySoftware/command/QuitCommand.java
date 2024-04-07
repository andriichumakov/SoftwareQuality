package qualitySoftware.command;

import qualitySoftware.command.Command;

public class QuitCommand implements Command
{
    public void execute() {
        System.exit(0);
    }
}
