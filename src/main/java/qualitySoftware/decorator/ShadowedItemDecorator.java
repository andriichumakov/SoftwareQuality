package qualitySoftware.decorator;

import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class ShadowedItemDecorator extends SlideItemDecorator
{
    private Color shadowColor;
    private int shadowThickness;

    public ShadowedItemDecorator(SlideItem wrappee, Color shadowColor, int shadowThickness) {
        super(wrappee);
        this.shadowColor = shadowColor;
        this.shadowThickness = shadowThickness;
    }

    public Color getShadowColor() {
        return shadowColor;
    }

    public int getShadowThickness() {
        return shadowThickness;
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        Rectangle boundingBox = super.getBoundingBox(g, observer, scale, style);
        boundingBox.grow(shadowThickness, shadowThickness);
        return boundingBox;
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        super.draw(x - this.shadowThickness, y - this.shadowThickness, scale, g, style, observer);

        // Draw shadow
        g.setColor(shadowColor);
        Rectangle boundingBox = getBoundingBox(g, observer, scale, style);
        int shadowX = boundingBox.x + x;
        int shadowY = boundingBox.y + y;
        int shadowWidth = boundingBox.width;
        int shadowHeight = boundingBox.height;
        g.fillRect(shadowX, shadowY + shadowHeight, shadowWidth, shadowThickness); // Bottom shadow
        g.fillRect(shadowX + shadowWidth, shadowY, shadowThickness, shadowHeight + shadowThickness); // Right shadow
    }

    @Override
    public String toXML() {
        return "<wrap kind=\"shadow\" color=\"" + this.formatColor(this.getShadowColor()) +
                "\" thickness=\"" + this.getShadowThickness() + "\">" +
                this.wrappee.toXML() + "</wrap>";
    }
}