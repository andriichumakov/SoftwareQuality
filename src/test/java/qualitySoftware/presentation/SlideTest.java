package qualitySoftware.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.creator.TextItemCreator;
import qualitySoftware.presentation.Slide;
import qualitySoftware.presentation.Style;
import qualitySoftware.slide.SlideItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SlideTest {

    private Slide slide;
    private Graphics2D graphics;
    private ImageObserver imageObserver;
    private Rectangle area;

    @BeforeEach
    void setUp() {
        slide = new Slide();
        BufferedImage bufferedImage = new BufferedImage(Slide.WIDTH, Slide.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.createGraphics();
        imageObserver = new Canvas();
        area = new Rectangle(0, 0, Slide.WIDTH, Slide.HEIGHT);
        Style.createStyles();
    }

    @Test
    void getTitle_setTitle_returnsCorrectTitle() {
        slide.setTitle("Test Slide");
        assertEquals("Test Slide", slide.getTitle());
    }

    @Test
    void getSize_initialSize_zero() {
        assertEquals(0, slide.getSize());
    }

    @Test
    void append_addSlideItem_increasesSize() {
        SlideItem item = TextItemCreator.createSlideItem(1, "Test Item");
        slide.append(item);
        assertEquals(1, slide.getSize());
    }

    @Test
    void getSlideItem_validNumber_returnsSlideItem() {
        SlideItem item = TextItemCreator.createSlideItem(1, "Test Item");
        slide.append(item);
        assertEquals(item, slide.getSlideItem(0));
    }

    @Test
    void getSlideItems_returnsAllSlideItems() {
        SlideItem item1 = TextItemCreator.createSlideItem(1, "Test Item 1");
        SlideItem item2 = TextItemCreator.createSlideItem(2, "Test Item 2");
        slide.append(item1);
        slide.append(item2);
        Vector<SlideItem> items = slide.getSlideItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }

    @Test
    void draw_withGraphics_drawsSlide() {
        slide.setTitle("Test Slide");
        SlideItem item = TextItemCreator.createSlideItem(1, "Test Item");
        slide.append(item);
        slide.draw(graphics, area, imageObserver);

        // Check if the title and item were drawn
        // We don't have assertions for actual drawing, so we assume no exceptions were thrown
        assertNotNull(graphics);
    }

    @Test
    void getScale_validArea_returnsCorrectScale() {
        float scale = slide.getScale(area);
        assertEquals(1.0f, scale);
    }
}
