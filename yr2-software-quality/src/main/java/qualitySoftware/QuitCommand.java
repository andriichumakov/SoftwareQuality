<<<<<<<< HEAD:src/main/java/qualitySoftware/command/QuitCommand.java
package main.java.qualitySoftware.command;
========
package qualitySoftware;
>>>>>>>> refactor:yr2-software-quality/src/main/java/qualitySoftware/QuitCommand.java

public class QuitCommand implements Command
{
    public void execute() {
        System.exit(0);
    }
}
