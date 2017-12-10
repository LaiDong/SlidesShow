package slideshow.lab411.com.slideshow.data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PL_itto on 12/7/2017.
 */

public class PhotoInfo implements Serializable {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private int photoId;
    private String photoPath;
    //private String thumbPath;
//    private int width;
//    private int height;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

//    public int getWidth() {
//        return width;
//    }
//
//    public void setWidth(int width) {
//        this.width = width;
//    }
//
//    public int getHeight() {
//        return height;
//    }
//
//    public void setHeight(int height) {
//        this.height = height;
//    }

    public String getDateTaken() {
        if (date != null) {
            return dateFormat.format(date);
        }
        return "";
    }

    public String getTimeTaken() {
        if (date != null) {
            return timeFormat.format(date);
        }
        return "";
    }
}
