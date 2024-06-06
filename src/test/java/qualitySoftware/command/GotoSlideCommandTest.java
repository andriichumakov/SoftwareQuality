package qualitySoftware.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import qualitySoftware.command.GotoSlideCommand;
import qualitySoftware.presentation.Presentation;

import javax.swing.*;
import java.awt.*;

import static org.mockito.Mockito.*;

public class GotoSlideCommandTest {
    @Mock
    Frame frame;

    @Mock
    Presentation presentation;

    GotoSlideCommand gotoSlideCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gotoSlideCommand = new GotoSlideCommand(frame, presentation);
    }

    @Test
    public void execute_success_slideChanged() {
        // Given
        String inputPage = "1";

        // Mocking JOptionPane input
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(any(Object.class))).thenReturn(inputPage);

            // When
            gotoSlideCommand.execute();

            // Then
            verify(presentation, times(1)).setSlideNumber(Integer.parseInt(inputPage) - 1);
        }
    }

    @Test
    public void execute_throwsNumberFormatException_slideNotChanged() {
        // Given
        String inputPage = "invalid";

        // Mocking JOptionPane input and showMessageDialog
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(any(Object.class))).thenReturn(inputPage);
            doNothing().when(JOptionPane.class);
            JOptionPane.showMessageDialog(any(), anyString(), anyString(), anyInt());

            // When
            gotoSlideCommand.execute();

            // Then
            verify(presentation, never()).setSlideNumber(anyInt());
            mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(eq(frame), eq("Invalid slide number format"), eq("Error"), eq(JOptionPane.ERROR_MESSAGE)), times(1));
        }
    }
}
