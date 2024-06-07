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
 * <p>
 * Presentation maintains the slides in the presentation.
 * </p>
 * <p>
 * There is only one instance of this class.
 * </p>
 *
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class Presentation {

	private String showTitle;
	private ArrayList<Slide> showList = null;
	private int currentSlideNumber = 0;
	private SlideViewerComponent slideViewComponent = null;

	public Presentation() {
		slideViewComponent = null;
		clear();
	}

	// Constructor that sets the slide viewer component
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

	public int getSlideNumber() {
		return currentSlideNumber;
	}

	// Set the index of the current slide and update the viewer component
	public void setSlideNumber(int number) {
		currentSlideNumber = number;
		if (slideViewComponent != null) {
			slideViewComponent.update(this, getCurrentSlide());
		}
	}

	// Navigate to the previous slide unless at the beginning of the presentation
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
		}
	}

	// Navigate to the next slide unless at the end of the presentation
	public void nextSlide() {
		if (currentSlideNumber < (showList.size() - 1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Clear the presentation to prepare for a new one
	public void clear() {
		showList = new ArrayList<Slide>();
		setSlideNumber(-1);
	}

	public void append(Slide slide) {
		showList.add(slide);
	}

	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()) {
			return null;
		}
		return showList.get(number);
	}

	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	// Exit the application with the given exit code
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
