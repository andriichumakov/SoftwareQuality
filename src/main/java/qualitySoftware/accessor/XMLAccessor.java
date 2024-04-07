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
import qualitySoftware.slide.SlideItem;


/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor
{

	/** Default API to use. */
    protected static final String DEFAULT_API_TO_USE = "dom";
    
    /** namen van xml tags of attributen */
    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";
    
    /** tekst van messages */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";
    
    // get element title from the attributes
    private String getTitle(Element element, String tagName) {
		NodeList titles = element.getElementsByTagName(tagName);
		return titles.item(0).getTextContent();

	}

	public Element loadFile(String filename) throws IOException
	{
		try {
			// Create the basic document
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File(filename)); // Create a JDOM document
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

	public void loadFile(Presentation targetPres, String fileFrom) throws IOException {
		Element doc = this.loadFile(fileFrom);
		// create a presentation title from <title>
		targetPres.setTitle(this.getTitle(doc, SHOWTITLE));
		NodeList xmlSlides = doc.getElementsByTagName(SLIDE);

		// go through each <slide> tag and plug them into the presentation
		for (int slideIndex = 0; slideIndex < xmlSlides.getLength(); slideIndex++) {
			Element xmlSlide = (Element) xmlSlides.item(slideIndex);
			Slide slide = new Slide();
			this.loadSlide(slide, xmlSlide);
			targetPres.append(slide);
		}
	}

	protected void loadSlide(Slide slide, Element xmlSlide) {
		slide.setTitle(this.getTitle(xmlSlide, SLIDETITLE));
		// Recursively traverse all descendant nodes of xmlSlide
		loadSlideComponents(slide, xmlSlide.getChildNodes(), null);
	}

	protected void loadSlideComponents(Slide slide, NodeList nodeList, SlideItemDecorator lastWrapper)
	{
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
				lastEmptyWrapper = null; // clear the wrapper, since wrapper will always have a text item in it, so it will always be bound to something
			} else if ("item".equals(tagName)) {
				SlideItem item = itemFromXML(element);
				if (lastEmptyWrapper != null) {
					lastEmptyWrapper.setWrappee(item);
					slide.append(lastEmptyWrapper);
					lastEmptyWrapper = null;
				}
				else {
					slide.append(item);
				}
			}
		}
	}


	// load the presentation file
	/*
	public void loadFile(Presentation presentation, String filename) throws IOException {
		int slideNumber, itemNumber, max = 0, maxItems = 0;
		try {
			// set up file handling
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File(filename)); // Create a JDOM document
			Element doc = document.getDocumentElement();

			// set pres title
			presentation.setTitle(getTitle(doc, SHOWTITLE));

			// load all the slides
			NodeList slides = doc.getElementsByTagName(SLIDE);
			max = slides.getLength();
			for (slideNumber = 0; slideNumber < max; slideNumber++) {
				Element xmlSlide = (Element) slides.item(slideNumber);
				Slide slide = new Slide();
				this.loadSlide(slide, xmlSlide);
				presentation.append(slide);

			}
		} catch (FileNotFoundException exc) {
			throw exc;
		} catch (IOException iox) {
			System.err.println(iox.toString());
		} catch (SAXException sax) {
			System.err.println(sax.getMessage());
		} catch (ParserConfigurationException pcx) {
			System.err.println(PCE);
		}
	}
	 */

	/*
	protected void loadSlide(Slide slide, Element xmlSlide) {
		slide.setTitle(this.getTitle(xmlSlide, SLIDETITLE));

		NodeList slideComponents = xmlSlide.getChildNodes();
		SlideItemDecorator lastEmptyWrapper = null;

		for (int index = 0; index < slideComponents.getLength(); index++) {
			Node node = slideComponents.item(index);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				// some elements are not xml elements, so we should skip those
				continue;
			}
			Element item = (Element) slideComponents.item(index);
			SlideItem result = null;
			switch (item.getTagName()) {
				case "wrap":
					result = wrapperFromXML(item);
					if (result != null) {
						if (lastEmptyWrapper != null) {
							lastEmptyWrapper.setWrappee(result);
						}
						lastEmptyWrapper = (SlideItemDecorator) result;
					}
					break;
				case "item":
					result = itemFromXML(item);
					if (lastEmptyWrapper != null) {
						lastEmptyWrapper.setWrappee(result);
						lastEmptyWrapper = null;
					}
					break;
			}
			if (result != null) {
				slide.append(result);
			}
		}
        /*
		try {
			slide.setTitle(getTitle(xmlSlide, SLIDETITLE));

			NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
			int maxItems = slideItems.getLength();
			for (int itemNumber = 0; itemNumber < maxItems; itemNumber++) {
				Element item = (Element) slideItems.item(itemNumber);
				loadSlideItem(slide, item);
			}
		} catch (Exception exc) {
			throw exc;
		}
	}
	 */

	protected SlideItemDecorator wrapperFromXML(Element xmlWrapper) {
		SlideItemDecorator wrapper = null;
		NamedNodeMap wrapperAttributes = xmlWrapper.getAttributes();
		String wrapperType = wrapperAttributes.getNamedItem(KIND).getTextContent();

		Color color = Color.BLACK;
		String colorTxt = wrapperAttributes.getNamedItem("color").getTextContent();

		try {
			color = Color.decode(colorTxt);
		} catch (NumberFormatException | NullPointerException ex) {
			// Handle parsing error or null color
			ex.printStackTrace();
		}

		if (wrapperType.equals("shadow")) {
			int thickness = 1;
			String thicknessTxt = wrapperAttributes.getNamedItem("thickness").getTextContent();
			try {
				thickness = Integer.parseInt(thicknessTxt);
			} catch (NumberFormatException | NullPointerException ex) {
				// Handle parsing error or null thickness
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

	protected SlideItem itemFromXML(Element xmlItem) {
		int level = 1;
		// check if the item has specified a level
		NamedNodeMap attributes = xmlItem.getAttributes();
		String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
		if (leveltext != null) {
			// if so, try to parse it and set it as the level if it is valid
			try {
				level = Integer.parseInt(leveltext);
			} catch(NumberFormatException x) {
				System.err.println(NFE);
			}
		}
		// getting item type, checking if its text or image or invalid, creating corresponding item
		String type = attributes.getNamedItem(KIND).getTextContent();
		SlideItem slideItem = SlideItemCreator.createSlideItem(type, level, xmlItem.getTextContent());
		if (slideItem == null) {
			System.err.println(UNKNOWNTYPE);
			return null;
		}
		return slideItem;
	}

	// loads a single slide item
	protected void loadSlideItem(Slide slide, Element item) {
		// set the default level to 1
		int level = 1;

		// check if the item has specified a level
		NamedNodeMap attributes = item.getAttributes();
		String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
		if (leveltext != null) {
			// if so, try to parse it and set it as the level if it is valid
			try {
				level = Integer.parseInt(leveltext);
			} catch(NumberFormatException x) {
				System.err.println(NFE);
			}
		}

		// Check if the item is wrapped
		NodeList wrapList = item.getElementsByTagName("wrap");
		if (wrapList.getLength() > 0) {
			// If wrapped, apply decorators
			Element wrapElement = (Element) wrapList.item(0);
			String borderColor = wrapElement.getAttribute("borderColor");
			String borderWidth = wrapElement.getAttribute("borderWidth");
			String shadowColor = wrapElement.getAttribute("shadowColor");
			String shadowThickness = wrapElement.getAttribute("shadowThickness");

			SlideItem wrappedItem = null; // Initialize the wrapped item

			// Apply border decorator if borderColor and borderWidth are provided
			if (!borderColor.isEmpty() && !borderWidth.isEmpty()) {
				wrappedItem = new BorderedItemDecorator(wrappedItem, Color.decode(borderColor));
			}

			// Apply shadow decorator if shadowColor and shadowThickness are provided
			if (!shadowColor.isEmpty() && !shadowThickness.isEmpty()) {
				ShadowedItemDecorator shadowDecorator = new ShadowedItemDecorator(wrappedItem, Color.decode(shadowColor), Integer.parseInt(shadowThickness));
				// If the item is already decorated with a border, set the shadow decorator as its wrappee
				if (wrappedItem != null) {
					shadowDecorator.setWrappee(wrappedItem);
				}
				wrappedItem = shadowDecorator;
			}

			// Recursively process nested wrap elements
			if (wrappedItem != null) {
				// Get the nested item inside the wrap
				Element nestedItem = (Element) wrapElement.getElementsByTagName("item").item(0);
				// Load the nested item
				loadSlideItem(slide, nestedItem);
				// Append the wrapped item to the slide
				slide.append(wrappedItem);
			}
		} else {
			// If not wrapped, load the item normally
			String type = attributes.getNamedItem(KIND).getTextContent();
			SlideItem slideItem = SlideItemCreator.createSlideItem(type, level, item.getTextContent());
			if (slideItem == null) {
				System.err.println(UNKNOWNTYPE);
			} else {
				slide.append(slideItem);
			}
		}
	}

    /*
	protected void loadSlideItem(Slide slide, Element item) {
		// set the default level to 1
		int level = 1;

		// check if the item has specified a level
		NamedNodeMap attributes = item.getAttributes();
		String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
		if (leveltext != null) {
			// if so, try to parse it and set it as the level if it is valid
			try {
				level = Integer.parseInt(leveltext);
			} catch(NumberFormatException x) {
				System.err.println(NFE);
			}
		}
		// getting item type, checking if its text or image or invalid, creating corresponding item
		String type = attributes.getNamedItem(KIND).getTextContent();
		SlideItem slideItem = SlideItemCreator.createSlideItem(type, level, item.getTextContent());
		if (slideItem == null) {
			System.err.println(UNKNOWNTYPE);
		} else {
			slide.append(slideItem);
		}
	}
	 */

	// save the presentation in a file
	public void saveFile(Presentation presentation, String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename)); // file handler
		// heading
		out.println("<?xml version=\"1.0\"?>");
		out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
		out.println("<presentation>");
		out.print("<showtitle>");
		out.print(presentation.getTitle());
		out.println("</showtitle>");
		// slides
		for (int slideNumber=0; slideNumber<presentation.getSize(); slideNumber++) {
			Slide slide = presentation.getSlide(slideNumber);
			this.saveSlide(out, slide);
		}
		//ending
		out.println("</presentation>");
		out.close();
	}

	// save a single slide
	protected void saveSlide(PrintWriter out, Slide slide)
	{
		// heading + title
		out.println("<slide>");
		out.println("<title>" + slide.getTitle() + "</title>");
		Vector<SlideItem> slideItems = slide.getSlideItems();
		// iterate over all slide items and save them
		for (int itemNumber = 0; itemNumber<slideItems.size(); itemNumber++) {
			SlideItem slideItem = slideItems.elementAt(itemNumber);
			saveSlideItem(out, slideItem);
		}
		out.println("</slide>");
	}

	// save a single slide item
	protected void saveSlideItem(PrintWriter out, SlideItem slideItem)
	{
		// Delegate the responsibility to the slide items
		out.println(slideItem.toXML());
        /*
		// heading
		out.print("<item kind=");
		// determine item type
		if (slideItem instanceof TextItem) {
			out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
			out.print( ( (TextItem) slideItem).getText());
		} else {
			if (slideItem instanceof BitmapItem) {
				out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
				out.print( ( (BitmapItem) slideItem).getName());
			} else {
				System.out.println("Ignoring " + slideItem);
			}
		}
		out.println("</item>");

		 */
	}
}
