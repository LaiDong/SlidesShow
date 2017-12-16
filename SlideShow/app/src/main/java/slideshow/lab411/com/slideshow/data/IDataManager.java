package slideshow.lab411.com.slideshow.data;

import java.util.List;

import slideshow.lab411.com.slideshow.base.IActionCallback;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;

/**
 * Created by PL_itto on 12/16/2017.
 */

public interface IDataManager {
    void loadPhoto(IActionCallback callback);

    void savedAddedPhotos(List<PhotoInfo> list,boolean clearOldData);

    void deleteAllSavedPhoto();
}
