package slideshow.lab411.com.slideshow.ui.imagegrid.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import slideshow.lab411.com.slideshow.base.BasePresenter;
import slideshow.lab411.com.slideshow.base.IActionCallback;
import slideshow.lab411.com.slideshow.data.model.PhotoFolderInfo;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;
import slideshow.lab411.com.slideshow.ui.imagegrid.IPhotoGridContract;
import slideshow.lab411.com.slideshow.utils.AppConstants;
import slideshow.lab411.com.slideshow.utils.MediaUtils;

/**
 * Created by PL_itto on 12/6/2017.
 */

public class PhotoGridPresenter<V extends IPhotoGridContract.IPhotoGridView>
        extends BasePresenter<V> implements IPhotoGridContract.IPhotoGridPresenter<V> {
    private static final String TAG = "Lab411." + PhotoGridPresenter.class.getSimpleName();

    @Override
    public void initDefaultPhoto() {
//        MediaUtils.getAllPhotoFolder(context, null, mLoadPhotoCallback);
        List<PhotoInfo> data = new ArrayList<>();
        for (int res : AppConstants.PhotoGrid.DEFAULT_IMAGES) {
            PhotoInfo info = new PhotoInfo();
            info.setResImage(true);
            info.setResImageId(res);
            data.add(info);
        }
        if (mView != null)
            mView.updateListPhoto(data);
    }
//
//    IActionCallback<PhotoFolderInfo> mLoadPhotoCallback = new IActionCallback<PhotoFolderInfo>() {
//
//        @Override
//        public void onSuccess(PhotoFolderInfo result) {
//            if (mView != null && result != null) {
//                mView.updateListPhoto(result);
//            }
//        }
//
//        @Override
//        public void onFailed(PhotoFolderInfo result) {
//
//        }
//    };
}
