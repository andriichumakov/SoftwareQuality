package qualitySoftware.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.accessor.*;
import qualitySoftware.ui.*;

import static org.junit.jupiter.api.Assertions.*;

class PresentationTest {

    private Presentation presentation;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
    }

    @Test
    void getTitle_setTitle_returnsCorrectTitle() {
        presentation.setTitle("Test Presentation");
        assertEquals("Test Presentation", presentation.getTitle());
    }

    @Test
    void getSize_initialSize_zero() {
        assertEquals(0, presentation.getSize());
    }

    @Test
    void append_addSlide_increasesSize() {
        presentation.append(new Slide());
        assertEquals(1, presentation.getSize());
    }

    @Test
    void setSlideNumber_validNumber_updatesCurrentSlide() {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(1);
        assertEquals(slide2, presentation.getCurrentSlide());
    }

    @Test
    void prevSlide_notFirstSlide_decrementsSlideNumber() {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(1);
        presentation.prevSlide();
        assertEquals(slide1, presentation.getCurrentSlide());
    }

    @Test
    void nextSlide_notLastSlide_incrementsSlideNumber() {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(0);
        presentation.nextSlide();
        assertEquals(slide2, presentation.getCurrentSlide());
    }

    @Test
    void clear_removesAllSlides() {
        presentation.append(new Slide());
        presentation.clear();
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
    }

    @Test
    void getSlide_validNumber_returnsSlide() {
        Slide slide = new Slide();
        presentation.append(slide);
        assertEquals(slide, presentation.getSlide(0));
    }

    @Test
    void getSlide_invalidNumber_returnsNull() {
        assertNull(presentation.getSlide(0));
    }

    @Test
    void getCurrentSlide_returnsCurrentSlide() {
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);
        assertEquals(slide, presentation.getCurrentSlide());
    }

    @Test
    void setShowView_updatesSlideViewerComponent() {
        MockSlideViewerComponent mockSlideViewerComponent = new MockSlideViewerComponent();
        presentation.setShowView(mockSlideViewerComponent);
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);
        assertTrue(mockSlideViewerComponent.updated);
    }

    @Test
    void openFile_withMockInput_loadsPresentation() {
        presentation.openFile(new MockSlideViewerFrame("Test Frame"));

        // Assert that the presentation is loaded
        assertEquals(0, presentation.getSlideNumber());
    }

    // Mock classes for testing

    private static class MockSlideViewerComponent extends SlideViewerComponent {
        boolean updated = false;

        public MockSlideViewerComponent() {
            super(null, null);
        }

        @Override
        public void update(Presentation presentation, Slide slide) {
            updated = true;
        }
    }

    private static class MockSlideViewerFrame extends SlideViewerFrame {
        private String mockInput = "test.xml";

        public MockSlideViewerFrame(String title) {
            super(title, new Presentation(), new MenuController(), new KeyController());
        }

        @Override
        public String getStrDialogInput(String message) {
            return mockInput;
        }
    }

    private static class MockXMLAccessor extends XMLAccessor {
        static boolean loaded = false;
        static boolean saved = false;

        @Override
        public void loadFile(Presentation presentation, String filename) {
            loaded = true;
        }

        @Override
        public void saveFile(Presentation presentation, String filename) {
            saved = true;
        }
    }
}
