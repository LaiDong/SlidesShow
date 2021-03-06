package slideshow.lab411.com.slideshow.ui.showphoto;

import android.view.MenuItem;

import slideshow.lab411.com.slideshow.base.IBasePresenter;
import slideshow.lab411.com.slideshow.base.IBaseView;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;

/**
 * Created by PL_itto on 12/7/2017.
 */

public interface IShowPhotoContract {
    interface IShowPhotoView extends IBaseView {
        void updateCurrentItem(PhotoInfo info);

        void startSlideShow();

        void stopSlideShow();
    }

    interface IShowPhotoPresenter<V extends IShowPhotoView> extends IBasePresenter<V> {

    }
}
