package qualitySoftware;

public abstract class SlideItemCreator {
    public static SlideItem createSlideItem(Class<? extends SlideItem> slideItemType, int level, String content) {
        if (TextItem.class.isAssignableFrom(slideItemType)) {
            return TextItemCreator.createSlideItem(level, content);
        } else if (BitmapItem.class.isAssignableFrom(slideItemType)) {
            return BitmapItemCreator.createSlideItem(level, content);
        } else {
            return null;
        }
    }
}
