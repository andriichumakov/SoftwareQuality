package qualitySoftware;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The application window for a slideviewcomponent
 * 
 * @author Ian F. Darwin, ian@darwinsys.com
 * @author Gert Florijn
 * @author Sylvia Stuurman
 * @version 1.6 2014/05/16
 */
public class SlideViewerFrame extends JFrame {
    private static final long serialVersionUID = 3227L;

    private static final String TITLE = "Jabberpoint 1.6 - OU";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    public SlideViewerFrame(String title, Presentation presentation) {
        super(title);
        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
        presentation.setShowView(slideViewerComponent);
        setupWindow(slideViewerComponent, presentation);
    }

    // Setup GUI
    public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
        setTitle(TITLE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().add(slideViewerComponent);
        addKeyListener(presentation.getKeyController()); // add a controller
        setMenuBar(new MenuController(this, presentation)); // add another controller
        setSize(new Dimension(WIDTH, HEIGHT)); // Same sizes as Slide has.
        setVisible(true);
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> {
            Presentation presentation = new Presentation(); // Create your presentation object here
            new SlideViewerFrame(TITLE, presentation);
        });
    }
}
