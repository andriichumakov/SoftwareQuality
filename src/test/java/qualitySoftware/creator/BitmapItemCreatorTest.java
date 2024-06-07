package qualitySoftware.creator;

import qualitySoftware.slide.BitmapItem;

public class BitmapItemCreatorTest {
    public static BitmapItem createSlideItem(int level, String imageName) {
        return new BitmapItem(level, imageName);
    }
}