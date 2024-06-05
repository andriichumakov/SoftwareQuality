package qualitySoftware.presentation;

import qualitySoftware.ui.SlideViewerComponent;
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
}