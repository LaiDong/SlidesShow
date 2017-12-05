package slideshow.lab411.com.slideshow.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by PL_itto on 11/21/2017.
 */

public interface IBaseView {
    void showMessage(@StringRes int resId);

    void showMessage(@NonNull String msg);

    boolean isNetworkConnected();
}
