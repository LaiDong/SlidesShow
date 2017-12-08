package slideshow.lab411.com.slideshow.ui.imagegrid;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import slideshow.lab411.com.slideshow.base.IBasePresenter;
import slideshow.lab411.com.slideshow.base.IBaseView;
import slideshow.lab411.com.slideshow.data.model.PhotoFolderInfo;

/**
 * Created by PL_itto on 12/6/2017.
 */

public interface IPhotoGridContract {
    interface IPhotoGridView extends IBaseView {
        void updateListPhoto(@NonNull PhotoFolderInfo data);

        void onSelectModeSwitch(boolean enabled);
    }

    interface IPhotoGridPresenter<V extends IPhotoGridView> extends IBasePresenter<V> {
        void loadImage(Context context);
    }
}
