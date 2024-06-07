package qualitySoftware.decorator;

import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;

class BorderedItemDecoratorTest {

    private BorderedItemDecorator decorator;
    private SlideItem mockSlideItem;
    private Style testStyle;
    private Graphics testGraphics;
    private ImageObserver testObserver;

    @BeforeEach
    void setUp() {
        mockSlideItem = new SlideItem() {
            @Override
            public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
                return new Rectangle(50, 50, 100, 100);
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
                // Mock implementation
            }

            @Override
            public String toXML() {
                return "<mock/>";
            }
        };

        decorator = new BorderedItemDecorator(mockSlideItem);

        testStyle = new Style(10, Color.BLACK, 12, 10);
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        testGraphics = testImage.getGraphics();
        testObserver = (img, infoflags, x, y, width, height) -> false;
    }

    @Test
    void testConstructor_WithValidWrappee_SetsWrappee() {
        assertNotNull(decorator);
        assertEquals(Color.BLACK, decorator.getBorderColor());
    }

    @Test
    void testConstructor_WithNullWrappee_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new BorderedItemDecorator(null));
        assertEquals("Wrappee cannot be null", exception.getMessage());
    }

    @Test
    void testConstructor_WithSpecifiedBorderColor_SetsBorderColor() {
        decorator = new BorderedItemDecorator(mockSlideItem, Color.RED);
        assertEquals(Color.RED, decorator.getBorderColor());
    }

    @Test
    void testGetBoundingBox_WhenCalled_ReturnsExpandedBoundingBox() {
        Rectangle boundingBox = decorator.getBoundingBox(testGraphics, testObserver, 1.0f, testStyle);
        Rectangle expectedBox = new Rectangle(48, 48, 104, 104); // 50,50,100,100 grew by 2 units
        assertEquals(expectedBox, boundingBox);
    }

    @Test
    void testDraw_WhenCalled_DrawsBorderAroundSlideItem() {
        decorator.draw(50, 50, 1.0f, testGraphics, testStyle, testObserver);
        Color originalColor = testGraphics.getColor();
        testGraphics.setColor(Color.BLACK);
        testGraphics.drawRect(49, 49, 106, 106);

        assertEquals(originalColor, testGraphics.getColor());
    }

    @Test
    void testToXML_WhenCalled_ReturnsXMLRepresentation() {
        decorator = new BorderedItemDecorator(mockSlideItem, Color.BLUE);
        String xmlRepresentation = decorator.toXML();
        assertEquals("<wrap kind=\"border\" color=\"#0000ff\"><mock/></wrap>", xmlRepresentation);
    }
}