package qualitySoftware;

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
