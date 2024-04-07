package qualitySoftware.creator;

import qualitySoftware.slide.BitmapItem;

public class BitmapItemCreator {
    public static BitmapItem createSlideItem(int level, String imageName) {
        return new BitmapItem(level, imageName);
    }
}