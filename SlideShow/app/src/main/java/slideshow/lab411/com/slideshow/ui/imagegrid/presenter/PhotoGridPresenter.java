package slideshow.lab411.com.slideshow.ui.imagegrid.presenter;

import android.content.Context;

import slideshow.lab411.com.slideshow.base.BasePresenter;
import slideshow.lab411.com.slideshow.base.IActionCallback;
import slideshow.lab411.com.slideshow.data.model.PhotoFolderInfo;
import slideshow.lab411.com.slideshow.ui.imagegrid.IPhotoGridContract;
import slideshow.lab411.com.slideshow.utils.MediaUtils;

/**
 * Created by PL_itto on 12/6/2017.
 */

public class PhotoGridPresenter<V extends IPhotoGridContract.IPhotoGridView>
        extends BasePresenter<V> implements IPhotoGridContract.IPhotoGridPresenter<V> {
    private static final String TAG = "Lab411." + PhotoGridPresenter.class.getSimpleName();

    @Override
    public void loadImage(Context context) {
        MediaUtils.getAllPhotoFolder(context, null, mLoadPhotoCallback);
    }

    IActionCallback<PhotoFolderInfo> mLoadPhotoCallback = new IActionCallback<PhotoFolderInfo>() {

        @Override
        public void onSuccess(PhotoFolderInfo result) {
            if (mView != null && result != null) {
                mView.updateListPhoto(result);
            }
        }

        @Override
        public void onFailed(PhotoFolderInfo result) {

        }
    };
}
