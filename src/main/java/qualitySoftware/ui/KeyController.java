package qualitySoftware.ui;

import qualitySoftware.command.Command;
import qualitySoftware.command.NextSlideCommand;
import qualitySoftware.command.PreviousSlideCommand;
import qualitySoftware.command.QuitCommand;
import qualitySoftware.presentation.Presentation;

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

	public KeyController() {
		this.commands = new HashMap<>();
	}

	public void setup(Presentation presentation) {
		// responsible for ensuring that the key controller knows which commands to expect
		// if there are any new presentation-related keyboard commands, put them here
		NextSlideCommand next = new NextSlideCommand(presentation);
		PreviousSlideCommand prev =  new PreviousSlideCommand(presentation);
		QuitCommand quit = new QuitCommand(presentation);
		// Next Slide shortcuts
		this.addCommand(KeyEvent.VK_DOWN, next);
		this.addCommand(KeyEvent.VK_PAGE_DOWN, next);
		this.addCommand(KeyEvent.VK_ENTER, next);
		this.addCommand( KeyEvent.VK_PLUS, next);
		// Previous slide shortcuts
		this.addCommand(KeyEvent.VK_UP, prev);
		this.addCommand(KeyEvent.VK_PAGE_UP, prev);
		this.addCommand(KeyEvent.VK_MINUS, prev);
		// Quit Command shortcuts
		this.addCommand(KeyEvent.VK_Q, quit);
	}

	public void addCommand(Integer keyCode, Command command) {
		if (!this.commands.containsKey(keyCode)) {
			this.commands.put(keyCode, command);
		}
	}

	public void removeCommand(Integer keyCode) {
		this.commands.remove(keyCode);
	}

	public void executeCommand(Integer keyCode) {
		Command command = this.commands.get(keyCode);
		if (command != null) {
			command.execute();
		}
	}

	// override from KeyAdapter, this method is invoked any time a key is pressed
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();
		executeCommand(keyCode);
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
