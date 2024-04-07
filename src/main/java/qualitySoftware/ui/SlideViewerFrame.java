package qualitySoftware.ui;

import qualitySoftware.command.*;
import qualitySoftware.presentation.Presentation;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 * <p>The application window for a slideviewcomponent</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;
	
	private static final String JABTITLE = "Jabberpoint 1.6 - OU";
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;

	private MenuController menuController;
	private KeyController keyController;
	
	public SlideViewerFrame(String title, Presentation presentation, MenuController menuController, KeyController keyController) {
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
		QuitCommand quit = new QuitCommand();
		// Next Slide shortcuts
		keyController.addCommand(KeyEvent.VK_DOWN, next);
		keyController.addCommand(KeyEvent.VK_PAGE_DOWN, next);
		keyController.addCommand(KeyEvent.VK_ENTER, next);
		keyController.addCommand('+', next);
		// Previous slide shortcuts
		keyController.addCommand(KeyEvent.VK_UP, prev);
		keyController.addCommand(KeyEvent.VK_PAGE_UP, prev);
		keyController.addCommand('-', prev);
		// Quit Command shortcuts
		keyController.addCommand('q', quit);
		keyController.addCommand('Q', quit);
	}

	private void setupMenuController(Presentation presentation) {
		// Commands
		OpenFileCommand openFile = new OpenFileCommand(this, presentation);
		NewFileCommand newFile = new NewFileCommand(this, presentation);
		SaveFileCommand saveFile = new SaveFileCommand(this, presentation);
		QuitCommand quit = new QuitCommand();
		NextSlideCommand nextSlide = new NextSlideCommand(presentation);
		PreviousSlideCommand prevSlide = new PreviousSlideCommand(presentation);
		GotoSlideCommand gotoSlide = new GotoSlideCommand(this, presentation);
		AboutBoxCommand aboutBox = new AboutBoxCommand(this, presentation);
		// Command Binds
		this.menuController.bindMenuItem("Open", openFile);
		this.menuController.bindMenuItem("New", newFile);
		this.menuController.bindMenuItem("Save", saveFile);
		this.menuController.bindMenuItem("Exit", quit);
		this.menuController.bindMenuItem("Next", nextSlide);
		this.menuController.bindMenuItem("Prev", prevSlide);
		this.menuController.bindMenuItem("Go to", gotoSlide);
		this.menuController.bindMenuItem("About", aboutBox);
	}


// Setup GUI
	public void setupWindow(SlideViewerComponent 
			slideViewerComponent, Presentation presentation) {
		setTitle(JABTITLE);
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		getContentPane().add(slideViewerComponent);
		addKeyListener(this.keyController); // add a controller
		setMenuBar(this.menuController);	// add another controller
		setSize(new Dimension(WIDTH, HEIGHT)); // Same sizes as Slide has.
		setVisible(true);
	}
}
