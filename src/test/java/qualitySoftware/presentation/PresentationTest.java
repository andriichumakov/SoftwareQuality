package qualitySoftware.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.ui.SlideViewerComponent;
import qualitySoftware.ui.SlideViewerFrame;
import qualitySoftware.accessor.XMLAccessor;

import javax.swing.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PresentationTest {

    private Presentation presentation;
    private MockSlideViewerComponent mockSlideViewerComponent;
    private MockSlideViewerFrame mockSlideViewerFrame;

    @BeforeEach
    void setUp() {
        mockSlideViewerComponent = new MockSlideViewerComponent();
        mockSlideViewerFrame = new MockSlideViewerFrame("Test Frame");
        presentation = new Presentation(mockSlideViewerComponent);
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
    void setSlideNumber_updatesSlideViewerComponent() {
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);
        assertTrue(mockSlideViewerComponent.updated);
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
    void openFile_validFile_loadsPresentation() {
        // Mocking file dialog input
        mockSlideViewerFrame.setMockInput("test.xml");

        // Mocking XMLAccessor loadFile method
        MockXMLAccessor mockXMLAccessor = new MockXMLAccessor();
        presentation.openFile(mockSlideViewerFrame);

        // Verifying the presentation loaded
        assertTrue(mockXMLAccessor.loaded);
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void saveFile_validFile_savesPresentation() {
        // Mocking file dialog input
        mockSlideViewerFrame.setMockInput("test.xml");

        // Mocking XMLAccessor saveFile method
        MockXMLAccessor mockXMLAccessor = new MockXMLAccessor();
        presentation.saveFile(mockSlideViewerFrame);

        // Verifying the presentation saved
        assertTrue(mockXMLAccessor.saved);
    }

    // Mock classes

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
        private String mockInput;

        public MockSlideViewerFrame(String title) {
            super(title, new Presentation(), new MenuController(), new KeyController());
        }

        public void setMockInput(String input) {
            mockInput = input;
        }

        @Override
        public String getStrDialogInput(String message) {
            return mockInput;
        }
    }

    private static class MockXMLAccessor extends XMLAccessor {
        boolean loaded = false;
        boolean saved = false;

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
