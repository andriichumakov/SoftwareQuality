package qualitySoftware.decorator;

import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * The ShadowedItemDecorator class adds a shadow to a SlideItem.
 * It wraps around a SlideItem and decorates its appearance with a shadow.
 */
public class ShadowedItemDecorator extends SlideItemDecorator {

    private Color shadowColor;
    private int shadowThickness;

    // Constructor with specified shadow color and thickness
    public ShadowedItemDecorator(SlideItem wrappee, Color shadowColor, int shadowThickness) {
        super(wrappee);
        this.shadowColor = shadowColor;
        this.shadowThickness = shadowThickness;
    }

    // Getter to retrieve the shadow color
    public Color getShadowColor() {
        return shadowColor;
    }

    // Getter to retrieve the shadow thickness
    public int getShadowThickness() {
        return shadowThickness;
    }

    /**
     * Overrides the getBoundingBox method to expand the bounding box by the shadow
     * thickness.
     */
    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        Rectangle boundingBox = super.getBoundingBox(g, observer, scale, style);
        // Increase bounding box size to account for the shadow
        boundingBox.grow(shadowThickness, shadowThickness);
        return boundingBox;
    }

    /**
     * Overrides the draw method to first draw the wrapped item, then draw the
     * shadow.
     */
    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        // Draw the wrapped item first, applying any existing decorations
        super.draw(x - this.shadowThickness, y - this.shadowThickness, scale, g, style, observer);

        g.setColor(shadowColor);

        // Calculate the bounding box of the item with the shadow offset
        Rectangle boundingBox = getBoundingBox(g, observer, scale, style);
        int shadowX = boundingBox.x + x;
        int shadowY = boundingBox.y + y;
        int shadowWidth = boundingBox.width;
        int shadowHeight = boundingBox.height;

        // Draw the shadow rectangles
        g.fillRect(shadowX, shadowY + shadowHeight, shadowWidth, shadowThickness); // Bottom shadow
        g.fillRect(shadowX + shadowWidth, shadowY, shadowThickness, shadowHeight + shadowThickness); // Right shadow
    }

    /**
     * Converts the decorator and its wrappee to XML format.
     */
    @Override
    public String toXML() {
        return "<wrap kind=\"shadow\" color=\"" + this.formatColor(this.getShadowColor())
                + "\" thickness=\"" + this.getShadowThickness() + "\">" + this.wrappee.toXML() + "</wrap>";
    }
}