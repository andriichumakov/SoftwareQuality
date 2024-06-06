package qualitySoftware.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.presentation.Presentation;
import java.awt.Frame;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NewFileCommandTest {

    private Frame mockFrame;
    private Presentation mockPresentation;
    private NewFileCommand newFileCommand;

    @BeforeEach
    void setUp() {
        mockFrame = mock(Frame.class);
        mockPresentation = mock(Presentation.class);
        newFileCommand = new NewFileCommand(mockFrame, mockPresentation);
    }

    @Test
    void testConstructor_WhenCalled_InitializesFrameAndPresentation() {
        assertNotNull(newFileCommand);
        assertEquals(mockFrame, newFileCommand.getFrame());
        assertEquals(mockPresentation, newFileCommand.getPresentation());
    }

    @Test
    void testExecute_WhenCalled_ClearsPresentationAndRepaintsFrame() {
        newFileCommand.execute();

        verify(mockPresentation, times(1)).clear();
        verify(mockFrame, times(1)).repaint();
    }
}
