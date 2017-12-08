package slideshow.lab411.com.slideshow.ui.showphoto;

import slideshow.lab411.com.slideshow.base.IBasePresenter;
import slideshow.lab411.com.slideshow.base.IBaseView;

/**
 * Created by PL_itto on 12/7/2017.
 */

public interface IShowPhotoContract {
    interface IShowPhotoView extends IBaseView{

    }

    interface IShowPhotoPresenter<V extends IShowPhotoView> extends IBasePresenter<V>{

    }
}
