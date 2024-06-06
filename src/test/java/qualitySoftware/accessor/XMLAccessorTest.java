package qualitySoftware.accessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;
import qualitySoftware.decorator.BorderedItemDecorator;
import qualitySoftware.decorator.ShadowedItemDecorator;
import qualitySoftware.decorator.SlideItemDecorator;
import qualitySoftware.presentation.Presentation;
import qualitySoftware.presentation.Slide;
import qualitySoftware.slide.SlideItem;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.Color;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XMLAccessorTest {

    private XMLAccessor xmlAccessor;
    private Presentation mockPresentation;
    private Slide mockSlide;
    private Element mockElement;
    private NodeList mockNodeList;
    private Node mockNode;
    private SlideItem mockSlideItem;
    private SlideItemDecorator mockSlideItemDecorator;

    @BeforeEach
    void setUp() {
        xmlAccessor = new XMLAccessor();
        mockPresentation = mock(Presentation.class);
        mockSlide = mock(Slide.class);
        mockElement = mock(Element.class);
        mockNodeList = mock(NodeList.class);
        mockNode = mock(Node.class);
        mockSlideItem = mock(SlideItem.class);
        mockSlideItemDecorator = mock(SlideItemDecorator.class);
    }

    @Test
    void testGetTitle_ReturnsCorrectTitle() {
        when(mockElement.getElementsByTagName("title")).thenReturn(mockNodeList);
        when(mockNodeList.item(0)).thenReturn(mockNode);
        when(mockNode.getTextContent()).thenReturn("Test Title");

        String title = xmlAccessor.getTitle(mockElement, "title");
        assertEquals("Test Title", title);
    }

    @Test
    void testLoadFile_ReturnsDocElementOnValidFile() throws Exception {
        // Setup mock XML parsing infrastructure
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(
                "<root><showtitle>Test Show</showtitle></root>".getBytes()
        ));

        // Mocking loadFile dependencies
        XMLAccessor spyAccessor = spy(xmlAccessor);
        doReturn(builder).when(spyAccessor).newDocumentBuilder();
        doReturn(document).when(builder).parse(any(File.class));

        Element docElement = spyAccessor.loadFile("test.xml");
        assertNotNull(docElement);
        assertEquals("root", docElement.getTagName());
    }

    @Test
    void testLoadFile_PrintedErrorMessagesOnInvalidFile() {
        String invalidFilename = "nonexistent.xml";
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        try {
            xmlAccessor.loadFile(invalidFilename);
        } catch (IOException ignored) {
        }

        System.setErr(System.err);

        String errorMessage = errContent.toString();
        assertTrue(errorMessage.contains("nonexistent.xml"));
    }

    @Test
    void testLoadSlide_SetsSlideTitle() {
        when(mockElement.getElementsByTagName("title")).thenReturn(mockNodeList);
        when(mockNodeList.item(0)).thenReturn(mockNode);
        when(mockNode.getTextContent()).thenReturn("Slide Title");

        xmlAccessor.loadSlide(mockSlide, mockElement);
        verify(mockSlide, times(1)).setTitle("Slide Title");
    }

    @Test
    void testWrapperFromXML_ReturnsProperDecorator() {
        when(mockElement.getTagName()).thenReturn("wrap");
        NamedNodeMap attributes = mock(NamedNodeMap.class);
        Node kindNode = mock(Node.class);
        Node colorNode = mock(Node.class);
        Node thicknessNode = mock(Node.class);

        when(mockElement.getAttributes()).thenReturn(attributes);
        when(attributes.getNamedItem("kind")).thenReturn(kindNode);
        when(attributes.getNamedItem("color")).thenReturn(colorNode);
        when(attributes.getNamedItem("thickness")).thenReturn(thicknessNode);

        when(kindNode.getTextContent()).thenReturn("shadow");
        when(colorNode.getTextContent()).thenReturn("#000000");
        when(thicknessNode.getTextContent()).thenReturn("5");

        SlideItemDecorator decorator = xmlAccessor.wrapperFromXML(mockElement);
        assertNotNull(decorator);
        assertTrue(decorator instanceof ShadowedItemDecorator);
    }

    @Test
    void testItemFromXML_CreatesSlideItem() {
        NamedNodeMap attributes = mock(NamedNodeMap.class);
        Node levelNode = mock(Node.class);
        Node kindNode = mock(Node.class);

        when(mockElement.getAttributes()).thenReturn(attributes);
        when(attributes.getNamedItem("level")).thenReturn(levelNode);
        when(attributes.getNamedItem("kind")).thenReturn(kindNode);

        when(levelNode.getTextContent()).thenReturn("1");
        when(kindNode.getTextContent()).thenReturn("text");
        when(mockElement.getTextContent()).thenReturn("Slide Item Content");

        SlideItem item = xmlAccessor.itemFromXML(mockElement);
        assertNotNull(item);
    }

    @Test
    void testSaveFile_WritesPresentationToXML() throws IOException {
        File tempFile = File.createTempFile("test-presentation", ".xml");
        when(mockPresentation.getTitle()).thenReturn("Test Presentation");
        when(mockPresentation.getSize()).thenReturn(1);
        when(mockPresentation.getSlide(0)).thenReturn(mockSlide);
        when(mockSlide.getTitle()).thenReturn("Slide Title");

        xmlAccessor.saveFile(mockPresentation, tempFile.getAbsolutePath());

        assertTrue(tempFile.length() > 0);

        tempFile.deleteOnExit();
    }

    @Test
    void testSaveSlide_WritesSlideToPrintWriter() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Slide slide = new Slide();
        slide.setTitle("Test Slide");

        xmlAccessor.saveSlide(printWriter, slide);

        printWriter.flush();
        String output = stringWriter.toString();
        assertTrue(output.contains("<slide>"));
        assertTrue(output.contains("<title>Test Slide</title>"));
        assertTrue(output.contains("</slide>"));
    }

    @Test
    void testSaveSlideItem_WritesSlideItemToPrintWriter() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        SlideItem slideItem = mock(SlideItem.class);

        xmlAccessor.saveSlideItem(printWriter, slideItem);

        printWriter.flush();
        String output = stringWriter.toString();
        assertTrue(output.contains("<!--TODO make sure to implement the save-->"));
    }
}