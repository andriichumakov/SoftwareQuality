package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import qualitySoftware.accessor.XMLAccessor;
import javax.swing.*;
import java.awt.Frame;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaveFileCommandTest {

    private SaveFileCommand saveFileCommand;
    private Frame mockFrame;
    private Presentation mockPresentation;
    private XMLAccessor mockXMLAccessor;

    @BeforeEach
    void setUp() {
        mockFrame = mock(Frame.class);
        mockPresentation = mock(Presentation.class);
        saveFileCommand = new SaveFileCommand(mockFrame, mockPresentation);
        mockXMLAccessor = mock(XMLAccessor.class);
        JOptionPane.setRootFrame(null); // Prevent actual dialogs during tests
    }

    @Test
    void testConstructor_WithValidParameters_SuccessfullyCreatesObject() {
        assertNotNull(saveFileCommand);
    }

    @Test
    void testExecute_WithValidFileName_SuccessfullySavesFile() throws IOException {
        String fileName = "validFile.xml";

        try (MockedStatic<JOptionPane> jOptionPaneMock = mockStatic(JOptionPane.class)) {
            jOptionPaneMock.when(() -> JOptionPane.showInputDialog(any())).thenReturn(fileName);

            saveFileCommand.execute();

            verify(mockXMLAccessor).saveFile(mockPresentation, fileName);
        }
    }

    @Test
    void testExecute_WithEmptyFileName_UsesDefaultSaveFileName() throws IOException {
        String defaultFileName = "test.xml";

        try (MockedStatic<JOptionPane> jOptionPaneMock = mockStatic(JOptionPane.class)) {
            jOptionPaneMock.when(() -> JOptionPane.showInputDialog(any())).thenReturn("");

            saveFileCommand.execute();

            verify(mockXMLAccessor).saveFile(mockPresentation, defaultFileName);
        }
    }

    @Test
    void testExecute_WithNullFileName_UsesDefaultSaveFileName() throws IOException {
        String defaultFileName = "test.xml";

        try (MockedStatic<JOptionPane> jOptionPaneMock = mockStatic(JOptionPane.class)) {
            jOptionPaneMock.when(() -> JOptionPane.showInputDialog(any())).thenReturn(null);

            saveFileCommand.execute();

            verify(mockXMLAccessor).saveFile(mockPresentation, defaultFileName);
        }
    }

    @Test
    void testExecute_WithIOException_ShowsErrorMessage() throws IOException {
        String fileName = "invalidFile.xml";

        try (MockedStatic<JOptionPane> jOptionPaneMock = mockStatic(JOptionPane.class)) {
            jOptionPaneMock.when(() -> JOptionPane.showInputDialog(any())).thenReturn(fileName);
            doThrow(new IOException("IO Error")).when(mockXMLAccessor).saveFile(mockPresentation, fileName);

            saveFileCommand.execute();

            jOptionPaneMock.verify(() -> JOptionPane.showMessageDialog(eq(mockFrame), contains("IO Error"),
                    eq("Save Error"), eq(JOptionPane.ERROR_MESSAGE)));
        }
    }
}