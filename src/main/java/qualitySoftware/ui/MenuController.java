package qualitySoftware.ui;

import qualitySoftware.command.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * <p>
 * The controller for the menu
 * </p>
 *
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

	private static final long serialVersionUID = 227L;

	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";

	protected static final String TESTFILE = "test.xml";
	protected static final String SAVEFILE = "dump.xml";

	protected static final String IOEX = "IO Exception: ";
	protected static final String LOADERR = "Load Error";
	protected static final String SAVEERR = "Save Error";

	private final HashMap<String, MenuItem> menuItems;
	private final HashMap<String, Command> commands;

	public MenuController() {
		this.menuItems = new HashMap<>();
		this.commands = new HashMap<>();
		this.addFileMenu();
		this.addViewMenu();
		this.addHelpMenu();
	}

	private void addFileMenu() {
		Menu fileMenu = new Menu(FILE);
		fileMenu.add(this.mkMenuItem(NEW));
		fileMenu.add(this.mkMenuItem(OPEN));
		fileMenu.add(this.mkMenuItem(SAVE));
		fileMenu.addSeparator();
		fileMenu.add(this.mkMenuItem(EXIT));
		this.add(fileMenu);
	}

	private void addViewMenu() {
		Menu viewMenu = new Menu(VIEW);
		viewMenu.add(this.mkMenuItem(NEXT));
		viewMenu.add(this.mkMenuItem(PREV));
		viewMenu.add(this.mkMenuItem(GOTO));
		this.add(viewMenu);
	}

	private void addHelpMenu() {
		Menu helpMenu = new Menu(HELP);
		helpMenu.add(this.mkMenuItem(ABOUT));
		this.add(helpMenu);
	}

	// Create a menu item
	public MenuItem mkMenuItem(String name) {
		MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				executeCommand(name);
			}
		});
		this.menuItems.put(name, menuItem);
		return menuItem;
	}

	public void addCommand(String name, Command command) {
		this.commands.put(name, command);
	}

	public void removeCommand(String name) {
		this.commands.remove(name);
	}

	public void executeCommand(String name) {
		Command command = this.commands.get(name);
		if (command != null) {
			command.execute();
		}
	}

	public MenuItem getMenuItem(String name) {
		return menuItems.getOrDefault(name, null);
	}

	public void bindMenuItem(String name, Command command) {
		MenuItem menuItem = this.getMenuItem(name);
		if (menuItem != null) {
			this.addCommand(name, command);
		}
	}
}
