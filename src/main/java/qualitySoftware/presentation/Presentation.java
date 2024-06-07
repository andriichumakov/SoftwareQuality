package qualitySoftware.presentation;

import qualitySoftware.accessor.Accessor;
import qualitySoftware.accessor.XMLAccessor;
import qualitySoftware.command.FrameCommand;
import qualitySoftware.ui.SlideViewerComponent;
import qualitySoftware.ui.SlideViewerFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * <p>Presentation maintains the slides in the presentation.</p>
 * <p>There is only instance of this class.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
	private String showTitle; // title of the presentation
	private ArrayList<Slide> showList = null; // an ArrayList with Slides
	private int currentSlideNumber = 0; // the slidenummer of the current Slide
	private SlideViewerComponent slideViewComponent = null; // the viewcomponent of the Slides


	public Presentation() {
		slideViewComponent = null;
		clear();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
		clear();
	}

	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	// give the number of the current slide
	public int getSlideNumber() {
		return currentSlideNumber;
	}

	// change the current slide number and signal it to the window
	public void setSlideNumber(int number) {
		currentSlideNumber = number;
		if (slideViewComponent != null) {
			slideViewComponent.update(this, getCurrentSlide());
		}
	}

	// go to the previous slide unless your at the beginning of the presentation
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
	    }
	}

	// go to the next slide unless your at the end of the presentation.
	public void nextSlide() {
		if (currentSlideNumber < (showList.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Delete the presentation to be ready for the next one.
	public void clear() {
		showList = new ArrayList<Slide>();
		setSlideNumber(-1);
	}

	// Add a slide to the presentation
	public void append(Slide slide) {
		showList.add(slide);
	}

	// Get a slide with a certain slidenumber
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
			return (Slide)showList.get(number);
	}

	// Give the current slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}

	public void openFile(SlideViewerFrame frame) {
		this.clear();
		XMLAccessor xmlAccessor = new XMLAccessor();
		String targetFile = frame.getStrDialogInput(new String(FrameCommand.FILENAME));
		if (targetFile.isEmpty()) { // if no file is specified, open the test file (test.xml)
			targetFile = FrameCommand.TESTFILE;
		}
		try {
			xmlAccessor.loadFile(this, targetFile);
			this.setSlideNumber(0);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(frame, FrameCommand.IOEX + exc,
					FrameCommand.LOADERR, JOptionPane.ERROR_MESSAGE);
		}
		frame.repaint();
		System.out.println("Opened File: " + targetFile);
	}

	public void gotoSlide(SlideViewerFrame frame) {
		String pageNumberStr = JOptionPane.showInputDialog((Object) FrameCommand.PAGENR);
		int pageNumber = Integer.parseInt(pageNumberStr);
		this.setSlideNumber(pageNumber - 1);
	}

	public void newFile(SlideViewerFrame frame) {
		this.clear();
		frame.repaint();
	}

	public void saveFile(SlideViewerFrame frame) {
		Accessor xmlAccessor = new XMLAccessor();
		String targetFile = JOptionPane.showInputDialog((Object) FrameCommand.FILENAME);
		if (targetFile == null || targetFile.trim().isEmpty()) { // if no file is specified, open the test file (test.xml)
			targetFile = FrameCommand.SAVEFILE;
		}
		try {
			xmlAccessor.saveFile(this, targetFile);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(frame, FrameCommand.IOEX + exc, FrameCommand.SAVEERR, JOptionPane.ERROR_MESSAGE);
		}
		System.out.println("Saved in: " + targetFile);
	}
}
