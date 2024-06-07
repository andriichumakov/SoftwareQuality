package qualitySoftware.slide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.presentation.Style;
import qualitySoftware.slide.BitmapItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BitmapItemTest {

    private BitmapItem bitmapItem;
    private Style style;
    private Graphics2D graphics;
    private ImageObserver imageObserver;
    private BufferedImage bufferedImage;

    @BeforeEach
    void setUp() throws IOException {
        bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        File tempFile = File.createTempFile("testImage", ".png");
        ImageIO.write(bufferedImage, "png", tempFile);

        bitmapItem = new BitmapItem(1, tempFile.getAbsolutePath());
        Style.createStyles(); // Initialize styles
        style = Style.getStyle(1);
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.createGraphics();
        imageObserver = new Canvas();

        tempFile.deleteOnExit(); // Ensure the temporary file is deleted on exit
    }

    @Test
    void getName_withImageName_returnsImageName() {
        assertNotNull(bitmapItem.getName());
        assertTrue(bitmapItem.getName().endsWith(".png"));
    }

    @Test
    void getBoundingBox_withGraphicsAndStyleAndScale_returnsRectangle() {
        Rectangle boundingBox = bitmapItem.getBoundingBox(graphics, imageObserver, 1.0f, style);
        assertNotNull(boundingBox);
        assertTrue(boundingBox.width > 0);
        assertTrue(boundingBox.height > 0);
    }

    @Test
    void toString_withLevelAndImageName_returnsCorrectString() {
        assertTrue(bitmapItem.toString().startsWith("BitmapItem[1,"));
        assertTrue(bitmapItem.toString().endsWith(".png]"));
    }

    @Test
    void toXML_withLevelAndImageName_returnsCorrectXML() {
        assertTrue(bitmapItem.toXML().startsWith("<item kind=\"image\" level=\"1\">"));
        assertTrue(bitmapItem.toXML().endsWith(".png</item>"));
    }
}
