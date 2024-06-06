package qualitySoftware.accessor;

import java.awt.*;
import java.io.*;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import qualitySoftware.creator.SlideItemCreator;
import qualitySoftware.decorator.BorderedItemDecorator;
import qualitySoftware.decorator.ShadowedItemDecorator;
import qualitySoftware.decorator.SlideItemDecorator;
import qualitySoftware.presentation.Presentation;
import qualitySoftware.presentation.Slide;
import qualitySoftware.slide.BitmapItem;
import qualitySoftware.slide.SlideItem;
import qualitySoftware.slide.TextItem;

/**
 * XMLAccessor class handles reading and writing of XML files for presentations.
 * It utilizes DOM parsing to read XML content and build presentation objects.
 * Various versions indicate iterative improvements over the years.
 */
public class XMLAccessor extends Accessor {
	/** Default API to use for parsing XML */
	protected static final String DEFAULT_API_TO_USE = "dom";

	/** Common XML tag/attribute names */
	protected static final String SHOWTITLE = "showtitle";
	protected static final String SLIDETITLE = "title";
	protected static final String SLIDE = "slide";
	protected static final String ITEM = "item";
	protected static final String LEVEL = "level";
	protected static final String KIND = "kind";
	protected static final String TEXT = "text";
	protected static final String IMAGE = "image";

	/** Error messages */
	protected static final String PCE = "Parser Configuration Exception";
	protected static final String UNKNOWNTYPE = "Unknown Element type";
	protected static final String NFE = "Number Format Exception";

	// Retrieve the title of an element given a tag name
	protected String getTitle(Element element, String tagName) {
		NodeList titles = element.getElementsByTagName(tagName);
		return titles.item(0).getTextContent();
	}

