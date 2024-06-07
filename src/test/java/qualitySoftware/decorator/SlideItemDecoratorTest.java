package qualitySoftware.decorator;

import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;

class SlideItemDecoratorTest {

    private static class MockSlideItem implements SlideItem {
        private int level;

        public MockSlideItem(int level) {
            this.level = level;
        }

        @Override
        public int getLevel() {
            return level;
        }

        @Override
        public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
            return new Rectangle(0, 0, 100, 50);
        }

        @Override
        public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
            // Mock drawing logic
        }

        @Override
        public String toXML() {
            return "";
        }
    }

    private static class ConcreteSlideItemDecorator extends SlideItemDecorator {
        public ConcreteSlideItemDecorator(SlideItem wrappee) {
            super(wrappee);
        }

        @Override
        public String toXML() {
            return "<ConcreteSlideItemDecorator/>";
        }
    }

    private SlideItem mockSlideItem;
    private SlideItemDecorator decorator;
    private Style testStyle;
    private BufferedImage testImage;
    private Graphics testGraphics;
    private ImageObserver testObserver;

    @BeforeEach
    void setUp() {
        mockSlideItem = new MockSlideItem(1);
        decorator = new ConcreteSlideItemDecorator(mockSlideItem);

        testStyle = new Style(10, Color.BLACK, 12, 10);
        testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        testGraphics = testImage.getGraphics();
        testObserver = (img, infoflags, x, y, width, height) -> false;
    }

    @Test
    void testConstructor_WithValidWrappee_SetsWrappee() {
        assertNotNull(decorator.wrappee);
        assertEquals(mockSlideItem, decorator.wrappee);
    }

    @Test
    void testConstructor_WithNullWrappee_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new ConcreteSlideItemDecorator(null));
    }

    @Test
    void testGetLevel_WhenCalled_ReturnsCorrectLevel() {
        assertEquals(1, decorator.getLevel());
    }

    @Test
    void testSetWrappee_WithValidWrappee_ChangesWrappee() {
        SlideItem newMockItem = new MockSlideItem(2);
        decorator.setWrappee(newMockItem);

        assertEquals(newMockItem, decorator.wrappee);
    }

    @Test
    void testSetWrappee_WithNullWrappee_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> decorator.setWrappee(null));
    }

    @Test
    void testFormatColor_WithValidColor_ReturnsCorrectHex() {
        String hexColor = decorator.formatColor(Color.RED);
        assertEquals("#ff0000", hexColor);
    }

    @Test
    void testFormatColor_WithNullColor_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> decorator.formatColor(null));
    }

    @Test
    void testGetBoundingBox_WhenCalled_ReturnsCorrectRectangle() {
        Rectangle boundingBox = decorator.getBoundingBox(testGraphics, testObserver, 1.0f, testStyle);

        assertNotNull(boundingBox);
        assertEquals(new Rectangle(0, 0, 100, 50), boundingBox);
    }

    @Test
    void testDraw_WhenCalled_DrawsWrappedItem() {
        decorator.draw(10, 10, 1.0f, testGraphics, testStyle, testObserver);

        // Can't directly test the drawing, but ensuring no exceptions is an indicator here
        assertTrue(true);
    }

    @Test
    void testToXML_WhenCalled_ReturnsCorrectXMLFormat() {
        assertEquals("<ConcreteSlideItemDecorator/>", decorator.toXML());
    }
}