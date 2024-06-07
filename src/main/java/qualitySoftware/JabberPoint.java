package qualitySoftware;

import qualitySoftware.accessor.Accessor;
import qualitySoftware.accessor.XMLAccessor;
import qualitySoftware.presentation.Presentation;
import qualitySoftware.presentation.Style;
import qualitySoftware.ui.KeyController;
import qualitySoftware.ui.MenuController;
import qualitySoftware.ui.SlideViewerFrame;

import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * JabberPoint Main Program
 * 
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.-
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

public class JabberPoint {
    protected static final String IOERR = "IO Error: ";
    protected static final String JABERR = "Jabberpoint Error ";
    protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";

    /**
     * The main program entry point.
     * 
     * @param argv command line arguments, expects an optional file name for the presentation
     */
    public static void main(String[] argv) {
        Style.createStyles();

        Presentation presentation = new Presentation();
        new SlideViewerFrame(JABVERSION, presentation, new MenuController(), new KeyController());

        try {
            loadPresentation(argv, presentation);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, IOERR + ex, JABERR, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void loadPresentation(String[] argv, Presentation presentation) throws IOException {
        if (argv.length == 0) {
            Accessor.getDemoAccessor().loadFile(presentation, "");
        } else {
            new XMLAccessor().loadFile(presentation, argv[0]);
        }
        presentation.setSlideNumber(0);
    }
}