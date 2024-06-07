package qualitySoftware.ui;

import qualitySoftware.command.*;
import qualitySoftware.presentation.Presentation;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.*;

/**
 * The application window for a slide view component.
 * 
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * 
 * @autor Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 */

public class
SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;

	private static final String JABTITLE = "Jabberpoint 1.6 - OU";
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;

	private MenuController menuController;
	private KeyController keyController;

	/**
	 * Constructor to create the main application window.
	 * Sets up the key and menu controllers, the slide viewer component, and the
	 * window properties.
	 */
	public SlideViewerFrame(String title, Presentation presentation, MenuController menuController,
			KeyController keyController) {
		super(title);
		this.menuController = menuController;
		this.keyController = keyController;
		this.setupKeyController(presentation);
		this.setupMenuController(presentation);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		presentation.setShowView(slideViewerComponent);
		setupWindow(slideViewerComponent, presentation);
	}

	private void setupKeyController(Presentation presentation) {
		// responsible for ensuring that the key controller knows which commands to expect
		// if there are any new presentation-related keyboard commands, put them here
		NextSlideCommand next = new NextSlideCommand(presentation);
		PreviousSlideCommand prev =  new PreviousSlideCommand(presentation);
		QuitCommand quit = new QuitCommand(presentation);
		// Next Slide shortcuts
		this.keyController.addCommand(KeyEvent.VK_DOWN, next);
		this.keyController.addCommand(KeyEvent.VK_PAGE_DOWN, next);
		this.keyController.addCommand(KeyEvent.VK_ENTER, next);
		this.keyController.addCommand( KeyEvent.VK_PLUS, next);
		// Previous slide shortcuts
		this.keyController.addCommand(KeyEvent.VK_UP, prev);
		this.keyController.addCommand(KeyEvent.VK_PAGE_UP, prev);
		this.keyController.addCommand(KeyEvent.VK_MINUS, prev);
		// Quit Command shortcuts
		this.keyController.addCommand(KeyEvent.VK_Q, quit);
	}

	private void setupMenuController(Presentation presentation) {
		// Commands for various menu actions
		OpenFileCommand openFile = new OpenFileCommand(this, presentation);
		NewFileCommand newFile = new NewFileCommand(this, presentation);
		SaveFileCommand saveFile = new SaveFileCommand(this, presentation);
		QuitCommand quit = new QuitCommand(presentation);
		NextSlideCommand nextSlide = new NextSlideCommand(presentation);
		PreviousSlideCommand prevSlide = new PreviousSlideCommand(presentation);
		GotoSlideCommand gotoSlide = new GotoSlideCommand(this, presentation);
		AboutBoxCommand aboutBox = new AboutBoxCommand(this, presentation);

		// Bind menu items to commands
		this.menuController.addCommand("Open", openFile);
		this.menuController.addCommand("New", newFile);
		this.menuController.addCommand("Save", saveFile);
		this.menuController.addCommand("Exit", quit);
		this.menuController.addCommand("Next", nextSlide);
		this.menuController.addCommand("Prev", prevSlide);
		this.menuController.addCommand("Go to", gotoSlide);
		this.menuController.addCommand("About", aboutBox);
	}

	/**
	 * Setup the main application window.
	 * Initializes window properties, adds components, and sets visibility.
	 */
	public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
		setTitle(JABTITLE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		getContentPane().add(slideViewerComponent);
		addKeyListener(this.keyController); // add a controller
		setMenuBar(this.menuController);    // add another controller
		setSize(new Dimension(WIDTH, HEIGHT)); // Same sizes as Slide has.
		setVisible(true);
	}

	public String getStrDialogInput(String message) {
		return JOptionPane.showInputDialog(message);
	}

	public int getIntDialogInput(String message) {
		return Integer.parseInt(this.getStrDialogInput(message));
	}
}