	// Load an XML file and parse it into a DOM Element
	public Element loadFile(String filename) throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File(filename));
			Element doc = document.getDocumentElement();
			return doc;
		} catch (IOException iox) {
			System.err.println(iox.toString());
		} catch (SAXException sax) {
			System.err.println(sax.getMessage());
		} catch (ParserConfigurationException pcx) {
			System.err.println(PCE);
		}
		return null;
	}

	// Load presentation data from a file into a Presentation object
	public void loadFile(Presentation targetPres, String fileFrom) throws IOException {
		Element doc = this.loadFile(fileFrom);
		targetPres.setTitle(this.getTitle(doc, SHOWTITLE));
		NodeList xmlSlides = doc.getElementsByTagName(SLIDE);
		for (int slideIndex = 0; slideIndex < xmlSlides.getLength(); slideIndex++) {
			Element xmlSlide = (Element) xmlSlides.item(slideIndex);
			Slide slide = new Slide();
			this.loadSlide(slide, xmlSlide);
			targetPres.append(slide);
		}
	}

	// Load an individual slide from an XML element
	protected void loadSlide(Slide slide, Element xmlSlide) {
		slide.setTitle(this.getTitle(xmlSlide, SLIDETITLE));
		// Recursively traverse all descendant nodes of xmlSlide and load slide components
		loadSlideComponents(slide, xmlSlide.getChildNodes(), null);
	}

	// Recursively load slide components, handling nested decorations
	protected void loadSlideComponents(Slide slide, NodeList nodeList, SlideItemDecorator lastWrapper) {
		SlideItemDecorator lastEmptyWrapper = lastWrapper;
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			String tagName = element.getTagName();
			if ("wrap".equals(tagName)) {
				SlideItemDecorator emptyWrap = this.wrapperFromXML(element);
				if (lastEmptyWrapper != null) {
					lastEmptyWrapper.setWrappee(emptyWrap);
				}
				lastEmptyWrapper = emptyWrap;
				this.loadSlideComponents(slide, element.getChildNodes(), lastEmptyWrapper);
				lastEmptyWrapper = null;
			} else if ("item".equals(tagName)) {
				SlideItem item = itemFromXML(element);
				if (lastEmptyWrapper != null) {
					lastEmptyWrapper.setWrappee(item);
					slide.append(lastEmptyWrapper);
					lastEmptyWrapper = null;
				} else {
					slide.append(item);
				}
			}
		}
	}

	// Create a wrapper (decorator) from an XML element
	protected SlideItemDecorator wrapperFromXML(Element xmlWrapper) {
		SlideItemDecorator wrapper = null;
		NamedNodeMap wrapperAttributes = xmlWrapper.getAttributes();
		String wrapperType = wrapperAttributes.getNamedItem(KIND).getTextContent();
		Color color = Color.BLACK;
		String colorTxt = wrapperAttributes.getNamedItem("color").getTextContent();
		try {
			color = Color.decode(colorTxt);
		} catch (NumberFormatException | NullPointerException ex) {
			ex.printStackTrace();
		}
		// Determine the wrapper type and create the appropriate decorator
		if (wrapperType.equals("shadow")) {
			int thickness = 1;
			String thicknessTxt = wrapperAttributes.getNamedItem("thickness").getTextContent();
			try {
				thickness = Integer.parseInt(thicknessTxt);
			} catch (NumberFormatException | NullPointerException ex) {
				ex.printStackTrace();
			}
			if (color != null && thickness != 0) {
				wrapper = new ShadowedItemDecorator(null, color, thickness);
			}
		} else if (wrapperType.equals("border")) {
			if (color != null) {
				wrapper = new BorderedItemDecorator(null, color);
			}
		}
		return wrapper;
	}

	// Create a SlideItem from an XML element
	protected SlideItem itemFromXML(Element xmlItem) {
		int level = 1; // Default level
		NamedNodeMap attributes = xmlItem.getAttributes();
		String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
		if (leveltext != null) {
			try {
				level = Integer.parseInt(leveltext);
			} catch (NumberFormatException x) {
				System.err.println(NFE);
			}
		}
		// Determine the type of item (text/image), create the item accordingly
		String type = attributes.getNamedItem(KIND).getTextContent();
		SlideItem slideItem = SlideItemCreator.createSlideItem(type, level, xmlItem.getTextContent());
		if (slideItem == null) {
			System.err.println(UNKNOWNTYPE);
			return null;
		}
		return slideItem;
	}

	// Save the presentation to an XML file
	public void saveFile(Presentation presentation, String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		// Write XML header and root element
		out.println("<?xml version=\"1.0\"?>");
		out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
		out.println("<presentation>");
		out.print("<showtitle>");
		out.print(presentation.getTitle());
		out.println("</showtitle>");
		// Iterate over slides and save each one
		for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
			Slide slide = presentation.getSlide(slideNumber);
			this.saveSlide(out, slide);
		}
		out.println("</presentation>");
		out.close();
	}

	// Save an individual slide
	protected void saveSlide(PrintWriter out, Slide slide) {
		out.println("<slide>");
		out.println("<title>" + slide.getTitle() + "</title>");
		Vector<SlideItem> slideItems = slide.getSlideItems();
		// Iterate over slide items and save each one
		for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++) {
			SlideItem slideItem = slideItems.elementAt(itemNumber);
			saveSlideItem(out, slideItem);
		}
		out.println("</slide>");
	}

	// Save a single slide item (to be implemented)
	protected void saveSlideItem(PrintWriter out, SlideItem slideItem) {
		int level = slideItem.getLevel();
		if (slideItem instanceof TextItem) {
			out.println("<item kind=\"" + TEXT + "\" level=\"" + level + "\">");
			out.print(((TextItem) slideItem).getText());
		} else if (slideItem instanceof BitmapItem) {
			out.println("<item kind=\"" + IMAGE + "\" level=\"" + level + "\">");
			out.print(((BitmapItem) slideItem).getName());
		}
		out.println("</item>");
	}

	// Create a new DocumentBuilder instance
	public DocumentBuilder newDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println(PCE);
			e.printStackTrace();
		}
		return null;
	}
}
