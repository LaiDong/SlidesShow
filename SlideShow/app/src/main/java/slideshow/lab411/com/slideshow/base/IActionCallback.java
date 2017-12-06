package slideshow.lab411.com.slideshow.base;

/**
 * Created by PL_itto on 12/7/2017.
 */

public interface IActionCallback<V extends Object> {
    void onSuccess(V result);

    void onFailed(V result);
}
