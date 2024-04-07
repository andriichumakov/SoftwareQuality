import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qualitySoftware.command.GotoSlideCommand;
import qualitySoftware.presentation.Presentation;
import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GotoSlideCommandTest {
    @Mock
    Frame frame;

    @Mock
    Presentation presentation;

    GotoSlideCommand gotoSlideCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gotoSlideCommand = new GotoSlideCommand(frame, presentation);
    }

    @Test
    public void execute_success_slideChanged() {
        // Given
        String inputPage = "5";
        // Mocking JOptionPane input
        when(JOptionPane.showInputDialog(any(Object.class))).thenReturn(inputPage);

        // When
        gotoSlideCommand.execute();

        // Then
        verify(presentation, times(1)).setSlideNumber(Integer.parseInt(inputPage) - 1);
    }

    @Test
    public void execute_throwsNumberFormatException_slideNotChanged() {
        // Given
        String inputPage = "abc";
        // Mocking JOptionPane input
        when(JOptionPane.showInputDialog(any(Object.class))).thenReturn(inputPage);

        // When
        try {
            gotoSlideCommand.execute();
            fail("Expected NumberFormatException not thrown");
        } catch (NumberFormatException e) {
            // Then
            verify(presentation, never()).setSlideNumber(anyInt());
        }
    }
}