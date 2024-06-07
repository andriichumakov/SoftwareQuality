package qualitySoftware.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.presentation.Presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class NextSlideCommandTest {

    private Presentation mockPresentation;
    private NextSlideCommand nextSlideCommand;

    @BeforeEach
    void setUp() {
        // Create a mock Presentation object
        mockPresentation = mock(Presentation.class);

        // Create an instance of NextSlideCommand using the mock Presentation
        nextSlideCommand = new NextSlideCommand(mockPresentation);
    }

    @Test
    void testConstructor_WithValidPresentation_SetsPresentation() {
        // Verify that the Presentation object is correctly set in the constructor
        assertNotNull(nextSlideCommand.presentation);
        assertEquals(mockPresentation, nextSlideCommand.presentation);
    }

    @Test
    void testExecute_WhenCalled_CallsNextSlideOnPresentation() {
        // Execute the command
        nextSlideCommand.execute();

        // Verify that the nextSlide() method is called on the Presentation object
        verify(mockPresentation, times(1)).nextSlide();
    }
}