package qualitySoftware.creator;

import qualitySoftware.slide.TextItem;

public class TextItemCreator {
    public static TextItem createSlideItem(int level, String text) {
        return new TextItem(level, text);
    }
}