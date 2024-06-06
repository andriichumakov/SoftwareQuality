package qualitySoftware.command;

import qualitySoftware.presentation.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Frame;

import static org.junit.jupiter.api.Assertions.*;

class FrameCommandTest {

    private Frame testFrame;
    private Presentation testPresentation;

    @BeforeEach
    void setUp() {
        testFrame = new Frame();
        testPresentation = new Presentation();
    }

    @Test
    void testConstructor_InitializesFrameAndPresentation() {
        FrameCommand command = new FrameCommand(testFrame, testPresentation) {
            @Override
            public void execute() {
                // No implementation needed for this test
            }
        };

        assertNotNull(command.frame);
        assertNotNull(command.presentation);
        assertEquals(testFrame, command.frame);
        assertEquals(testPresentation, command.presentation);
    }

    @Test
    void testFilenameConstant_IsSetCorrectly() {
        assertEquals("Filename?", FrameCommand.FILENAME);
    }

    @Test
    void testTestfileConstant_IsSetCorrectly() {
        assertEquals("test.xml", FrameCommand.TESTFILE);
    }

    @Test
    void testSavefileConstant_IsSetCorrectly() {
        assertEquals("dump.xml", FrameCommand.SAVEFILE);
    }

    @Test
    void testIoExceptionConstant_IsSetCorrectly() {
        assertEquals("IO Exception: ", FrameCommand.IOEX);
    }

    @Test
    void testLoadErrorConstant_IsSetCorrectly() {
        assertEquals("Load Error", FrameCommand.LOADERR);
    }

    @Test
    void testSaveErrorConstant_IsSetCorrectly() {
        assertEquals("Save Error", FrameCommand.SAVEERR);
    }

    @Test
    void testPageNumberConstant_IsSetCorrectly() {
        assertEquals("Page number?", FrameCommand.PAGENR);
    }

    @Test
    void testExecute_IsAbstractMethod() {
        assertThrows(AbstractMethodError.class, () -> {
            FrameCommand command = new FrameCommand(testFrame, testPresentation) {
                @Override
                public void execute() {
                    throw new AbstractMethodError();
                }
            };
            command.execute();
        });
    }
}