import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.accessor.DemoPresentation;
import qualitySoftware.presentation.Presentation;
import qualitySoftware.presentation.Slide;
// Added import statement

import static org.junit.jupiter.api.Assertions.*;

public class DemoPresentationTest {
    private Presentation presentation;
    private DemoPresentation demoPresentation;

    @BeforeEach // Use @Before instead of @BeforeEach for JUnit 4
    public void setUp() {
        presentation = new Presentation();
        demoPresentation = new DemoPresentation();
    }

    @Test // Added @Test annotation
    public void testLoadFile() {
        demoPresentation.loadFile(presentation, "unused");
        Slide firstSlide = presentation.getSlide(0);
        assertEquals("JabberPoint", firstSlide.getTitle());

        Slide secondSlide = presentation.getSlide(1);
        assertEquals("Demonstration of levels and stijlen", secondSlide.getTitle());

        Slide thirdSlide = presentation.getSlide(2);
        assertEquals("The third slide", thirdSlide.getTitle());

        Slide fourthSlide = presentation.getSlide(3);
        assertEquals("The fourth slide", fourthSlide.getTitle());
    }

    @Test // Added @Test annotation
    public void testSaveFile() {
        assertThrows(IllegalStateException.class, () -> {
            demoPresentation.saveFile(presentation, "unused");
        });
    }
}