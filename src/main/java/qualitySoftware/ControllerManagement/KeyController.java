package main.java.qualitySoftware.ControllerManagement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.HashMap;

/** <p>This is the KeyController (KeyListener)</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class KeyController extends KeyAdapter {
	private HashMap<Integer, Command> commands; // stores a list of all the keys that trigger a command; use addCommand to add another pair

	public KeyController()
	{
		this.commands = new HashMap<>();
	}

	public void addCommand(int keyCode, Command command)
	{
		if (!this.commands.containsKey(keyCode)) {
			this.commands.put(keyCode, command);
		}
	}

	// override from KeyAdapter, this method is invoked any time a key is pressed
	public void keyPressed(KeyEvent keyEvent)
	{
		// check if there is a corresponding command to the key, if so, execute it;
		int keyCode = keyEvent.getKeyCode();
		if (this.commands.containsKey(keyCode)) {
			this.commands.get(keyCode).execute();
		}
	}
	/*
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()) {
			case KeyEvent.VK_PAGE_DOWN:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_ENTER:
			case '+':
				presentation.nextSlide();
				break;
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_UP:
			case '-':
				presentation.prevSlide();
				break;
			case 'q':
			case 'Q':
				System.exit(0);
				break; // Probably never reached!!
			default:
				break;
		}
	}*/
}
