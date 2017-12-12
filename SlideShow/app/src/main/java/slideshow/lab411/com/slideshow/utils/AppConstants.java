package slideshow.lab411.com.slideshow.utils;

import slideshow.lab411.com.slideshow.R;

/**
 * Created by PL_itto on 12/10/2017.
 */

public final class AppConstants {
    public static final class ShowPhoto {
        public static final String EXTRA_PHOTO_LIST = "extra_photo_list";
        public static final String EXTRA_PHOTO_POSITION = "extra_position";
        public static final String EXTRA_MODE_SHOW = "extra_show_mode";
        public static final int MODE_SLIDE = 0;
        public static final int MODE_NORMAL = 1;

    }

    public static final class PhotoGrid {
        public static final int REQUEST_PHOTO = 100;
        public static final int DEFAULT_IMAGES[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7
                , R.drawable.img8, R.drawable.img9, R.drawable.img10};
    }

    public static final class PhotoSelect {
        public static final String EXTRA_PHOTO_RESULT = "extra_photo_result";
    }

}

