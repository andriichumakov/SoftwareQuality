package qualitySoftware.slide;

import qualitySoftware.presentation.Style;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * Abstract class representing an item on a slide.
 *
 * All BaseSlideItems have drawing functionality.
 *
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 */

public abstract class BaseSlideItem implements SlideItem {

    private int level;

    // Constructor to set the level of the slide item.
    public BaseSlideItem(int level) {
        this.level = level;
    }

    public BaseSlideItem() {
        this(0);
    }

    // Get the level of the slide item.
    public int getLevel() {
        return level;
    }

    // Draw the item
    public abstract void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer);

    // Get the bounding box of the item
    public abstract Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style);

    // Convert the item to its XML representation.
    public abstract String toXML();
}
