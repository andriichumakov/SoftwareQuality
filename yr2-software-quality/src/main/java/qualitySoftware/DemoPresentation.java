<<<<<<<< HEAD:src/main/java/qualitySoftware/accessor/DemoPresentation.java
package main.java.qualitySoftware.accessor;

import main.java.qualitySoftware.slide.BitmapItem;
import main.java.qualitySoftware.controller.Presentation;
import main.java.qualitySoftware.slide.Slide;
========
package qualitySoftware;

import org.w3c.dom.Text;

import java.awt.*;
>>>>>>>> refactor:yr2-software-quality/src/main/java/qualitySoftware/DemoPresentation.java

/** A built in demo-presentation
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

class DemoPresentation extends Accessor {

	public void loadFile(Presentation presentation, String unusedFilename) {
		presentation.setTitle("Demo Presentation");
		Slide slide;
		slide = new Slide();
		slide.setTitle("JabberPoint");
		slide.append(1, "The Java Presentation Tool");
		slide.append(2, "Copyright (c) 1996-2000: Ian Darwin");
		slide.append(2, "Copyright (c) 2000-now:");
		slide.append(2, "Gert Florijn andn Sylvia Stuurman");
		slide.append(4, "Starting JabberPoint without a filename");
		slide.append(4, "shows this presentation");
		slide.append(1, "Navigate:");
		slide.append(3, "Next slide: PgDn or Enter");
		slide.append(3, "Previous slide: PgUp or up-arrow");
		slide.append(3, "Quit: q or Q");
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("Demonstration of levels and stijlen");
		slide.append(1, "Level 1");
		slide.append(2, "Level 2");
		slide.append(1, "Again level 1");
		slide.append(1, "Level 1 has style number 1");
		slide.append(2, "Level 2 has style number  2");
		slide.append(3, "This is how level 3 looks like");
		slide.append(4, "And this is level 4");
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("The third slide");
		slide.append(1, "To open a new presentation,");
		slide.append(2, "use File->Open from the menu.");
		slide.append(1, " ");
		slide.append(1, "This is the end of the presentation.");
		slide.append(BitmapItemCreator.createSlideItem(1, "JabberPoint.gif"));
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("The fourth slide");
		slide.append(new ShadowedItemDecorator(TextItemCreator.createSlideItem(1, "This item has a shadow"),
				Color.DARK_GRAY, 3));
		slide.append(new BorderedItemDecorator(TextItemCreator.createSlideItem(2, "This one has a border"),
				Color.YELLOW));
		slide.append(new ShadowedItemDecorator(new BorderedItemDecorator(TextItemCreator.createSlideItem(3,
				"This one has both"), Color.BLUE), Color.DARK_GRAY, 3));
		slide.append(new BorderedItemDecorator(SlideItemCreator.createSlideItem(BitmapItem.class, 1, "serclogo_fc.jpg"), Color.red));
		presentation.append(slide);
	}

	public void saveFile(Presentation presentation, String unusedFilename) {
		throw new IllegalStateException("Save As->Demo! called");
	}
}
