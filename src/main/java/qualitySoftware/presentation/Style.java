package qualitySoftware.presentation;

import java.awt.Color;
import java.awt.Font;

/**
 * The Style class encapsulates styling properties such as indent, color, font,
 * and leading.
 * It maps style-number directly to item-level, used in Slide styles.
 * Authors: Ian F. Darwin, Gert Florijn, Sylvia Stuurman
 * Versions: 1.1 to 1.6
 */
public class Style {
	private static Style[] styles;

	private static final String FONTNAME = "Helvetica";
	private int indent;
	private Color color;
	private Font font;
	private int fontSize;
	private int leading;

	/**
	 * Creates and initializes the styles array with predefined styles.
	 */
	public static void createStyles() {
		styles = new Style[5];
		styles[0] = new Style(0, Color.RED, 48, 20);
		styles[1] = new Style(20, Color.BLUE, 40, 10);
		styles[2] = new Style(50, Color.BLACK, 36, 10); // styles for each of the item-levels
		styles[3] = new Style(70, Color.BLACK, 30, 10);
		styles[4] = new Style(90, Color.BLACK, 24, 10);
	}

	public static Style getStyle(int level) {
		if (level >= styles.length) {
			level = styles.length - 1;
		}
		return styles[level];
	}

	/**
	 * Initializes a Style object with the given parameters.
	 *
	 * @param indent   The indent value.
	 * @param color    The text color.
	 * @param fontSize The font size.
	 * @param leading  The leading value.
	 */

	public Style(int indent, Color color, int fontSize, int leading) {
		this.indent = indent;
		this.color = color;
		this.font = new Font(FONTNAME, Font.BOLD, this.fontSize = fontSize);
		this.leading = leading;
	}

	// Getters for Style properties
	public int getIndent() {
		return indent;
	}

	public Color getColor() {
		return color;
	}

	public Font getFont() {
		return font;
	}

	public int getFontSize() {
		return fontSize;
	}

	public int getLeading() {
		return leading;
	}

	@Override
	public String toString() {
		return "[" + indent + "," + color + "; " + fontSize + " on " + leading + "]";
	}

	/**
	 * Gets the font with a scaled size.
	 *
	 * @param scale The scale factor.
	 * @return The scaled font.
	 */
	public Font getFont(float scale) {
		return font.deriveFont(fontSize * scale);
	}
}
