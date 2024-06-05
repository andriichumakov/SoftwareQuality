package qualitySoftware.slide;

import qualitySoftware.presentation.Slide;
import qualitySoftware.presentation.Style;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.ArrayList;

/**
 * Class representing a text item.
 * A TextItem has drawing functionality.
 * 
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * 
 * @autor Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 */

public class TextItem extends BaseSlideItem {

	private String text;
	private static final String EMPTYTEXT = "No Text Given";

	// Constructor to create a TextItem with a specified level and text.
	public TextItem(int level, String string) {
		super(level);
		text = string;
	}

	// Default constructor creating an empty TextItem.
	public TextItem() {
		this(0, EMPTYTEXT);
	}

	// Get the text of the item.
	public String getText() {
		return text == null ? "" : text;
	}

	/**
	 * Get the AttributedString for the item.
	 * This method creates an AttributedString using the given style and scale,
	 * which is used for rendering the text.
	 * 
	 * @param style the style to be applied
	 * @param scale the scale factor
	 * @return the attributed string
	 */
	public AttributedString getAttributedString(Style style, float scale) {
		AttributedString attrStr = new AttributedString(getText());
		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());
		return attrStr;
	}

	/**
	 * Get the bounding box of the item.
	 * This method calculates the bounding box that encompasses the entire text
	 * item.
	 * 
	 * @param g        the graphics context
	 * @param observer the image observer
	 * @param scale    the scale factor
	 * @param myStyle  the style to be applied
	 * @return the bounding box of the item
	 */
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		List<TextLayout> layouts = getLayouts(g, myStyle, scale);
		int xsize = 0;
		int ysize = (int) (myStyle.getLeading() * scale);

		// Iterate over the text layouts to determine the maximum width and total height
		for (TextLayout layout : layouts) {
			Rectangle2D bounds = layout.getBounds();
			if (bounds.getWidth() > xsize) {
				xsize = (int) bounds.getWidth();
			}
			if (bounds.getHeight() > 0) {
				ysize += bounds.getHeight();
			}
			ysize += layout.getLeading() + layout.getDescent();
		}
		return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
	}

	/**
	 * Draw the item.
	 * This method renders the text item on the provided graphics context.
	 * 
	 * @param x        the x-coordinate
	 * @param y        the y-coordinate
	 * @param scale    the scale factor
	 * @param g        the graphics context
	 * @param myStyle  the style to be applied
	 * @param observer the image observer
	 */
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		// Check if text is empty or null, if so, do nothing
		if (text == null || text.isEmpty()) {
			return;
		}

		List<TextLayout> layouts = getLayouts(g, myStyle, scale);
		Point pen = new Point(x + (int) (myStyle.getIndent() * scale), y + (int) (myStyle.getLeading() * scale));
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(myStyle.getColor());

		// Draw each layout line
		for (TextLayout layout : layouts) {
			pen.y += layout.getAscent();
			layout.draw(g2d, pen.x, pen.y);
			pen.y += layout.getDescent();
		}
	}

	/**
	 * Get the layouts for the text item.
	 * This method splits the text into multiple TextLayout objects for proper
	 * rendering.
	 * 
	 * @param g     the graphics context
	 * @param s     the style to be applied
	 * @param scale the scale factor
	 * @return a list of text layouts
	 */
	private List<TextLayout> getLayouts(Graphics g, Style s, float scale) {
		List<TextLayout> layouts = new ArrayList<>();
		AttributedString attrStr = getAttributedString(s, scale);
		Graphics2D g2d = (Graphics2D) g;
		FontRenderContext frc = g2d.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
		float wrappingWidth = (Slide.WIDTH - s.getIndent()) * scale;

		// Create TextLayouts for each line of text
		while (measurer.getPosition() < getText().length()) {
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			layouts.add(layout);
		}
		return layouts;
	}

	@Override
	public String toString() {
		return "TextItem[" + getLevel() + "," + getText() + "]";
	}

	@Override
	public String toXML() {
		return "<item kind=\"text\" level=\"" + this.getLevel() + "\">" + this.getText() + "</item>";
	}
}