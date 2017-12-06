package slideshow.lab411.com.slideshow.data.model;

/**
 * Created by PL_itto on 12/7/2017.
 */

public class PhotoInfo {
    private int photoId;
    private String photoPath;
    //private String thumbPath;
    private int width;
    private int height;

    public PhotoInfo() {
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
