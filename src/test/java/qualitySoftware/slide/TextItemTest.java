package qualitySoftware.slide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.presentation.Style;
import qualitySoftware.slide.TextItem;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextItemTest {

    private TextItem textItem;
    private Style style;
    private Graphics2D graphics;
    private ImageObserver imageObserver;

    @BeforeEach
    void setUp() {
        textItem = new TextItem(1, "Sample Text");
        Style.createStyles(); // Initialize styles
        style = Style.getStyle(1);
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.createGraphics();
        imageObserver = new Canvas();
    }

    @Test
    void getText_defaultConstructor_returnsNoTextGiven() {
        TextItem defaultTextItem = new TextItem();
        assertEquals("No Text Given", defaultTextItem.getText());
    }

    @Test
    void getText_withText_returnsSampleText() {
        assertEquals("Sample Text", textItem.getText());
    }

    @Test
    void getAttributedString_withStyleAndScale_returnsAttributedString() {
        AttributedString attributedString = textItem.getAttributedString(style, 1.0f);
        assertNotNull(attributedString);
    }

    @Test
    void getBoundingBox_withGraphicsAndStyleAndScale_returnsRectangle() {
        Rectangle boundingBox = textItem.getBoundingBox(graphics, imageObserver, 1.0f, style);
        assertNotNull(boundingBox);
        assertTrue(boundingBox.width > 0);
        assertTrue(boundingBox.height > 0);
    }

    @Test
    void draw_withGraphicsAndStyleAndScale_rendersText() {
        textItem.draw(10, 10, 1.0f, graphics, style, imageObserver);

        // Verify if the text drawing is set up correctly by checking the color
        assertEquals(style.getColor(), graphics.getColor());
    }

    @Test
    void toString_withLevelAndText_returnsCorrectString() {
        assertEquals("TextItem[1,Sample Text]", textItem.toString());
    }

    @Test
    void toXML_withLevelAndText_returnsCorrectXML() {
        assertEquals("<item kind=\"text\" level=\"1\">Sample Text</item>", textItem.toXML());
    }
}
