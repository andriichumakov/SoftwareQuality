package qualitySoftware.decorator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShadowedItemDecoratorTest {

    private SlideItem mockSlideItem;
    private ShadowedItemDecorator shadowedItemDecorator;
    private Graphics testGraphics;
    private ImageObserver testObserver;
    private Style testStyle;

    @BeforeEach
    void setUp() {
        mockSlideItem = mock(SlideItem.class);
        shadowedItemDecorator = new ShadowedItemDecorator(mockSlideItem, Color.GRAY, 5);

        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        testGraphics = testImage.getGraphics();
        testObserver = (img, infoflags, x, y, width, height) -> false;
        testStyle = new Style(10, Color.BLACK, 12, 10);
    }

    @Test
    void testConstructor_SetsShadowColorAndThickness() {
        assertEquals(Color.GRAY, shadowedItemDecorator.getShadowColor());
        assertEquals(5, shadowedItemDecorator.getShadowThickness());
    }

    @Test
    void testGetShadowColor() {
        assertEquals(Color.GRAY, shadowedItemDecorator.getShadowColor());
    }

    @Test
    void testGetShadowThickness() {
        assertEquals(5, shadowedItemDecorator.getShadowThickness());
    }

    @Test
    void testGetBoundingBox_ExpandsByShadowThickness() {
        Rectangle mockBoundingBox = new Rectangle(10, 20, 30, 40);
        when(mockSlideItem.getBoundingBox(testGraphics, testObserver, 1.0f, testStyle)).thenReturn(mockBoundingBox);

        Rectangle boundingBox = shadowedItemDecorator.getBoundingBox(testGraphics, testObserver, 1.0f, testStyle);

        assertEquals(new Rectangle(5, 15, 40, 50), boundingBox);
    }

    @Test
    void testDraw_RendersWrappedItemAndShadow() {
        doNothing().when(mockSlideItem).draw(anyInt(), anyInt(), anyFloat(), any(Graphics.class), any(Style.class), any(ImageObserver.class));
        Rectangle mockBoundingBox = new Rectangle(10, 20, 30, 40);
        when(mockSlideItem.getBoundingBox(any(Graphics.class), any(ImageObserver.class), anyFloat(), any(Style.class))).thenReturn(mockBoundingBox);

        shadowedItemDecorator.draw(10, 20, 1.0f, testGraphics, testStyle, testObserver);

        verify(mockSlideItem).draw(5, 15, 1.0f, testGraphics, testStyle, testObserver);
    }

    @Test
    void testToXML_ContainsShadowAttributesAndWrappeeXML() {
        when(mockSlideItem.toXML()).thenReturn("<item>Test</item>");

        String xmlOutput = shadowedItemDecorator.toXML();

        assertTrue(xmlOutput.contains("kind=\"shadow\""));
        assertTrue(xmlOutput.contains("color=\"#808080\""));
        assertTrue(xmlOutput.contains("thickness=\"5\""));
        assertTrue(xmlOutput.contains("<item>Test</item>"));
    }
}