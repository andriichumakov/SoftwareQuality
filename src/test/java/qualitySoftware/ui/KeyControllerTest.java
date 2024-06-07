package qualitySoftware.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.command.Command;
import qualitySoftware.presentation.Presentation;
import qualitySoftware.ui.KeyController;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class KeyControllerTest {

    private KeyController keyController;
    private MockCommand nextCommand;
    private MockCommand prevCommand;
    private MockCommand quitCommand;

    @BeforeEach
    void setUp() {
        keyController = new KeyController();
        nextCommand = new MockCommand();
        prevCommand = new MockCommand();
        quitCommand = new MockCommand();

        // Adding commands manually for the test
        keyController.addCommand(KeyEvent.VK_DOWN, nextCommand);
        keyController.addCommand(KeyEvent.VK_PAGE_DOWN, nextCommand);
        keyController.addCommand(KeyEvent.VK_ENTER, nextCommand);
        keyController.addCommand(KeyEvent.VK_PLUS, nextCommand);

        keyController.addCommand(KeyEvent.VK_UP, prevCommand);
        keyController.addCommand(KeyEvent.VK_PAGE_UP, prevCommand);
        keyController.addCommand(KeyEvent.VK_MINUS, prevCommand);

        keyController.addCommand(KeyEvent.VK_Q, quitCommand);
    }

    @Test
    void testNextSlideCommands() {
        // Simulate key presses for next slide commands
        simulateKeyPress(KeyEvent.VK_DOWN);
        assertTrue(nextCommand.executed, "Next command should be executed for DOWN key");

        nextCommand.executed = false; // Reset the flag
        simulateKeyPress(KeyEvent.VK_PAGE_DOWN);
        assertTrue(nextCommand.executed, "Next command should be executed for PAGE_DOWN key");

        nextCommand.executed = false; // Reset the flag
        simulateKeyPress(KeyEvent.VK_ENTER);
        assertTrue(nextCommand.executed, "Next command should be executed for ENTER key");

        nextCommand.executed = false; // Reset the flag
        simulateKeyPress(KeyEvent.VK_PLUS);
        assertTrue(nextCommand.executed, "Next command should be executed for PLUS key");
    }

    @Test
    void testPreviousSlideCommands() {
        // Simulate key presses for previous slide commands
        simulateKeyPress(KeyEvent.VK_UP);
        assertTrue(prevCommand.executed, "Previous command should be executed for UP key");

        prevCommand.executed = false; // Reset the flag
        simulateKeyPress(KeyEvent.VK_PAGE_UP);
        assertTrue(prevCommand.executed, "Previous command should be executed for PAGE_UP key");

        prevCommand.executed = false; // Reset the flag
        simulateKeyPress(KeyEvent.VK_MINUS);
        assertTrue(prevCommand.executed, "Previous command should be executed for MINUS key");
    }

    @Test
    void testQuitCommands() {
        // Simulate key press for quit command
        simulateKeyPress(KeyEvent.VK_Q);
        assertTrue(quitCommand.executed, "Quit command should be executed for Q key");
    }

    @Test
    void testAddAndRemoveCommand() {
        MockCommand testCommand = new MockCommand();
        keyController.addCommand(KeyEvent.VK_A, testCommand);
        simulateKeyPress(KeyEvent.VK_A);
        assertTrue(testCommand.executed, "Test command should be executed for A key");

        keyController.removeCommand(KeyEvent.VK_A);
        testCommand.executed = false; // Reset the flag
        simulateKeyPress(KeyEvent.VK_A);
        assertFalse(testCommand.executed, "Test command should not be executed after removal");
    }

    private void simulateKeyPress(int keyCode) {
        KeyEvent keyEvent = new KeyEvent(new java.awt.TextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);
        keyController.keyPressed(keyEvent);
    }

    private static class MockCommand implements Command {
        boolean executed = false;

        @Override
        public void execute() {
            executed = true;
        }
    }
}
