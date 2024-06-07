package qualitySoftware.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.command.Command;
import qualitySoftware.ui.MenuController;

import java.awt.MenuItem;

import static org.junit.jupiter.api.Assertions.*;

class MenuControllerTest {

    private MenuController menuController;
    private MockCommand testCommand;

    @BeforeEach
    void setUp() {
        menuController = new MenuController();
        testCommand = new MockCommand();
    }

    @Test
    void testMenuControllerInitialization() {
        assertNotNull(menuController.getMenuItem(MenuController.NEW));
        assertNotNull(menuController.getMenuItem(MenuController.OPEN));
        assertNotNull(menuController.getMenuItem(MenuController.SAVE));
        assertNotNull(menuController.getMenuItem(MenuController.EXIT));
        assertNotNull(menuController.getMenuItem(MenuController.NEXT));
        assertNotNull(menuController.getMenuItem(MenuController.PREV));
        assertNotNull(menuController.getMenuItem(MenuController.GOTO));
        assertNotNull(menuController.getMenuItem(MenuController.ABOUT));
    }

    @Test
    void testMkMenuItem() {
        MenuItem menuItem = menuController.mkMenuItem("TestItem");
        assertNotNull(menuItem);
        assertEquals("TestItem", menuItem.getLabel());
    }

    @Test
    void testAddCommand() {
        menuController.addCommand("TestCommand", testCommand);
        menuController.executeCommand("TestCommand");
        assertTrue(testCommand.executed);
    }

    @Test
    void testRemoveCommand() {
        menuController.addCommand("TestCommand", testCommand);
        menuController.removeCommand("TestCommand");
        menuController.executeCommand("TestCommand");
        assertFalse(testCommand.executed);
    }

    @Test
    void testExecuteCommand() {
        menuController.addCommand("TestCommand", testCommand);
        menuController.executeCommand("TestCommand");
        assertTrue(testCommand.executed);
    }

    @Test
    void testGetMenuItem() {
        MenuItem menuItem = menuController.getMenuItem(MenuController.NEW);
        assertNotNull(menuItem);
        assertEquals(MenuController.NEW, menuItem.getLabel());
    }

    @Test
    void testBindMenuItem() {
        menuController.bindMenuItem(MenuController.SAVE, testCommand);
        menuController.executeCommand(MenuController.SAVE);
        assertTrue(testCommand.executed);
    }

    @Test
    void testActionPerformed() {
        menuController.addCommand(MenuController.NEW, testCommand);
        MenuItem menuItem = menuController.getMenuItem(MenuController.NEW);
        assertNotNull(menuItem);
        menuItem.getActionListeners()[0].actionPerformed(null); // Trigger the action
        assertTrue(testCommand.executed);
    }

    private static class MockCommand implements Command {
        boolean executed = false;

        @Override
        public void execute() {
            executed = true;
        }
    }
}
