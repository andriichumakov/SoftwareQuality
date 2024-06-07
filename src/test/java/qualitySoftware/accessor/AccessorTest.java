package qualitySoftware.accessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.presentation.Presentation;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AccessorTest {

    private Accessor accessor;

    @BeforeEach
    void setUp() {
        // Accessor is abstract, so we create an anonymous class to test it
        accessor = new Accessor() {
            @Override
            public void loadFile(Presentation p, String fn) throws IOException {
                // Test implementation
            }

            @Override
            public void saveFile(Presentation p, String fn) throws IOException {
                // Test implementation
            }
        };
    }

    @Test
    void testGetDemoAccessor_ReturnsDemoPresentationInstance() {
        Accessor demoAccessor = Accessor.getDemoAccessor();
        assertNotNull(demoAccessor);
        assertTrue(demoAccessor instanceof DemoPresentation);
    }

    @Test
    void testDemoNameConstant() {
        assertEquals("Demonstration presentation", Accessor.DEMO_NAME);
    }

    @Test
    void testDefaultExtensionConstant() {
        assertEquals(".xml", Accessor.DEFAULT_EXTENSION);
    }

    @Test
    void testConstructor() {
        assertNotNull(accessor); // Verifies that the abstract class can have an instance through the anonymous subclass
    }

    @Test
    void testLoadFile_ImplementationThrowsIOException() {
        Presentation presentation = new Presentation();
        String filename = "testfile.xml";

        assertThrows(IOException.class, () -> {
            accessor.loadFile(presentation, filename);
        });
    }

    @Test
    void testSaveFile_ImplementationThrowsIOException() {
        Presentation presentation = new Presentation();
        String filename = "testfile.xml";

        assertThrows(IOException.class, () -> {
            accessor.saveFile(presentation, filename);
        });
    }
}