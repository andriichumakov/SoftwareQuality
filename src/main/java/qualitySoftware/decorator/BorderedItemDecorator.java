package qualitySoftware.decorator;

import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * The BorderedItemDecorator class adds a border around a SlideItem.
 * It wraps around a SlideItem and decorates its appearance with a border.
 */
public class BorderedItemDecorator extends SlideItemDecorator {
    // Constants for default values
    private static final int BORDER_GROWTH = 2;
    private static final int DRAW_OFFSET = 4;
    private static final int BORDER_OFFSET = 1;

    // The color of the border
    private final Color borderColor;

    // Constructor with a default border color (black)
    public BorderedItemDecorator(SlideItem wrappee) {
        this(wrappee, Color.BLACK);
    }

    // Constructor allowing specification of border color
    public BorderedItemDecorator(SlideItem wrappee, Color borderColor) {
        super(wrappee);
        if (wrappee == null) {
            throw new IllegalArgumentException("Wrappee cannot be null");
        }
        this.borderColor = borderColor;
    }

    // Getter to retrieve the border color
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Overrides the getBoundingBox method to expand the bounding box by 2 units for
     * the border.
     */
    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        Rectangle boundingBox = super.getBoundingBox(g, observer, scale, style);
        // Increase bounding box size to account for the border
        boundingBox.grow(BORDER_GROWTH, BORDER_GROWTH);
        return boundingBox;
    }

    // Draws the wrapped item and its border at the specified location.
    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        // Get the bounding box of the item
        Rectangle boundingBox = getBoundingBox(g, observer, scale, style);
        // Draw the wrapped item first, applying any existing decorations
        super.draw(x - DRAW_OFFSET, y - DRAW_OFFSET, scale, g, style, observer);
        // Draw the border just outside the bounding box
        g.setColor(borderColor);
        g.drawRect(boundingBox.x - BORDER_OFFSET + x, boundingBox.y - BORDER_OFFSET + y,
                boundingBox.width + BORDER_GROWTH, boundingBox.height + BORDER_GROWTH);
    }

    // Converts the decorator and its wrappee to XML format.
    @Override
    public String toXML() {
        return "<wrap kind=\"border\" color=\"" + this.formatColor(this.getBorderColor()) + "\">" + this.wrappee.toXML()
                + "</wrap>";
    }
}