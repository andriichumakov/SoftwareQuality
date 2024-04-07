package qualitySoftware.decorator;

import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;

import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class SlideItemDecorator implements SlideItem
{
    protected SlideItem wrappee;

    public SlideItemDecorator(SlideItem wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public int getLevel()
    {
        return this.wrappee.getLevel();
    }

    public SlideItem getWrappee()
    {
        return this.wrappee;
    }

    public void setWrappee(SlideItem wrappee)
    {
        this.wrappee = wrappee;
    }

    public String formatColor(Color color)
    {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        return this.wrappee.getBoundingBox(g, observer, scale, style);
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        this.wrappee.draw(x, y, scale, g, style, observer);
    }

    public abstract String toXML();
}
