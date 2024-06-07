package qualitySoftware.accessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.presentation.Presentation;

import static org.junit.jupiter.api.Assertions.*;

class DemoPresentationTest {

    private DemoPresentation demoPresentation;
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        demoPresentation = new DemoPresentation();
        presentation = new Presentation();
    }

    @Test
    void loadFile_loadsDemoPresentation() {
        // Act
        demoPresentation.loadFile(presentation, "");

        // Assert presentation title
        assertEquals("Demo Presentation", presentation.getTitle());

        // Assert the number of slides
        assertEquals(4, presentation.getSize());
    }

    @Test
    void saveFile_throwsIllegalStateException() {
        // Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            // Act
            demoPresentation.saveFile(presentation, "");
        });
        assertEquals("Save As->Demo! called", exception.getMessage());
    }
}
