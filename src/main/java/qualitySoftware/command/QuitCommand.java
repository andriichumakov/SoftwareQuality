package qualitySoftware.command;

public class QuitCommand implements Command
{
    public void execute() {
        System.exit(0);
    }
}
