package slideshow.lab411.com.slideshow.base;

import android.content.Context;

/**
 * Created by PL_itto on 11/22/2017.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    private static final String TAG = "PL_itto.BasePresenter";

    //    public final IDataManager mDataManager;
    public V mView;

//    @Inject
//    public BasePresenter(IDataManager dataManager) {
//        mDataManager = dataManager;
//    }

    @Override
    public void onAttach(V view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    public V getMvpView() {
        return mView;
    }

    public boolean isViewAttached() {
        return mView == null;

    }
}
