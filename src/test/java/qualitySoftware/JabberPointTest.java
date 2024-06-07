package qualitySoftware;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qualitySoftware.accessor.Accessor;
import qualitySoftware.accessor.XMLAccessor;
import qualitySoftware.presentation.Presentation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class JabberPointTest {
    private final String sampleXMLPresentation = "<presentation><showtitle>Unit Test Pres</showtitle><slide><title>Slide Title</title><item level=\"1\" kind=\"text\">Item Text</item></slide></presentation>";
    private Presentation presentation;

    @BeforeEach
    public void setUp() {
        this.presentation = new Presentation();
    }

    @Test
    public void loadPresentation_noArguments_loadDemoPresentation() {
        assertDoesNotThrow(
                () -> {
                    JabberPoint.loadPresentation(new String[] {}, this.presentation);
                });
        assertEquals(presentation.getTitle(), "Demo Presentation");
    }

    @Test
    public void loadPresentation_existingTargetFile_loadTargetPresentation() throws IOException {
        // create an empty file to load the presentation from
        File tempFile = Files.createTempFile("unit_test", ".xml").toFile();
        PrintWriter out = new PrintWriter(tempFile);

        try {
            // write our sample presentation into the temp file
            out.println(sampleXMLPresentation);
            out.close();

            // Act
            JabberPoint.loadPresentation(new String[]{tempFile.getAbsolutePath()}, presentation);

            // Assert
            assertEquals("Unit Test Pres", presentation.getTitle());
        } finally {
            // Clean up the temporary file
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
