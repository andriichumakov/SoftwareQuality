<<<<<<<< HEAD:src/main/java/qualitySoftware/slide/SlideItem.java
package main.java.qualitySoftware.slide;

import main.java.qualitySoftware.Style;
========
package qualitySoftware;
>>>>>>>> refactor:yr2-software-quality/src/main/java/qualitySoftware/SlideItem.java

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * <p>
 * The abstract class for an item on a slide
 * <p>
 * <p>
 * All SlideItems have drawingfunctionality.
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

<<<<<<<< HEAD:src/main/java/qualitySoftware/slide/SlideItem.java
public interface SlideItem {

	// Give the bounding box
	public abstract Rectangle getBoundingBox(Graphics g,
			ImageObserver observer, float scale, Style style);

	// Draw the item
	public abstract void draw(int x, int y, float scale,
========
interface SlideItem {

// Give the bounding box
	public Rectangle getBoundingBox(Graphics g,
			ImageObserver observer, float scale, Style style);

	public int getLevel();

// Draw the item
	public void draw(int x, int y, float scale,
>>>>>>>> refactor:yr2-software-quality/src/main/java/qualitySoftware/SlideItem.java
			Graphics g, Style style, ImageObserver observer);

	public String toXML();
}
