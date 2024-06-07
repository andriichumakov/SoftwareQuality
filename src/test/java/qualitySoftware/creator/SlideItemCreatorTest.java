package qualitySoftware.creator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.slide.BitmapItem;
import qualitySoftware.slide.TextItem;
import qualitySoftware.slide.SlideItem;

import static org.junit.jupiter.api.Assertions.*;

class SlideItemCreatorTest {

    @BeforeEach
    void setUp() {
        // No specific setup required for static methods
    }

    @Test
    void testCreateSlideItem_WithTextItemClass_ReturnsTextItem() {
        SlideItem result = SlideItemCreator.createSlideItem(TextItem.class, 1, "Sample Text");
        assertNotNull(result);
        assertTrue(result instanceof TextItem);
        assertEquals(1, result.getLevel());
        assertEquals("Sample Text", ((TextItem) result).getText());
    }

    @Test
    void testCreateSlideItem_WithBitmapItemClass_ReturnsBitmapItem() {
        SlideItem result = SlideItemCreator.createSlideItem(BitmapItem.class, 2, "SampleImage.jpg");
        assertNotNull(result);
        assertTrue(result instanceof BitmapItem);
        assertEquals(2, result.getLevel());
        assertEquals("SampleImage.jpg", ((BitmapItem) result).getName());
    }

    @Test
    void testCreateSlideItem_WithUnknownClass_ReturnsNull() {
        SlideItem result = SlideItemCreator.createSlideItem(SlideItem.class, 1, "Unknown");
        assertNull(result);
    }

    @Test
    void testCreateSlideItem_WithTextString_ReturnsTextItem() {
        SlideItem result = SlideItemCreator.createSlideItem("text", 1, "Sample Text");
        assertNotNull(result);
        assertTrue(result instanceof TextItem);
        assertEquals(1, result.getLevel());
        assertEquals("Sample Text", ((TextItem) result).getText());
    }

    @Test
    void testCreateSlideItem_WithImageString_ReturnsBitmapItem() {
        SlideItem result = SlideItemCreator.createSlideItem("image", 2, "SampleImage.jpg");
        assertNotNull(result);
        assertTrue(result instanceof BitmapItem);
        assertEquals(2, result.getLevel());
        assertEquals("SampleImage.jpg", ((BitmapItem) result).getName());
    }

    @Test
    void testCreateSlideItem_WithUnknownString_ReturnsNull() {
        SlideItem result = SlideItemCreator.createSlideItem("unknown", 1, "Unknown");
        assertNull(result);
    }
}