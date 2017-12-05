package slideshow.lab411.com.slideshow.ui.imagegrid;

import slideshow.lab411.com.slideshow.base.IBasePresenter;
import slideshow.lab411.com.slideshow.base.IBaseView;

/**
 * Created by PL_itto on 12/6/2017.
 */

public interface IPhotoGridContract {
    interface IPhotoGridView extends IBaseView {

    }

    interface IPhotoGridPresenter<V extends IPhotoGridView> extends IBasePresenter<V> {

    }
}
