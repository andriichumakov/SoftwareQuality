package qualitySoftware.decorator;

import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * SlideItemDecorator is an abstract class that serves as a base for all
 * decorators
 * that enhance the functionality of SlideItem objects by adding additional
 * features.
 */

public abstract class SlideItemDecorator implements SlideItem {

    // The SlideItem that this decorator wraps around
    protected SlideItem wrappee;

    // Constructor that accepts a SlideItem to be wrapped
    public SlideItemDecorator(SlideItem wrappee) {
        this.wrappee = wrappee;
    }

    // Retrieve the level of the wrapped SlideItem
    @Override
    public int getLevel() {
        return this.wrappee.getLevel();
    }

    // Setter to change the wrapped SlideItem
    public void setWrappee(SlideItem wrappee) {
        this.wrappee = wrappee;
    }

    // Format a Color object to a hex string
    public String formatColor(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    // Retrieve the bounding box of the wrapped SlideItem
    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        return this.wrappee.getBoundingBox(g, observer, scale, style);
    }

    // Draw the wrapped SlideItem
    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        this.wrappee.draw(x, y, scale, g, style, observer);
    }

    // Convert the decorator and its wrappee to XML format; to be implemented by
    // subclasses
    public abstract String toXML();
}