package qualitySoftware;

import java.io.File;

public class BitmapItemCreator {
    public static BitmapItem createSlideItem(int level, String imageName) {
        return new BitmapItem(level, imageName);
    }
}