package slideshow.lab411.com.slideshow.ui.selectphoto.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import slideshow.lab411.com.slideshow.base.BaseActivity;
import slideshow.lab411.com.slideshow.data.model.PhotoFolderInfo;
import slideshow.lab411.com.slideshow.ui.selectphoto.IPhotoSelectContract.IPhotoSelectView;

/**
 * Created by PL_itto on 12/12/2017.
 */

public class PhotoSelectActivity extends BaseActivity{

    @NonNull
    @Override
    public Fragment getFragment() {
        return new PhotoSelectFragment();
    }
}
