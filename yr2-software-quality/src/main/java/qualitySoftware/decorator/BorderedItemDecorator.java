package qualitySoftware.decorator;

import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class BorderedItemDecorator extends SlideItemDecorator
{
    private Color borderColor;

    public BorderedItemDecorator(SlideItem wrappee) {
        super(wrappee);
        this.borderColor = Color.BLACK;
    }
    public BorderedItemDecorator(SlideItem wrappee, Color borderColor) {
        super(wrappee);
        this.borderColor = borderColor;
    }

    public Color getBorderColor()
    {
        return borderColor;
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style)
    {
        Rectangle boundingBox = super.getBoundingBox(g, observer, scale, style);
        boundingBox.grow(2, 2);
        return boundingBox;
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer)
    {
        Rectangle boundingBox = getBoundingBox(g, observer, scale, style);

        // Draw the wrapped item first
        super.draw(x -4, y-4, scale, g, style, observer);

        // Draw the border just outside the bounding box
        g.setColor(borderColor);
        g.drawRect(boundingBox.x - 1 + x, boundingBox.y - 1 + y, boundingBox.width + 2, boundingBox.height + 2);
    }

    @Override
    public String toXML() {
        return "<wrap kind=\"border\" color=\"" + this.formatColor(this.getBorderColor()) + "\">" +
                this.wrappee.toXML() + "</wrap>";
    }
}
