package slideshow.lab411.com.slideshow.ui.showphoto.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.List;

import slideshow.lab411.com.slideshow.R;
import slideshow.lab411.com.slideshow.base.BaseActivity;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;
import slideshow.lab411.com.slideshow.utils.AppConstants;

/**
 * Created by PL_itto on 12/10/2017.
 */

public class ShowPhotoActivity extends BaseActivity {
    private static final String TAG = "Lab411." + ShowPhotoActivity.class.getSimpleName();

    @NonNull
    @Override
    public Fragment getFragment() {
        List<PhotoInfo> list = (List<PhotoInfo>) getIntent().getSerializableExtra(AppConstants.ShowPhoto.EXTRA_PHOTO_LIST);
        int pos = getIntent().getIntExtra(AppConstants.ShowPhoto.EXTRA_PHOTO_POSITION, -1);
        return ShowPhotoFragment.newInstance(list, pos);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
