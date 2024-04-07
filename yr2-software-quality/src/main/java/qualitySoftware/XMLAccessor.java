package qualitySoftware;

import java.io.*;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor {

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

	// load the presentation file
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

	protected void loadSlide(Slide slide, Element xmlSlide) {
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
		// getting item type, checking if its text or image or invalid, creating corresponding item
		String type = attributes.getNamedItem(KIND).getTextContent();
		if (TEXT.equals(type)) {
			slide.append(new TextItem(level, item.getTextContent()));
		} else if (IMAGE.equals(type)) {
			slide.append(new BitmapItem(level, item.getTextContent()));
		} else {
			System.err.println(UNKNOWNTYPE);
		}
	}

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
		Vector<BaseSlideItem> slideItems = slide.getSlideItems();
		// iterate over all slide items and save them
		for (int itemNumber = 0; itemNumber<slideItems.size(); itemNumber++) {
			BaseSlideItem slideItem = (BaseSlideItem) slideItems.elementAt(itemNumber);
			saveSlideItem(out, slideItem);
		}
		out.println("</slide>");
	}

	// save a single slide item
	protected void saveSlideItem(PrintWriter out, SlideItem slideItem)
	{
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
