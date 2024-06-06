package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.accessor.XMLAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class OpenFileCommandTest {

    private OpenFileCommand openFileCommand;
    private Frame mockFrame;
    private Presentation mockPresentation;
    private XMLAccessor mockXMLAccessor;

    @BeforeEach
    void setUp() {
        mockFrame = mock(Frame.class);
        mockPresentation = mock(Presentation.class);
        mockXMLAccessor = mock(XMLAccessor.class);

        openFileCommand = new OpenFileCommand(mockFrame, mockPresentation);

        // Replace XMLAccessor with the mocked one
        Field xmlAccessorField = null;
        try {
            xmlAccessorField = OpenFileCommand.class.getDeclaredField("xmlAccessor");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        xmlAccessorField.setAccessible(true);
        try {
            xmlAccessorField.set(openFileCommand, mockXMLAccessor);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testExecute_WhenNoFileSpecified_OpensTestFile() throws IOException {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString())).thenReturn("");

            openFileCommand.execute();

            verify(mockPresentation).clear();
            verify(mockXMLAccessor).loadFile(mockPresentation, OpenFileCommand.TESTFILE);
            verify(mockPresentation).setSlideNumber(0);
            verify(mockFrame).repaint();
        }
    }

    @Test
    void testExecute_WhenFileSpecified_OpensSpecifiedFile() throws IOException {
        String targetFile = "example.xml";

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString())).thenReturn(targetFile);

            openFileCommand.execute();

            verify(mockPresentation).clear();
            verify(mockXMLAccessor).loadFile(mockPresentation, targetFile);
            verify(mockPresentation).setSlideNumber(0);
            verify(mockFrame).repaint();
        }
    }

    @Test
    void testExecute_WhenIOExceptionOccurs_ShowsErrorDialog() throws IOException {
        String targetFile = "example.xml";

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString())).thenReturn(targetFile);
            doThrow(new IOException("Test IOException")).when(mockXMLAccessor).loadFile(mockPresentation, targetFile);

            openFileCommand.execute();

            verify(mockPresentation).clear();
            verify(mockXMLAccessor).loadFile(mockPresentation, targetFile);
            verify(mockPresentation, never()).setSlideNumber(0);
            verify(mockFrame).repaint();
            mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(
                    eq(mockFrame),
                    eq(OpenFileCommand.IOEX + "Test IOException"),
                    eq(OpenFileCommand.LOADERR),
                    eq(JOptionPane.ERROR_MESSAGE)
            ));
        }
    }
}
