package qualitySoftware.ui;

import qualitySoftware.command.Command;

import java.awt.MenuBar;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 * <p>
 * The controller for the menu
 * </p>
 * 
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
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

	private HashMap<String, MenuItem> menuItems; // used to look up menu items based on their name to bind commands

	public MenuController() {
		this.menuItems = new HashMap<>();
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

	// create a menu item
	public MenuItem mkMenuItem(String name) {
		MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));
		this.menuItems.put(name, menuItem);
		return menuItem;
	}

	public void bindMenuItem(MenuItem menuItem, Command command) {
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command.execute();
			}
		});
	}

	public MenuItem getMenuItem(String name) {
		return menuItems.getOrDefault(name, null);
	}

	public void bindMenuItem(String name, Command command) {
		MenuItem menuItem = this.getMenuItem(name);
		if (menuItem != null) {
			this.bindMenuItem(menuItem, command);
		}
	}
}
