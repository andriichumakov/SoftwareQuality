package qualitySoftware.slide;

import qualitySoftware.presentation.Style;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Class representing a Bitmap item.
 * Bitmap items have the responsibility to draw themselves.
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

public class BitmapItem extends BaseSlideItem {

	private BufferedImage bufferedImage; // The image to be drawn
	private String imageName; // The name of the image file

	// Constants for error messages
	protected static final String FILE = "File ";
	protected static final String NOTFOUND = " not found";

	// Constructor to create a BitmapItem with a specified level and image name.
	public BitmapItem(int level, String name) {
		super(level);
		imageName = name;
		try {
			bufferedImage = ImageIO.read(new File(imageName));
		} catch (IOException e) {
			System.err.println(FILE + imageName + NOTFOUND);
		}
	}

	// Default constructor creating an empty BitmapItem.
	public BitmapItem() {
		this(0, null);
	}

	// Get the name of the image file.
	public String getName() {
		return imageName;
	}

	// Get the bounding box of the image.
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		return new Rectangle(
				(int) (myStyle.getIndent() * scale),
				0,
				(int) (bufferedImage.getWidth(observer) * scale),
				((int) (myStyle.getLeading() * scale)) + (int) (bufferedImage.getHeight(observer) * scale));
	}

	// Draw the image.
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		int width = x + (int) (myStyle.getIndent() * scale);
		int height = y + (int) (myStyle.getLeading() * scale);
		g.drawImage(
				bufferedImage,
				width,
				height,
				(int) (bufferedImage.getWidth(observer) * scale),
				(int) (bufferedImage.getHeight(observer) * scale),
				observer);
	}

	// Return a string representation of the BitmapItem.
	public String toString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}

	// Convert the item to its XML representation.
	public String toXML() {
		return "<item kind=\"image\" level=\"" + this.getLevel() + "\">" + this.getName() + "</item>";
	}
}
