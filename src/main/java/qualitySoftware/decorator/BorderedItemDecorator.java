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

    // The color of the border
    private Color borderColor;

    // Constructor with a default border color (black)
    public BorderedItemDecorator(SlideItem wrappee) {
        super(wrappee);
        this.borderColor = Color.BLACK;
    }

    // Constructor allowing specification of border color
    public BorderedItemDecorator(SlideItem wrappee, Color borderColor) {
        super(wrappee);
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
        boundingBox.grow(2, 2);
        return boundingBox;
    }

    /**
     * Overrides the draw method to first draw the wrapped item, then draw the
     * surrounding border.
     */
    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        // Get the bounding box of the item
        Rectangle boundingBox = getBoundingBox(g, observer, scale, style);
        // Draw the wrapped item first, applying any existing decorations
        super.draw(x - 4, y - 4, scale, g, style, observer);
        // Draw the border just outside the bounding box
        g.setColor(borderColor);
        g.drawRect(boundingBox.x - 1 + x, boundingBox.y - 1 + y, boundingBox.width + 2, boundingBox.height + 2);
    }

    /**
     * Converts the decorator and its wrappee to XML format.
     */
    @Override
    public String toXML() {
        return "<wrap kind=\"border\" color=\"" + this.formatColor(this.getBorderColor()) + "\">"
                + this.wrappee.toXML() + "</wrap>";
    }
}