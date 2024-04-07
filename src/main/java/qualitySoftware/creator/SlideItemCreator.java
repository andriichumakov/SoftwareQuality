package qualitySoftware.creator;

import qualitySoftware.slide.BitmapItem;
import qualitySoftware.slide.TextItem;
import qualitySoftware.slide.SlideItem;

public abstract class SlideItemCreator {
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";

    public static SlideItem createSlideItem(Class<? extends SlideItem> slideItemType, int level, String content) {
        if (TextItem.class.isAssignableFrom(slideItemType)) {
            return TextItemCreator.createSlideItem(level, content);
        } else if (BitmapItem.class.isAssignableFrom(slideItemType)) {
            return BitmapItemCreator.createSlideItem(level, content);
        } else {
            return null;
        }
    }

    public static SlideItem createSlideItem(String slideItemType, int level, String content) {
        if (TEXT.equals(slideItemType)) {
            return TextItemCreator.createSlideItem(level, content);
        } else if (IMAGE.equals(slideItemType)) {
            return BitmapItemCreator.createSlideItem(level, content);
        } else {
            return null;
        }
    }
}
