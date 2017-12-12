package slideshow.lab411.com.slideshow.ui.imagegrid;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import slideshow.lab411.com.slideshow.base.IBasePresenter;
import slideshow.lab411.com.slideshow.base.IBaseView;
import slideshow.lab411.com.slideshow.data.model.PhotoFolderInfo;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;

/**
 * Created by PL_itto on 12/6/2017.
 */

public interface IPhotoGridContract {
    /* Main Photo Grid */
    interface IPhotoGridView extends IBaseView {
        void updateListPhoto(@NonNull List<PhotoInfo> data);

        void onSelectModeSwitch(boolean enabled);

        void showPhoto(@NonNull List<PhotoInfo> data, int position);

        void openGallery();

        void openSlideShow();
    }

    interface IPhotoGridPresenter<V extends IPhotoGridView> extends IBasePresenter<V> {
        void initDefaultPhoto();
    }

}
