package qualitySoftware;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class BorderedItemDecorator extends SlideItemDecorator
{
    private Color borderColor;
    private int borderWidth;

    public BorderedItemDecorator(SlideItem wrappee) {
        super(wrappee);
        this.borderColor = Color.BLACK;
        this.borderWidth = 1;
    }
    public BorderedItemDecorator(SlideItem wrappee, Color borderColor, int borderWidth) {
        super(wrappee);
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    public Color getBorderColor()
    {
        return borderColor;
    }

    public int getBorderWidth()
    {
        return borderWidth;
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style)
    {
        Rectangle boundingBox = super.getBoundingBox(g, observer, scale, style);
        boundingBox.grow(borderWidth * 2, borderWidth * 2);
        return boundingBox;
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer)
    {
        g.setColor(borderColor);
        Rectangle boundingBox = getBoundingBox(g, observer, scale, style);
        g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        super.draw(x, y, scale, g, style, observer);
    }

    @Override
    public String toXML() {
        return "<wrap kind=\"border\" size=\"" + this.getBorderWidth() + "\" color=" + this.getBorderColor().toString() + "\">" +
                this.wrappee.toXML() + "</wrap>";
    }
}
